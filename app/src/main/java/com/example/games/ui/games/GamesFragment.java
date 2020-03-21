package com.example.games.ui.games;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.games.Game;
import com.example.games.GameActivity;
import com.example.games.MainActivity;
import com.example.games.R;
import com.example.games.SelectedGameFragment;
import com.example.games.ui.CustomList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class GamesFragment extends Fragment {
    ArrayList<Game>games;
    CustomList adapter;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_games, container, false);

        return root;

        ///from me-->


    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search Games");
        final DatabaseReference myRef=database.getReference("VideoGames");
        final SearchView searchView=view.findViewById(R.id.main_search_bar);
        final ListView listView=view.findViewById(R.id.main_listview);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot newSnapshot:dataSnapshot.getChildren()){
                        games.add(newSnapshot.getValue(Game.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.toLowerCase().trim().length()>=3){
                    adapter=new CustomList(getContext(),games);

                    Query query=myRef.orderByChild("name").limitToFirst(10);

                }
                return false;
            }
        });


    }

    }



