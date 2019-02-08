package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utilities and functions
 *
 * @author Tomás Cardenal López
 */
public class Utils {
    /**
     * The application's context
     */
    Context context;

    /**
     * Starts a utils object on the given context
     * @param context
     */
    public Utils(Context context){
        this.context=context;
    }

    /**
     * Parses a bitmap from assets
     * @param file
     * @return
     */
    public Bitmap getBitmapFromAssets(String file) {
        try {
            InputStream is=context.getAssets().open(file);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }
}
