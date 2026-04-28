package com.example.project5;


import com.example.project5.pizza.Order;


import java.util.ArrayList;

/**
 * Singleton class representing the restaurant, it contains the current and the placed orders.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public final class Restaurant { //centralize control; cannot be extended
    private static Restaurant restaurant; //the single instance
    private ArrayList<Order> currentOrders;
    private ArrayList<Order> placedOrders;

    /**
     * This is the default constructor of the class.
     */
    private Restaurant() {
        currentOrders = new ArrayList<>();
        placedOrders = new ArrayList<>();

    }

    /**
     * This method gets the instance of the restaurant.
     * @return the variable restaurant.
     */
    public static synchronized Restaurant getInstance()
    { //allow the client to get the instance
        if (restaurant == null)
            restaurant = new Restaurant(); //lazy approach – create the instance when needed
        return restaurant;
    }

    /**
     * This method sets the current orders.
     * @param newValue is the new value of the current orders.
     */
    public void setCurrentOrders (ArrayList<Order> newValue) {
        currentOrders = newValue;
    }

    /**
     * This method gets the current orders.
     * @return the current orders.
     */
    public ArrayList<Order> getCurrentOrders () {
        return currentOrders;
    }

    /**
     * This method gets the placed orders.
     * @return placed orders as an arrayList.
     */
    public ArrayList<Order> getPlacedOrders() {
        return placedOrders;
    }

    /**
     * This method sets the placed orders.
     * @param placedOrders is the new value of the placed orders.
     */
    public void setPlacedOrders(ArrayList<Order> placedOrders) {
        this.placedOrders = placedOrders;
    }
}