package com.example.shardingjdbcdemo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {

    private long orderId;

    private int userId;

    private long addressId;

    private String status;
}
