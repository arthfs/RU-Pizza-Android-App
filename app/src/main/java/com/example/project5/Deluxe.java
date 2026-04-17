package com.example.project5;

import java.util.ArrayList;

/**
 * Subclass of Pizza that Represents a Deluxe pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class Deluxe extends Pizza {
    private static final double COST_OF_SMALL = 18.99;
    private static final double COST_OF_MEDIUM = 20.99;
    private static final double COST_OF_LARGE = 22.99;

    /**
     * This is the default constructor of the class.
     */
    public Deluxe()
    {
        super();
    }

    /**
     * Parametrized constructor that uses the style to create this class.
     * @param style is the chosen style of the pizza.
     */
    public Deluxe (String style)
    {
        super();
        setCrust(style.equals("Chicago-style") ? Crust.DEEP_DISH :
                Crust.BROOKLYN);
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.ONION);
        toppings.add(Topping.MUSHROOM);
        this.setToppings(toppings);
    }

    /**
     * This method calculates the price of a Deluxe pizza.
     * @return the cost as a double.
     */
    @Override
    public double price() {
        Size size = getSize();
        return size.equals(Size.SMALL) ? COST_OF_SMALL :
                size.equals(Size.MEDIUM) ? COST_OF_MEDIUM : COST_OF_LARGE;
    }
}


