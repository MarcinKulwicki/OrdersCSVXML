package com.marcinkulwicki.service;


import com.marcinkulwicki.dto.Order;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class OrderService {

    /**
     * Function takes parameters from CSV line, for example 1,1,Bułka,1,1 return 5 elements tab {"1","1","Bułka","1","1"}
     * For Order.class
     * @param line one line from CSV line
     * @return order parameters
     * @throws VerifyError When parameters have more or less then 5 parameters
     */
    public String[] splitLineCSV(String line) throws VerifyError {

        String[] orderFromLine = line.split(",");
        if (orderFromLine.length < 5) {
            throw new VerifyError("CSV line have under 5 parameters");
        } else if (orderFromLine.length > 5) {
            throw new VerifyError("CSV line have more then 5 parameters");
        }
        return orderFromLine;
    }

    /**
     * Function read CSV file
     * @param fileName Name of file (without extension)
     * @return All orders in csv file
     */
    public List<Order> readCSVFile(String fileName) {

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
                        System.out.println(e.getMessage() + " Error in line " + counter + " in "+fileName+".csv");
                    }
                    counter++;
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage() + " Error in line " + counter + " in "+fileName+".csv");
                    counter++;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

    /**
     * Adding new Order to exist Orders
     * @param orders All Orders
     * @param order New Order
     * @return List of Orders with new Order
     */
    public List<Order> addOrderToList(List<Order> orders, Order order) {
        orders.add(order);
        return orders;
    }

    /**
     * Function generate new CSV file in ./CSV/ folder. If file exist, file will be replaced
     * @param fileName Name of new file (without extension)
     * @param orders List of all Orders with one must be added to new file
     */
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

    /**
     * Function to read Orders from XML file
     * @param fileName Name of file XML (without extension)
     * @return List with all Orders
     */
    public List<Order> readXMLFile(String fileName){

        if(validateXMLFile(fileName) == false) return new ArrayList<>();

        String xml = convertXMLFileToString(fileName);

        String[] ordersTab = xml.split("</request>");
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < ordersTab.length; i++) {

            orders.add(
                    new Order(
                            ordersTab[i].substring(ordersTab[i].indexOf("<clientId>") + 10, ordersTab[i].indexOf("</clientId>")),
                            ordersTab[i].substring(ordersTab[i].indexOf("<requestId>") + 11, ordersTab[i].indexOf("</requestId>")),
                            ordersTab[i].substring(ordersTab[i].indexOf("<name>") + 6, ordersTab[i].indexOf("</name>")),
                            ordersTab[i].substring(ordersTab[i].indexOf("<quantity>") + 10, ordersTab[i].indexOf("</quantity>")),
                            ordersTab[i].substring(ordersTab[i].indexOf("<price>") + 7, ordersTab[i].indexOf("</price>"))
                    )
            );
        }
        return orders;
    }

    /**
     * Function convert XML file to String, can be used to display xml file
     * @param fileName Name of file (without extension)
     * @return String when file will be found, null if file don't exist
     */
    private String convertXMLFileToString(String fileName) {

        StringBuilder sb = new StringBuilder();

        File file = new File("./XML/" + fileName + ".xml");
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine() + "\n");
            }
            int begin = sb.toString().indexOf("<requests>");
            int end = sb.toString().indexOf("</requests>");
            return (sb.toString().substring(begin + 15, end - 1).replaceAll(" ", "").replaceAll("\n", ""));
        } catch (FileNotFoundException e) {
            System.out.println("File not found in OrderService->readXMLFile: " + e.getMessage());
        }
        return null;
    }

    /**
     * Function to validate XML file
     * @param xmlFileName Name of file (without extension)
     * @return When file is correct -> true
     */
    public static boolean validateXMLFile(String xmlFileName) {
        try {
            DOMParser parser = new DOMParser();
            parser.setFeature("http://xml.org/sax/features/validation", true);
            parser.setProperty(
                    "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
                    "memory.xsd");

            ErrorHandler errors = new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    System.out.println("WARRING: " + exception.getMessage());
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    System.out.println("ERROR: " + exception.getMessage());
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    System.out.println("FATAL ERROR: " + exception.getMessage());
                }
            };
            parser.setErrorHandler(errors);
            parser.parse("./XML/" + xmlFileName + ".xml");

        } catch (Exception e) {
            System.out.print("Problem parsing the file - "+xmlFileName+".xml\n");
            return false;
        }
        return true;
    }

    /**
     * Function to generate XML file from all Orders
     * @param fileName Name of file (without extension)
     * @param orders All Orders
     */
    public void generateXMLFileFromListOrders(String fileName, List<Order> orders) {

        try (FileWriter fileWriter = new FileWriter("./XML/" + fileName + ".xml", false)){

            fileWriter.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
            fileWriter.append("<!DOCTYPE requests SYSTEM \"./order.dtd\">\n");

            fileWriter.append("<requests>\n");
            for(int i = 0 ; i < orders.size() ; i++){
                fileWriter.append("    <request>\n");

                fileWriter.append("        <clientId>"+orders.get(i).getClientId()+"</clientId>\n");
                fileWriter.append("        <requestId>"+orders.get(i).getRequestId()+"</requestId>\n");
                fileWriter.append("        <name>"+orders.get(i).getName()+"</name>\n");
                fileWriter.append("        <quantity>"+orders.get(i).getQuantity()+"</quantity>\n");
                fileWriter.append("        <price>"+orders.get(i).getPrice()+"</price>\n");

                fileWriter.append("    </request>\n");
            }
            fileWriter.append("</requests>\n");

        }catch (IOException e){
            System.out.println("ERROR in generate XML file in OrderService: "+e.getMessage());
        }
    }

    /**
     * Function get all file to read, extension is needed, function get .xml files and .csv
     * @param args files .csv and .xml for example { starter.xml , starter.csv }
     * @return All Orders from files
     */
    public List<Order> loadFiles(List<String> args){

        List<Order> orders = new ArrayList<>();

        for(int i = 0 ; i < args.size() ; i++){

            if(args.get(i).substring(args.get(i).length()-4).equalsIgnoreCase(".xml")){
                orders = addOrdersToOrderList(orders, readXMLFile(args.get(i).substring(0, args.get(i).length()-4)));
            }else if (args.get(i).substring(args.get(i).length()-4).equalsIgnoreCase(".csv")){
                orders = addOrdersToOrderList(orders, readCSVFile(args.get(i).substring(0, args.get(i).length()-4)));
            }

        }
        return orders;
    }

    /**
     * Function to adding one list of Orders to other list of Orders
     * @param orders Main list of Orders, this list will be increased
     * @param ordersFromFile Orders with one you want add
     * @return orders
     */
    public List<Order> addOrdersToOrderList(List<Order> orders, List<Order> ordersFromFile){
        Iterator<Order> iterator = ordersFromFile.iterator();
        while (iterator.hasNext()){
            orders.add(iterator.next());
        }
        return orders;
    }

    /**
     * Function to get all orders by client
     * @param orders All Orders to search
     * @param clientId ID client
     * @return All Orders by Client Id
     */
    public List<Order> filterOrdersByClientId(List<Order> orders, String clientId){
        List<Order> orderList = new ArrayList<>();
        Iterator<Order> it = orders.iterator();
        while (it.hasNext()) {
            Order order = it.next();
            if (clientId.equalsIgnoreCase(order.getClientId() + ""))
                orderList.add(order);
        }
        return orderList;
    }


}
