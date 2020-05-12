package com.example.games.ui.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.games.Article;
import com.example.games.ArticleActivity;
import com.example.games.MainActivity;
import com.example.games.NewsAdapter;
import com.example.games.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
FirebaseDatabase database=FirebaseDatabase.getInstance();
DatabaseReference newsRef,breakingNews,allRef;
FirebaseStorage storage=FirebaseStorage.getInstance();
StorageReference newsPicsRef=storage.getReference("NewsPics");



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_news, container, false);

        return root;
    }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);
         SearchView searchView=view.findViewById(R.id.article_search_bar);

         newsRef=database.getReference("NewsAndArticles");
         allRef=newsRef.child("AllArticles");
         breakingNews=newsRef.child("breakingNews");


         if(getArguments()!=null&&getArguments().getInt("tosearch")==1){
             getArguments().clear();
             searchView.setIconified(false) ;
         }

         final TextView Title1=view.findViewById(R.id.article1_title);
         final TextView Title2=view.findViewById(R.id.article2_title);
         final TextView Title3=view.findViewById(R.id.article3_title);
         final TextView Title4=view.findViewById(R.id.article4_title);
         final TextView url1=view.findViewById(R.id.article1_url);
         final TextView url2=view.findViewById(R.id.article2_url);
         final TextView url3=view.findViewById(R.id.article3_url);
         final TextView url4=view.findViewById(R.id.article4_url);
         final ImageView Cover1=view.findViewById(R.id.article1_cover);
         final ImageView Cover2=view.findViewById(R.id.article2_cover);
         final ImageView Cover3=view.findViewById(R.id.article3_cover);
         final ImageView Cover4=view.findViewById(R.id.article4_cover);
         CardView Card1=view.findViewById(R.id.card1);
         CardView Card2=view.findViewById(R.id.card2);
         CardView Card3=view.findViewById(R.id.card3);
         CardView Card4=view.findViewById(R.id.card4);

         RecyclerView recyclerView=view.findViewById(R.id.articles_recyclerview);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         final ArrayList<Article> articles=new ArrayList<>();
         final NewsAdapter adapter=new NewsAdapter(getContext(),articles);
         recyclerView.setAdapter(adapter);

         final LinearLayout layout=view.findViewById(R.id.tohide);



         searchView.setOnSearchClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 layout.setVisibility(View.GONE);
             }
         });
         searchView.setOnCloseListener(new SearchView.OnCloseListener() {
             @Override
             public boolean onClose() {
                 layout.setVisibility(View.VISIBLE);
                 return false;
             }
         });

      breakingNews.child("article1").addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Article article=dataSnapshot.getValue(Article.class);
              Title1.setText(article.getTitle());
              url1.setText(article.getArticleURL());
              newsPicsRef.child(article.getCoverId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                  @Override
                  public void onSuccess(Uri uri) {
                      Glide.with(getContext()).load(uri).centerCrop().placeholder(R.drawable.ic_launcher_foreground).
                              into(Cover1);
                  }
              });
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

         breakingNews.child("article2").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Article article=dataSnapshot.getValue(Article.class);
                 Title2.setText(article.getTitle());
                 url2.setText(article.getArticleURL());
                 newsPicsRef.child(article.getCoverId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         Glide.with(getContext()).load(uri).centerCrop().placeholder(R.drawable.ic_launcher_foreground).
                                 into(Cover2);
                     }
                 });
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

         breakingNews.child("article3").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Article article=dataSnapshot.getValue(Article.class);
                 Title3.setText(article.getTitle());
                 url3.setText(article.getArticleURL());
                 newsPicsRef.child(article.getCoverId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         Glide.with(getContext()).load(uri).centerCrop().placeholder(R.drawable.ic_launcher_foreground).
                                 into(Cover3);
                     }
                 });
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

         breakingNews.child("article4").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Article article=dataSnapshot.getValue(Article.class);
                 Title4.setText(article.getTitle());
                 url4.setText(article.getArticleURL());
                 newsPicsRef.child(article.getCoverId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         Glide.with(getContext()).load(uri).centerCrop().placeholder(R.drawable.ic_launcher_foreground).
                                 into(Cover4);
                     }
                 });
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

         Card1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getContext(), ArticleActivity.class);
                 intent.putExtra("url",url1.getText().toString());
                 startActivity(intent);
             }
         });
         Card2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getContext(), ArticleActivity.class);
                 intent.putExtra("url",url2.getText().toString());
                 startActivity(intent);

             }
         });
         Card3.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getContext(), ArticleActivity.class);
                 intent.putExtra("url",url3.getText().toString());
                 startActivity(intent);

             }
         });
         Card4.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getContext(), ArticleActivity.class);
                 intent.putExtra("url",url4.getText().toString());
                 startActivity(intent);

             }
         });

         allRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if (dataSnapshot.exists()){
                     for (DataSnapshot ds:dataSnapshot.getChildren()){
                         articles.add(ds.getValue(Article.class));
                     }
                     adapter.notifyDataSetChanged();
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });












     }
 }