package com.wangxin.springboot.controller;

import com.wangxin.springboot.common.constant.UserResult;
import com.wangxin.springboot.common.constant.UserResultConstant;
import com.wangxin.springboot.model.Product;
import com.wangxin.springboot.other.Merchandise;
import com.wangxin.springboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2  //启动swagger注解
//定义名称，如果没有定义，则默认显示类名
@Api(value = "欢迎接口", description = "演示swagger的control类", tags = "swagger test controller")
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    UserService userService;

    @Value("${x}") //动态注入外部配置文件的属性值，用#则是SpEL表达式对应的内容
    private String x;

    @Value("${content}")
    private String content;

    @Autowired
    private Merchandise merchandise;

    @ApiOperation(value = "欢迎方法，返回字符串！") //描述方法的作用
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String say() {
        return "Hello spring boot!" + merchandise.getCar();
    }

    @ApiOperation(value = "查询产品总数！")
    @RequestMapping(value = "/product/all", method = RequestMethod.GET)
    public UserResult selectProductAll() {
        UserResult urs;
        List<Product> productList = userService.selectProductAll();
        urs = new UserResult(UserResultConstant.SUCCESS, productList);
        return urs;
    }
}
