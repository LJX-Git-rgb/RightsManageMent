package com.ljx.service.impl;

import com.github.pagehelper.PageHelper;
import com.ljx.domain.Orders;
import com.ljx.dao.*;
import com.ljx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService implements IOrdersService {

    @Autowired
    IOrderDao dao;

    @Override
    public List<Orders> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return dao.findAll();
    }

    @Override
    public Orders findById(String ordersId) throws Exception {
        return dao.findById(ordersId);
    }
}
