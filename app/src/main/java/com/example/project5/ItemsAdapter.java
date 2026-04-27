package com.example.project5;

import static com.example.project5.util.Methods.priceFormat;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project5.pizza.Pizza;

import java.util.List;

/**
 * Adapter class for the recycler view of the first view.
 * Each item in the recycler view is a card that contains the information about the pizza.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
     private List<Pizza> pizzas;
     private Pizza selectedPizza;
     private View selectedView;

    /**
     * Default constructor of the class.
     */
    public ItemsAdapter()
     {

     }

    /**
     * Method used to get the selected view.
     * @return the selected view.
     */
    public View getSelectedView() {
        return selectedView;
    }

    /**
     * Method used to set the selected view.
     * @param selectedView the selected view.
     */
    public void setSelectedView(View selectedView) {
        this.selectedView = selectedView;
    }

    /**
     * Method used to get the selected pizza.
     * @return the selected pizza.
     */
    public  Pizza getSelectedPizza() {
        return selectedPizza;
    }

    /**
     * Method used to set the selected pizza.
     * @param selected the selected pizza.
     */
    public  void setSelectedPizza(Pizza selected) {
        selectedPizza = selected;
    }

    /**
     * Constructor of the class.
     * @param pizzas the list of pizzas.
     */
    public ItemsAdapter (List<Pizza> pizzas)
    {
        this.pizzas = pizzas;
    }

    /**
     * Method used to create the view holder.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return a new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ItemsAdapter.ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // set up the view for each card
       View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view1_card, parent,
                false);

        return new ItemsHolder(view);
    }

    /**
     * Method used to bind the view holder.
     * @param holder   The ViewHolder to represent the contents of the item at the specified
     *                 position.
     * @param position The position of the item in the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemsHolder holder, int position) {

        //fill up the Card's information about the pizza

        priceFormat.applyPattern("#,###.00");

        Pizza pizza = pizzas.get(position);
        holder.sizeType.setText(String.format("%s %s", pizza.getSize(),
                pizza.getClass().getSimpleName()));
        holder.style.setText(pizza.getStyle());
        holder.price.setText(String.format("$ %s", priceFormat.format(pizza.price())));
        holder.crust.setText(pizza.getCrust().toString());
        holder.toppings.setText(pizza.stringToppingList());

        String type = pizza.getClass().getSimpleName();
        //select the appropriate picture
        if (pizza.getStyle().equals("Chicago-Style"))
        {
            holder.imageView.setImageResource( type.equals("BBQChicken") ?
                    R.drawable.chicago_style_bbqchicken : type.equals("Deluxe") ?
                    R.drawable.chicago_style_deluxe: R.drawable.chicago_style_meatzza );
        }

        else {
            holder.imageView.setImageResource( type.equals("BBQChicken") ?
                    R.drawable.new_york_style_bbqchicken : type.equals("Deluxe") ?
                    R.drawable.new_york_style_deluxe: R.drawable.new_york_style_meatzza);
        }
    }

    /**
     * Method used that gets the number of items in the list.
     * @return the number of items in the list as an integer.
     */
    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    /**
     * Class that holds the view of the card.
     * @author Marc-Arthur Saint Louis and Lamita Farroukh.
     */
    public class ItemsHolder extends RecyclerView.ViewHolder {
        TextView sizeType;
        TextView style;
        TextView crust;
        TextView toppings;

        TextView price;

        ImageView imageView;



        /**
         * Parameterized constructor of the class.
         * @param itemView the view associated with the card.
         */
        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            sizeType = itemView.findViewById(R.id.size_type);
            style = itemView.findViewById(R.id.pizza_style);
            crust = itemView.findViewById(R.id.pizza_crust);
            toppings = itemView.findViewById(R.id.pizza_toppings);
            price = itemView.findViewById(R.id.total);
            imageView =  itemView.findViewById(R.id.card_picture);

            itemView.setOnClickListener((View v)->{
                if (getBindingAdapter()!= null) {
                    if (selectedView == null)
                        selectedView = v;

                    selectedView.setBackgroundColor(Color.WHITE);
                    //position of the selected pizza

                    int position = getBindingAdapterPosition();
                    v.setBackgroundColor(Color.MAGENTA);

                    selectedPizza = ((ItemsAdapter) getBindingAdapter()).pizzas.get(position);
                }
                selectedView = v;
            });
        }
    }
}
