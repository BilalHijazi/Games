package com.example.games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private List<Review> mReviews;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public ReviewsAdapter(Context context, ArrayList<Review> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mReviews = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView UserName,Comment,Date,Email,gameName;
        RatingBar Rating;

        ViewHolder(View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.reviewer_username);
            Email=itemView.findViewById(R.id.reviewer_email);
            Comment=itemView.findViewById(R.id.reviewer_comment);
            Date=itemView.findViewById(R.id.review_date);
            Rating=itemView.findViewById(R.id.reviewer_rating);
            gameName=itemView.findViewById(R.id.reviewer_game_name);
        }


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String username = mReviews.get(mReviews.size()-position-1).getUser().getUserName();
        String email=mReviews.get(mReviews.size()-position-1).getUser().getEmailaddress();
        String comment=mReviews.get(mReviews.size()-position-1).getComment();
        String date=mReviews.get(mReviews.size()-position-1).getDate();
        String gamename=mReviews.get(mReviews.size()-position-1).getGameName();
        float rating=mReviews.get(mReviews.size()-position-1).getRate();
        holder.UserName.setText(username);
        holder.Email.setText(email);
        holder.Comment.setText(comment);
        holder.Date.setText(date);
        holder.Rating.setRating(rating);
        holder.gameName.setText(gamename);
}

    @Override
    public int getItemCount() {
        return mReviews.size();
    }


}


