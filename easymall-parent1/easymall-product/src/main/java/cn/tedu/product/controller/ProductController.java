package cn.tedu.product.controller;

import cn.tedu.product.service.ProductService;
import com.jt.common.pojo.Product;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("product/manage")
public class ProductController {
    @Autowired
    private ProductService ps;
    //进行商品分页查询功能
    @RequestMapping("/pageManage")
    public EasyUIResult pageQuery(HttpServletRequest req,Integer page, Integer rows){

        //EasyUIResult data.total data.rows{"total":"","rows"}
        //total 表示商品总数量 select count(*) from t_product
        //rows List类型，商品分页结果 List<Product> page=1 rows=5
        return ps.pageQuery(page,rows);
    }
    //单个商品查询
    @RequestMapping(value="item/{productId}",method= RequestMethod.DELETE)//RESTful REST风格
    public Product queryOneProduct(@PathVariable String productId){
        return ps.queryOneyProduct(productId);
    }
    //新增商品对象数据
    @RequestMapping("save")
    public SysResult addProduct(Product product){
        //调用业务层新增返回数据 一律使用trycatch判断成功还是失败
        try{
            ps.addProduct(product);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            //返回失败数据
            return SysResult.build(201,"新增失败",null);
        }
    }
    //商品数据修改
    @RequestMapping("update")
    public SysResult updateProduct(Product product){
        try{
            ps.updateProduct(product);//product具备一个完整的结构 productId
            //返回成功信息
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"失败",null);
        }
    }
}
