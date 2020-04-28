package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {
    StorageReference storageRef=FirebaseStorage.getInstance().getReference("GamesPics");
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference gamesRef=database.getReference("VideoGames");
    DatabaseReference usersRef=database.getReference("Users");
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    Dialog dialog;
    ReviewsAdapter adapter;
    ArrayList<Review> newRevs;
    ArrayList<Game> newBookmarks;
    myAdapterFlipper adapterFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final Game SelectedGame=(Game)getIntent().getSerializableExtra("selected_game");
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        toolbar.setTitle(SelectedGame.getName());


        final Button ReviewButton=findViewById(R.id.btn_add_review);
        final TextView GameTitle=findViewById(R.id.game_title);
        final TextView GameRate =findViewById(R.id.game_rate);
        final ImageView CoverImage=findViewById(R.id.cover_image);
        final TextView GameInfo=findViewById(R.id.game_info);
        final TextView ReviewText=findViewById(R.id.review_text);
        TextView Publisher=findViewById(R.id.publisher_txt);
        TextView Genre=findViewById(R.id.genre_txt);
        final ImageView BookMark =findViewById(R.id.bookmark);



        usersRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(User.class).getBookMarks()!=null&&IsGameContained(dataSnapshot.getValue(User.class).getBookMarks(),SelectedGame)){
                    BookMark.setImageResource(R.drawable.ic_bookmarked);
                }
                else {
                    BookMark.setImageResource(R.drawable.ic_unbookmarked);
                }
                BookMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dataSnapshot.getValue(User.class).getBookMarks()!=null&&IsGameContained(dataSnapshot.getValue(User.class).getBookMarks(),SelectedGame)){
                            newBookmarks=dataSnapshot.getValue(User.class).getBookMarks();
                            RemoveGameFromList(newBookmarks,SelectedGame);
                            usersRef.child(mAuth.getCurrentUser().getUid()).child("bookMarks").setValue(newBookmarks).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(GameActivity.this,"Removed From Bookmarks",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else {
                            if(dataSnapshot.getValue(User.class).getBookMarks()==null){
                                newBookmarks=new ArrayList<>();
                            }
                            else {
                                newBookmarks=dataSnapshot.getValue(User.class).getBookMarks();
                            }
                                newBookmarks.add(SelectedGame);
                                usersRef.child(mAuth.getCurrentUser().getUid()).child("bookMarks").setValue(newBookmarks).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(GameActivity.this,"Added To Bookmarks",Toast.LENGTH_SHORT).show();

                                    }
                                });


                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Genre.setText(SelectedGame.getGenres());
        GameTitle.setText(SelectedGame.getName());
        Publisher.setText("Publisher: "+SelectedGame.getPublisher());
        GameRate.setText(SelectedGame.getAvgRating()+"");
        final LinearLayout linearLayout=findViewById(R.id.platforms_layout);
            ImageView icon=new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(30 ,0, 0, 0);
            if(SelectedGame.getPlatforms().toLowerCase().contains("playstation")){
                icon.setImageResource(R.drawable.playstation_icon);
                icon.setLayoutParams(params);
                linearLayout.addView(icon);
                icon=new ImageView(this);
            }
            if(SelectedGame.getPlatforms().toLowerCase().contains("xbox")){
                icon.setImageResource(R.drawable.xbox_icon);
                icon.setLayoutParams(params);
                linearLayout.addView(icon);
                icon=new ImageView(this);

            }
            if(SelectedGame.getPlatforms().toLowerCase().contains("android")){
                icon.setImageResource(R.drawable.android_icon);
                icon.setLayoutParams(params);
                linearLayout.addView(icon);
                icon=new ImageView(this);

            }
            if(SelectedGame.getPlatforms().toLowerCase().contains("ios")){
                icon.setImageResource(R.drawable.iphone_icon);
                icon.setLayoutParams(params);
                linearLayout.addView(icon);
                icon=new ImageView(this);

            }
            if(SelectedGame.getPlatforms().toLowerCase().contains("pc")){
                icon.setImageResource(R.drawable.pc_icon);
                icon.setLayoutParams(params);
                linearLayout.addView(icon);

            }
        if(SelectedGame.getPlatforms().toLowerCase().contains("mac")){
            icon.setImageResource(R.drawable.mac_icon);
            icon.setLayoutParams(params);
            linearLayout.addView(icon);

        }
        if(SelectedGame.getPlatforms().toLowerCase().contains("nintendo")){
            icon.setImageResource(R.drawable.nintendo_icon);
            icon.setLayoutParams(params);
            linearLayout.addView(icon);

        }

        final ExpandableLinearLayout expandableLinearLayout=findViewById(R.id.platforms_expandaple);
        TextView allPlatforms=findViewById(R.id.platforms_txt);
        allPlatforms.setText(SelectedGame.getPlatforms());


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expandableLinearLayout.isExpanded())
                expandableLinearLayout.collapse();
                else
                    expandableLinearLayout.expand();
            }
        });





           final AdapterViewFlipper adapterViewFlipper = findViewById(R.id.pics_flipper);
            storageRef.child(SelectedGame.getDbID()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                     adapterFlipper = new myAdapterFlipper(GameActivity.this, listResult.getItems());
                    adapterViewFlipper.setAdapter(adapterFlipper);
                    adapterViewFlipper.setFlipInterval(3000);
                    adapterViewFlipper.startFlipping();
                }
            });








        final RecyclerView recyclerView=findViewById(R.id.reviews_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(SelectedGame.getReviews()!=null) {
            newRevs=SelectedGame.getReviews();


        }
        else {
            newRevs =new ArrayList<>();

        }
        adapter = new ReviewsAdapter(GameActivity.this, newRevs);
        recyclerView.setAdapter(adapter);



       storageRef.child(SelectedGame.getDbID()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
           @Override
           public void onSuccess(ListResult listResult) {
               listResult.getItems().get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Glide.with(GameActivity.this).load(uri).placeholder(R.drawable.ic_launcher_foreground)
                               .into(CoverImage);
                   }
               });
           }
       });
        GameInfo.setText(SelectedGame.getInfo());

        for (Review review:newRevs){
            if (review.getUser().getEmailaddress().toLowerCase().equals(mAuth.getCurrentUser().getEmail()))
                ReviewButton.setEnabled(false);
        }



       if(SelectedGame.getPrices()!=null) {
          ListView PricesList = findViewById(R.id.prices_list);
          ArrayList<GamePrice> prices = SelectedGame.getPrices();
          PriceAdapter priceAdapter = new PriceAdapter(GameActivity.this, prices);
          PricesList.setAdapter(priceAdapter);
          setListViewHeightBasedOnChildren(PricesList); // Idunno if this does work!
      }








        ReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (mAuth.getCurrentUser() != null) {
                        openDialog();
                        Button PostBtn=dialog.findViewById(R.id.btn_post);
                        Button CancelBtn=dialog.findViewById(R.id.btn_cancel);
                        final RatingBar RateBar=dialog.findViewById(R.id.rating_post);
                        final EditText Comment=dialog.findViewById(R.id.edittxt_comment);

                        CancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        PostBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String userID=mAuth.getCurrentUser().getUid();
                                usersRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        User user=dataSnapshot.getValue(User.class);
                                        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                                        Review review=new Review(user,Comment.getText().toString(),currentDate,RateBar.getRating());

                                        newRevs.add(review);

                                        SelectedGame.setReviews(newRevs);
                                        SelectedGame.setAvgRating(SelectedGame.getAvgRating()+((review.getRate()-SelectedGame.getAvgRating())/SelectedGame.getReviews().size()));
                                        gamesRef.child(SelectedGame.getDbID()).setValue(SelectedGame).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(GameActivity.this,"Post Added!",Toast.LENGTH_SHORT).show();
                                                adapter.notifyDataSetChanged();
                                                ReviewButton.setEnabled(false);
                                                dialog.cancel();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                newRevs.remove(newRevs.size()-1);
                                            }
                                        });




                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });




                            }
                        });

                    }
                    else
                        Toast.makeText(getApplicationContext(), "You should sign in first!", Toast.LENGTH_LONG).show();



            }
        });










    }


    public void openDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.review_post);
        dialog.setTitle("Posting Review");
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public boolean IsGameContained(ArrayList<Game>games,Game game){
        for (int i=0;i<games.size();i++){
            if(games.get(i).getDbID().equals(game.getDbID()))
                return true;
            }
            return false;
        }
        public boolean RemoveGameFromList(ArrayList<Game>games,Game game){
        for (int i=0;i<games.size();i++){
            if(games.get(i).getDbID().equals(game.getDbID())){
                games.remove(i);
                return true;
            }

        }
        return false;
        }




 }
