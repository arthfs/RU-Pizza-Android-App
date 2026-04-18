package com.example.project5.util;

import android.content.Context;
import android.widget.Toast;

public class Methods {
    public static void display(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
