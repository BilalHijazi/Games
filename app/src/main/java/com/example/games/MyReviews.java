package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyReviews extends AppCompatActivity {
FirebaseAuth mAuth= FirebaseAuth.getInstance();
FirebaseDatabase database=FirebaseDatabase.getInstance();
DatabaseReference usersRef=database.getReference("Users");
DatabaseReference gamesRef=database.getReference("VideoGames");
User ThisUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);
        setTitle("My Reviews");
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
        final RecyclerView recyclerView=findViewById(R.id.myreviews_list);
        final TextView NoItemsText=findViewById(R.id.no_reviews_txt);
        final ArrayList<Review> myReviews=new ArrayList<>();

        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        Game game=ds.getValue(Game.class);
                        if(game.getReviews()!=null)
                        for (int i = 0; i < game.getReviews().size(); i++) {
                            Review review=game.getReviews().get(i);
                            if(review.getUser().getEmailaddress().equals(mAuth.getCurrentUser().getEmail())){
                                myReviews.add(review);
                            }
                        }
                    }
                    System.out.println(myReviews);
                    if(myReviews.size()==0) {
                        NoItemsText.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        usersRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerView.setAdapter(null);
                ThisUser=dataSnapshot.getValue(User.class);
                    ReviewsAdapter adapter = new ReviewsAdapter(MyReviews.this,myReviews);
                    recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
