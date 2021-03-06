package com.example.shardingjdbcdemo.repository;

import com.example.shardingjdbcdemo.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class OrderRepositoryImplTest {

    @Autowired
    OrderRepositoryImpl orderRepository;

    @Test
    void jdbcTemplateTest() {
        Order order = new Order();
        order.setAddressId(1);
        order.setUserId(1);
        order.setStatus("jdbcTemplate with shardingsphere-jdbc");
        long id = orderRepository.jdbcTemplateTest(order);
        assertNotEquals(id, 0);
    }

    @Test
    void rawJdbcTest() {
        Order order = new Order();
        order.setAddressId(2);
        order.setUserId(2);
        order.setStatus("raw jdbc with shardingsphere-jdbc");
        long id = orderRepository.rawJdbcTest(order);
        assertNotEquals(id, 0);
    }

    @Test
    void JdbcTemplateWithRawDataSourceTest(){
        Order order = new Order();
        order.setAddressId(3);
        order.setUserId(3);
        order.setStatus("raw datasource");
        long id = orderRepository.JdbcTemplateWithRawDataSourceTest(order);
        assertNotEquals(id, 0);
    }
}