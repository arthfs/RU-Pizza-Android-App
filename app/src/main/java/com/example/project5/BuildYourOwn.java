package com.example.project5;

import java.util.ArrayList;

/**
 * Subclass of Pizza that allows the user create his/her own pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class BuildYourOwn extends Pizza {

    private static final int MAX_SIZE = 5;

    private static final double COST_OF_SMALL = 10.99;
    private static final double COST_OF_MEDIUM = 12.99;
    private static final double COST_OF_LARGE = 14.99;
    private static final double TOPPING_PRICE = 1.69;

    /**
     * This is the default constructor of the class.
     */
    public BuildYourOwn()
    {
        super();
        ArrayList<Topping> toppings = new ArrayList<>(MAX_SIZE);
        setToppings(toppings);
    }

    /**
     * Parametrized constructor that uses the style to create this class.
     * @param style is the chosen style of the pizza.
     */
    public BuildYourOwn(String style)
    {
        super();
        setCrust(style.equals("Chicago-style") ? Crust.PAN :
                Crust.HAND_TOSSED);
        ArrayList<Topping> toppings = new ArrayList<>(MAX_SIZE);
        setToppings(toppings);
    }

    /**
     * This method calculates the price of a pizza built by the user.
     * @return the cost as a double.
     */
    @Override
    public double price() {
        double additionalPrice = 0;
        ArrayList<Topping> toppings = getToppings();

        for (int i = 0; i < toppings.size(); i++)
            if (toppings.get(i) != null)
                additionalPrice += TOPPING_PRICE;

        Size size = getSize();
        return (size.equals(Size.SMALL) ? COST_OF_SMALL :
                size.equals(Size.MEDIUM) ? COST_OF_MEDIUM : COST_OF_LARGE)
                + additionalPrice;
    }

}


