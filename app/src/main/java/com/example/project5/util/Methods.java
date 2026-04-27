package com.example.project5.util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Class that allows to reuse some methods we use very often.
 * @author Marc-Arthur Saint Louis and Lamita Farroukh.
 */
public class Methods {
    public static DecimalFormat priceFormat =  new DecimalFormat();

    /**
     * Method used to create a toast and display its content.
     * @param context The context of the view.
     * @param message The message we want to display.
     */
    public static void display(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method used to create an alert dialog and display its content.
     * @param context The context of the view.
     * @param message The message we want to display.
     */
    public static void displayError(Context context, String message)
    {
        AlertDialog.Builder dialog =  new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.show();
    }

}
