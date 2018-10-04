package com.marcinkulwicki.service;

import com.marcinkulwicki.dto.Order;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class OrderServiceReports {


    public String readFileToString(String file) throws IOException {
        StringBuilder sb = new StringBuilder();

        if(file.substring(file.length()-3).equalsIgnoreCase("csv")){
            File f = new File("./CSV/" + file);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine())
                sb.append(scanner.nextLine()+"\n");
        }else if (file.substring(file.length()-3).equalsIgnoreCase("xml")){
            File f = new File("./XML/" + file);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine())
                sb.append(scanner.nextLine()+"\n");

        }else throw new IOException("Cannot find file: "+file);

        return sb.toString();
    }

    /*
    ************************************
    Number of Orders
    ************************************
     */
    public long numberOfAllOrders(List<Order> orders) {
        long counter = 0;
        for (Order order : orders) {
            counter += order.getQuantity();
        }
        return counter;
    }

    public long numberOfAllOrders(List<Order> orders, String clientId) {
        long counter = 0;
        for (Order order : orders) {
            if (clientId.equalsIgnoreCase(order.getClientId() + ""))
                counter += order.getQuantity();
        }
        return counter;
    }

    public void showNumberOfAllOrders(long counter){
        System.out.println("Number of all Orders: "+counter);
    }

    public void generateCSVNumberOfAllOrders(long counter, String fileName){

        try (FileWriter fileWriter = new FileWriter("./CSV/" + fileName + ".csv", false)){

            fileWriter.append("Orders_Number\n"+counter);

        }catch (IOException e){
            System.out.println("ERROR: Cannot write to "+fileName+".csv - "+e.getMessage());
        }
    }

    public void generateXMLNumberOfAllOrders(long counter, String fileName){

        try (FileWriter fileWriter = new FileWriter("./XML/" + fileName + ".xml", false)){


            fileWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
            fileWriter.append("<!DOCTYPE requests SYSTEM \"./orderReports.dtd\">\n\n");

            fileWriter.append("<requests>\n");
            fileWriter.append("    <request>\n");
            fileWriter.append("        <ordersNumber>"+counter+"</ordersNumber>\n");
            fileWriter.append("    </request>\n");
            fileWriter.append("</requests>\n");

        }catch (IOException e){
            System.out.println("ERROR: Cannot write to "+fileName+".xml - "+e.getMessage());
        }
    }

    /*
    ************************************
    Cost of Orders
    ************************************
     */
    public Double costOfAllOrders(List<Order> orders) {
        Double counter = 0.0;
        for (Order order : orders) {
            counter += order.getPrice();
        }
        return counter;
    }

    public Double costOfAllOrders(List<Order> orders, String clientId) {
        Double counter = 0.0;
        for (Order order : orders) {
            if (clientId.equalsIgnoreCase(order.getClientId() + ""))
                counter += order.getPrice();
        }
        return counter;
    }

    public void showCostOfAllOrders(Double counter){
        System.out.println("Cost of all Orders: "+counter);
    }

    public void generateCSVCostOfAllOrders(Double counter, String fileName){

        try (FileWriter fileWriter = new FileWriter("./CSV/" + fileName + ".csv", false)){

            fileWriter.append("Orders_Cost\n"+counter);

        }catch (IOException e){
            System.out.println("ERROR: Cannot write to "+fileName+".csv - "+e.getMessage());
        }
    }

    public void generateXMLCostOfAllOrders(Double counter, String fileName){

        try (FileWriter fileWriter = new FileWriter("./XML/" + fileName + ".xml", false)){


            fileWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
            fileWriter.append("<!DOCTYPE requests SYSTEM \"./orderReports.dtd\">\n\n");

            fileWriter.append("<requests>\n");
            fileWriter.append("    <request>\n");
            fileWriter.append("        <ordersCost>"+counter+"</ordersCost>\n");
            fileWriter.append("    </request>\n");
            fileWriter.append("</requests>\n");

        }catch (IOException e){
            System.out.println("ERROR: Cannot write to "+fileName+".xml - "+e.getMessage());
        }
    }

    /*
    ************************************
    Display Orders
    ************************************
     */
    //Not tested
    public void showOrders(List<Order> orders) {
        Iterator<Order> it = orders.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    //Not tested
    public void showOrders(List<Order> orders, String clientId) {
        Iterator<Order> it = orders.iterator();
        while (it.hasNext()) {
            Order order = it.next();
            if (clientId.equalsIgnoreCase(order.getClientId() + ""))
                System.out.println(order.toString());
        }
    }


    /*
    ************************************
    Average Cost
    ************************************
     */
    public Double averageCostOrder(List<Order> orders, String requestId) {
        Double counter = 0.0;
        int step = 0;
        for (Order order : orders) {
            if (requestId.equalsIgnoreCase(order.getRequestId() + "")) {
                counter += order.getPrice();
                step++;
            }
        }
        if (step == 0) return 0.0;
        return counter / step;
    }

    public Double averageCostOrder(List<Order> orders, String requestId, String clientId) {
        Double counter = 0.0;
        int step = 0;
        for (Order order : orders) {
            if (requestId.equalsIgnoreCase(order.getRequestId() + "") &&
                    clientId.equalsIgnoreCase(order.getClientId() + "")) {

                counter += order.getPrice();
                step++;
            }
        }
        if (step == 0) return 0.0;
        return counter / step;
    }

    public void showAverageCostOfAllOrders(Double counter){
        System.out.println("Average cost of all Orders: "+counter);
    }

    public void generateCSVAverageCostOfAllOrders(Double counter, String fileName){

        try (FileWriter fileWriter = new FileWriter("./CSV/" + fileName + ".csv", false)){

            fileWriter.append("Average_Cost\n"+counter);

        }catch (IOException e){
            System.out.println("ERROR: Cannot write to "+fileName+".csv - "+e.getMessage());
        }
    }

    public void generateXMLAverageCostOfAllOrders(Double counter, String fileName){

        try (FileWriter fileWriter = new FileWriter("./XML/" + fileName + ".xml", false)){


            fileWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
            fileWriter.append("<!DOCTYPE requests SYSTEM \"./orderReports.dtd\">\n\n");

            fileWriter.append("<requests>\n");
            fileWriter.append("    <request>\n");
            fileWriter.append("        <averageCost>"+counter+"</averageCost>\n");
            fileWriter.append("    </request>\n");
            fileWriter.append("</requests>\n");

        }catch (IOException e){
            System.out.println("ERROR: Cannot write to "+fileName+".xml - "+e.getMessage());
        }
    }

}
