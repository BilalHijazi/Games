package com.example.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.games.ui.CustomList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListShow extends AppCompatActivity {
    CustomList adapter;
    ArrayList<Game>Games;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_show);

        final DatabaseReference myRef=database.getReference("VideoGames");

        Games=new ArrayList<Game>();
        final ListView listView= findViewById(R.id.list_game_fragment);
        final SearchView searchView= findViewById(R.id.search_game_fragment);



        Query query=myRef.orderByChild("avgRating").limitToFirst(20);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot newSnapshot : dataSnapshot.getChildren()) {
                        Games.add(newSnapshot.getValue(Game.class));

                    }
                    adapter = new CustomList(getApplicationContext(),Games);
                    listView.setAdapter(adapter);
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            System.out.println("TextChanged");
                            adapter.getFilter().filter(newText);
                            return false;
                        }
                    });

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                            Intent intent=new Intent(getApplicationContext(),GameActivity.class);
                            intent.putExtra("selected_game",(Game)parent.getAdapter().getItem(position));
                            startActivity(intent);

                        }
                    });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
