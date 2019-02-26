package com.example.tomascrd.c_r_s_h.core;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import com.example.tomascrd.c_r_s_h.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Utilities and functions
 *
 * @author Tomás Cardenal López
 */
public class Utils {
    /**
     * The application's context
     */
    private Context context;
    /**
     * String used for userInput
     */
    private String userInput;

    /**
     * Starts a utils object on the given context
     *
     * @param context
     */
    public Utils(Context context) {
        this.context = context;
    }

    /**
     * Parses a bitmap from assets
     *
     * @param file The path to the file on assets
     * @return The bitmap
     */
    public Bitmap getBitmapFromAssets(String file) {
        try {
            InputStream is = context.getAssets().open(file);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Generates a new pseudorandom integer value between the given values
     *
     * @param min the minimum bound, inclusive
     * @param max the maximum bound, inclusive
     * @return the pseudorandom number, or gets
     */
    public static int getRandom(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("The max value can't be less or equal than the min value");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Promots the user for text on a dialog
     * @param title The dialog title
     * @return The text input by the user, or null if no text was input
     */
    public AlertDialog.Builder textDialog(String title) {
        userInput = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

// Set up the input
        final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton(context.getString(R.string.dialogOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("textdialogtest","onclickok" );
                userInput = input.getText().toString();
            }
        });
        builder.setNegativeButton(context.getString(R.string.dialogCancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("textdialogtest","onclickcancel" );
                dialog.cancel();
            }
        });
        return builder;
    }

    public String getUserInput(){
        return this.userInput;
    }
}
