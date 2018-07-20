package com.wangxin.springboot.service.impl;

import com.wangxin.springboot.common.annotation.Log;
import com.wangxin.springboot.mapper.UserMapper;
import com.wangxin.springboot.model.Product;
import com.wangxin.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Log(logStr = "aop日志测试！")
    @Override
    public List<Product> selectProductAll() {
        return userMapper.selectProductAll();
    }

    @Override
    public List<Product> selectProductByName(String name) {
        return userMapper.selectProductByName(name);
    }

    @Override
    public List<Product> selectProductById(int id) {
        return userMapper.selectProductById(id);
    }

    @Override
    public int updateProductById(Product product) {
        return userMapper.updateProductById(product);
    }

    @Log(logStr = "添加一个产品")
    @Override
    public int insertProduct(Product product) {
        return userMapper.insertProduct(product);
    }

    @Override
    public int deleteProduct(int id) {
        return userMapper.deleteProduct(id);
    }

}
