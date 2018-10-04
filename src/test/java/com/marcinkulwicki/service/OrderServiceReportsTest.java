package com.marcinkulwicki.service;

import com.marcinkulwicki.dto.Order;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceReportsTest {

    private List<Order> orders;

    @Before
    public void setUp(){
        Order ordersTab[] = {
                new Order("1", "2","Ananas","1", "8.00"),
                new Order("1", "1","Woda","6", "5.00"),
                new Order("2", "2","Woda","6", "5.00"),
                new Order("2", "1","Lód","10", "25.00"),
                new Order("2", "2","Kurczak","1", "9.50"),
                new Order("2", "3","Lizak","25", "28.00"),
                new Order("2", "3","Patelnia","1", "129.99"),
                new Order("3", "3","Guma","3", "12.00")
        };
        orders = new ArrayList<>();
        for (Order order:ordersTab) {
            orders.add(order);
        }

    }

    @Test
    public void numberOfAllOrders() {
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        assertEquals(53L, orderServiceReports.numberOfAllOrders(orders));
    }

    @Test
    public void numberOfAllOrdersByClientId() {
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        assertEquals(7L, orderServiceReports.numberOfAllOrders(orders, "1"));
    }

    @Test
    public void costOfAllOrders() {
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        Double d = 222.49;
        assertEquals(d, orderServiceReports.costOfAllOrders(orders));
    }

    @Test
    public void costOfAllOrdersByClientId() {
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        Double d = 12.00;
        assertEquals(d, orderServiceReports.costOfAllOrders(orders,"3"));
    }

    @Test
    public void averageCostOrder() {
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        Double d = 7.5;
        assertEquals(d, orderServiceReports.averageCostOrder(orders, "2"));
    }

    @Test
    public void averageCostOrderByClientId() {
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        Double d = 8.0;
        assertEquals(d, orderServiceReports.averageCostOrder(orders, "2" , "1"));
    }

    @Test
    public void readFileToString() throws IOException {
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        String file1 = orderServiceReports.readFileToString("starter.csv");
        assertEquals("Client_Id,Request_Id,Name,Quantity,", file1.substring(0,file1.indexOf("Price")));

        String file2 = orderServiceReports.readFileToString("starter.xml");
        assertEquals("<?xml version=\"1.0\" encoding=\"", file2.substring(0, file2.indexOf("UTF-8")));
    }
    @Test(expected = IOException.class)
    public void readFileToStringException() throws IOException {
        OrderServiceReports orderServiceReports = new OrderServiceReports();
        orderServiceReports.readFileToString("starter.cs34v");
    }

    @Test
    public void generateCSVNumberOfAllOrders() throws IOException {
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        orderServiceReports.generateCSVNumberOfAllOrders(4, "test_aswerda");

        String csv = orderServiceReports.readFileToString("test_aswerda.csv");
        assertEquals("Orders_Num" , csv.substring(0,csv.indexOf("ber")));

    }

    @Test
    public void generateXMLNumberOfAllOrders() throws IOException {
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        orderServiceReports.generateXMLNumberOfAllOrders(4, "test_aswerda");

        String xml = orderServiceReports.readFileToString("test_aswerda.xml");
        assertEquals("<ordersNumber>4", xml.substring(xml.indexOf("<ordersNumber>4"),xml.indexOf("</ordersNumber>")));

        assertTrue(OrderService.validateXMLFile("test_aswerda"));
    }

    @Test
    public void generateCSVCostOfAllOrders() throws IOException{
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        Double d = 6.0;
        orderServiceReports.generateCSVCostOfAllOrders(d, "test_aswerdb");

        String csv = orderServiceReports.readFileToString("test_aswerdb.csv");
        assertEquals("Orders_Cost\n6.0\n" , csv);
    }

    @Test
    public void generateXMLCostOfAllOrders() throws IOException{
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        Double d = 7.01;
        orderServiceReports.generateXMLCostOfAllOrders(d, "test_aswerdb");

        String xml = orderServiceReports.readFileToString("test_aswerdb.xml");
        assertEquals("<ordersCost>7.01", xml.substring(xml.indexOf("<ordersCost>7.01"),xml.indexOf("</ordersCost>")));

        assertTrue(OrderService.validateXMLFile("test_aswerdb"));
    }

    @Test
    public void generateCSVAverageCostOfAllOrders() throws IOException{
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        Double d = 10.0;
        orderServiceReports.generateCSVAverageCostOfAllOrders(d, "test_aswerdc");

        String csv = orderServiceReports.readFileToString("test_aswerdc.csv");
        assertEquals("Average_Cost\n10.0\n" , csv);
    }

    @Test
    public void generateXMLAverageCostOfAllOrders() throws IOException{
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        Double d = 12.00;
        orderServiceReports.generateXMLAverageCostOfAllOrders(d, "test_aswerdc");

        String xml = orderServiceReports.readFileToString("test_aswerdc.xml");
        assertEquals("<averageCost>12.0", xml.substring(xml.indexOf("<averageCost>12.0"),xml.indexOf("</averageCost>")));


        assertTrue(OrderService.validateXMLFile("test_aswerdc"));
    }
}