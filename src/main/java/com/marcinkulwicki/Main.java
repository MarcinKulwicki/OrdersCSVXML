package com.marcinkulwicki;

import com.marcinkulwicki.service.OrderService;

public class Main {

    public static void main(String[] args) {


        OrderService orderService = new OrderService();
        orderService.loadOrdersFromCSVFile("starter");

    }
}
