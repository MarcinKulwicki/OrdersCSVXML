package com.marcinkulwicki;

import com.marcinkulwicki.dto.Order;
import com.marcinkulwicki.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

public class Main {

    public static void main(String[] args) {


        OrderService orderService = new OrderService();




        List<Order> orderList = orderService.loadFiles("starter.xml" , "test.xml", "starter.CSV");

        System.out.println("");


    }
}
