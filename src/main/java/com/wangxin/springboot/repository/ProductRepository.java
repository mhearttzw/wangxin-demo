package com.wangxin.springboot.repository;

import com.wangxin.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    public List<Product> findByName(String name);
}
