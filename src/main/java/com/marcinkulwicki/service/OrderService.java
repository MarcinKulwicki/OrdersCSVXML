package com.marcinkulwicki.service;


import com.marcinkulwicki.dto.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {


    public String[] splitLineCSV(String line) throws VerifyError{

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
            File file = new File("./CSV/"+fileName+".csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String[] orderLine = splitLineCSV(scanner.nextLine());
                try{
                    Order order = new Order(orderLine[0], orderLine[1], orderLine[2], orderLine[3], orderLine[4]);
                    orders.add(order);
                    counter++;
                }catch (VerifyError e){
                    if(counter>1){
                        System.out.println(e.getMessage()+" Error in line "+counter);
                    }
                    counter++;
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage()+" Error in line "+counter);
                    counter++;
                }

            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

}
