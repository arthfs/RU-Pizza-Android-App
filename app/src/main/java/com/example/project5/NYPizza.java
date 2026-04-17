package com.example.project5;

/**
 * Concrete class used to create pizzas according to a New York style.
 * It is used to create Deluxe, Meatzza, BBQChicken and BuildYourOwn pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class NYPizza implements PizzaFactory {

    /**
     * Default constructor.
     */
    public NYPizza()
    {

    }

    /**
     * This method creates a Deluxe pizza according to a New York style.
     * @return Pizza object representing the pizza.
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe("New York Style");
    }

    /**
     * This method creates a Meatzza pizza according to a New York style.
     * @return Pizza object representing the pizza.
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza("New York Style");
    }

    /**
     * This method creates a BBQChicken pizza according to a New York style.
     * @return Pizza object representing the pizza.
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken("New York Style");
    }

    /**
     * This method creates a BuildYourOwn pizza according to a New York style.
     * @return Pizza object representing the pizza.
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn("New York Style");
    }
}