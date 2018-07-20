package com.wangxin.springboot.service;

import com.wangxin.springboot.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    List<Product> selectProductAll();

    /**
     * 根据名称查询产品列表
     */
    List<Product> selectProductByName(String name);

    /**
     * 根据id查询产品
     */
    List<Product> selectProductById(int id);

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
}
