package com.example.project5;

import static com.example.project5.util.Methods.display;
import static com.example.project5.util.Methods.displayError;
import static com.example.project5.util.Methods.priceFormat;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project5.pizza.ChicagoPizza;
import com.example.project5.pizza.NYPizza;
import com.example.project5.pizza.Order;
import com.example.project5.pizza.Pizza;
import com.example.project5.pizza.Size;
import com.example.project5.pizza.Topping;

import java.util.ArrayList;

/**
 * Class that handles all the logic for letting the user build their own pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class BuildYourOwnActivity extends AppCompatActivity {
    private static final int NOT_SELECTED = -1;

    private Order currentOrder;
    private Topping[] availableToppings = Topping.values();

    private Pizza pizza;


    private RadioGroup sizeRadioBtnGroup;
    private RadioGroup styleRadioBtnGroup;
    private TextView crust;
    private Spinner orderSpinner;
    private ArrayAdapter<Order> orderAdapter;

    private ListView toppings;
    private ArrayAdapter<Topping> toppingAdapter;

    private TextView price;
    private Restaurant restaurant = Restaurant.getInstance();

    /**
     * This is the default constructor of the class.
     */
    public BuildYourOwnActivity()
    {
        
    }

    /**
     * Method used to set everything to their initial state (listView, spinner, textView...etc.).
     */
    private void clear()
    {
        RadioButton size = findViewById(sizeRadioBtnGroup.getCheckedRadioButtonId());
        size.setChecked(false);

        RadioButton style = findViewById(styleRadioBtnGroup.getCheckedRadioButtonId());
        style.setChecked(false);

        orderSpinner.setSelection(0);
        crust.setText("");

        //reset the background color of the selected toppings
        price.setText("");
        for (int i = 0 ; i < availableToppings.length; i++)
        {
            Topping topping =  toppingAdapter.getItem(i);
            if (topping!= null)
                topping.setSelected(false);
        }
        toppingAdapter.notifyDataSetChanged();
    }

    /**
     * Method used to update the spinner to reflect the new changes in the orders.
     */
    private void updateSpinner()
    {
        ArrayList<Order> modifiedOrders = (ArrayList<Order>) Restaurant.getInstance().
                getCurrentOrders().clone();
        modifiedOrders.add(0, new Order(0));
        orderAdapter= new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,  modifiedOrders);
        orderSpinner.setAdapter(orderAdapter);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    /**
     * Method to add the onClickListener to the addOrder button.
     * If the user filled all the required information, the pizza will be added to the order.
     * Otherwise, an error message will be displayed.
     */
    private void setupAddOrderButton() {
        ImageButton addToOrder = findViewById(R.id.addOrder2);
        addToOrder.setOnClickListener((view)->{
            //check if everything was filled.
            if (sizeRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED) {
                displayError(this, "You must select the size of the pizza");
                return;
            }

            else if (styleRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED) {
                displayError(this, "You must select the style of the pizza");
                return;
            }

            else if (currentOrder == null) {
                displayError(this, "You must select an order");
                return;
            }

            // it's a new order
            if (currentOrder.getNumber() == 0) {
                int currentOrderNumber = Order.orderNumber++;
                Order newOrder = new Order(currentOrderNumber);
                newOrder.getPizzas().add(pizza);
                restaurant.getCurrentOrders().add(newOrder);
                orderAdapter.notifyDataSetChanged();
                display(this, "New order successfully added");
            }

            else { //existing order
                currentOrder.getPizzas().add(pizza);
                display(this, "Pizza successfully added to order " +
                        currentOrder.toString());
            }
            updateSpinner();
            orderAdapter.notifyDataSetChanged();
            clear();
        });
    }

    /**
     * Method to add the onClickListener to the listView.
     * Whenever the user selects/deselects a topping, the background color will change and the
     * price will be updated.
     */
    private void setUpToppingListener() {
        toppings.setOnItemClickListener((parent,v,position,c)->{
            Topping topping = (Topping) parent.getItemAtPosition(position);
            if (!toppings.isActivated()) {
                if (sizeRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED)
                    displayError(this,
                            "You must first select the size of the pizza");

                else if (styleRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED)
                    displayError(this,
                            "You must also select the style of the pizza");
                return;
            }
            //check for max topping
            else if (!topping.isSelected() && pizza.getToppings().size() == Pizza.MAX_TOPPING) {
                displayError(this, "You can only add up to 5 toppings");
                return;
            }

            if (!topping.isSelected()) {
                pizza.getToppings().add(topping);
                v.setBackgroundColor(Color.MAGENTA);
            }

            else
                pizza.getToppings().remove(topping);

            topping.setSelected(!topping.isSelected());
            toppingAdapter.notifyDataSetChanged();

            if (styleRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED ||
                    sizeRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED)
                return;

            TextView price = findViewById(R.id.pizzaPrice);
            price.setText(String.format("$ %s", priceFormat.format(pizza.price())));
        });
    }

    /**
     * Method to add the onClickListener to the radio buttons.
     * Whenever the user changes the style, the crust and the price will be updated.
     */
    private void setUpStyleListener()
    {
            // add a listener when the user changes the style
            styleRadioBtnGroup = findViewById(R.id.style);
            styleRadioBtnGroup.setOnCheckedChangeListener((group,id)->{
            RadioButton selected = findViewById(group.getCheckedRadioButtonId());

            pizza =  selected.getText().toString().equals("Chicago Style") ? new ChicagoPizza().
                    createBuildYourOwn() : new NYPizza().createBuildYourOwn();

            crust = findViewById(R.id.crust);
            crust.setText(pizza.getCrust().toString());

            if ( sizeRadioBtnGroup.getCheckedRadioButtonId() != NOT_SELECTED)
            {
                toppings.setActivated(true);
                TextView price = findViewById(R.id.pizzaPrice);
                RadioButton selectedSize = findViewById(sizeRadioBtnGroup.
                        getCheckedRadioButtonId());
                pizza.setSize(Size.valueOf(selectedSize.getText().toString().toUpperCase()));
                price.setText(String.format("$ %s", priceFormat.format(pizza.price())));
            }

        });
    }

    /**
     * Method used to set up the spinner for the orders.
     */
    private void setupOrderSpinner()
    {
        orderSpinner = findViewById(R.id.orderSpinner2);
        ArrayList<Order> modifiedOrders = (ArrayList<Order>) Restaurant.getInstance().
                getCurrentOrders().clone();
        modifiedOrders.add(0, new Order(0));
        updateSpinner();

        orderSpinner.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            /**
             * Event handler implemented in the anonymous inner class.
             * @param parent the parent view.
             * @param view the view.
             * @param i the position of the item.
             * @param l the id of the item.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                currentOrder = (Order) parent.getItemAtPosition(i);
            }

            /**
             * Event handler implemented in the anonymous inner class.
             * @param adapterView the adapter view.
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        }));
    }

    /**
     * Method used to set up the necessary things (adapter, onClickListener,...) for this view.
     */
    private void setUp() {
        toppings = findViewById(R.id.toppings);
        sizeRadioBtnGroup = findViewById(R.id.size);
        sizeRadioBtnGroup.setOnCheckedChangeListener((group, id)->{
            RadioButton selected = findViewById(group.getCheckedRadioButtonId());
            if (pizza != null) {
                toppings.setActivated(true);
                pizza.setSize(Size.valueOf(selected.getText().toString().toUpperCase()));
                price = findViewById(R.id.pizzaPrice);
                price.setText(String.format("$ %s", priceFormat.format(pizza.price())));
            }
        });
       setUpStyleListener();

        //set up the spinner for the orders
        setupOrderSpinner();

        toppingAdapter = new ToppingAdapter(this, availableToppings);
        toppings.setAdapter(toppingAdapter);
        toppings.setActivated(false);

        setUpToppingListener();
        setupAddOrderButton();
    }

    /**
     * Method used to create the menu.
     * @param menu the menu to be created.
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Method used to handle the navigation between the views.
     * @param item the menu option being clicked.
     * @return true if the option was selected, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        String[] intentData = getIntent().toString().replace("}","").trim()
                .split("project5/.");

        String currentActivity = intentData[1];
        if (item.getItemId() == R.id.option_1) {
            if (!currentActivity.equals(MainActivity.class.getSimpleName())) {
                startActivity(intent);
                return true;
            }
        }

        //replace with your own activity
        else if (item.getItemId() == R.id.option_2) {
            if (!currentActivity.equals(MainActivity2.class.getSimpleName())) {
                intent = new Intent(this, MainActivity2.class);
                startActivity(intent);
                return true;
            }
        }

        //replace with your own activity
        else if (item.getItemId() == R.id.option_3) {
            if (!currentActivity.equals(MainActivity3.class.getSimpleName())) {
                intent = new Intent(this, MainActivity3.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method used to create the activity.
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_your_own);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                            systemBars.bottom);
                    return insets;
                });
        setUp();
    }
}