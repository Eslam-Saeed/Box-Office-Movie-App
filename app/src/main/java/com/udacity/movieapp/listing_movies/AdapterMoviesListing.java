package com.udacity.movieapp.listing_movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movieapp.R;
import com.udacity.movieapp.common.helpers.dbandprovider.MoviesDBController;
import com.udacity.movieapp.common.helpers.ServicesHelper;
import com.udacity.movieapp.common.interfaces.MovieClickListener;
import com.udacity.movieapp.common.models.Movie;

import java.util.ArrayList;

/**
 * Created by eslam on 11/20/17.
 */

public class AdapterMoviesListing extends RecyclerView.Adapter<AdapterMoviesListing.MyViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Movie> listMyMovies;
    private MovieClickListener mMovieClickListener;
    private MoviesDBController mDBController;

    AdapterMoviesListing(Context context, ArrayList<Movie> listMovies, MovieClickListener movieClickListener) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.listMyMovies = listMovies;
        this.mMovieClickListener = movieClickListener;
        this.mDBController = new MoviesDBController(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_movie, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        final Movie currentMovie = listMyMovies.get(position);
        if (currentMovie != null) {
            Picasso.with(mContext).load(ServicesHelper.IMAGE_URL.concat(currentMovie.getPosterPath())).into(myViewHolder.imgMoviePoster);
            myViewHolder.txtMovieTitle.setText(currentMovie.getTitle());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMovieClickListener.navigateToDetailsScreen(currentMovie);
                }
            });
            if (mDBController.isFavoriteMovie(currentMovie.getId()))
                myViewHolder.imgFavorite.setVisibility(View.VISIBLE);
            else
                myViewHolder.imgFavorite.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (listMyMovies == null)
            return 0;
        return listMyMovies.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMoviePoster, imgFavorite;
        private TextView txtMovieTitle;

        MyViewHolder(View itemView) {
            super(itemView);
            imgMoviePoster = itemView.findViewById(R.id.imgMoviePoster);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
            txtMovieTitle = itemView.findViewById(R.id.txtMovieTitle);
        }
    }
}
