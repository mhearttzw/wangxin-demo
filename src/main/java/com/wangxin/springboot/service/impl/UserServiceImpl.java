package com.wangxin.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.wangxin.springboot.common.annotation.Log;
import com.wangxin.springboot.common.util.LogAnnotationWrapperUtil;
import com.wangxin.springboot.mapper.UserMapper;
import com.wangxin.springboot.model.Product;
import com.wangxin.springboot.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 使用@Autowired注释进行byType注入，如果需要byName（byName就是通过id去标识）注入，增加@Qualifier注释。
    // 一般在候选Bean数目不为1时应该加@Qualifier注释。
    @Autowired
    private RedisTemplate redisTemplate;

    @Log(logStr = "aop日志测试！")
    @Override
    public List<Product> selectProductAll() throws NotFoundException {
        // 从缓存中获取城市信息
        String key = "allProducts:";
        ValueOperations<String, List<Product>> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            List<Product> productList = operations.get(key);
            LogAnnotationWrapperUtil.get().setLog("从缓存中读取数据：" + JSON.toJSONString(productList));
            return productList;
        }

        // 从数据库获取信息
        List<Product> productList = userMapper.selectProductAll();

        // 插入缓存
        operations.set(key, productList);
        LogAnnotationWrapperUtil.get().setLog("产品列表缓存：" + JSON.toJSONString(productList));
        return productList;
    }

    @Override
    public List<Product> selectProductByName(String name) {
        return userMapper.selectProductByName(name);
    }

    @Override
    public List<Product> selectProductById(int id) {
        return userMapper.selectProductById(id);
    }

    @Log(logStr = "更新产品信息")
    @Override
    public int updateProductById(Product product) throws NotFoundException {
        int flag = userMapper.updateProductById(product);
        String key = "产品列表缓存：";
        if (flag==1 && redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
            LogAnnotationWrapperUtil.get().setLog("删除产品列表缓存！");
        }
        return flag;
    }

    @Log(logStr = "添加一个产品")
    @Override
    public int insertProduct(Product product) throws NotFoundException {
        int flag = userMapper.insertProduct(product);
        String key = "产品列表缓存：";
        if (flag==1 && redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
            LogAnnotationWrapperUtil.get().setLog("删除产品列表缓存！");
        }
        return flag;
    }

    @Log(logStr = "删除一个产品")
    @Override
    public int deleteProduct(int id) throws NotFoundException {
        int flag = userMapper.deleteProduct(id);
        String key = "产品列表缓存：";
        if (flag==1 && redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
            LogAnnotationWrapperUtil.get().setLog("删除产品列表缓存！");
        }
        return flag;
    }

}
