package com.udacity.movieapp.movie_details;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.udacity.movieapp.R;
import com.udacity.movieapp.common.base.BasePresenter;
import com.udacity.movieapp.common.helpers.ConnectionDetector;
import com.udacity.movieapp.common.helpers.ServicesHelper;
import com.udacity.movieapp.common.models.Movie;
import com.udacity.movieapp.common.models.dto.MainResponse;

/**
 * Created by eslam on 11/21/17.
 */

public class PresenterMovieDetails extends BasePresenter {
    private Context mContext;
    private ViewMovieDetails mViewMovieDetails;

    public PresenterMovieDetails(Context context, ViewMovieDetails viewMovieDetails) {
        mContext = context;
        mViewMovieDetails = viewMovieDetails;
    }

    void getMovieDetails(String searchFilter) {
        if (ConnectionDetector.isConnectingToInternet(mContext)) {
            mViewMovieDetails.showProgressBar(true);
            ServicesHelper.getInstance(mContext).getMovieDetails(searchFilter, geMovieDetailsSuccessListener, getMovieDetailsErrorListener);
        }
    }

    private Response.Listener<Movie> geMovieDetailsSuccessListener = new Response.Listener<Movie>() {
        @Override
        public void onResponse(Movie movie) {
            mViewMovieDetails.showProgressBar(false);
            if (movie != null) {
                mViewMovieDetails.onMovieDetailsSuccess(movie);
            } else
                mViewMovieDetails.onMovieDetailsFail(mContext.getString(R.string.error_message));
        }
    };

    private Response.ErrorListener getMovieDetailsErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mViewMovieDetails.showProgressBar(false);
            mViewMovieDetails.onMovieDetailsFail(mContext.getString(R.string.error_message));
        }
    };
}
