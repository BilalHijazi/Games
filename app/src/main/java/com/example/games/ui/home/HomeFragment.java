package com.example.games.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.games.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    FloatingActionButton fab;
    FloatingActionButton Subfab1;
    FloatingActionButton Subfab2;
    boolean isFABOpen=false;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        fab = view.findViewById(R.id.fab);
        Subfab1 = view.findViewById(R.id.subfab1);
        Subfab2 = view.findViewById(R.id.subfab2);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen)
                    showFABMenu();
                else
                    closeFABMenu();
            }
        });

    }

    private void showFABMenu(){
        isFABOpen=true;

        //Rotate fab
        fab.setImageResource(R.drawable.ic_add);
        ViewCompat.animate(fab)
                .rotation(135.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();




        Subfab1.animate().translationY(-getResources().getDimension(R.dimen.standard_75));
        Subfab2.animate().translationY(-getResources().getDimension(R.dimen.standard_130));
    }

    private void closeFABMenu(){
        isFABOpen=false;

        //Rotate fab
        ViewCompat.animate(fab)
                .rotation(0.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();

        fab.setImageResource(R.drawable.ic_search);

        Subfab1.animate().translationY(0);
        Subfab2.animate().translationY(0);
    }

}