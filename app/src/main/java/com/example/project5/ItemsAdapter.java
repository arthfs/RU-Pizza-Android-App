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

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
     private List<Pizza> pizzas;
     private Pizza selectedPizza;
     private View selectedView;

    public View getSelectedView() {
        return selectedView;
    }

    public void setSelectedView(View selectedView) {
        this.selectedView = selectedView;
    }

    public  Pizza getSelectedPizza() {
        return selectedPizza;
    }

    public  void setSelectedPizza(Pizza selected) {
        selectedPizza = selected;
    }

    public ItemsAdapter (List<Pizza> pizzas)
    {
        this.pizzas = pizzas;
    }


    private void setUp()
    {

    }

    @NonNull
    @Override
    public ItemsAdapter.ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // set
        // up the view for each card
       View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view1_card, parent,
                false);


        return new ItemsHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public class ItemsHolder extends RecyclerView.ViewHolder {
        TextView sizeType;
        TextView style;
        TextView crust;
        TextView toppings;

        TextView price;

        ImageView imageView;


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
