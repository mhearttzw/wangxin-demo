package com.wangxin.springboot.controller;

import com.wangxin.springboot.model.Product;
import com.wangxin.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有产品列表
     */
    @RequestMapping(value = "/product/all")
    public String selectProductAll(Model model) {
        model.addAttribute("productList", userService.selectProductAll());
        return "index";
    }

    /**
     * 根据名称查询产品
     */
    @RequestMapping(value = "/product/search", method = RequestMethod.GET)
    public String selectProductByName(Model model,
                                      @RequestParam("name") String name) {
        System.out.println("name:" + name);
        List<Product> productList = userService.selectProductByName(name);
        model.addAttribute("productList", productList);
        return "index";
    }

    /**
     * 产品编辑页展示
     */
    @RequestMapping(value = "/product/edit/show", method = RequestMethod.GET)
    public String showProductById(Model model,
                           @RequestParam("id") int id) {
        List<Product> productList = userService.selectProductById(id);
        model.addAttribute("productList", productList);
        return "edit";
    }

    /**
     * 产品编辑提交
     */
    @RequestMapping(value = "/product/edit", method = RequestMethod.POST)
    public String updateProductById(Model model,
                                    @ModelAttribute Product product) {
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
     * 产品增加页展示
     */
    @RequestMapping(value = "/product/insert/show", method = RequestMethod.GET)
    public String insertProduct() {
        return "insert";
    }

    /**
     * 增加产品
     */
    @RequestMapping(value = "/product/insert", method = RequestMethod.POST)
    public String showInsertProduct(Model model,
                                @ModelAttribute Product product) {
        int result = userService.insertProduct(product);
        if (result == 1) {
            List<Product> productList = userService.selectProductAll();
            model.addAttribute("productList", productList);
            return "index";
        } else {
            return "error11";
        }
    }

    /**
     * 删除产品
     */
    @RequestMapping(value = "/product/delete", method = RequestMethod.GET)
    public String deleteProductById(Model model,
                                    @RequestParam("id") int id) {
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

}
