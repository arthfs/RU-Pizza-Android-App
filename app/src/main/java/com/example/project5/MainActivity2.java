package com.example.project5;

import static com.example.project5.util.Methods.display;
import static com.example.project5.util.Methods.displayError;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project5.pizza.Order;
import com.example.project5.pizza.Pizza;

import java.text.DecimalFormat;
import java.util.ArrayList;



/**
 * This class is the activity where orders are placed.
 * A  user selects an  order number  from the spinner, it gets displayed in the
 * recycler view. The user can select a pizza from the list view and click the
 * "remove" button to remove a pizza from the order,or "Remove All"
 * to remove all pizzas, or "place order" to place an order.
 * @author Marc-Arthur and Lamita Farroukh
 */
public class MainActivity2 extends AppCompatActivity {
    private ArrayList<Order> allCurrentOrders = new ArrayList<>();
    Order selectedOrder;
    private ArrayList<Order> allPlacedOrders = new ArrayList<>();
    Pizza currPizza;
    PizzasAdapter rvAdapter;
    RecyclerView pizzasRV;
    TextView subtotal;
    TextView orderTotal;
    TextView tax;
    View  placeOrderButton;
    View removeAllButton;
    Spinner orderNumSpinner;


    private static final int SUBTOTAL_EMPTY = 0;
    private static final double TAX_PERCENT = 0.06625;
    private static final double NO_TAX = 0;


    /**
     * This method sets up the spinner that contains the order numbers.
     * @return the spinner containing the order numbers.
     */
    private Spinner settingUpSpinner() {
        orderNumSpinner = findViewById(R.id.spinnerNums);
        ArrayAdapter<Order> orderNumAdapter = new ArrayAdapter<Order>(this,
                android.R.layout.simple_spinner_item, Restaurant.getInstance().getCurrentOrders() );

        orderNumSpinner.setAdapter(orderNumAdapter);
        orderNumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return orderNumSpinner;
    }

