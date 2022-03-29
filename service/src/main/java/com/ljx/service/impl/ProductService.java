package com.ljx.service.impl;

import com.github.pagehelper.PageHelper;
import com.ljx.dao.*;
import com.ljx.domain.Product;
import com.ljx.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductDao dao;

    @Override
    public List<Product> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page, size);
        return dao.findAll();
    }

    @Override
    public void save(Product product) throws Exception {
        dao.save(product);
    }
}
