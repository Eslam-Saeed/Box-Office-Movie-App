package com.udacity.movieapp.movie_details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movieapp.R;
import com.udacity.movieapp.common.base.BaseFragment;
import com.udacity.movieapp.common.helpers.Constants;
import com.udacity.movieapp.common.helpers.MoviesDBController;
import com.udacity.movieapp.common.helpers.ServicesHelper;
import com.udacity.movieapp.common.interfaces.ToolbarChangeListener;
import com.udacity.movieapp.common.models.Movie;
import com.udacity.movieapp.common.models.Reviews;
import com.udacity.movieapp.common.models.Trailers;

/**
 * Created by eslam on 11/21/17.
 */

public class FragmentMovieDetails extends BaseFragment implements ViewMovieDetails {
    private View mView;
    private RecyclerView rvTrailers, rvReviews;
    private ToolbarChangeListener mToolbarChangeListener;
    private ProgressBar mProgressBarDetails;
    private Context mContext;
    private Movie mMovie;
    private PresenterMovieDetails mPresenterMovieDetails;
    private ImageView imgPoster;
    private TextView txtTitle, txtYear, txtRate, txtOverview, txtTrailers, txtReviews;
    private Button btnMarkFavorite;
    private AdapterTrailers mAdapterTrailers;
    private AdapterReviews mAdapterReviews;
    private boolean isFavorite = false;
    private MoviesDBController mDBController;
    private RelativeLayout rlMovieDetails;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mPresenterMovieDetails = new PresenterMovieDetails(mContext, this);
        mDBController = new MoviesDBController(mContext);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(Constants.MOVIE_DETAILS);
            if (mMovie != null)
                isFavorite = mDBController.isFavoriteMovie(mMovie.getId());
        }
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
        if (mMovie != null) {
            mPresenterMovieDetails.getMovieTrailersAndReviews(mMovie.getId());
            onMovieDetailsSuccess(mMovie);
        }
        return mView;
    }

    @Override
    protected void initializeViews(View v) {
        mProgressBarDetails = v.findViewById(R.id.progressBarMovieDetails);
        rvTrailers = v.findViewById(R.id.rvTrailers);
        rvReviews = v.findViewById(R.id.rvReviews);
        txtTrailers = v.findViewById(R.id.txtTrailers);
        txtReviews = v.findViewById(R.id.txtReviews);
        imgPoster = v.findViewById(R.id.imgMovieDetail);
        txtTitle = v.findViewById(R.id.txtMovieTitle);
        txtYear = v.findViewById(R.id.txtYearRelease);
        txtRate = v.findViewById(R.id.txtMovieRate);
        txtOverview = v.findViewById(R.id.txtOverview);
        btnMarkFavorite = v.findViewById(R.id.btnMarkFavorite);
        rlMovieDetails = v.findViewById(R.id.rlMovieDetails);
        if (isFavorite) {
            btnMarkFavorite.setText(getString(R.string.un_favourite));
            btnMarkFavorite.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }

        rvTrailers.setNestedScrollingEnabled(false);
        rvReviews.setNestedScrollingEnabled(false);
    }

    @Override
    protected void setListeners() {
        btnMarkFavorite.setOnClickListener(btnMarkFavoriteClickListener);
    }

    private View.OnClickListener btnMarkFavoriteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO mark as favorite/unfavorite
            int numOfRowsAffected = 0;
            if (isFavorite) {
                numOfRowsAffected = mDBController.deleteFromFavourite(mMovie.getId());
                if (numOfRowsAffected != 0) {
                    isFavorite = !isFavorite;
                    btnMarkFavorite.setText(getString(R.string.mark_as_favourite));
                    btnMarkFavorite.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccentOpacity30));
                }
            } else {
                long result = mDBController.addFavouriteMovie(mMovie);
                if (result != -1) {
                    isFavorite = !isFavorite;
                    btnMarkFavorite.setText(getString(R.string.un_favourite));
                    btnMarkFavorite.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                }
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
            rlMovieDetails.setVisibility(View.VISIBLE);
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

    @Override
    public void onTrailersSuccess(Trailers trailers) {
        if (trailers != null && trailers.getListTrailers() != null && trailers.getListTrailers().size() > 0) {
            rvTrailers.setVisibility(View.VISIBLE);
            txtTrailers.setVisibility(View.VISIBLE);
            rvTrailers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            mAdapterTrailers = new AdapterTrailers(mContext, trailers.getListTrailers());
            rvTrailers.setAdapter(mAdapterTrailers);
        }
    }

    @Override
    public void onReviewsSuccess(Reviews reviews) {
        if (reviews != null && reviews.getListReviews() != null && reviews.getListReviews().size() > 0) {
            rvReviews.setVisibility(View.VISIBLE);
            txtReviews.setVisibility(View.VISIBLE);
            rvReviews.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            mAdapterReviews = new AdapterReviews(mContext, reviews.getListReviews());
            rvReviews.setAdapter(mAdapterReviews);
        }
    }

    public static FragmentMovieDetails newInstance(Movie movie) {
        FragmentMovieDetails fragment = new FragmentMovieDetails();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.MOVIE_DETAILS, movie);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static FragmentMovieDetails newInstance() {
        return new FragmentMovieDetails();

    }
}
