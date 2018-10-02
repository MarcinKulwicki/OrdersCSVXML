package com.marcinkulwicki;

import com.marcinkulwicki.dto.Order;

public class Main {

    public static void main(String[] args) {


        try {
            Order ord = new Order("25513", "2", "Bulka", "1" , "1");

            System.out.println(ord.getPrice());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
