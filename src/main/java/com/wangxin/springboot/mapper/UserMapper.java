package com.wangxin.springboot.mapper;

import com.wangxin.springboot.model.BorrowOrder;
import com.wangxin.springboot.model.PayOrderNotify;
import com.wangxin.springboot.model.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    List<Product> selectProductAll();

    /**
     * 根据名称查询产品列表
     * @param name
     * @return
     */
    List<Product> selectProductByName(@Param("name") String name);

    /**
     * 根据id查询产品
     */
    List<Product> selectProductById(@Param("id") int id);

    /**
     * 根据id修改产品
     */
    int updateProductById(Product product);

    /**
     * 增加产品
     */
    int insertProduct(Product product);

    /**
     * 删除产品
     */
    int deleteProduct(@Param("id") int id);

    /**
     * 插入借款订单信息
     */
    int insertBorrowOrder(BorrowOrder borrowOrder);

    /**
     * 更新借款订单信息
     */
    int updateBorrowOrder(@Param("state") int state);

    /**
     * 插入支付订单信息
     */
    int insertPayOrder(PayOrderNotify payOrderNotify);
}
