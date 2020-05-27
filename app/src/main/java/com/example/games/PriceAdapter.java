package com.example.games;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class PriceAdapter extends ArrayAdapter<GamePrice> {
    private Context context;
    private ArrayList<GamePrice> prices;

    public PriceAdapter(Context context, ArrayList<GamePrice> prices){
        super(context,0,prices);
        this.context=context;
        this.prices=prices;
            }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View row=convertView;
       if(row==null){
            row= LayoutInflater.from(getContext()).inflate(R.layout.price_item,parent,false);
       }
       final GamePrice gamePrice=prices.get(position);
        TextView StoreName =row.findViewById(R.id.store_name);
        TextView Price=row.findViewById(R.id.price);
        TextView Website=row.findViewById(R.id.website);
        TextView Platform=row.findViewById(R.id.price_platform);
        final ImageView Favicon=row.findViewById(R.id.favicon);

        StoreName.setText(gamePrice.getStoreName());
        Price.setText(gamePrice.getPrice()+"");
        Platform.setText(gamePrice.getPlatform());
        Website.setText(gamePrice.getStoreURL());

        new Thread(new Runnable() {
            public void run() {
                final Bitmap b = fetchFavicon(Uri.parse(gamePrice.getStoreURL()));
                Favicon.post(new Runnable() {
                    public void run() {
                        Favicon.setImageBitmap(b);
                    }
                });
            }
        }).start();


        return row;
    }



    private Bitmap fetchFavicon(Uri uri) {
        final Uri iconUri = uri.buildUpon().path("favicon.ico").build();

        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(iconUri.toString()).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            return BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            return null;
        }
    }
}
