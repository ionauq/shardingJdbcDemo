package com.example.shardingjdbcdemo.repository;

import com.example.shardingjdbcdemo.entity.Order;


public interface OrderRepository {
    long jdbcTemplateTest(Order order);

    long rawJdbcTest(Order order);
}
