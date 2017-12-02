package com.udacity.movieapp.movie_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movieapp.R;
import com.udacity.movieapp.common.helpers.Utilities;
import com.udacity.movieapp.common.models.Trailers;

import java.util.ArrayList;

/**
 * Created by eslam on 12/2/17.
 */

public class AdapterTrailers extends RecyclerView.Adapter<AdapterTrailers.MyViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Trailers.Trailer> mListTrailers;

    public AdapterTrailers(Context context, ArrayList<Trailers.Trailer> listTrailers) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mListTrailers = listTrailers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_trailer, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        final Trailers.Trailer trailer = mListTrailers.get(position);
        if (trailer != null) {
            myViewHolder.txtTrailerTitle.setText(trailer.getTrailerName());
            Picasso.with(mContext).load(trailer.getTrailerImagePath()).into(myViewHolder.imgTrailer);

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.openVideo(mContext , trailer.getTrailerURL());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mListTrailers == null)
            return 0;
        return mListTrailers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTrailer;
        private TextView txtTrailerTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgTrailer = itemView.findViewById(R.id.imgTrailer);
            txtTrailerTitle = itemView.findViewById(R.id.txtTrailer);
        }
    }
}
