package com.marcinkulwicki.dto;

public class Order {

    //Client_Id
    private int clientId; // not longer than 6 char
    //Request_Id
    private long requestId;
    //Name
    private String name; // not longer than 255 char
    //Quantity
    private int quantity;
    //Price
    private Double price;

    //Reports
    private long allOrders;
    private Double allCost;
    private Double averageCost;

    //Constructors
    public Order() { }

    public Order(String clientId){
        setClientId(clientId);
    }

    public Order(String clientId, String requestId, String name, String quantity, String price) throws NumberFormatException, VerifyError {

        setClientId(clientId);
        setRequestId(requestId);
        setName(name);
        setQuantity(quantity);
        setPrice(price);
    }

    //Setter and Getters
    public void setRequestId(String requestId) throws NumberFormatException {
        try {
            this.requestId = Long.parseLong(requestId);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Request id is not a number");
        }
    }

    public void setName(String name) throws VerifyError {
        if (name.length() < 256) {
            this.name = name;
        } else {
            throw new VerifyError("Name should be not longer than 255 char");
        }

    }

    public void setQuantity(String quantity) throws NumberFormatException {
        try {
            this.quantity = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Quantity is not Integer");
        }
    }

    public void setPrice(String price) throws NumberFormatException {
        if((price.charAt(price.length()-1)+"").equalsIgnoreCase("d")){
            throw new NumberFormatException("Price is invalid");
        }
        try {
            this.price = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Price is invalid");
        }
    }

    public void setAllOrders(long allOrders) {
        this.allOrders = allOrders;
    }

    public void setAllCost(Double allCost) {
        this.allCost = allCost;
    }

    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }

    public int getClientId() {
        return clientId;
    }

    public long getRequestId() {
        return requestId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public long getAllOrders() {
        return allOrders;
    }

    public Double getAllCost() {
        return allCost;
    }

    public Double getAverageCost() {
        return averageCost;
    }

    //toString
    @Override
    public String toString() {
        return this.clientId+" "+this.requestId+" "+this.name+" "+this.quantity+" "+this.price;
    }

    public void setClientId(String clientId) throws NumberFormatException, VerifyError {
        if (clientId.length() > 6) {
            throw new VerifyError("Client id is too long, should be not longer than 6 char");
        } else {
            try {
                this.clientId = Integer.parseInt(clientId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Client id is not a number");
            }
        }
    }
}
