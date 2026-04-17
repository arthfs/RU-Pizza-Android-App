package com.example.project5;

import android.content.Context;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static int TYPES_OF_PIZZA = 3;
    private static int orderNumber = 1;
    private ArrayList<Order> currentOrders = new ArrayList<>();
    Order selectedOrder;
    ItemsAdapter adapter;


    private void display(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    private void setUp()
    {
        Order order = new Order(0);
        currentOrders.add(order);

       Spinner spinner =  findViewById(R.id.orderSpinner);
        ArrayList<CharSequence> currOrders = new ArrayList<>();
        for (Order tempOrder: currentOrders)
            currOrders.add(tempOrder.toString());

       ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(this,
               android.R.layout.simple_spinner_item,  currOrders);

       spinner.setAdapter(spinnerAdapter);
       spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //anonymous inner class

           /**
            * Event handler implemented in the anonymous inner class.
            */
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedOrder = currentOrders.get(position);
               display(parent.getContext(), selectedOrder.toString());
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
               //it's fine to leave it empty
           }
       });

        View addOrderButton =  findViewById(R.id.addOrder);
        addOrderButton.setOnClickListener((view -> {
            HashMap<View, Pizza> ref = ItemsAdapter.getViewPizza();
            Pizza pizza = ref.get(ItemsAdapter.getSelectedPizza());
            String message = pizza.toString();

            // it's a new order
            if (selectedOrder.getNumber() == 0)
            {
                Order newOrder = new Order(orderNumber++);
                newOrder.getPizzas().add(pizza);
                spinnerAdapter.add(newOrder.toString());
                display(this, "New order successfully added");
            }

            else //existing order
            {
                selectedOrder.getPizzas().add(pizza);
                display(this, "Pizza successfully added to order " +
                        selectedOrder.toString());
            }
            // Prints to Logcat with tag "MyTag"
            Log.d("MyTag",Integer.toString(currentOrders.size()));

        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.pizzaList);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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



        adapter = new ItemsAdapter(pizzas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setUp();
    }
}