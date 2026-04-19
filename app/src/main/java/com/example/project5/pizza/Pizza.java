package com.example.project5.pizza;


import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Abstract class representing a pizza.
 * It includes the toppings, the size and the crust of the pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public abstract class Pizza {
    public static final int MAX_TOPPING = 5;
    private ArrayList<Topping> toppings;
    private Crust crust;
    private Size size;

    /**
     * This is the default constructor of the class.
     */
    public Pizza() {

    }

    /**
     * This method gets the toppings of a pizza and saves them in a list.
     * @return is the observable list containing all toppings for a specific
     * pizza.
     */
    private ArrayList<Topping> retrieveChosenToppings() {
        ArrayList<Topping> chosenToppings = new ArrayList<>();
        int j = 0;
        while ( j < toppings.size()) {
            chosenToppings.add(toppings.get(j));
            j = j + 1;
        }
        return chosenToppings;
    }

    /**
     * This method creates a string containing all toppings.
     * @return a string containing all toppings.
     */
    public String stringToppingList() {
        ArrayList<Topping> chosenToppingsList = retrieveChosenToppings();
        String chosenToppings = "";
        int j = 0;
        while ( j < chosenToppingsList.size()) {
            if (chosenToppings.isEmpty())
                chosenToppings = chosenToppingsList.get(j).toString();
            else
                chosenToppings += ", " + chosenToppingsList.get(j).toString();
            j = j + 1;
        }
        return chosenToppings;
    }


    /**
     * This method gets the toppings of a pizza.
     * @return the toppings of a pizza.
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * This method sets the toppings of a pizza.
     * @param toppings is the toppings of a pizza.
     */
    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    /**
     * Getter method that returns the crust of the pizza.
     * @return the crust of the pizza.
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * This method sets the crust of each pizza.
     * @param crust is the crust of the pizza.
     */
    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    /**
     * Getter method that returns the size of the pizza.
     * @return the size of the pizza.
     */
    public Size getSize() {
        return size;
    }

    /**
     * This method sets the size for a pizza.
     * @param size is the size of the pizza.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Getter method used to retrieve the style of a pizza based on its crust.
     * @return Chicago-Style or New-York Style;
     */
    public String getStyle()
    {
        ArrayList<Crust> chicagoStyles = new ArrayList<>();
        chicagoStyles.add(Crust.DEEP_DISH);
        chicagoStyles.add(Crust.PAN);
        chicagoStyles.add(Crust.STUFFED);

        return chicagoStyles.contains(crust) ? "Chicago-Style" :
                "New-York Style";
    }

    /**
     * This is an abstract method to calculate the price of a pizza.
     * @return a double representing the price.
     */
    public abstract double price();


    /**
     * This method returns a string with specific format for each pizza.
     * It includes its size ,style ,crust, price, and toppings.
     * @return a string containing the details of a pizza.
     */
    @Override
    public String toString() {
        DecimalFormat priceFormat =  new DecimalFormat();
        priceFormat.applyPattern("#,###.00");
        String wantedPrice = priceFormat.format(price());
        String fixedFormat = size + " " + getClass().getSimpleName() + " ("
                + crust + ", " + getStyle() +  ") " +
                "with toppings: " + stringToppingList() + " . Price: $"
                + wantedPrice;
        return fixedFormat;

    }
}

