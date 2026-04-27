package com.example.project5;

import static com.example.project5.util.Methods.display;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project5.pizza.Order;
import com.example.project5.pizza.Pizza;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the adapter for the third activity it helps display placed orders in the recycler view.
 * @author Marc-Arthur and Lamita Farroukh
 */
public class PizzasAdapter extends RecyclerView.Adapter<PizzasAdapter.HolderOfPizzas> {
    private List<Pizza> pizzasInOrder;
    private Order currOrder;
    private View chosenPizzaCard;
    private Pizza chosePizza;

    private static HashMap<View, Pizza> pizzaHashM = new HashMap<>();
    private static final int SUBTOTAL_EMPTY = 0;
    private static final double TAX_PERCENT = 0.06625;
    private static final double NO_TAX = 0;

    /**
     * This is the constructor of the adapter.
     * @param allPizzas is the list of pizzas in a specific order.
     */
    public PizzasAdapter(ArrayList<Pizza> allPizzas, Order gottenOrder) {
        this.pizzasInOrder = allPizzas;
        this.currOrder = gottenOrder;
    }

    /**
     * This method gives a hashmap representing the pizzas card layouts.
     * @return a hash map where each card view represents one pizza.
     */
    public static HashMap<View, Pizza> getPizzaHmap() {
        return pizzaHashM;
    }

    /**
     * This method gets the Pizza object when a pizza card view is selected.
     * @return the pizza object that was selected.
     */
    public Pizza getSelectedPizza() {
        return chosePizza;
    }

    /**
     * This method changes the Pizza object when a pizza card view is selected.
     * @param selected the pizza object that was selected.
     */
    public void setSelectedPizza(Pizza selected) {
        chosePizza = selected;
    }

    /**
     * This method tells us which card view we are currently in.
     * @return the pizza card view.
     */
    public View getChosenPizzaCard() {
        return chosenPizzaCard;
    }

    /**
     * This method helps us keep track of the current card view.
     * @param chosenPizzaC is the  card view we want to keep track of that we are in.
     */
    public void setChosenPizzaCard(View chosenPizzaC) {
        chosenPizzaCard = chosenPizzaC;
    }

