package com.example.games;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
  private  AppBarConfiguration mAppBarConfiguration;
  private FirebaseAuth mAuth;
      private FirebaseDatabase database = FirebaseDatabase.getInstance();
      private DatabaseReference UsersRef,breakingNewsRef;
      private NavigationView navigationView;
      private TextView HeaderUserText;
      private NavController navController;
      private DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsersRef=database.getReference("Users");
        breakingNewsRef=database.getReference("NewsAndArticles").child("breakingNews");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        Intent serviceIntent=new Intent(this,NewsService.class);







         drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_news, R.id.nav_video_games)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
       // NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);






        View headerview = navigationView.getHeaderView(0);
        final TextView Welcome=(TextView)headerview.findViewById(R.id.txt_welcome);;



          HeaderUserText=(TextView)headerview.findViewById(R.id.header_email);






    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    //Change UI according to user data.
    public void  updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            navigationView.getMenu().findItem(R.id.user_properties).setVisible(true);
            navigationView.getMenu().findItem(R.id.menu_login).setVisible(false);
            UsersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HeaderUserText.setText(dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("userName").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        }else {
            Toast.makeText(this,"No Logged in user",Toast.LENGTH_LONG).show();
            navigationView.getMenu().findItem(R.id.menu_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.user_properties).setVisible(false);

            HeaderUserText.setText("");

        }
    }

    @Override
    public boolean onNavigationItemSelected (MenuItem menuItem){

        switch (menuItem.getItemId()){
            case R.id.nav_home:
            case R.id.nav_news:
            case R.id.nav_video_games:
                drawer.closeDrawers();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                return NavigationUI.onNavDestinationSelected(menuItem, navController);

            case R.id.menu_logout:
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.logout_popup);
                dialog.setCancelable(false);
                Button Yes=dialog.findViewById(R.id.logout_yes);
                Button No=dialog.findViewById(R.id.logout_no);
                dialog.show();
                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAuth.signOut();
                        updateUI(null);
                        dialog.cancel();
                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                break;

            case R.id.menu_login:
                final Dialog dialogLogin = new Dialog(MainActivity.this);
                dialogLogin.setContentView(R.layout.login_popup);
                dialogLogin.setCancelable(false);
                Button login = (Button) dialogLogin.findViewById(R.id.popup_login);
                Button Cancel = (Button) dialogLogin.findViewById(R.id.popup_cancel);
                TextView Signup = (TextView) dialogLogin.findViewById(R.id.txt_signup);
                final EditText emailaddr = (EditText) dialogLogin.findViewById(R.id.txt_emailaddr);
                final EditText password = (EditText) dialogLogin.findViewById(R.id.txt_password);
                dialogLogin.show();

                login.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (emailaddr.getText().toString().isEmpty()) {
                            emailaddr.setError("Please enter your email address");
                            emailaddr.requestFocus();
                        } else if (password.getText().toString().isEmpty()) {
                            password.setError("Please enter your password");
                            emailaddr.requestFocus();
                        } else {
                            mAuth.signInWithEmailAndPassword(emailaddr.getText().toString(), password.getText().toString())
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Log.d("TAG11", "signInWithEmail:success");
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                dialogLogin.cancel();
                                                updateUI(user);
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Log.w("tag22", "signInWithEmail:failure", task.getException());
                                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                                updateUI(null);
                                            }

                                            // ...
                                        }
                                    });
                        }
                    }
                });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLogin.cancel();
                    }
                });


                Signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLogin.cancel();
                        final Dialog dialogSighnUp = new Dialog(MainActivity.this);
                        dialogSighnUp.setContentView(R.layout.signup_popup);
                        dialogSighnUp.setCancelable(false);
                        Button signup = (Button) dialogSighnUp.findViewById(R.id.popup_signup);
                        Button Cancel = (Button) dialogSighnUp.findViewById(R.id.popup_cancel);
                        final EditText username = (EditText) dialogSighnUp.findViewById(R.id.txt_username);
                        final EditText emailaddr = (EditText) dialogSighnUp.findViewById(R.id.txt_emailaddr);
                        final EditText password = (EditText) dialogSighnUp.findViewById(R.id.txt_password);
                        final EditText phoneNumber=(EditText)dialogSighnUp.findViewById(R.id.txt_phone_number);

                        dialogSighnUp.show();

                        signup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (username.getText().toString().isEmpty()){
                                    username.setText("User");
                                }

                                else if (emailaddr.getText().toString().isEmpty()) {
                                    emailaddr.setError("Please enter your email address");
                                    emailaddr.requestFocus();
                                }
                                else if (password.getText().toString().isEmpty()) {
                                    password.setError("Please enter your password");
                                    emailaddr.requestFocus();
                                }
                                else {



                                                mAuth.createUserWithEmailAndPassword(emailaddr.getText().toString().trim(),password.getText().toString().trim())
                                                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if (task.isSuccessful()) {
                                                                    // Sign in success, update UI with the signed-in user's information
                                                                    User user=new User(username.getText().toString().trim(),emailaddr.getText().toString().trim(),phoneNumber.getText().toString().trim());
                                                                    Log.d("TAG1", "createUserWithEmail:success");
                                                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                                                    UsersRef.child(firebaseUser.getUid()).setValue(user);//  should I put onSuccessListener?
                                                                    dialogSighnUp.cancel();
                                                                    updateUI(firebaseUser);
                                                                } else {
                                                                    // If sign in fails, display a message to the user.
                                                                    Log.w("TAG2", "createUserWithEmail:failure", task.getException());
                                                                    Toast.makeText(MainActivity.this ,"Authentication failed.",
                                                                            Toast.LENGTH_SHORT).show();
                                                                    updateUI(null);
                                                                }

                                                                // ...
                                                            }
                                                        });





                                            }
                                        }






                        });

                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogSighnUp.cancel();
                            }
                        });

                    }
                });


                break;

            case R.id.menu_settings:
                Intent SettingsIntent=new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(SettingsIntent);

                break;

            case R.id.menu_profile:
                Intent ProfileIntent=new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(ProfileIntent);
                break;

            case R.id.menu_bookmarks:
                Intent BookmarksIntent=new Intent(MainActivity.this,BookmarksActivity.class);
                startActivity(BookmarksIntent);
                break;

            case R.id.menu_reviews:
                Intent ReviewsIntent=new Intent(MainActivity.this,MyReviews.class);
                startActivity(ReviewsIntent);
                break;
                 default:
                break;




        }
        return false;

    }







}
