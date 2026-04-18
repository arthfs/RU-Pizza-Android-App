package com.example.project5;

import static com.example.project5.util.Methods.display;
import static com.example.project5.util.Methods.priceFormat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project5.pizza.ChicagoPizza;
import com.example.project5.pizza.NYPizza;
import com.example.project5.pizza.Order;
import com.example.project5.pizza.Pizza;
import com.example.project5.pizza.Size;
import com.example.project5.pizza.Topping;

import java.util.ArrayList;

public class BuildYourOwnActivity extends AppCompatActivity {
    private static final int NOT_SELECTED = -1;
    //must be taken from the singleton class
    ArrayList<Order> currentOrders = new ArrayList<>();
    Order currentOrder;
    Topping currentTopping;
    Pizza pizza;
    private void setUp()
    {

        //add a listener for the size

        RadioGroup sizeRadioBtnGroup = findViewById(R.id.size);
        sizeRadioBtnGroup.setOnCheckedChangeListener((group, id)->{
            RadioButton selected = findViewById(group.getCheckedRadioButtonId());
            if (pizza != null)
            {
                pizza.setSize(Size.valueOf(selected.getText().toString().toUpperCase()));
                TextView price = findViewById(R.id.pizzaPrice);
                price.setText(String.format("$ %s", priceFormat.format(pizza.price())));
            }
        });

        // add a listener when the user changes the style
        RadioGroup styleRadioBtnGroup = findViewById(R.id.style);
        styleRadioBtnGroup.setOnCheckedChangeListener((group,id)->{
            RadioButton selected = findViewById(group.getCheckedRadioButtonId());

           pizza =  selected.getText().toString().equals("Chicago Style") ? new ChicagoPizza().
                   createBuildYourOwn() : new NYPizza().createBuildYourOwn();

           TextView crust = findViewById(R.id.crust);
           crust.setText(pizza.getCrust().toString());

           if ( sizeRadioBtnGroup.getCheckedRadioButtonId() != NOT_SELECTED)
           {
               TextView price = findViewById(R.id.pizzaPrice);
               RadioButton selectedSize = findViewById(sizeRadioBtnGroup.getCheckedRadioButtonId());
               pizza.setSize(Size.valueOf(selectedSize.getText().toString().toUpperCase()));
               price.setText(String.format("$ %s", priceFormat.format(pizza.price())));
           }

        });

        //just for testing
        currentOrders.add(new Order(0));
        currentOrders.add(new Order(1));
        currentOrders.add(new Order(2));

        //set up the spinner for the orders
        Spinner orderSpinner = findViewById(R.id.orderSpinner2);
        ArrayAdapter<Order> orderAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                        currentOrders);

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
        ListView toppings = findViewById(R.id.toppings);

        ArrayAdapter<Topping> toppingAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, Topping.values());
        toppings.setAdapter(toppingAdapter);

        //add the listener
        toppings.setOnItemClickListener((parent,v,position,c)->{

            int currentColor;
            if (v.getBackground() == null)
                currentColor = Color.TRANSPARENT;
            else
                currentColor = ((ColorDrawable) v.getBackground()).getColor();

            Topping topping = (Topping) parent.getItemAtPosition(position);
            if (currentColor == Color.TRANSPARENT)
                pizza.getToppings().add(topping);

            else
                pizza.getToppings().remove(topping);

            v.setBackgroundColor( currentColor == Color.TRANSPARENT? Color.MAGENTA :
                    Color.TRANSPARENT);

            //update the price
            if ( sizeRadioBtnGroup.getCheckedRadioButtonId() != NOT_SELECTED &&
                    styleRadioBtnGroup.getCheckedRadioButtonId()!= NOT_SELECTED)
            {
                TextView price = findViewById(R.id.pizzaPrice);
                price.setText(String.format("$ %s", priceFormat.format(pizza.price())));
            }
        });

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