package com.example.project5.pizza;

/**
 * Interface that lets the user create 4 types of pizza.
 * It contains abstract methods to create: Deluxe, Meatzza, BBQChicken and
 * BuildYourOwn pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public interface PizzaFactory {
    /**
     * Abstract method to create a Deluxe object.
     * @return a pizza object.
     */
    Pizza createDeluxe();

    /**
     * Abstract method to create a Meatzza object.
     * @return a pizza object.
     */
    Pizza createMeatzza();

    /**
     * Abstract method to create a BBQChicken object.
     * @return a pizza object.
     */
    Pizza createBBQChicken();

    /**
     * Abstract method to create a BuildYourOwn object.
     * @return a pizza object.
     */
    Pizza createBuildYourOwn();
}