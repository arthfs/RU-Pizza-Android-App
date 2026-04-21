package com.example.project5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project5.pizza.Pizza;

import java.util.ArrayList;

public class ItemsAdapter2 extends RecyclerView.Adapter<ItemsAdapter2.ItemsHolder>{
    ArrayList<Pizza> pizzas;
    ItemsAdapter2(ArrayList<Pizza> pizzas)
    {
        this.pizzas = pizzas;
    }

    @NonNull
    @Override
    public ItemsAdapter2.ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view3_card, parent,
                false);


        return new ItemsAdapter2.ItemsHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter2.ItemsHolder holder, int position) {
        Pizza pizza = pizzas.get(position);
        holder.size.setText(String.format("%s %s", pizza.getSize(),
                pizza.getClass().getSimpleName()));
        holder.style.setText(pizza.getStyle());
        holder.crust.setText(pizza.getCrust().toString());
        holder.toppings.setText(pizza.stringToppingList());
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public class ItemsHolder extends RecyclerView.ViewHolder {
        TextView size;
        TextView style;
        TextView crust;
        TextView toppings;
        TextView price;


       public  ItemsHolder(@NonNull View itemView) {
           super(itemView);
           size = itemView.findViewById(R.id.size_type3);
           style = itemView.findViewById(R.id.pizza_style3);
           crust = itemView.findViewById(R.id.pizza_crust3);
           toppings = itemView.findViewById(R.id.pizza_toppings3);
           price = itemView.findViewById(R.id.total3);
       }
    }
}
