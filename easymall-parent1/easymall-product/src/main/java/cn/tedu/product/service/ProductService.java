package cn.tedu.product.service;

import cn.tedu.product.mapper.ProductMapper;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import com.jt.common.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    //操作持久层
    @Autowired
    private ProductMapper pm;
    public EasyUIResult pageQuery(Integer page, Integer rows) {

        //最终返回一个封装好total rows的easyUIResult对象
        EasyUIResult result=new EasyUIResult();
        int total=pm.selectProductCount();//详细描述增删查改，和条件
        result.setTotal(total);
        //分页查询 select * from t_product limit start,rows;
        int start=(page-1)*rows;
        List<Product> pList=pm.selectProductListByPage(start,rows);
        result.setRows(pList);
        //业务层执行完毕
        return result;

    }
    @Autowired
    private JedisCluster cluster;
    public Product queryOneyProduct(String productId) {
        /*
            1.生成一个对应该商品的查询key值,每一个
            key值都对应一个唯一的商品,操作redis缓存
            业务逻辑+唯一值(productId)
            2.判断缓存是否命中
                2.1命中直接用 不用走持久层
                2.2未命中,从数据库获取,存储在缓存,后续使用
         */
        //判断锁是否存在
        String lockUpdateKey=productId+".lock";
        if(cluster.exists(lockUpdateKey)){
            //不再继续执行缓存逻辑,直接访问数据库
            return pm.selectProductById(productId);
        }
        String key="product_query_"+productId;
        try{
            if(cluster.exists(key)){
                //key对应的商品数据存在与redis 命中
                //直接使用 转化json-product
                String pJson=cluster.get(key);
                /*
                String :字符串json格式数据
                Class:转化成的类反射对象
                 */
                return MapperUtil.MP.readValue(pJson,Product.class);
            }else{
                Product product = pm.selectProductById(productId);
                //存放到缓存一份
                //json 转化
                String pJson = MapperUtil.MP.writeValueAsString(product);
                cluster.setex(key,60*60*2,pJson);
                return product;
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("缓存有问题,数据未命中");
            return null;//出异常返回错误数据
        }

    }

    public void addProduct(Product product) {
        //最终 insert 数据库 productId
        //UUID 同一台服务器产生的uuid每次必不相同
        //分布式集群服务器产生的uuid可能相同，可能性可以忽略
        // 生成key 转化Json 直接存储
        product.setProductId(UUID.randomUUID().toString());
        //TODO add to redis
        //jedis.set("id",product)
        pm.insertProduct(product);
        //发送个消息
    }

    public void updateProduct(Product product) {
        //生成操作的商品对应的key
        String lockUpdateKey=product.getProductId()+".lock";
        String key="product_query_"+product.getProductId();
        //添加一个锁 普通的key值
        cluster.set(lockUpdateKey,"");
        //删除商品查询的缓存
        cluster.del(key);
        pm.updateProductById(product);
        cluster.del(lockUpdateKey);
    }
}