    /**
     * This method makes a pizza card view.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return a pizza  card view.
     */
    @NonNull
    @Override
    public PizzasAdapter.HolderOfPizzas onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View cView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view3_card, parent,
                false);


        return new PizzasAdapter.HolderOfPizzas(cView);
    }

    /**
     * This method fills up the card's information about the pizza.
     * @param holder   is the pizza card view.
     * @param position is the index of the pizza in the list of pizzas of the current orders.
     */
    @Override
    public void onBindViewHolder(@NonNull PizzasAdapter.HolderOfPizzas holder, int position) {

        DecimalFormat priceFormat = new DecimalFormat();
        priceFormat.applyPattern("#,###.00");

        Pizza ourPizza = pizzasInOrder.get(position);
        holder.sizeType.setText(String.format("%s %s", ourPizza.getSize(),
                ourPizza.getClass().getSimpleName()));
        holder.style.setText(ourPizza.getStyle());
        holder.price.setText(String.format("$ %s", priceFormat.format(ourPizza.price())));
        holder.crust.setText(ourPizza.getCrust().toString());
        holder.toppings.setText(ourPizza.stringToppingList());

        String type = ourPizza.getClass().getSimpleName();

        pizzaHashM.put(holder.itemView, pizzasInOrder.get(position));

    }

    /**
     * This method gives the number of pizzas in the list of pizzas.
     * It is used to know how many  the card views should be shown.
     * @return an integer representing the number of pizzas.
     */
    @Override
    public int getItemCount() {
        return pizzasInOrder.size();
    }

    /**
     * This class represents a view holder of a pizza card view.
     */
    public class HolderOfPizzas extends RecyclerView.ViewHolder {
        TextView sizeType;
        TextView style;
        TextView crust;
        View removePizzaButton;
        TextView toppings;
        TextView price;
        TextView subtotal;
        TextView orderTotal;
        TextView tax;
        Button cancelbtn;



        /**
         * This method makes the necessary updates when the remove button for a pizza is clicked.
         * It sets up a lister for the "Remove" button.
         */
        private void removingAPizza() {
            cancelbtn.setOnClickListener((view -> {
                int position = getBindingAdapterPosition();
                currOrder.getPizzas().remove(position);
                Spinner spinner = itemView.getRootView().findViewById(R.id.spinnerNums);
                if (currOrder.getPizzas().isEmpty())
                {
                    int last = Restaurant.getInstance().getCurrentOrders().size() - 1;
                    boolean wasLast = Restaurant.getInstance().getCurrentOrders().
                            get(last).getNumber() == currOrder.getNumber();
                    Restaurant.getInstance().getCurrentOrders().remove(currOrder);
                    ArrayAdapter<Order> adapter = (ArrayAdapter<Order>)  spinner.getAdapter();
                    adapter.notifyDataSetChanged();
                    Order selected;

                    if (wasLast && ! Restaurant.getInstance().getCurrentOrders().isEmpty()) {
                        selected = Restaurant.getInstance().getCurrentOrders().get(0);
                        currOrder = selected;
                    }

                    else
                        selected = (Order) spinner.getSelectedItem();

                    if (selected == null) {
                        RecyclerView recyclerView =  itemView.getRootView().
                                findViewById(R.id.recyclerView);
                        recyclerView.setAdapter(null);
                        pizzasInOrder = null;
                        return;
                    }

                    currOrder = selected;
                    pizzasInOrder = selected.getPizzas();
                    getFinaltotal();
                }

                getFinaltotal();
                notifyDataSetChanged();
            }));
        }


        /**
         * This method sets up the spinner that contains the order numbers.
         * @return the spinner containing the order numbers.
         */
        private Spinner settingUpSpinner() {
            Spinner orderNumSpinner = itemView.findViewById(R.id.spinnerNums);
            ArrayAdapter<Order> orderNumAdapter = new ArrayAdapter<Order>(itemView.getContext(),
                    android.R.layout.simple_spinner_item, Restaurant.getInstance()
                    .getCurrentOrders() );

            orderNumSpinner.setAdapter(orderNumAdapter); //shows in spinner
            orderNumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            return orderNumSpinner;
        }

        /**
         * This is a helper method that calculates the total of an order.
         * It sets it's text field in the view.
         */
        private void getFinaltotal() {
            orderTotal = itemView.getRootView().findViewById(R.id.total);
            double subTotalMoney = editedSubtotal();
            double taxMoney = editedTax();
            double finalTotalMoney= subTotalMoney + taxMoney;

            DecimalFormat priceFormat =  new DecimalFormat();
            priceFormat.applyPattern("#,###.00");
            String newtotal= priceFormat.format(finalTotalMoney);
            orderTotal.setText(newtotal);

        }

        /**
         * This method changes the tax amount in the view.
         * It does it based on if there are any pizzas or not.
         * sets the text field for tax 0.06625 if there are pizzas, otherwise it
         * sets it to 0.
         * @return double representing the new tax amount.
         */
        private double editedTax() {

            tax = itemView.getRootView().findViewById(R.id.tax);
            if(currOrder!=null) {
                ArrayList<Pizza> pizzasList = currOrder.getPizzas();
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
            for(int j = 0; j< currOrder.getPizzas().size();j++) {
                subtotal = subtotal + currOrder.getPizzas().get(j)
                        .price();
            }
            return subtotal;

        }

        /**
         * This is a helper method that changes the subtotal in the view.
         * It happens whenever a pizza is selected to be removed or all pizzas
         * are removed.
         * @return a double representing the new subtotal.
         */
        private double editedSubtotal() {
            subtotal = itemView.getRootView().findViewById(R.id.subTotal);

            if (currOrder != null) {
                ArrayList<Pizza> pizzasList = currOrder.getPizzas();
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
         * This method removes the selected pizza from current order.
         * It also updates the recycle view, and spinner when necessary.
         */
        private void removeThePizza(@NonNull View itemView) {
            removePizzaButton.setOnClickListener((view -> {
                HashMap<View, Pizza> adapterHashMap = PizzasAdapter.getPizzaHmap();
                currOrder.getPizzas().remove(chosePizza);

                if (currOrder != null) {
                    display(itemView.getContext(), "Pizza was successfully removed.");
                }

                if (currOrder.getPizzas().isEmpty()) {
                    Restaurant.getInstance().getCurrentOrders().remove(currOrder);
                    RecyclerView test = itemView.findViewById(R.id.recyclerView);
                    Spinner spinner = itemView.findViewById(R.id.spinnerNums);
                    spinner.setSelection(0);
                    display(itemView.getContext(), "Order was successfully removed. ");
                    currOrder = null;
                }

            }));

        }

        /**
         * This method is the constructor for HolderOfPizzas.
         * @param itemView represents the pizza card view.
         */
        public HolderOfPizzas(@NonNull View itemView) {
            super(itemView);
            sizeType = itemView.findViewById(R.id.size_type3);
            style = itemView.findViewById(R.id.pizza_style3);
            crust = itemView.findViewById(R.id.pizza_crust3);
            removePizzaButton = itemView.findViewById(R.id.cancelOrder3);

            toppings = itemView.findViewById(R.id.pizza_toppings3);
            price = itemView.findViewById(R.id.total3);
            cancelbtn = itemView.findViewById(R.id.cancelOrder3);
            removingAPizza();

        }

    }


}

