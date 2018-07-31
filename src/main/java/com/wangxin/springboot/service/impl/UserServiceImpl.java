package com.wangxin.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.wangxin.springboot.common.annotation.Log;
import com.wangxin.springboot.common.utils.LogAnnotationWrapperUtil;
import com.wangxin.springboot.mapper.UserMapper;
import com.wangxin.springboot.model.BorrowOrder;
import com.wangxin.springboot.model.PayOrderNotify;
import com.wangxin.springboot.model.Product;
import com.wangxin.springboot.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    protected static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    // 使用@Autowired注释进行byType注入，如果需要byName（byName就是通过id去标识）注入，增加@Qualifier注释。
    // 一般在候选Bean数目不为1时应该加@Qualifier注释。
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate srt;

    private final String key = "allProducts:";

    @Log(logStr = "aop日志测试！")
    @Override
    public List<Product> selectProductAll() throws NotFoundException {
        // 从缓存中获取城市信息
        // String key = "allProducts:";
        //ValueOperations<String, List<Product>> operations = redisTemplate.opsForValue();
        ValueOperations<String, String> srtOperation = srt.opsForValue();

        // 缓存存在
        boolean hasKey = srt.hasKey(key);
        if (hasKey) {
            // json字符串解析成对象list
            List<Product> productList = JSON.parseArray(srtOperation.get(key), Product.class);
            logger.info("从缓存中读取数据：" + JSON.toJSONString(productList));
            return productList;
        }

        // 从数据库获取信息
        List<Product> productList = userMapper.selectProductAll();

        // 插入缓存
        //operations.set(key, productList);
        srtOperation.set(key, JSON.toJSONString(productList));
        logger.info("产品列表缓存：" + JSON.toJSONString(productList));
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

    @Override
    public int updateProductById(Product product) throws NotFoundException {
        int flag = userMapper.updateProductById(product);
        if (flag==1 && srt.hasKey(key)) {
            srt.delete(key);
            logger.info("删除产品列表缓存！");
        }
        return flag;
    }

    @Override
    public int insertProduct(Product product) throws NotFoundException {
        int flag = userMapper.insertProduct(product);
        // String key = "产品列表缓存：";
        if (flag==1 && srt.hasKey(key)) {
            srt.delete(key);
            logger.info("删除产品列表缓存！");
        }
        return flag;
    }

    @Override
    public int deleteProduct(int id) throws NotFoundException {
        int flag = userMapper.deleteProduct(id);
        // String key = "产品列表缓存：";
        if (flag==1 && srt.hasKey(key)) {
            srt.delete(key);
            logger.info("删除产品列表缓存！");
        }
        return flag;
    }

    @Override
    public int borrowProduct(BorrowOrder borrowOrder) throws NotFoundException {
        int flag = userMapper.insertBorrowOrder(borrowOrder);
        if (flag == 1) {
            logger.info("产品订单创建成功");
        }
        return flag;
    }

    @Log(logStr = "完成订单支付")
    @Override
    public int payBorrowOrder(PayOrderNotify payOrderNotify) {
        // TODO 这里用插入支付记录来替代真实环境中的支付场景
        int flag = userMapper.selectPayOrderByBorrowOrderUuid(payOrderNotify.getBorrowOrderUuid());
        if (flag == 0) {
            userMapper.insertPayOrder(payOrderNotify);
            userMapper.updateBorrowOrder(payOrderNotify.getBorrowOrderUuid(), 1);
            flag = 1;
        }
        return flag;
    }

}
