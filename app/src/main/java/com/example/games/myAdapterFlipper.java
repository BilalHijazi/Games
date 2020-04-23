package com.example.games;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.games.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

public class myAdapterFlipper extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<StorageReference>pics;
    StorageReference storageRef= FirebaseStorage.getInstance().getReference("GamesPics");

    public myAdapterFlipper(Context context, List<StorageReference>pics) {
        this.context = context;
        this.pics = pics;
        this.inflater=LayoutInflater.from(context);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView=inflater.inflate(R.layout.flipper_item,null);
        final ImageView imageView=convertView.findViewById(R.id.flipper_image);
        pics.get(position).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).placeholder(R.drawable.ic_launcher_foreground)
                        .into(imageView);
            }
        });
                return convertView;



    }


    @Override
    public int getCount() {
        return pics.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
