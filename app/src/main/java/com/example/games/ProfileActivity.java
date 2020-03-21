package com.example.games;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();

        UsersRef= database.getReference("Users");
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


       UsersRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               FirebaseUser user=mAuth.getCurrentUser();
               TextView UserID=(TextView)findViewById(R.id.profile_userid);
               TextView UserName=(TextView)findViewById(R.id.profile_username);
               TextView UserEmail=(TextView)findViewById(R.id.profile_useremail);
               TextView UserPhoneNumber=(TextView)findViewById(R.id.profile_userphonenumber);
               UserID.setText(UserID.getText().toString()+"  "+user.getUid());
               UserName.setText(UserName.getText().toString()+"  "+dataSnapshot.child(user.getUid()).child("userName").getValue());
               UserEmail.setText(UserEmail.getText().toString()+"  "+dataSnapshot.child(user.getUid()).child("emailaddress").getValue());
               UserPhoneNumber.setText(UserPhoneNumber.getText().toString()+"  "+dataSnapshot.child(user.getUid()).child("phoneNumber").getValue());

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
}
