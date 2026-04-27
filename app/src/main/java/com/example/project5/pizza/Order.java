package com.example.project5.pizza;

import java.util.ArrayList;

/**
 * Class representing an order, it contains the order number and the pizzas.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class Order {
    public static int orderNumber = 1;
    private int number;
    private ArrayList<Pizza> pizzas;

    /**
     * This is the default constructor of the class.
     */
    public Order()
    {
        pizzas = new ArrayList<>();
    }

    /**
     * Parametrized constructor that create an order with an order number.
     * @param number is the order number.
     */
    public Order(int number)
    {
        this.number = number;
        this.pizzas = new ArrayList<>();
    }

    /**
     * This method gets the number of an order.
     * @return an integer representing  the number of an order.
     */
    public int getNumber() {
        return number;
    }

    /**
     * This method sets the number of an order.
     * @param number an integer representing  the number of an order.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * This method gets the subtotal of an order.
     * @return the total price of this order (excluding tax)
     */
    public double getSubTotal()
    {
        double subtotal = 0;
        for (int j = 0; j < pizzas.size(); j++)
            subtotal = subtotal + pizzas.get(j).price();

        return subtotal;
    }

    /**
     * This method gets the pizzas of an order
     * @return an arraylist containing all pizzas of an order.
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * This method sets pizzas of an order.
     * @param pizzas an arraylist containing all pizzas of an order.
     */
    public void setPizzas(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }


    /**
     * This method overrides the tostring method to have a specific format.
     * @return a string representing the order number.
     */
    @Override
    public String toString() {
        if (number == 0)
            return "New Order";

        return "Order # " + number;
    }
}