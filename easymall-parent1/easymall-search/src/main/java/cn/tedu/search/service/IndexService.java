package cn.tedu.search.service;

import cn.tedu.search.mapper.IndexMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService {
    //注入client
    @Autowired
    private TransportClient client;
    @Autowired
    private IndexMapper im;
    public void createIndex(String indexName) throws Exception {
        //调用client的方法
        //client.admin.indices().prepareCreate
        //判断索引是否存在,不存在则创建,存在停止后续逻辑
        IndicesExistsResponse response = client.admin().indices().prepareExists(indexName).get();
        if(response.isExists()){
            //创建过了,不再创建
            return;
        }
        //创建索引
        client.admin().indices().prepareCreate(indexName).get();
        //写入数据 读取数据库List<Porudct> 循环将所有json
        //作为document对象数据存储到该索引,type=product,id=productId
        List<Product> pList=im.seletAllProduct();
        for (Product p:pList){
            //每个商品都转化为json
            String json = MapperUtil.MP.writeValueAsString(p);
            client.prepareIndex(indexName,
                    "product",p.getProductId())
                    .setSource(json).get();
        }
    }

    public List<Product> search(String text, Integer rows, Integer page) {
        //封装一个match query查询对象
        MatchQueryBuilder query =
                QueryBuilders.matchQuery(
                        "productName", text);
        //client生成request,添加相关请求参数 query start rows
        int start=(page-1)*rows;
        SearchRequestBuilder request =
                client.prepareSearch("easymall");
        request.setQuery(query).setFrom(start).setSize(rows);
        SearchResponse response = request.get();
        //遍历response中数据,解析成返回值List<Product>
        List<Product> pList=new ArrayList<>();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit:hits){
            //获取json就可以直接解析
            String pJson = hit.getSourceAsString();
            //pjson={"productName":"123","productCategory":"手机","":""}
            try{
                Product p = MapperUtil.
                        MP.readValue(pJson, Product.class);
                pList.add(p);
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return pList;
    }
}
