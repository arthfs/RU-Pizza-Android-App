package com.example.project5;

/**
 * Concrete class used to create pizzas with a Chicago style.
 * It is used to create Deluxe, Meatzza, BBQChicken and BuildYourOwn pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class ChicagoPizza implements PizzaFactory {

    /**
     * This is the default constructor of the class.
     */
    public ChicagoPizza()
    {

    }

    /**
     * This method creates a Deluxe pizza according to a Chicago style.
     * @return Pizza object representing the pizza.
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe("Chicago-style");
    }

    /**
     * This method creates a BBQChicken pizza according to a Chicago style.
     * @return Pizza object representing the pizza.
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken("Chicago-style");
    }

    /**
     * This method creates a BuildYourOwn pizza according to a Chicago style.
     * @return Pizza object representing the pizza.
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn("Chicago-style");
    }

    /**
     * This method creates a Meatzza pizza according to a Chicago style.
     * @return Pizza object representing the pizza.
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza("Chicago-style");
    }
}


