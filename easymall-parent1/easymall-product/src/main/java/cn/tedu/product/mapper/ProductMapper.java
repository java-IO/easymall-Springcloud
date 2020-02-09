package cn.tedu.product.mapper;

import com.jt.common.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int selectProductCount();
    //mybatis 持久层方法，具备2个以上参数时
    //处理编译，赋值参数时，找不到对应关系
    //select * from t_product #{start},#{rows}
    List<Product> selectProductListByPage
    (@Param("start")int start, @Param("rows")Integer rows);

    Product selectProductById(String productId);

    void insertProduct(Product product);

    void updateProductById(Product product);
}
