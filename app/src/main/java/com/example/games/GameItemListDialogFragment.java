package com.example.games;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.example.games.ui.CustomList;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     GameItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class GameItemListDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    ArrayList<Game> Games;
    FirebaseDatabase database=FirebaseDatabase.getInstance();


    // TODO: Customize parameters
    public static GameItemListDialogFragment newInstance(int itemCount) {
        final GameItemListDialogFragment fragment = new GameItemListDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_item_list_dialog_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        Games = new ArrayList<Game>();
        final DatabaseReference myRef = database.getReference("VideoGames");

        Query query=myRef.orderByChild("avgRating").limitToFirst(5);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot newSnapshot : dataSnapshot.getChildren()) {
                        Games.add(newSnapshot.getValue(Game.class));

                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new GameItemAdapter(Games));



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }




    private class ViewHolder extends RecyclerView.ViewHolder  {

        final TextView GameName,GameInfo;
        final ImageView image;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.list_single, parent, false));
            GameName = itemView.findViewById(R.id.listview_item_title);
            GameInfo=  itemView.findViewById(R.id.listview_item_description);
            image=itemView.findViewById(R.id.listview_image);


        }
    }

    private class GameItemAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Game> Games;

        //private final int mItemCount;

        GameItemAdapter( List<Game>games) {
            Games=games;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.GameName.setText(Games.get(position).getName());
            holder.GameInfo.setText(Games.get(position).getInfo());
            //IMAGE REMAINING...
        }

        @Override
        public int getItemCount() {
            return Games.size();
        }

    }

}
