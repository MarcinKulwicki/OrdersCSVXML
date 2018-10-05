package com.marcinkulwicki;


import com.marcinkulwicki.dto.Order;
import com.marcinkulwicki.service.OrderService;
import com.marcinkulwicki.service.OrderServiceReports;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Program do obsługi zamówień

        OrderService orderService = new OrderService();
        OrderServiceReports orderServiceReports = new OrderServiceReports();

        //1. Program na wejściu przyjmuje jako argument liste plikow csv i xml
        System.out.println("\n||**************1**************||");
        List<String> stringListEntryFile = new ArrayList<>();
        stringListEntryFile.add("starter.csv");
        stringListEntryFile.add("starter.sdf");
        stringListEntryFile.add("starter.xml");
        stringListEntryFile.add("startesr.xml");
        stringListEntryFile.add("startesr.csv");
        List<Order> ordersEntry = orderService.loadFiles(stringListEntryFile);
        orderServiceReports.showOrders(ordersEntry);

        //2. Kazdy plik zawiera jedno lub wiecej zamowien
        System.out.println("\n||**************2**************||");
        System.out.println("To zalatwiaja validowane Settery");

        //3. Kazde zamowienie nalezy zapisac w bazie danych (in memory)
        System.out.println("\n||**************3**************||");
        try{
            orderService.addOrderToList(ordersEntry, new Order("4", "2","Łosoś", "12" , "352.20"));
            orderService.addOrderToList(ordersEntry, new Order("5", "1","Makrela", "1" , "32.26"));
            orderService.addOrderToList(ordersEntry, new Order("6", "3","Mintaj", "2" , "52.60"));
            orderService.addOrderToList(ordersEntry, new Order("6", "5","Dorsz", "8" , "13.80"));
            orderService.addOrderToList(ordersEntry, new Order("7", "1","Korniszon", "100" , "100.20"));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        try{
            orderService.addOrderToList(ordersEntry, new Order("4.", "2","Łosoś", "12" , "352.20"));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        try{
            orderService.addOrderToList(ordersEntry, new Order("4", "2d","Łosoś", "12.00" , "352.20"));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        try{
            orderService.addOrderToList(ordersEntry, new Order("4", "2","Łosoś", "12" , "assd"));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        orderServiceReports.showOrders(ordersEntry);


        //4. Raporty wystwietlane na ekran oraz Tworzone pliki xml i csv
        System.out.println("\n||**************3**************||");
        System.out.println("Raporty wyswietlane oraz zapisywane do plikow xml oraz csv");

        System.out.println("\na. ");
        orderServiceReports.showNumberOfAllOrders(orderServiceReports.numberOfAllOrders(ordersEntry));
        orderServiceReports.generateXMLNumberOfAllOrders(orderServiceReports.numberOfAllOrders(ordersEntry), "a_AllOrder");
        orderServiceReports.generateCSVNumberOfAllOrders(orderServiceReports.numberOfAllOrders(ordersEntry), "a_AllOrder");

        System.out.println("\nb. (o wskazanym ID) ");
        orderServiceReports.showNumberOfAllOrders(orderServiceReports.numberOfAllOrders(ordersEntry, "2"));
        orderServiceReports.generateXMLNumberOfAllOrders(orderServiceReports.numberOfAllOrders(ordersEntry, "2"), "b_AllOrder");
        orderServiceReports.generateCSVNumberOfAllOrders(orderServiceReports.numberOfAllOrders(ordersEntry, "2"), "b_AllOrder");


        System.out.print("\nc. ");
        orderServiceReports.showCostOfAllOrders(orderServiceReports.costOfAllOrders(ordersEntry));
        orderServiceReports.generateXMLCostOfAllOrders(orderServiceReports.costOfAllOrders(ordersEntry), "c_AllCost");
        orderServiceReports.generateCSVCostOfAllOrders(orderServiceReports.costOfAllOrders(ordersEntry), "c_AllCost");

        System.out.print("\nd. (o wskazanym ID) ");
        orderServiceReports.showCostOfAllOrders(orderServiceReports.costOfAllOrders(ordersEntry, "1"));
        orderServiceReports.generateXMLCostOfAllOrders(orderServiceReports.costOfAllOrders(ordersEntry, "1"), "d_AllByIdCost");
        orderServiceReports.generateCSVCostOfAllOrders(orderServiceReports.costOfAllOrders(ordersEntry, "1"), "d_AllByIdCost");

        System.out.println("\ne. ");
        orderServiceReports.showOrders(ordersEntry);
        orderService.generateXMLFileFromListOrders("e_AllOrders", ordersEntry);
        orderService.generateCSVFileFromListOrders("e_AllOrders", ordersEntry);

        System.out.println("\nf. (o wskazanym ID) ");
        orderServiceReports.showOrders(ordersEntry, "6");
        orderService.generateXMLFileFromListOrders("f_AllOrdersById", orderService.filterOrdersByClientId(ordersEntry, "6"));
        orderService.generateCSVFileFromListOrders("f_AllOrdersById", orderService.filterOrdersByClientId(ordersEntry, "6"));

        System.out.println("\ng. ");
        orderServiceReports.showAverageCostOfAllOrders(orderServiceReports.averageCostOrder(ordersEntry , "1"));
        orderServiceReports.generateXMLAverageCostOfAllOrders(orderServiceReports.averageCostOrder(ordersEntry , "1"),"g_AveCostOrder");
        orderServiceReports.generateCSVAverageCostOfAllOrders(orderServiceReports.averageCostOrder(ordersEntry , "1"), "g_AveCostOrder");


        System.out.println("\nh. (o wskazanym ID) ");
        orderServiceReports.showAverageCostOfAllOrders(orderServiceReports.averageCostOrder(ordersEntry , "1" ,"1"));
        orderServiceReports.generateXMLAverageCostOfAllOrders(orderServiceReports.averageCostOrder(ordersEntry , "1" , "1"),"h_AveCostOrderById");
        orderServiceReports.generateCSVAverageCostOfAllOrders(orderServiceReports.averageCostOrder(ordersEntry , "1", "1"), "h_AveCostOrderById");


    }
}
