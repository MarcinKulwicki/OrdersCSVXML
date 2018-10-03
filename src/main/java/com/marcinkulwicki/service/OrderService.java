package com.marcinkulwicki.service;


import com.marcinkulwicki.dto.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class OrderService {


    public String[] splitLineCSV(String line) throws VerifyError {

        String[] orderFromLine = line.split(",");
        if (orderFromLine.length < 5) {
            throw new VerifyError("CSV line have under 5 parameters");
        } else if (orderFromLine.length > 5) {
            throw new VerifyError("CSV line have more then 5 parameters");
        }
        return orderFromLine;
    }

    //NotTested
    public List<Order> loadOrdersFromCSVFile(String fileName) {

        List<Order> orders = new ArrayList<>();
        int counter = 1;
        try {
            File file = new File("./CSV/" + fileName + ".csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String[] orderLine = splitLineCSV(scanner.nextLine());
                try {
                    Order order = new Order(orderLine[0], orderLine[1], orderLine[2], orderLine[3], orderLine[4]);
                    orders.add(order);
                    counter++;
                } catch (VerifyError e) {
                    if (counter > 1) {
                        System.out.println(e.getMessage() + " Error in line " + counter);
                    }
                    counter++;
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage() + " Error in line " + counter);
                    counter++;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

    public void showOrders(List<Order> orders) {
        Iterator<Order> it = orders.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    public List<Order> addOrderToList(List<Order> orders, Order order) {
        orders.add(order);
        return orders;
    }

    public void generateCSVFileFromListOrders(String fileName, List<Order> orders) {

        try (FileWriter fileWriter = new FileWriter("./CSV/" + fileName + ".csv", false)) {

            fileWriter.append("Client_Id,Request_Id,Name,Quantity,Price\n");

            for (int i = 0; i < orders.size(); i++) {
                fileWriter.append(
                                orders.get(i).getClientId() + "," +
                                orders.get(i).getRequestId() + "," +
                                orders.get(i).getName() + "," +
                                orders.get(i).getQuantity() + "," +
                                orders.get(i).getPrice() + "\n"
                );
            }
        } catch (IOException e) {
            System.out.println("Cannot save CSV file (name: " + fileName + ")");
        }
    }

}
