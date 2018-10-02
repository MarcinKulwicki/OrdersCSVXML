package com.marcinkulwicki.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class OrderTest {

    @Test(expected = VerifyError.class)
    public void setClientIdMoreThanSevenChar() throws VerifyError {

        Order order = new Order();
        order.setClientId("1234567");
    }

    @Test(expected = NumberFormatException.class)
    public void setClientIdNotNumber() throws NumberFormatException {

        Order order = new Order();
        order.setClientId("a213");
    }

    @Test
    public void setClientIdTest() {

        Order order = new Order();
        order.setClientId("12345");

        assertEquals(order.getClientId(), 12345);
    }

    @Test
    public void setRequestIdTest() {

        Order order = new Order();
        order.setRequestId("0123456789");

        assertEquals(order.getRequestId(), 123456789L);
    }

    @Test(expected = NumberFormatException.class)
    public void setRequestIdNotNumber() throws NumberFormatException {

        Order order = new Order();
        order.setRequestId("546546L");

    }

    @Test
    public void setNameTest(){

        Order order = new Order();

        order.setName("a");
        assertEquals(order.getName(), "a");

        order.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum scelerisque lorem vitae pulvinar tempor. Quisque quis lacinia sapien, fermentum porta dolor. Curabitur elementum neque lacinia ex varius hendrerit eu vel velit. Praesent ullamcorper amet.");
        assertEquals(order.getName(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum scelerisque lorem vitae pulvinar tempor. Quisque quis lacinia sapien, fermentum porta dolor. Curabitur elementum neque lacinia ex varius hendrerit eu vel velit. Praesent ullamcorper amet.");
    }

    @Test(expected = VerifyError.class)
    public void setNameTooLongName(){

        Order order = new Order();
        order.setName(".Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum scelerisque lorem vitae pulvinar tempor. Quisque quis lacinia sapien, fermentum porta dolor. Curabitur elementum neque lacinia ex varius hendrerit eu vel velit. Praesent ullamcorper amet.");
    }

    @Test
    public void setQuantityTest() {

        Order order = new Order();
        order.setQuantity("12345678");
        assertEquals(order.getQuantity(), 12345678);
    }

    @Test(expected = NumberFormatException.class)
    public void setQuantityNotNumber() throws NumberFormatException{

        Order order = new Order();
        order.setQuantity("2312445i");
    }

    @Test
    public void setPrice() {

        Order order = new Order();
        order.setPrice("1234");
        Double a = 1234d;
        assertEquals(order.getPrice(), a);
    }

    @Test(expected = NumberFormatException.class)
    public void setPriceNotNumber() throws NumberFormatException{

        Order order = new Order();
        order.setPrice("22132D");
    }
}