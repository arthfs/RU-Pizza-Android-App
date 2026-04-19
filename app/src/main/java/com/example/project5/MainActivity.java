package com.example.project5;

import static com.example.project5.util.Methods.display;
import static com.example.project5.util.Methods.displayError;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project5.pizza.ChicagoPizza;
import com.example.project5.pizza.Order;
import com.example.project5.pizza.Pizza;
import com.example.project5.pizza.PizzaFactory;
import com.example.project5.pizza.Size;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private static int TYPES_OF_PIZZA = 3;
    private static int orderNumber = 1;
    private ArrayList<Order> currentOrders = new ArrayList<>();
    Order selectedOrder;
    ItemsAdapter pizzaAdapter;
    Spinner orderSpinner ;


    private void setUp()
    {
        //here I set up the spinner (combo box) for the orders number
        Order order = new Order(0);
        currentOrders.add(order);

        orderSpinner =  findViewById(R.id.orderSpinner);

       ArrayAdapter<Order> spinnerAdapter = new ArrayAdapter<Order>(this,
               android.R.layout.simple_spinner_item,  currentOrders);

       orderSpinner.setAdapter(spinnerAdapter);
       spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       // I added a listener
       orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //anonymous inner class

           /**
            * Event handler implemented in the anonymous inner class.
            */
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                   selectedOrder = (Order) parent.getItemAtPosition(position);

               //display(parent.getContext(), selectedOrder.toString());
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
               //it's fine to leave it empty
           }
       });

        // I added a listener for the shopping cart button.
        View addOrderButton =  findViewById(R.id.addOrder);
        addOrderButton.setOnClickListener((view -> {

            //get the pizza
            Pizza pizza = pizzaAdapter.getSelectedPizza();

            if (pizza == null)
            {
                displayError(this,"You must select a pizza");
                return;
            }

            if (selectedOrder == null)
            {
                displayError(this,"You must select an order number");
                return;
            }
            // it's a new order
            if (selectedOrder.getNumber() == 0)
            {
                Order newOrder = new Order(orderNumber++);
                newOrder.getPizzas().add(pizza);
                spinnerAdapter.add(newOrder);
                display(this, "New order successfully added");
            }

            else //existing order
            {
                selectedOrder.getPizzas().add(pizza);
                display(this, "Pizza successfully added to order " +
                        selectedOrder.toString());
            }
            clear();
        }));
    }

    private void clear()
    {
        orderSpinner.setSelection(0);
        pizzaAdapter.getSelectedView().setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //get the recyclerview from the UI
        RecyclerView recyclerView = findViewById(R.id.pizzaList);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // I'm adding all the types of pizzas
        ArrayList<Pizza> pizzas = new ArrayList<>();

        PizzaFactory chicagoFactory = new ChicagoPizza();
        for (int i = 0; i < TYPES_OF_PIZZA; i++  )
        {
            for (int j = 0; j < Size.values().length; j++)
            {
                Pizza pizza = i == 0 ? chicagoFactory.createDeluxe(): i == 1 ?
                        chicagoFactory.createBBQChicken(): chicagoFactory.createMeatzza();

                pizza.setSize(Size.values()[j]);
                pizzas.add(pizza);
            }
        }


        // add all the pizzas to the adapter
        pizzaAdapter = new ItemsAdapter(pizzas);
        recyclerView.setAdapter(pizzaAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setUp();

        View buildYourOwnBtn = findViewById(R.id.buildYourOwnBtn);
        buildYourOwnBtn.setOnClickListener((View) ->{
            Intent intent = new Intent(this, BuildYourOwnActivity.class);
            startActivity(intent);
        });

    }
}