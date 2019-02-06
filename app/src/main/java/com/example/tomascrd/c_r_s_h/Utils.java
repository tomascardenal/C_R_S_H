package com.example.tomascrd.c_r_s_h;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    Context context;

    public Utils(Context context){
        this.context=context;
    }



    int getPixels(float dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return (int)(dp*metrics.density);
    }


    public Bitmap escalaAnchura(int res, int nuevoAncho) {
        Bitmap bitmapAux=BitmapFactory.decodeResource(context.getResources(), res);
        if (nuevoAncho==bitmapAux.getWidth()) return bitmapAux;
        return Bitmap.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    public  Bitmap escalaAltura(int res, int nuevoAlto ) {
        Bitmap bitmapAux=BitmapFactory.decodeResource(context.getResources(), res);
        if (nuevoAlto==bitmapAux.getHeight()) return bitmapAux;
        return Bitmap.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) / bitmapAux.getHeight(), nuevoAlto, true);
    }

    public  Bitmap escalaAnchura(String fichero, int nuevoAncho) {
        Bitmap bitmapAux=getBitmapFromAssets(fichero);
        if (nuevoAncho==bitmapAux.getWidth()) return bitmapAux;
        return Bitmap.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    public  Bitmap escalaAltura(String fichero, int nuevoAlto ) {
        Bitmap bitmapAux=getBitmapFromAssets(fichero);
        if (nuevoAlto==bitmapAux.getHeight()) return bitmapAux;
        return Bitmap.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) / bitmapAux.getHeight(), nuevoAlto, true);
    }

    public Bitmap[] getFrames(int numImg, String dir, String tag, int width){
        Bitmap[] aux=new Bitmap[numImg];
        for (int i=0;i<numImg;i++) aux[i]=escalaAnchura(dir+"/"+tag+" ("+(i+1)+").png",width);
        return aux;
    }


    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is=context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    /* 1280x775
    public int getDpW(int pixels){
        return (int)((pixels/12.8)*PantallaInicioView.anchoPantalla)/100;
    }

    // 1280x775
    public int getDpH(int pixels){
        return (int)((pixels/7.75)*PantallaInicioView.altoPantalla)/100;
    }*/
}
