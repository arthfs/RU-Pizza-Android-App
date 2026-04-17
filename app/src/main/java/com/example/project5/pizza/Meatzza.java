package com.example.project5.pizza;

import java.util.ArrayList;

/**
 * Subclass of Pizza that Represents a Meatzza pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class Meatzza extends Pizza {
    private static final double COST_OF_SMALL = 19.99;
    private static final double COST_OF_MEDIUM = 21.99;
    private static final double COST_OF_LARGE = 23.99;

    /**
     * This is the default constructor for the class.
     */
    public Meatzza()
    {
        super();
    }

    /**
     * Parametrized constructor that uses the style to create this class.
     * @param style is the chosen style of the pizza.
     */
    public Meatzza (String style)
    {
        super();
        setCrust(style.equals("Chicago-style") ? Crust.STUFFED :
                Crust.HAND_TOSSED);
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.HAM);
        this.setToppings(toppings);
    }

    /**
     * This method calculates the price of a Meatzza pizza.
     * @return the cost as a double.
     */
    @Override
    public double price() {
        Size size = getSize();
        return size.equals(Size.SMALL) ? COST_OF_SMALL :
                size.equals(Size.MEDIUM) ? COST_OF_MEDIUM : COST_OF_LARGE;
    }
}


