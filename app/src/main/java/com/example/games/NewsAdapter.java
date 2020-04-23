package com.example.games;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<Article> mAricles;
    private LayoutInflater mInflater;
    private Context context;
    StorageReference storageRef= FirebaseStorage.getInstance().getReference("NewsPics");

    public NewsAdapter(Context context, ArrayList<Article> data) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.mAricles = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView Title,Url;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.article_name);
            Url=itemView.findViewById(R.id.article_url);
            imageView=itemView.findViewById(R.id.article_image);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context,ArticleActivity.class);
            intent.putExtra("url",Url.getText().toString());
            context.startActivity(intent);
        }

    }

    @Override
    public void onBindViewHolder(final NewsAdapter.ViewHolder holder, int position) {
        String title = mAricles.get(position).getTitle();
        String url=mAricles.get(position).getArticleURL();
        holder.Title.setText(title);
        holder.Url.setText(url);

            storageRef.child(mAricles.get(position).getCoverId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri.toString()).
                            centerCrop().placeholder(R.drawable.ic_launcher_foreground).into(holder.imageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });



    }

    @Override
    public int getItemCount() {
        return mAricles.size();
    }



}