    /**
     * This method lets the recycle view adapter display the pizzas of the current order.
     */
    private void editAdapter(ArrayList<Pizza> listOfPizzas) {
        rvAdapter = new PizzasAdapter(listOfPizzas, selectedOrder);
        pizzasRV.setAdapter(rvAdapter);
        pizzasRV.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * This is a helper method that calculates the total of an order.
     * It sets it's text field in the activity.
     */
    private void getFinaltotal() {
        double subTotalMoney = editedSubtotal();
        double taxMoney = editedTax();
        double finalTotalMoney= subTotalMoney + taxMoney;

        DecimalFormat priceFormat =  new DecimalFormat();
        priceFormat.applyPattern("#,###.00");
        String newtotal= priceFormat.format(finalTotalMoney);
        orderTotal.setText(newtotal);

    }

    /**
     * This method changes the tax amount in the activity.
     * It does it based on if there are any pizzas or not.
     * sets the text field for tax 0.06625 if there are pizzas, otherwise it
     * sets it to 0.
     * @return double representing the new tax amount.
     */
    private double editedTax() {
        if(selectedOrder!=null) {
            ArrayList<Pizza> pizzasList = selectedOrder.getPizzas();
            boolean checkIfEmpty = pizzasList.isEmpty();

            if (checkIfEmpty == false) {
                double subT = getSubtotal();
                DecimalFormat priceFormat =  new DecimalFormat();
                priceFormat.applyPattern("#,###.00");
                double retrievedTax = TAX_PERCENT * subT;
                String newTax = priceFormat.format(retrievedTax);
                tax.setText(newTax);
                return retrievedTax;
            }

            else{
                DecimalFormat priceFormat =  new DecimalFormat();
                priceFormat.applyPattern("#,###.00");
                String newTax = priceFormat.format(NO_TAX);
                tax.setText(newTax);
                return NO_TAX;
            }

        }

        else{
            DecimalFormat priceFormat =  new DecimalFormat();
            priceFormat.applyPattern("#,###.00");

            String newTax = priceFormat.format(NO_TAX);
            tax.setText(newTax);
            return NO_TAX;

        }

    }

    /**
     * This is a helper method that calculates the subtotal of an order.
     * @return a double representing the subtotal.
     */
    private double getSubtotal() {
        double subtotal = 0;
        for(int j = 0; j< selectedOrder.getPizzas().size();j++) {
            subtotal = subtotal + selectedOrder.getPizzas().get(j)
                    .price();
        }
        return subtotal;

    }

    /**
     * This method makes the necessary updates when the "remove all" button is clicked.
     * It sets up a listener for this button.
     */
    private void removingAllPizza() {
        removeAllButton.setOnClickListener((view -> {

            if (rvAdapter == null) {
                displayError(this,"No Order found to be placed.");
                return;
            }

            if(rvAdapter.getCurrOrder() != null) {
                if(rvAdapter.getCurrOrder().getPizzas().isEmpty() == false) {
                    rvAdapter.getCurrOrder().getPizzas().clear();
                    Restaurant.getInstance().getCurrentOrders().remove(rvAdapter.getCurrOrder());
                    editAdapter(rvAdapter.getCurrOrder().getPizzas());
                    settingUpSpinner();
                    selectedOrder = null;
                    rvAdapter.setCurrOrder(null);
                    currPizza = null;
                    display(this,"Order was successfully removed. ");

                }

                else {
                    displayError(this,"No order was found to remove pizzas.");

                }
                getFinaltotal();

            }

            else {
                displayError(this,"No order was found to remove pizzas.");
            }

        }));
    }

    /**
     * This method makes the necessary updates when the place order button is clicked.
     * It sets up a listener for this button.
     */
    private void placingAnOrder() {
        placeOrderButton.setOnClickListener((view -> {
            if (rvAdapter == null) {
                displayError(this,"No Order found to be placed.");
                return;
            }

            if(rvAdapter.getCurrOrder() != null) {
                if(rvAdapter.getCurrOrder().getPizzas().isEmpty() == false) {
                    Restaurant.getInstance().getPlacedOrders().add(rvAdapter.getCurrOrder());
                    Restaurant.getInstance().getCurrentOrders().remove(rvAdapter.getCurrOrder());
                    settingUpSpinner();
                    ArrayList<Pizza> listCopyPizzas = (ArrayList<Pizza>) rvAdapter.getCurrOrder()
                            .getPizzas().clone();
                    listCopyPizzas.clear();
                    editAdapter(listCopyPizzas);
                    selectedOrder = null;
                    rvAdapter.setCurrOrder(null);
                    currPizza = null;
                    display(this,"Order was successfully placed. ");
                    getFinaltotal();
                }

                else {
                    getFinaltotal();
                    displayError(this,"No Order found to be placed.");
                }
            }

            else {
                displayError(this,"No Order found to be placed.");
            }

        }));
    }

    /**
     * This is a helper method that changes the subtotal in the activity.
     * It happens whenever a pizza is selected to be removed or all pizzas
     * are removed.
     * @return a double representing the new subtotal.
     */
    private double editedSubtotal() {
        if (selectedOrder != null) {
            ArrayList<Pizza> pizzasList =selectedOrder.getPizzas();
            boolean checkIfEmpty = pizzasList.isEmpty();
            if (checkIfEmpty == false) {
                DecimalFormat priceFormat =  new DecimalFormat();
                priceFormat.applyPattern("#,###.00");
                double retrievedSub = getSubtotal();
                String newSub = priceFormat.format(retrievedSub);
                subtotal.setText(newSub);
                return retrievedSub;
            }

            else{
                DecimalFormat priceFormat =  new DecimalFormat();
                priceFormat.applyPattern("#,###.00");
                String newSub = priceFormat.format(SUBTOTAL_EMPTY);
                subtotal.setText(newSub);
                return SUBTOTAL_EMPTY;
            }

        }
        else{
            DecimalFormat priceFormat =  new DecimalFormat();
            priceFormat.applyPattern("#,###.00");
            String newSub = priceFormat.format(SUBTOTAL_EMPTY);
            subtotal.setText(newSub);
            return SUBTOTAL_EMPTY;

        }

    }

    /**
     * This method sets up the recycler view based on if an order number was selected from spinner.
     */
    private void setUp() {

        pizzasRV = findViewById(R.id.recyclerView);
        Spinner orderNumSpinner= settingUpSpinner();

        orderNumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Event handler implemented in the anonymous inner class.
             * It runs when  an order number is selected.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOrder = Restaurant.getInstance().getCurrentOrders().get(position);

                getFinaltotal();
                editAdapter(selectedOrder.getPizzas());



            }

            /**
             * Event handler implemented in the anonymous inner class.
             * It runs when no order number is selected.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedOrder = null;
                getFinaltotal();
            }

        } );

    }

    /**
     * This method makes the menu of the app.
     * @param menu is the Menu object provided.
     * @return a boolean that is true that allows to show the actual  menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * This method gets the option in the menu  selected and switches to the correct activity.
     * @param item is the menu item that was selected.
     * @return a boolean that is true that allows to show the activity according to the selected.
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

        else if (item.getItemId() == R.id.option_2) {
            if (!currentActivity.equals(MainActivity2.class.getSimpleName())) {
                intent = new Intent(this, MainActivity2.class);
                startActivity(intent);
                return true;
            }
        }

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
     * This method sets up the screen for the activity.
     * @param savedInstanceState contains the old data from the current activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order);

        subtotal = findViewById(R.id.subTotal);
        orderTotal = findViewById(R.id.total);
        tax = findViewById(R.id.tax);

        removeAllButton = findViewById(R.id.removeAll);
        placeOrderButton = findViewById(R.id.placeOrder);

        setUp();
        placingAnOrder();
        removingAllPizza();


    }



}

