package com.example.games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class PriceAdapter extends ArrayAdapter<GamePrice> {
    private Context context;
    private ArrayList<GamePrice> prices;

    public PriceAdapter(Context context,ArrayList arrayList){
        super(context,0,arrayList);
            }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View row=convertView;
       if(row==null){
            row= LayoutInflater.from(getContext()).inflate(R.layout.price_item,parent,false);
       }
       GamePrice gamePrice=prices.get(position);
        TextView StoreName =row.findViewById(R.id.store_name);
        TextView Price=row.findViewById(R.id.price);
        TextView Website=row.findViewById(R.id.website);

        StoreName.setText(gamePrice.getStoreName());
        Price.setText(gamePrice.getPrice()+"");


        return row;
    }
}
