package com.example.project5;

import com.example.project5.pizza.Order;

import java.util.ArrayList;

public final class Restaurant { //centralize control; cannot be extended
    public static int orderNumber = 1;
    private static Restaurant restaurant; //the single instance
    private ArrayList<Order> currentOrders;
    private ArrayList<Order> placedOrders;

    private Restaurant() {
        currentOrders = new ArrayList<>();
        currentOrders.add(new Order(0));
        placedOrders = new ArrayList<>();
    } //prevent the JVM from creating a public default constructor

    public static synchronized Restaurant getInstance()
    { //allow the client to get the instance
        if (restaurant == null)
            restaurant = new Restaurant(); //lazy approach – create the instance when needed
        return restaurant;
    }

    public void setCurrentOrders (ArrayList<Order> newValue) { //setter
        currentOrders = newValue;
    }
    public ArrayList<Order> getCurrentOrders () { //getter
        return currentOrders;
    }
}