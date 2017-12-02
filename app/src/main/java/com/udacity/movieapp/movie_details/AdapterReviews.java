package com.udacity.movieapp.movie_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.movieapp.R;
import com.udacity.movieapp.common.models.Reviews;
import com.udacity.movieapp.common.models.Trailers;

import java.util.ArrayList;

/**
 * Created by eslam on 12/2/17.
 */

public class AdapterReviews extends RecyclerView.Adapter<AdapterReviews.MyViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Reviews.Review> mListReviews;

    public AdapterReviews(Context context, ArrayList<Reviews.Review> listReviews) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mListReviews = listReviews;
    }

    @Override
    public AdapterReviews.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_review, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterReviews.MyViewHolder myViewHolder, int position) {
        Reviews.Review review = mListReviews.get(position);
        if (review != null) {
            myViewHolder.txtReviewAuthor.setText(review.getReviewAuthor());
            myViewHolder.txtReviewContent.setText(review.getReviewContent());
        }

    }

    @Override
    public int getItemCount() {
        if (mListReviews == null)
            return 0;
        return mListReviews.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtReviewAuthor, txtReviewContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtReviewAuthor = itemView.findViewById(R.id.txtAuthor);
            txtReviewContent = itemView.findViewById(R.id.txtReview);
        }
    }
}

