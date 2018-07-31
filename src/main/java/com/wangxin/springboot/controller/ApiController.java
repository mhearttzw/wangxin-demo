package com.wangxin.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.wangxin.springboot.common.annotation.Log;
import com.wangxin.springboot.common.constant.UserResult;
import com.wangxin.springboot.common.constant.UserResultConstant;
import com.wangxin.springboot.common.utils.CommonUtil;
import com.wangxin.springboot.common.utils.LogAnnotationWrapperUtil;
import com.wangxin.springboot.model.BorrowOrder;
import com.wangxin.springboot.model.PayOrderNotify;
import com.wangxin.springboot.model.Product;
import com.wangxin.springboot.other.Merchandise;
import com.wangxin.springboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2  //启动swagger注解
//定义名称，如果没有定义，则默认显示类名
@Api(value = "欢迎接口", description = "演示swagger的control类", tags = "swagger test controller")
@RestController
@RequestMapping("/api")
public class ApiController {

    protected static Logger logger = LoggerFactory.getLogger(ApiOperation.class);

    @Autowired
    UserService userService;

    @Value("${x}") //动态注入外部配置文件的属性值，用#则是SpEL表达式对应的内容
    private String x;

    @Value("${content}")
    private String content;

    @Autowired
    private Merchandise merchandise;

    @Log(logStr = "aop日志测试！")
    @ApiOperation(value = "欢迎方法，返回字符串！") //描述方法的作用
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String say() throws NotFoundException {
        logger.info("这是api接口的say方法！");
        return "Hello spring boot!" + merchandise.getCar();
    }

    @ApiOperation(value = "查询产品总数！")
    @RequestMapping(value = "/product/show/all", method = RequestMethod.GET)
    public UserResult selectProductAll() throws NotFoundException {
        UserResult urs;
        List<Product> productList = userService.selectProductAll();
        urs = new UserResult(UserResultConstant.SUCCESS, productList);
        return urs;
    }

    @ApiOperation(value = "根据名称模糊查询产品详情")
    @RequestMapping(value = "/product/search/{name}", method = RequestMethod.GET)
    public UserResult selectProductByName(@PathVariable("name") String name) {
        UserResult urs;
        List<Product> productList = userService.selectProductByName(name);
        urs = new UserResult(UserResultConstant.SUCCESS, productList);
        return urs;
    }

    @ApiOperation(value = "添加产品")
    @RequestMapping(value = "/product/insert", method = RequestMethod.POST)
    public UserResult showInsertProduct(@RequestParam("name") String name,
                                        @RequestParam("interestRate") double interestRate,
                                        @RequestParam("investmentHorizon") int investmentHorizon,
                                        @RequestParam("paybackMethod") int paybackMethod) throws NotFoundException {
        UserResult urs;
        Product product = new Product();
        product.setName(name);
        product.setInterestRate(interestRate);
        product.setInvestmentHorizon(investmentHorizon);
        product.setPaybackMethod(paybackMethod);
        int result = userService.insertProduct(product);
        if (result == 1) {
            urs = new UserResult(UserResultConstant.SUCCESS, "新增产品成功");
        } else {
            urs = new UserResult(UserResultConstant.FAILED, "新增产品失败");
        }
        return urs;
    }

    @ApiOperation(value = "修改产品信息")
    @RequestMapping(value = "/product/edit", method = RequestMethod.POST)
    public UserResult updateProductById(@RequestParam("id") int id, @RequestParam("name") String name,
                                        @RequestParam("interestRate") double interestRate,
                                        @RequestParam("investmentHorizon") int investmentHorizon,
                                        @RequestParam("paybackMethod") int paybackMethod) throws NotFoundException {
        UserResult urs;
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setInterestRate(interestRate);
        product.setInvestmentHorizon(investmentHorizon);
        product.setPaybackMethod(paybackMethod);
        int result = userService.updateProductById(product);
        if (result == 1) {
            urs = new UserResult(UserResultConstant.SUCCESS, "更新产品成功");
        } else {
            urs = new UserResult(UserResultConstant.FAILED, "更新产品失败");
        }
        return urs;
    }

    @ApiOperation("产品销售接口，负责生成订单")
    @RequestMapping(value = "/product/borrow", method = RequestMethod.POST)
    public UserResult borrowProductById(@RequestParam("productId") int productId,
                                        @RequestParam("name") String name,
                                        @RequestParam("interestRate") double interestRate,
                                        @RequestParam("investmentHorizon") int investmentHorizon,
                                        @RequestParam("paybackMethod") int paybackMethod,
                                        @RequestParam("borrowAmount") double borrowAmount) throws NotFoundException {
        UserResult urs;
        BorrowOrder borrowOrder = new BorrowOrder();
        String borrowOrderUuid = CommonUtil.getUUID();
        borrowOrder.setBorrowOrderUuid(borrowOrderUuid);
        borrowOrder.setBorrowAmount(borrowAmount);
        borrowOrder.setProductId(productId);
        borrowOrder.setUserUuid("ad");
        borrowOrder.setState(0);
        int flag = userService.borrowProduct(borrowOrder);
        JSONObject result = new JSONObject();
        if (flag == 1) {
            result.put("order_uuid", borrowOrderUuid);
            urs = new UserResult(UserResultConstant.SUCCESS, result);
        } else {
            urs = new UserResult(UserResultConstant.FAILED, "订单已创建");
        }
        return urs;
    }

    @ApiOperation("订单支付接口")
    @RequestMapping(value = "/product/order/pay", method = RequestMethod.POST)
    public UserResult payBorrowOrder(@RequestParam("borrowOrderUuid") String borrowOrderUuid,
                                     @RequestParam("borrowAmount") double borrowAmount,
                                     @RequestParam("productId") int productId) {
        UserResult urs;
        PayOrderNotify payOrderNotify = new PayOrderNotify();
        String payOrderUuid = CommonUtil.getUUID();
        payOrderNotify.setPayOrderUuid(payOrderUuid);
        payOrderNotify.setBorrowAmount(borrowAmount);
        payOrderNotify.setBorrowOrderUuid(borrowOrderUuid);
        payOrderNotify.setProductId(productId);
        payOrderNotify.setUserUuid("ad");
        int flag = userService.payBorrowOrder(payOrderNotify);
        if (flag!=0 || flag != 1) {
            urs = new UserResult(UserResultConstant.FAILED, "支付失败！");
        } else {
            urs = new UserResult(UserResultConstant.SUCCESS, "支付成功！");
        }
        return urs;
    }

}
