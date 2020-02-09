package cn.tedu.cart.mapper;

import com.jt.common.pojo.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> selectCartsByUserId(String userId);

    Cart selectExistCartByUserAndProduct(Cart cart);

    void updateCartNum(Cart cart);

    void insertCart(Cart cart);

    void deleteCart(Cart cart);
}
