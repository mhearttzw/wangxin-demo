package com.wangxin.springboot.service;

import com.wangxin.springboot.model.Product;
import javassist.NotFoundException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    List<Product> selectProductAll() throws NotFoundException;

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
    int updateProductById(Product product) throws NotFoundException;

    /**
     * 增加产品
     */
    int insertProduct(Product product) throws NotFoundException;

    /**
     * 删除产品
     */
    int deleteProduct(@Param("id") int id) throws NotFoundException;
}
