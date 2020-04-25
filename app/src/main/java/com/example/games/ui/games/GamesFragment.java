package com.example.games.ui.games;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.games.Game;
import com.example.games.GameActivity;
import com.example.games.R;
import com.example.games.CustomList;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class GamesFragment extends Fragment {
    ArrayList<Game>games,ActionGames,AdventureGames,StrategyGames,SportsGames;
    CustomList adapter,ActionAdapter,AdventureAdapter,StrategyAdapter,SportsAdapter;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    SearchView searchView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_games, container, false);

        return root;

        ///from me-->


    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Games");
        final DatabaseReference myRef=database.getReference("VideoGames");
        searchView=view.findViewById(R.id.main_search_bar);
        final ListView listView=view.findViewById(R.id.main_listview);
        games=new ArrayList<>();
        ActionGames=new ArrayList<>();
        AdventureGames=new ArrayList<>();
        StrategyGames=new ArrayList<>();
        SportsGames=new ArrayList<>();

        final TextView GamesNumAction=view.findViewById(R.id.action_games_number);
        final TextView GamesNumAdventure=view.findViewById(R.id.adventure_games_number);
        final TextView GamesNumStrategy=view.findViewById(R.id.strategy_games_number);
        final TextView GamesNumSports=view.findViewById(R.id.sports_games_number);
        final TextView AvgRatingAction=view.findViewById(R.id.avg_action_rating);
        final TextView AvgRatingAdventure=view.findViewById(R.id.avg_adventure_rating);
        final TextView AvgRatingStrategy=view.findViewById(R.id.avg_strategy_rating);
        final TextView AvgRatingSport=view.findViewById(R.id.avg_sports_rating);
        final Button AllGames=view.findViewById(R.id.all_games);
        Button AboutAction =view.findViewById(R.id.about_action);
        Button AboutAdventure =view.findViewById(R.id.about_adventure);
        Button AboutStrategy =view.findViewById(R.id.about_strategy);
        Button AboutSports =view.findViewById(R.id.about_sports);
        final Button AdvancedSearch = view.findViewById(R.id.advanced_search);
        final ExpandableRelativeLayout expandableRelativeLayout=view.findViewById(R.id.expandableLayout);
        String sortBy[]={"Rate","Popularity","Release Date","Alphabetical"};
        Spinner spinner=view.findViewById(R.id.spinner);
        ArrayAdapter spinAdapt=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,sortBy);
        spinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        ///////


        ///////

        AllGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AllGames.getText().toString().toLowerCase().equals("all games")){
                    AllGames.setText("Close");
                    AdvancedSearch.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                    listView.setAdapter(adapter);
                }
                else
                {
                    AllGames.setText("All Games");
                    AdvancedSearch.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    listView.setAdapter(null);

                }
            }
        });


        EditText WordsInTitle=view.findViewById(R.id.words_in_title);
        MultiAutoCompleteTextView Platforms=view.findViewById(R.id.platforms_tv);
        ArrayAdapter<String> platformsAdapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                getContext().getResources().getStringArray(R.array.platforms));
        Platforms.setAdapter(platformsAdapter);
        Platforms.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        MultiAutoCompleteTextView Genres=view.findViewById(R.id.genres_tv);
        ArrayAdapter<String> genresAdapter= new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                getContext().getResources().getStringArray(R.array.genres));
        Genres.setAdapter(genresAdapter);
        Genres.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        AdvancedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expandableRelativeLayout.isExpanded()) {
                    AdvancedSearch.setText("Advanced Search");
                    expandableRelativeLayout.collapse();
                    searchView.setVisibility(View.VISIBLE);
                    AllGames.setVisibility(View.VISIBLE);
                }
                    else {
                        AdvancedSearch.setText("Close");
                    expandableRelativeLayout.expand();
                    searchView.setVisibility(View.GONE);
                    AllGames.setVisibility(View.GONE);
                }


            }
        });


        AboutAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.popup_about);
                dialog.setTitle("About This Genre");
                TextView About=dialog.findViewById(R.id.about_txt);
                Button GotIt=dialog.findViewById(R.id.got_it);

                About.setText(R.string.about_action);

                GotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });

        AboutAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.popup_about);
                dialog.setTitle("About This Genre");
                TextView About=dialog.findViewById(R.id.about_txt);
                Button GotIt=dialog.findViewById(R.id.got_it);

                About.setText(R.string.about_adventure);

                GotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        AboutStrategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.popup_about);
                dialog.setTitle("About This Genre");
                TextView About=dialog.findViewById(R.id.about_txt);
                Button GotIt=dialog.findViewById(R.id.got_it);

                About.setText(R.string.about_strategy);

                GotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });

        AboutSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.popup_about);
                dialog.setTitle("About This Genre");
                TextView About=dialog.findViewById(R.id.about_txt);
                Button GotIt=dialog.findViewById(R.id.got_it);

                About.setText(R.string.about_sports);

                GotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });


        adapter = new CustomList(getContext(), games);
        ActionAdapter=new CustomList(getContext(),ActionGames);
        AdventureAdapter=new CustomList(getContext(),AdventureGames);
        StrategyAdapter=new CustomList(getContext(),StrategyGames);
        SportsAdapter=new CustomList(getContext(),SportsGames);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    games.clear();
                    ActionGames.clear();
                    AdventureGames.clear();
                    StrategyGames.clear();
                    SportsGames.clear();
                    for (DataSnapshot newSnapshot:dataSnapshot.getChildren()){
                        Game g=newSnapshot.getValue(Game.class);
                        games.add(g);
                        if(g.getGenres().contains("action"))
                            ActionGames.add(g);
                        if(g.getGenres().contains("adventure"))
                            AdventureGames.add(g);
                        if(g.getGenres().contains("strategy"))
                            StrategyGames.add(g);
                        if(g.getGenres().contains("sports"))
                            SportsGames.add(g);
                    }
                    adapter.notifyDataSetChanged();
                    ActionAdapter.notifyDataSetChanged();
                    AdventureAdapter.notifyDataSetChanged();
                    StrategyAdapter.notifyDataSetChanged();
                    SportsAdapter.notifyDataSetChanged();


                    int actionNum=0,adventureNum=0,strategyNum=0,sportNum=0;
                    double actionAvg=0,adventureAvg=0,strategyAvg=0,sportAvg=0;
                    for (int i=0;i<games.size();i++){
                        if (games.get(i).getGenres().toLowerCase().contains("action")) {
                            actionNum++;
                            actionAvg+=games.get(i).getAvgRating();
                        }
                        else
                            if (games.get(i).getGenres().toLowerCase().contains("adventure")){
                                adventureNum++;
                                adventureAvg+=games.get(i).getAvgRating();

                            }
                        else
                        if (games.get(i).getGenres().toLowerCase().contains("strategy")){
                            strategyNum++;
                            strategyAvg+=games.get(i).getAvgRating();

                        }
                        else
                        if (games.get(i).getGenres().toLowerCase().contains("sport")){
                            sportNum++;
                            sportAvg+=games.get(i).getAvgRating();

                        }

                    }
                    GamesNumAction.setText(actionNum+"");
                    GamesNumAdventure.setText(adventureNum+"");
                    GamesNumStrategy.setText(strategyNum+"");
                    GamesNumSports.setText(sportNum+"");
                    if (actionNum!=0)
                    actionAvg/=actionNum;
                    if (adventureNum!=0)
                        adventureAvg/=adventureNum;
                    if (strategyNum!=0)
                        strategyAvg/=strategyNum;
                    if (sportNum!=0)
                        sportAvg/=sportNum;
                    AvgRatingAction.setText(new DecimalFormat("0.0").format(actionAvg));
                    AvgRatingAdventure.setText(new DecimalFormat("0.0").format(adventureAvg));
                    AvgRatingStrategy.setText(new DecimalFormat("0.0").format(strategyAvg));
                    AvgRatingSport.setText(new DecimalFormat("0.0").format(sportAvg));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(getArguments()!=null&&getArguments().getInt("tosearch")==1){
            getArguments().clear();
            searchView.setIconified(false) ;
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if(newText.length()>2) {
                    listView.setAdapter(adapter);
                    adapter.getFilter().filter(newText);

                }
                else {
                    listView.setAdapter(null);

                }
                return false;
            }
        });


        CardView ActionCard=view.findViewById(R.id.action_card);
        CardView AdventureCard=view.findViewById(R.id.adventure_card);
        CardView StrategyCard=view.findViewById(R.id.strategy_card);
        CardView SportsCard=view.findViewById(R.id.sports_card);


        ActionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listView.setAdapter(ActionAdapter);

            }
        });
        AdventureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(AdventureAdapter);

            }
        });
        StrategyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(StrategyAdapter);

            }
        });
        SportsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(SportsAdapter);

            }
        });






        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),GameActivity.class);
                intent.putExtra("selected_game",(Game)parent.getAdapter().getItem(position));
                startActivity(intent);
            }
        });




    }


}



