package com.example.project5;

import java.util.ArrayList;

/**
 * Subclass of Pizza that Represents a BBQChicken pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class BBQChicken extends Pizza {
    private static final double COST_OF_SMALL = 16.99;
    private static final double COST_OF_MEDIUM = 18.99;
    private static final double COST_OF_LARGE = 20.99;

    /**
     * This is the default constructor of the class.
     */
    public BBQChicken()
    {
        super();
    }

    /**
     * Parametrized constructor that uses the style to create this class.
     * @param style is the chosen style of the pizza.
     */
    public BBQChicken ( String style)
    {
        super();
        setCrust(style.equals("Chicago-style") ? Crust.PAN : Crust.THIN);
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.BBQ_CHICKEN);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.PROVOLONE);
        toppings.add(Topping.CHEDDAR);
        this.setToppings(toppings);
    }

    /**
     * This method calculates the price of a BBQChicken pizza.
     * @return the cost as a double.
     */
    @Override
    public double price() {
        Size size = getSize();
        return size.equals(Size.SMALL) ? COST_OF_SMALL :
                size.equals(Size.MEDIUM) ? COST_OF_MEDIUM : COST_OF_LARGE;
    }
}

