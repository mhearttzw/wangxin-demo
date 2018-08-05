package com.wangxin.springboot.controller;

import com.wangxin.springboot.common.annotation.Log;
import com.wangxin.springboot.common.constant.UserResult;
import com.wangxin.springboot.common.utils.CommonUtil;
import com.wangxin.springboot.model.BorrowOrder;
import com.wangxin.springboot.model.PayOrderNotify;
import com.wangxin.springboot.model.Product;
import com.wangxin.springboot.repository.ProductRepository;
import com.wangxin.springboot.service.UserService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    /**
     * 查询所有产品列表
     */
    @Log(logStr = "查询所有产品列表")
    @RequestMapping(value = "/product/all")
    public String selectProductAll(Model model) throws NotFoundException {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        return "index";
    }

    /**
     * 根据名称查询产品
     */
    @Log(logStr = "根据名称查询产品")
    @RequestMapping(value = "/product/search", method = RequestMethod.GET)
    public String selectProductByName(Model model,
                                      @RequestParam("name") String name) {
        List<Product> productList = productRepository.findByName(name);
        model.addAttribute("productList", productList);
        return "index";
    }

    /**
     * 产品编辑页展示
     */
    @Log(logStr = "产品编辑页展示")
    @RequestMapping(value = "/product/edit/show", method = RequestMethod.GET)
    public String showProductById(Model model,
                           @RequestParam("id") int id) {
        List<Product> productList = productRepository.findById(id);
        model.addAttribute("productList", productList);
        return "edit";
    }

    /**
     * 产品编辑提交
     */
    @Log(logStr = "产品编辑提交")
    @RequestMapping(value = "/product/edit", method = RequestMethod.POST)
    public String updateProductById(Model model,
                                    @ModelAttribute Product product) throws NotFoundException {
        int result = userService.updateProductById(product);
        if (result == 1) {
            List<Product> productList = userService.selectProductAll();
            model.addAttribute("productList", productList);
            return "index";
        } else {
            System.out.println("balabala!");
            return "error11";
        }
    }

    /**
     * 产品出售页展示
     */
    @RequestMapping(value = "/product/borrow/show", method = RequestMethod.GET)
    public String borrowProduct(Model model,
                                @RequestParam("id") int id) {
        List<Product> productList = userService.selectProductById(id);
        model.addAttribute("productList", productList);
        return "borrow";
    }


    /**
     * 产品增加页展示
     */
    @RequestMapping(value = "/product/insert/show", method = RequestMethod.GET)
    public String insertProduct() {
        return "insert";
    }

    /**
     * 增加产品
     */
    @Log(logStr = "增加产品")
    @RequestMapping(value = "/product/insert", method = RequestMethod.POST)
    public String showInsertProduct(Model model,
                                @ModelAttribute Product product) throws NotFoundException {
        int result = userService.insertProduct(product);
        if (result == 1) {
            List<Product> productList = userService.selectProductAll();
            model.addAttribute("productList", productList);
            return "index";
        } else {
            return "index";
        }
    }

    /**
     * 删除产品
     */
    @RequestMapping(value = "/product/delete", method = RequestMethod.GET)
    public String deleteProductById(Model model,
                                    @RequestParam("id") int id) throws NotFoundException {
        int result = userService.deleteProduct(id);
        if (result == 1) {
            List<Product> productList = userService.selectProductAll();
            model.addAttribute("productList", productList);
            return "index";
        } else {
            System.out.println("删除失败！");
            return "error11";
        }
    }

    @ApiOperation("产品销售接口，负责生成订单")
    @RequestMapping(value = "/product/order/pay/show", method = RequestMethod.POST)
    public String borrowProductById(Model model,
                                    @Valid BorrowOrder borrowOrder,
                                    BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return "error11";
        }
        String borrowOrderUuid = CommonUtil.getUUID();
        borrowOrder.setBorrowOrderUuid(borrowOrderUuid);
        borrowOrder.setBorrowAmount(borrowOrder.getBorrowAmount());
        borrowOrder.setProductId(borrowOrder.getProductId());
        borrowOrder.setUserUuid("ad");
        borrowOrder.setState(0);
        int flag = userService.borrowProduct(borrowOrder);
        if (flag == 1) {
            model.addAttribute("borrowOrderInfo", borrowOrder);
            return "pay-borrow-order";
        }
        return "error11";
    }

    @ApiOperation("订单支付接口")
    @RequestMapping(value = "/product/order/pay", method = RequestMethod.POST)
    public String payBorrowOrder(Model model,
                                 @RequestParam("borrowOrderUuid") String borrowOrderUuid,
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
            model.addAttribute("payOrderInfo", "支付成功！");
        } else {
            model.addAttribute("payOrderInfo", "支付失败！");
        }
        return "pay-after";
    }

}
