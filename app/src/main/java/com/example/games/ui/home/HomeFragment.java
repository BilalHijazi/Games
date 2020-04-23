package com.example.games.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.games.Article;
import com.example.games.ArticleActivity;
import com.example.games.Game;
import com.example.games.GameActivity;
import com.example.games.MainActivity;
import com.example.games.R;
import com.example.games.Review;
import com.example.games.ui.games.GamesFragment;
import com.example.games.ui.news.NewsFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    FloatingActionButton fab;
    FloatingActionButton Subfab1;
    FloatingActionButton Subfab2;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference gamesRef=database.getReference("VideoGames");
    DatabaseReference newsRef=database.getReference("NewsAndArticles").child("breakingNews");
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference gamesStorage=storage.getReference("GamesPics");
    StorageReference newsStorage=storage.getReference("NewsPics");

    Game PopGame,HighGame,NewGame;
    boolean isFABOpen=false;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        fab = view.findViewById(R.id.fab);
        Subfab1 = view.findViewById(R.id.subfab1);
        Subfab2 = view.findViewById(R.id.subfab2);
        final CardView PopGameCard=view.findViewById(R.id.pop_game);
        final TextView PopGameName=view.findViewById(R.id.pop_game_name);
        final TextView PopGameRate=view.findViewById(R.id.pop_game_rate);
        final TextView ReviewCount=view.findViewById(R.id.review_count);

        final CardView HighGameCard=view.findViewById(R.id.highest_rate);
        final TextView HighestGameName=view.findViewById(R.id.highest_rate_title);
        final TextView HighestGameRate=view.findViewById(R.id.highest_rate_rating);
        final TextView HighestGameReviewCount=view.findViewById(R.id.highest_rate_review_count);

        final CardView NewGameCard=view.findViewById(R.id.new_game);
        final TextView NewGameName=view.findViewById(R.id.new_game_title);
        final TextView ReleaseDate=view.findViewById(R.id.release_date);

        final CardView ArticleCard =view.findViewById(R.id.top_article);
        final TextView ArticleTitle=view.findViewById(R.id.article_title);
        final TextView Url=view.findViewById(R.id.url);



        PopGame=new Game();
        PopGame.setReviews(new ArrayList<Review>());
        HighGame=new Game();
        NewGame=new Game();
        NewGame.setReleaseDate("00/01/0001");



        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                       for (DataSnapshot ds:dataSnapshot.getChildren()){
                           if(ds.getValue(Game.class).getReviews()!=null&&PopGame.getReviews().size()<ds.getValue(Game.class).getReviews().size()){
                               PopGame=ds.getValue(Game.class);
                           }
                           if (HighGame.getAvgRating()<ds.getValue(Game.class).getAvgRating()){
                               HighGame=ds.getValue(Game.class);
                           }
                           SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                           Date d= null;
                           try {
                               d = sdf.parse(NewGame.getReleaseDate());
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }
                           Date nd= null;
                           try {
                               nd = sdf.parse(ds.getValue(Game.class).getReleaseDate());
                           } catch (ParseException e) {
                               e.printStackTrace();
                           }
                           if (nd!=null&&nd.after(d)){
                               NewGame =ds.getValue(Game.class);
                           }
                       }
                       NewGameName.setText(NewGame.getName());
                       ReleaseDate.setText("Released in: "+NewGame.getReleaseDate());
                       PopGameName.setText(PopGame.getName());
                       PopGameRate.setText(new DecimalFormat("0.0").format(PopGame.getAvgRating()));
                       ReviewCount.setText("Review Count: "+PopGame.getReviews().size());
                       HighestGameName.setText(HighGame.getName());
                       HighestGameRate.setText(new DecimalFormat("0.00").format(HighGame.getAvgRating()));
                       if(HighGame.getReviews()!=null)
                       HighestGameReviewCount.setText("Review Count: "+HighGame.getReviews().size());
                       if(PopGame.getDbID()!=null)
                       gamesStorage.child(PopGame.getDbID()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                           @Override
                           public void onSuccess(ListResult listResult) {
                               listResult.getItems().get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       Glide.with(getContext()).load(uri).placeholder(R.drawable.ic_launcher_foreground)
                                               .into(new CustomTarget<Drawable>() {
                                                   @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                                   @Override
                                                   public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                       PopGameCard.setBackground(resource);
                                                   }

                                                   @Override
                                                   public void onLoadCleared(@Nullable Drawable placeholder) {

                                                   }
                                               });
                                   }
                               });
                           }
                       });
                        if(HighGame.getDbID()!=null)
                        gamesStorage.child(HighGame.getDbID()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                            @Override
                            public void onSuccess(ListResult listResult) {
                                listResult.getItems().get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getContext()).load(uri).placeholder(R.drawable.ic_launcher_foreground)
                                                .into(new CustomTarget<Drawable>() {
                                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                                    @Override
                                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                        HighGameCard.setBackground(resource);
                                                    }

                                                    @Override
                                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                                    }
                                                });
                                    }
                                });
                            }
                        });
                        if(NewGame.getDbID()!=null)
                        gamesStorage.child(NewGame.getDbID()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                            @Override
                            public void onSuccess(ListResult listResult) {
                                listResult.getItems().get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getContext()).load(uri).placeholder(R.drawable.ic_launcher_foreground)
                                                .into(new CustomTarget<Drawable>() {
                                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                                    @Override
                                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                        NewGameCard.setBackground(resource);
                                                    }

                                                    @Override
                                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                                    }
                                                });
                                    }
                                });
                            }
                        });


                   }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        PopGameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), GameActivity.class);
                intent.putExtra("selected_game",PopGame);
                startActivity(intent);
            }
        });



        Query articleQuery=newsRef.orderByKey().limitToFirst(1);

        articleQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Article article=new Article();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                 article=ds.getValue(Article.class);
                System.out.println(article);
                ArticleTitle.setText(article.getTitle());
                Url.setText(article.getArticleURL());
                newsStorage.child(article.getCoverId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getContext()).load(uri).placeholder(R.drawable.ic_launcher_foreground).
                                into(new CustomTarget<Drawable>() {
                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        ArticleCard.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ArticleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ArticleActivity.class);
                intent.putExtra("url",Url.getText().toString());
                startActivity(intent);

            }
        });







        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen)
                    showFABMenu();
                else
                    closeFABMenu();
            }
        });



        Subfab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GamesFragment fragment2 = new GamesFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle=new Bundle();
                bundle.putInt("tosearch",1);
                fragment2.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment2);
                fragmentTransaction.commit();


            }
        });

        Subfab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFragment fragment2 = new NewsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle=new Bundle();
                bundle.putInt("tosearch",1);
                fragment2.setArguments(bundle);
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment2);
                fragmentTransaction.commit();

            }
        });

    }

    private void showFABMenu(){
        isFABOpen=true;

        //Rotate fab
        fab.setImageResource(R.drawable.ic_add);
        ViewCompat.animate(fab)
                .rotation(135.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();




        Subfab1.animate().translationY(-getResources().getDimension(R.dimen.standard_75));
        Subfab2.animate().translationY(-getResources().getDimension(R.dimen.standard_130));
    }

    private void closeFABMenu(){
        isFABOpen=false;

        //Rotate fab
        ViewCompat.animate(fab)
                .rotation(0.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();

        fab.setImageResource(R.drawable.ic_search);

        Subfab1.animate().translationY(0);
        Subfab2.animate().translationY(0);
    }



}