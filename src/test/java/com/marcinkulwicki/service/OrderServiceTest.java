package com.marcinkulwicki.service;

import com.marcinkulwicki.dto.Order;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class OrderServiceTest {

    @Test
    public void splitLineCSV() {

        OrderService orderService = new OrderService();

        String name = "1,1,Bułka,1,10.00";

        String[] order = orderService.splitLineCSV(name);
        assertEquals("Should have 5 element",order.length, 5);
    }

    @Test(expected = VerifyError.class)
    public void splitLineCSVNotEnougthOptions() throws VerifyError{

        OrderService orderService = new OrderService();

        String name = "1,Bułka,1,10.00";

        orderService.splitLineCSV(name);
    }

    @Test(expected = VerifyError.class)
    public void splitLineCSVTooMuchOptions() throws VerifyError{

        OrderService orderService = new OrderService();

        String name = "1,1,1,Bułka,1,10.00";

        orderService.splitLineCSV(name);
    }

    @Test
    public void addOrderToList() {
        Order order = new Order("1","1","Bulka","1","10.00");
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order);

        OrderService orderService = new OrderService();
        orders = orderService.addOrderToList(orders,order);

        assertEquals(3, orders.size());
    }

    @Test
    public void generateCSVFileFromListOrders() {

        Order order = new Order("2","3","Bułka","1","10.20");
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order);

        OrderService orderService = new OrderService();
        orderService.generateCSVFileFromListOrders("test", orders);

        List<Order> ordersReaded = orderService.readCSVFile("test");

        assertEquals(2, ordersReaded.size());
        assertEquals("Bułka", ordersReaded.get(0).getName());
        assertEquals("Bułka", ordersReaded.get(1).getName());
        assertEquals(1, ordersReaded.get(1).getQuantity());
        assertEquals(2, ordersReaded.get(1).getClientId());
        assertEquals(3L, ordersReaded.get(1).getRequestId());
        Double d = 10.20;
        assertEquals(d, ordersReaded.get(1).getPrice());

    }

    @Test
    public void generateCSVFileFromListOrdersFirstLine() {

        Order order = new Order("2","3","Bułka","1","10.20");
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order);

        OrderService orderService = new OrderService();
        orderService.generateCSVFileFromListOrders("test", orders);

        try (FileReader fileReader = new FileReader("./CSV/test.csv")) {

            Scanner scanner = new Scanner(fileReader);
            String firstLane = scanner.nextLine();
            assertEquals("Client_Id,Request_Id,Name,Quantity,Price" , firstLane);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file ./CSV/test.csv , in function OrderServiceTest->generateCSVFileFromListOrdersTestFirstLine()");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void readXMLFile() {

        OrderService orderService = new OrderService();
        List<Order> orders = orderService.readXMLFile("starter");

        assertEquals("Bułka", orders.get(0).getName());
        assertEquals(2, orders.get(1).getQuantity());
        assertEquals(2, orders.get(3).getClientId());
        assertEquals(4, orders.size());

    }


    @Test
    public void generateXMLFileFromListOrders() {

        Order order = new Order("1", "5" , "Bułka" , "2","11.0");
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order);
        orders.add(order);

        OrderService orderService = new OrderService();
        orderService.generateXMLFileFromListOrders("test", orders);

        assertTrue(OrderService.validateXMLFile("test"));
    }

    @Test
    public void readCSVFile() {

        OrderService orderService = new OrderService();
        List<Order> orders = orderService.readCSVFile("starter");

        assertEquals("Bułka", orders.get(0).getName());
        assertEquals(2, orders.get(1).getQuantity());
        assertEquals(2, orders.get(3).getClientId());
        assertEquals(4, orders.size());
    }

    @Test
    public void loadFiles() {

        OrderService orderService = new OrderService();

        List<String> strings = new ArrayList<>();
        strings.add("starter.xml");
        strings.add("test.xml");
        strings.add("starter.CSV");
        List<Order> orderList = orderService.loadFiles(strings);

        assertEquals(11, orderList.size());
    }

    @Test
    public void filterOrdersByClientId() {
        Order order1 = new Order("1", "1" , "Bułka", "1", "1.0");
        Order order2 = new Order("1", "2" , "Noz", "4", "6.0");
        Order order3 = new Order("2", "3" , "Jajko", "5", "7.0");
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        OrderService orderService = new OrderService();

        assertEquals(2,orderService.filterOrdersByClientId(orders, "1").size());
        assertEquals("Noz", orderService.filterOrdersByClientId(orders, "1").get(1).getName());
    }
}