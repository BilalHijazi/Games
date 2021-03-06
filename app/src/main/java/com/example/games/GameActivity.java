package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;

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
    Button PostBtn,CancelBtn,ReviewButton,editReview;
    EditText Comment;
    RatingBar RateBar;
    ProgressBar progressBar;

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
        ReviewButton=findViewById(R.id.btn_add_review);
        editReview=findViewById(R.id.btn_edit_review);
        final TextView GameTitle=findViewById(R.id.game_title);
        final TextView GameRate =findViewById(R.id.game_rate);
        final ImageView CoverImage=findViewById(R.id.cover_image);
        final TextView GameInfo=findViewById(R.id.game_info);
        final TextView ReviewText=findViewById(R.id.review_text);
        TextView Publisher=findViewById(R.id.publisher_txt);
        TextView Genre=findViewById(R.id.genre_txt);
        final ImageView BookMark =findViewById(R.id.bookmark);
        TextView Range=findViewById(R.id.range);
        TextView singlePlayerCheck=findViewById(R.id.singleplayer_txt);
        TextView multiPlayerCheck=findViewById(R.id.multiplayer_checkbox);
        TextView releaseDate=findViewById(R.id.release_date_txt);
        TextView Series=findViewById(R.id.series_txt);
        progressBar=findViewById(R.id.progress_bar);

        Series.setText("Game Series: "+SelectedGame.getSeries());

        releaseDate.setText(SelectedGame.getReleaseDate());

        if(!SelectedGame.isSinglePlayer())
            singlePlayerCheck.setVisibility(View.GONE);
        if(!SelectedGame.isMultiPlayer())
            multiPlayerCheck.setVisibility(View.GONE);

        if(SelectedGame.getPrices()==null){
            Range.setVisibility(View.GONE);
        }
        else {
            String min=SelectedGame.getPrices().get(0).getPrice()+"";
            String max=SelectedGame.getPrices().get(0).getPrice()+"";
            for (int i = 1; i <SelectedGame.getPrices().size() ; i++) {
                GamePrice gamePrice=SelectedGame.getPrices().get(i);
                if(Double.parseDouble(min)>gamePrice.getPrice())
                    min =gamePrice.getPrice()+"";
                if(Double.parseDouble(max)<gamePrice.getPrice())
                    max =gamePrice.getPrice()+"";

            }
            Range.setText("range: "+min+"$ - "+max+"$");
        }


        if(mAuth.getCurrentUser()!=null)
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
                        if(mAuth.getCurrentUser()==null)
                            Toast.makeText(GameActivity.this, "You should login first", Toast.LENGTH_LONG).show();
                        else
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
        GameRate.setText(new DecimalFormat("#.#").format(SelectedGame.getAvgRating()));
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
                icon=new ImageView(this);


            }
        if(SelectedGame.getPlatforms().toLowerCase().contains("mac")){
            icon.setImageResource(R.drawable.mac_icon);
            icon.setLayoutParams(params);
            linearLayout.addView(icon);
            icon=new ImageView(this);


        }
        if(SelectedGame.getPlatforms().toLowerCase().contains("nintendo")){
            icon.setImageResource(R.drawable.nintendo_icon);
            icon.setLayoutParams(params);
            linearLayout.addView(icon);
            icon=new ImageView(this);


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
                     adapterFlipper = new myAdapterFlipper(getApplicationContext(), listResult.getItems());
                    adapterViewFlipper.setAdapter(adapterFlipper);
                    adapterViewFlipper.setFlipInterval(3000);
                    adapterViewFlipper.startFlipping();
                }
            });








        final RecyclerView recyclerView=findViewById(R.id.reviews_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newRevs=new ArrayList<>();
        if(SelectedGame.getReviews()!=null) {
            newRevs=SelectedGame.getReviews();

        }

        adapter = new ReviewsAdapter(getApplicationContext(), newRevs);
        recyclerView.setAdapter(adapter);



       storageRef.child(SelectedGame.getDbID()).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
           @Override
           public void onSuccess(ListResult listResult) {
               if (listResult.getItems().size()!=0)
               listResult.getItems().get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Glide.with(getApplicationContext()).load(uri).placeholder(R.drawable.ic_launcher_foreground)
                               .into(CoverImage);
                   }
               });
           }
       });
        GameInfo.setText(SelectedGame.getInfo());
        if(mAuth.getCurrentUser()!=null)
        for (int i=0;i<newRevs.size();i++){
            Review review=newRevs.get(i);
            if (review.getUser().getEmailaddress().equals(mAuth.getCurrentUser().getEmail())) {
                ReviewButton.setVisibility(View.GONE);
                editReview.setVisibility(View.VISIBLE);
            }

        }


        final ListView PricesList = findViewById(R.id.prices_list);

       if(SelectedGame.getPrices()!=null) {
          final ArrayList<GamePrice> prices = SelectedGame.getPrices();
          PriceAdapter priceAdapter = new PriceAdapter(GameActivity.this, prices);
          PricesList.setAdapter(priceAdapter);
          setListViewHeightBasedOnChildren(PricesList); // Idunno if this does work!
           PricesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Intent intent=new Intent(GameActivity.this,WebStorePrice.class);
                   intent.putExtra("url",prices.get(position).getStoreURL());
                   startActivity(intent);
               }
           });
      }

        ReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (mAuth.getCurrentUser() != null) {
                        openDialog();
                        CancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        PostBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               addReviewToGame(SelectedGame);
                            }
                        });

                    }
                    else
                        Toast.makeText(getApplicationContext(), "You should sign in first!", Toast.LENGTH_LONG).show();



            }
        });

       editReview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               for (int i = 0; i <SelectedGame.getReviews().size() ; i++) {
                   openDialog();
                   if(SelectedGame.getReviews().get(i).getUser().getEmailaddress().equals(mAuth.getCurrentUser().getEmail())){
                       Comment.setText(SelectedGame.getReviews().get(i).getComment());
                       RateBar.setRating(SelectedGame.getReviews().get(i).getRate());
                       PostBtn.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               addReviewToGame(SelectedGame);
                           }
                       });
                   }
               }

           }
       });




    }


    public void openDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.review_post);
        dialog.setTitle("Posting Review");
        dialog.setCancelable(false);
         PostBtn=dialog.findViewById(R.id.btn_post);
         CancelBtn=dialog.findViewById(R.id.btn_cancel);
         RateBar=dialog.findViewById(R.id.rating_post);
         Comment=dialog.findViewById(R.id.edittxt_comment);
        dialog.show();
    }

    public void addReviewToGame(final Game SelectedGame){
                progressBar.setVisibility(View.VISIBLE);
                PostBtn.setEnabled(false);
                String userID=mAuth.getCurrentUser().getUid();
                usersRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (int i=0;i<newRevs.size();i++){
                            Review review=newRevs.get(i);
                            if (review.getUser().getEmailaddress().equals(mAuth.getCurrentUser().getEmail())) {
                                newRevs.remove(i);

                            }

                        }
                        User user=dataSnapshot.getValue(User.class);
                        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        Review review=new Review(user,Comment.getText().toString(),currentDate,RateBar.getRating(),SelectedGame.getName());
                        newRevs.add(review);
                        SelectedGame.setReviews(newRevs);
                        SelectedGame.setAvgRating(SelectedGame.getAvgRating()+((review.getRate()-SelectedGame.getAvgRating())/SelectedGame.getReviews().size()));
                        gamesRef.child(SelectedGame.getDbID()).setValue(SelectedGame).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(GameActivity.this,"Post Added!",Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                                ReviewButton.setVisibility(View.GONE);
                                editReview.setVisibility(View.VISIBLE);
                                PostBtn.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                                dialog.dismiss();
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
