package com.example.project5;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import com.example.project5.pizza.Topping;

/**
 * Class that represents the adapter for the toppings.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class ToppingAdapter extends ArrayAdapter<Topping> {

    /**
     * Parameterized constructor of the class.
     * @param context the context.
     * @param toppings the toppings as an arrayList.
     */
    public ToppingAdapter(Context context, Topping[] toppings) {
        super(context, android.R.layout.simple_list_item_1, toppings);
    }

    /**
     * Getter method for the view.
     * @param position the position of the view.
     * @param convertView the view.
     * @param parent the parent view.
     * @return the view.
     */
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