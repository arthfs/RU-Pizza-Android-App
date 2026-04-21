package com.example.project5;

import static com.example.project5.util.Methods.display;
import static com.example.project5.util.Methods.displayError;
import static com.example.project5.util.Methods.priceFormat;

import android.graphics.Color;

import android.os.Bundle;
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

public class BuildYourOwnActivity extends AppCompatActivity {
    private static final int NOT_SELECTED = -1;

    Order currentOrder;
    Topping[] availableToppings = Topping.values();
    Topping currentTopping;
    Pizza pizza;


    RadioGroup sizeRadioBtnGroup;
    RadioGroup styleRadioBtnGroup;
    TextView crust;
    Spinner orderSpinner;


    ListView toppings;
    ArrayAdapter<Topping> toppingAdapter;

    TextView price;
    Restaurant restaurant = Restaurant.getInstance();

    ArrayList<View> selectedToppings;
    private void setUp()
    {
        selectedToppings = new ArrayList<>();
        toppings = findViewById(R.id.toppings);
        //add a listener for the size

        sizeRadioBtnGroup = findViewById(R.id.size);
        sizeRadioBtnGroup.setOnCheckedChangeListener((group, id)->{
            RadioButton selected = findViewById(group.getCheckedRadioButtonId());
            if (pizza != null)
            {
                toppings.setActivated(true);
                pizza.setSize(Size.valueOf(selected.getText().toString().toUpperCase()));
                price = findViewById(R.id.pizzaPrice);
                price.setText(String.format("$ %s", priceFormat.format(pizza.price())));
            }
        });

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
               RadioButton selectedSize = findViewById(sizeRadioBtnGroup.getCheckedRadioButtonId());
               pizza.setSize(Size.valueOf(selectedSize.getText().toString().toUpperCase()));
               price.setText(String.format("$ %s", priceFormat.format(pizza.price())));
           }

        });

        //just for testing
       // currentOrders.add(new Order(0));
        //currentOrders.add(new Order(1));
        //currentOrders.add(new Order(2));

        //set up the spinner for the orders
        orderSpinner = findViewById(R.id.orderSpinner2);
        ArrayAdapter<Order> orderAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                        restaurant.getCurrentOrders());

        orderSpinner.setAdapter(orderAdapter);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        orderSpinner.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                currentOrder = (Order) parent.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }));

        //set up the toppings


        toppingAdapter = new ToppingAdapter(this, availableToppings);

        toppings.setAdapter(toppingAdapter);
        toppings.setActivated(false);

        //add the listener
        toppings.setOnItemClickListener((parent,v,position,c)->{
            Topping topping = (Topping) parent.getItemAtPosition(position);

            if (!toppings.isActivated())
            {
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
                displayError(this,
                        "You can only add up to 5 toppings");
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

            //update the price
            if (styleRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED ||
                    sizeRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED)
                return;


            TextView price = findViewById(R.id.pizzaPrice);
            price.setText(String.format("$ %s", priceFormat.format(pizza.price())));

        });


        ImageButton addToOrder = findViewById(R.id.addOrder2);
        addToOrder.setOnClickListener((view)->{
            //check if everything was filled.
            if (sizeRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED)
            {
                displayError(this, "You must select the size of the pizza");
                return;
            }

            else if (styleRadioBtnGroup.getCheckedRadioButtonId() == NOT_SELECTED)
            {
                displayError(this, "You must select the style of the pizza");
                return;
            }

            else if (currentOrder == null)
            {
                displayError(this, "You must select an order");
                return;
            }

            // it's a new order
            if (currentOrder.getNumber() == 0)
            {
                int currentOrderNumber = Order.orderNumber++;
                        // MainActivity.getOrderNumber();
                Order newOrder = new Order(currentOrderNumber);


                newOrder.getPizzas().add(pizza);
                restaurant.getCurrentOrders().add(newOrder);
                orderAdapter.notifyDataSetChanged();
                //orderAdapter.add(newOrder);
                display(this, "New order successfully added");
            }

            else //existing order
            {
                currentOrder.getPizzas().add(pizza);
                display(this, "Pizza successfully added to order " +
                        currentOrder.toString());
            }
            clear();
        });

    }


    private void clear()
    {
           RadioButton size = findViewById(sizeRadioBtnGroup.getCheckedRadioButtonId());
           size.setChecked(false);

           RadioButton style = findViewById(styleRadioBtnGroup.getCheckedRadioButtonId());
           style.setChecked(false);

            crust.clearComposingText();
            orderSpinner.setSelection(0);

            //reset the background color of the selected toppings
            price.clearComposingText();
            for (int i = 0 ; i < availableToppings.length; i++)
            {
                Topping topping =  toppingAdapter.getItem(i);
                if (topping!= null)
                    topping.setSelected(false);
            }
            toppingAdapter.notifyDataSetChanged();

    }

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