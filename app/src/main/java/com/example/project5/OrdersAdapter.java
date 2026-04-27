package com.example.project5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.HolderOfOrders> {

    private List<Pizza> pizzasInOrder;
    private Order currOrder;
    private View chosenPizzaCard;
    private Pizza chosePizza;

    private static HashMap<View, Pizza> pizzaHashM = new HashMap<>();

    /**
     * This is the constructor of the adapter.
     * @param allPizzas is the list of pizzas in a specific order.
     * @param gottenOrder is the order we are currently handling.
     */
    public OrdersAdapter(ArrayList<Pizza> allPizzas, Order gottenOrder) {
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
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return a pizza  card view.
     */
    @NonNull
    @Override
    public OrdersAdapter.HolderOfOrders onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View cView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view4_card, parent,
                false);


        return new OrdersAdapter.HolderOfOrders(cView);
    }

    /**
     * This method fills up the card's information about the pizza.
     * @param holder   is the pizza card view.
     * @param position is the index of the pizza in the list of pizzas of the current orders.
     */
    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.HolderOfOrders holder, int position) {

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
     * @author Marc-Arthur Saint Louis and Lamita Farroukh.
     */
    public class HolderOfOrders extends RecyclerView.ViewHolder {
        TextView sizeType;
        TextView style;
        TextView crust;
        TextView toppings;
        TextView price;


        /**
         * This method is the constructor for HolderOfOrders.
         * @param itemView represents the pizza card view.
         */
        public HolderOfOrders(@NonNull View itemView) {
            super(itemView);
            sizeType = itemView.findViewById(R.id.size_type4);
            style = itemView.findViewById(R.id.pizza_style4);
            crust = itemView.findViewById(R.id.pizza_crust4);
            toppings = itemView.findViewById(R.id.pizza_toppings4);
            price = itemView.findViewById(R.id.total4);
        }


    }

}


