package com.marcinkulwicki;

import com.marcinkulwicki.dto.Order;
import com.marcinkulwicki.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

public class Main {

    public static void main(String[] args) {


        OrderService orderService = new OrderService();
        List<Order> orders = orderService.loadOrdersFromCSVFile("starter");
        orderService.showOrders(orders);



            System.out.println(OrderService.validateXMLFile("starter"));
            List<Order> ordersXML = orderService.readXMLFile("starter");


    }
}
