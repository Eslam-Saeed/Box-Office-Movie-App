package com.udacity.movieapp.movie_details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movieapp.R;
import com.udacity.movieapp.common.base.BaseFragment;
import com.udacity.movieapp.common.helpers.Constants;
import com.udacity.movieapp.common.helpers.ServicesHelper;
import com.udacity.movieapp.common.interfaces.ToolbarChangeListener;
import com.udacity.movieapp.common.models.Movie;

/**
 * Created by eslam on 11/21/17.
 */

public class FragmentMovieDetails extends BaseFragment implements ViewMovieDetails {
    private View mView;
    private ToolbarChangeListener mToolbarChangeListener;
    private ProgressBar mProgressBarDetails;
    private Context mContext;
    private Movie mMovie;
    private PresenterMovieDetails mPresenterMovieDetails;
    private ImageView imgPoster;
    private TextView txtTitle, txtYear, txtRate, txtOverview;
    private Button btnMarkFavorite;
    private boolean isFavorite = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (getArguments() != null)
            mMovie = getArguments().getParcelable(Constants.MOVIE_DETAILS);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mToolbarChangeListener = (ToolbarChangeListener) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_movie_details, null);
        initializeViews(mView);
        setListeners();
        if (mToolbarChangeListener != null)
            mToolbarChangeListener.changeToolbar(getString(R.string.movie_details), true, false);
        onMovieDetailsSuccess(mMovie);
        return mView;
    }

    @Override
    protected void initializeViews(View v) {
        mProgressBarDetails = v.findViewById(R.id.progressBarMovieDetails);
        imgPoster = v.findViewById(R.id.imgMovieDetail);
        txtTitle = v.findViewById(R.id.txtMovieTitle);
        txtYear = v.findViewById(R.id.txtYearRelease);
        txtRate = v.findViewById(R.id.txtMovieRate);
        txtOverview = v.findViewById(R.id.txtOverview);
        btnMarkFavorite = v.findViewById(R.id.btnMarkFavorite);
    }

    @Override
    protected void setListeners() {
        btnMarkFavorite.setOnClickListener(btnMarkFavoriteClickListener);
    }

    private View.OnClickListener btnMarkFavoriteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO mark as favorite/unfavorite
            if (isFavorite) {
            } else {
            }
        }
    };

    @Override
    public void showProgressBar(boolean shouldShow) {
        mProgressBarDetails.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onMovieDetailsSuccess(Movie movieDetails) {
        if (movieDetails != null) {
            txtTitle.setText(movieDetails.getTitle());
            txtOverview.setText(movieDetails.getOverview());
            txtRate.setText(String.valueOf(movieDetails.getVoteAverage()).concat(" /10"));
            txtYear.setText(movieDetails.getReleaseDate());
            Picasso.with(mContext).load(ServicesHelper.IMAGE_URL.concat(movieDetails.getPosterPath())).into(imgPoster);
        }
    }

    @Override
    public void onMovieDetailsFail(String message) {
        Snackbar.make(mView, message, Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    public static FragmentMovieDetails newInstance(Movie movie) {
        FragmentMovieDetails fragment = new FragmentMovieDetails();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.MOVIE_DETAILS, movie);
        fragment.setArguments(bundle);
        return fragment;
    }
}
