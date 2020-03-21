package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.games.ui.games.GamesFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GameActivity extends AppCompatActivity {
    StorageReference storageRef=FirebaseStorage.getInstance().getReference();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference("VideoGames");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Game SelectedGame=(Game)getIntent().getSerializableExtra("selected_game");
        final TextView GameTitle=findViewById(R.id.game_title);
        final ImageView CoverImage=findViewById(R.id.cover_image);
        final TextView GameInfo=findViewById(R.id.game_info);
        final TextView ReviewText=findViewById(R.id.review_text);
        final RatingBar GameRatingBar=findViewById(R.id.game_ratingbar);

        GameTitle.setText(SelectedGame.getName());
        if(SelectedGame.getInGamePicturesIDs()!=null)
        storageRef.child(SelectedGame.getInGamePicturesIDs().get(0)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri.toString()).
                        centerCrop().placeholder(R.drawable.ic_launcher_foreground).into(CoverImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        else
            CoverImage.setImageResource(R.drawable.ic_launcher_foreground);
        GameInfo.setText(SelectedGame.getInfo());



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
