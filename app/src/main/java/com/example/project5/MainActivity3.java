package com.example.project5;

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
 * This class is the activity for all the store's orders that were placed.
 * A user selects an order number from the spinner, it gets displayed in the recycler
 * view.
 * The user can click the cancel order button to remove the order.
 * @author Marc-Arthur and Lamita Farroukh
 */
public class MainActivity3 extends AppCompatActivity {

    Spinner orderNumSpinner;
    private ArrayList<Order> allCurrentOrders = new ArrayList<>();
    Order selectedOrder;
    private ArrayList<Order> allPlacedOrders = new ArrayList<>();
    OrdersAdapter rvAdapter;
    RecyclerView ordersRV;
    TextView total;
    View cancelButton;

    private static final double TAX_PERCENT = 0.06625;
    private static final double NO_TAX = 0;
    private static final double NO_SUBTOTAL = 0;
    private static final double NO_TOTAL = 0;

    /**
     * This method sets up the spinner that contains the order numbers.
     * @return the spinner containing the order numbers.
     */
    private Spinner settingUpSpinner() {
        orderNumSpinner = findViewById(R.id.spinner2);
        ArrayAdapter<Order> orderNumAdapter = new ArrayAdapter<Order>(this,
                android.R.layout.simple_spinner_item, Restaurant.getInstance().getPlacedOrders() );

        orderNumSpinner.setAdapter(orderNumAdapter);
        orderNumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return orderNumSpinner;
    }

    /**
     * This method lets the recycle view adapter display the pizzas of the current order.
     */
    private void editAdapter(Order currOrder) {
        rvAdapter = new OrdersAdapter(currOrder.getPizzas(),currOrder);
        ordersRV.setAdapter(rvAdapter);
        ordersRV.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * This method makes the necessary updates when the "Cancel Order" button is clicked.
     * It sets up a listener for this button.
     */
    private void clickingCancelPlaced() {
        cancelButton = findViewById(R.id.button2);
        cancelButton.setOnClickListener((view -> {
            if(selectedOrder != null) {
                Restaurant.getInstance().getPlacedOrders().remove(selectedOrder);
                settingUpSpinner();
                selectedOrder.getPizzas().clear();
                editAdapter(selectedOrder);
                selectedOrder = null;
                findTotal();
            }

            else {
                displayError(this,"No order selected to cancel.");
            }

        }));
    }

    /**
     * This method changes the tax amount in the view.
     * It does it based on if there are any pizzas or not.
     * It sets the text field for tax 0.06625 if there are pizzas,
     * otherwise it sets it to 0.
     * @return double representing the new tax amount.
     */
    private double changedTax() {
        if (selectedOrder != null) {
            ArrayList<Pizza> pizzasList = selectedOrder.getPizzas();
            boolean checkIfEmpty = pizzasList.isEmpty();
            if (!checkIfEmpty ) {
                double subT = getSubtotal();
                DecimalFormat priceFormat = new DecimalFormat();
                priceFormat.applyPattern("#,###.00");
                double retrievedTax = TAX_PERCENT * subT;
                return retrievedTax;
            } else {
                DecimalFormat priceFormat = new DecimalFormat();
                priceFormat.applyPattern("#,###.00");

                return NO_TAX;
            }
        } else {
            DecimalFormat priceFormat = new DecimalFormat();
            priceFormat.applyPattern("#,###.00");

            return NO_TAX;

        }
    }

    /**
     * This is a helper method that calculates the subtotal of an order.
     *
     * @return a double representing the subtotal.
     */
    private double getSubtotal() {
        double subtotal = 0;
        for (int j = 0; j < selectedOrder.getPizzas().size(); j++) {
            subtotal = subtotal + selectedOrder.getPizzas().get(j).price();
        }
        return subtotal;

    }

    /**
     * Helper method that calculates the total of an order.
     * It also sets it's text field in the view.
     */
    private void findTotal() {
        total = findViewById(R.id.totalPrice);
        double subTotalMoney = changedSubtotal();
        double taxMoney = changedTax();
        double finalTotalMoney = subTotalMoney + taxMoney;
        DecimalFormat priceFormat = new DecimalFormat();
        priceFormat.applyPattern("#,###.00");
        String newtotal = priceFormat.format(finalTotalMoney);
        total.setText(newtotal);
    }

    /**
     * This is a helper method that changes the subtotal in the view.
     * It happens whenever a pizza is selected to be removed or all pizzas
     * are removed.
     * @return a double representing the new subtotal.
     */
    private double changedSubtotal() {
        if (selectedOrder != null) {
            ArrayList<Pizza> pizzasList = selectedOrder.getPizzas();
            boolean checkIfEmpty = pizzasList.isEmpty();
            if (!checkIfEmpty ) {
                double retrievedSub = getSubtotal();
                return retrievedSub;
            } else {


                return NO_SUBTOTAL;
            }
        } else {
            return NO_SUBTOTAL;
        }
    }

    /**
     * This method sets up the recycler view based on if an order number was selected from spinner.
     */
    private void SetUp() {

        Spinner orderNumSpinner= settingUpSpinner();

        orderNumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Event handler implemented in the anonymous inner class.
             * It runs when  an order number is selected.
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOrder = Restaurant.getInstance().getPlacedOrders().get(position);


                findTotal();
                editAdapter(selectedOrder);



            }

            /**
             * Event handler implemented in the anonymous inner class.
             * It runs when no order number is selected.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedOrder = null;
                findTotal();
                return;
            }

        } );


    }


    /**
     * This method sets up the screen for the activity.
     * @param savedInstanceState contains the old data from the current activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_store_order);
        ordersRV = findViewById(R.id.recyclerView2);
        SetUp();
        clickingCancelPlaced();

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
}

