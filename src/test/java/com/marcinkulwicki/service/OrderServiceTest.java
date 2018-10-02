package com.marcinkulwicki.service;

import com.marcinkulwicki.dto.Order;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderServiceTest {

    @Test
    public void splitLineCSV() {

        OrderService orderService = new OrderService();

        String name = "1,1,Bułka,1,10.00";

        String[] order = orderService.splitLineCSV(name);
        assertEquals("Should have 5 element",order.length, 5);
    }

    @Test(expected = VerifyError.class)
    public void splitLineCSVNotEnougthOptions() throws VerifyError{

        OrderService orderService = new OrderService();

        String name = "1,Bułka,1,10.00";

        orderService.splitLineCSV(name);
    }

    @Test(expected = VerifyError.class)
    public void splitLineCSVTooMuchOptions() throws VerifyError{

        OrderService orderService = new OrderService();

        String name = "1,1,1,Bułka,1,10.00";

        orderService.splitLineCSV(name);
    }

}