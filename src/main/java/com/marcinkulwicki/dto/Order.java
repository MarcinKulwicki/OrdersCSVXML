package com.marcinkulwicki.dto;

public class Order {

    private int clientId; // not longer than 6 char
    private long requestId;
    private String name; // not longer than 255 char
    private int quantity;
    private Double price;

    public Order(String clientId, String requestId, String name, String quantity, String price) throws Exception {

        setClientId(clientId);
        setRequestId(requestId);
        setName(name);
        setQuantity(quantity);
        setPrice(price);
    }

    public void setClientId(String clientId) throws Exception {
        if (clientId.length() > 6) {
            throw new Exception("Client id is too long, should be not longer than 6 char");
        } else {
            try{
                this.clientId = Integer.parseInt(clientId);
            }catch (NumberFormatException e){
                throw new NumberFormatException("Client id is not a number");
            }
        }
    }

    public void setRequestId(String requestId) throws NumberFormatException {
        try {
            this.requestId = Long.parseLong(requestId);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Request id is not a number");
        }
    }

    public void setName(String name) throws Exception{
        if(name.length()<256){
            this.name = name;
        }else {
            throw new Exception("Name should be not longer than 255 char");
        }

    }

    public void setQuantity(String quantity) throws NumberFormatException {
        try{
            this.quantity = Integer.parseInt(quantity);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Quantity is not Integer");
        }
    }

    public void setPrice(String price) throws NumberFormatException {

        try{
            this.price = Double.parseDouble(price);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Price is invalid");
        }
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
}
