package com.example.project5;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.project5.pizza.Topping;

import java.util.ArrayList;
import java.util.List;

public class ToppingAdapter extends ArrayAdapter<Topping> {

    public ToppingAdapter(Context context, Topping[] toppings) {
        super(context, android.R.layout.simple_list_item_1, toppings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        Topping topping = getItem(position);

        if (topping.isSelected()) {
            view.setBackgroundColor(Color.MAGENTA);
        }

        else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;
    }



}