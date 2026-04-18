package com.example.project5.util;

import android.content.Context;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Methods {
    public static DecimalFormat priceFormat =  new DecimalFormat();
    public static void display(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
