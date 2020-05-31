package com.example.games;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements Filterable {
    private List<Article> mAricles;
    private LayoutInflater mInflater;
    private List<Article> filteredData;
    private Context context;
    public MyFilter mFilter;
    StorageReference storageRef= FirebaseStorage.getInstance().getReference("NewsPics");
    public NewsAdapter(Context context, ArrayList<Article> data) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.mAricles = data;
        this.filteredData=new ArrayList<>();
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
    public Filter getFilter() {
        if (mFilter == null){
            filteredData.clear();
            filteredData.addAll(this.mAricles);
            mFilter = new NewsAdapter.MyFilter(this,filteredData);
        }
        return mFilter;
    }
    private static class MyFilter extends Filter {
        private final NewsAdapter myAdapter;
        private final List<Article> originalList;
        private final List<Article> filteredList;
        private MyFilter(NewsAdapter myAdapter, List<Article> originalList) {
            this.myAdapter = myAdapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<Article>();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0){
                filteredList.addAll(originalList);
            }else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for ( Article article : originalList){
                    if (article.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(article);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            myAdapter.mAricles.clear();
            myAdapter.mAricles.addAll((ArrayList<Article>)filterResults.values);
            myAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public int getItemCount() {
        return mAricles.size();
    }
}
