package com.example.project5.util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;

public class Methods {
    public static DecimalFormat priceFormat =  new DecimalFormat();
    public static void display(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void displayError(Context context, String message)
    {
        AlertDialog.Builder dialog =  new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.show();
    }

}
