package com.marcinkulwicki;

import com.marcinkulwicki.dto.Order;
import com.marcinkulwicki.service.OrderService;
import com.marcinkulwicki.service.OrderServiceReports;

import java.util.List;

public class Main {

    public static void main(String[] args) {


        OrderService orderService = new OrderService();
        OrderServiceReports orderServiceReports = new OrderServiceReports();




        List<Order> orderList = orderService.loadFiles( "starter.CSV" , "starter.xml" , "test.xml");

        orderServiceReports.showOrders(orderList,"1");




    }
}
