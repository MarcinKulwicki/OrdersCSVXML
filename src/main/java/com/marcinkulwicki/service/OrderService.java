package com.marcinkulwicki.service;


import com.marcinkulwicki.dto.Order;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
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

    public String readXMLFile(String fileName){

        StringBuilder sb = new StringBuilder();

        File file = new File("./XML/"+fileName+".xml");
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()){
                sb.append(scanner.nextLine()+"\n");
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found in OrderService->readXMLFile: "+e.getMessage());
        }

        return sb.toString();
    }


    public static boolean validateXMLFile(String xmlFileName){
        try{
            DOMParser parser = new DOMParser();
            parser.setFeature("http://xml.org/sax/features/validation", true);
            parser.setProperty(
                    "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
                    "memory.xsd");

            ErrorHandler errors = new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    System.out.println("WARRING: "+exception.getMessage());
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    System.out.println("ERROR: "+exception.getMessage());
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    System.out.println("FATAL ERROR: "+exception.getMessage());
                }
            };
            parser.setErrorHandler(errors);
            parser.parse("./XML/"+xmlFileName+".xml");

        }catch (Exception e) {
            System.out.print("Problem parsing the file.");
            return false;
        }
        return true;
    }

}
