package com.udacity.movieapp.listing_movies;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.udacity.movieapp.R;
import com.udacity.movieapp.common.base.BasePresenter;
import com.udacity.movieapp.common.helpers.ConnectionDetector;
import com.udacity.movieapp.common.helpers.ServicesHelper;
import com.udacity.movieapp.common.models.dto.MainResponse;

/**
 * Created by Eslam.Mahmoud on 11/20/2017.
 */

class PresenterListingMovies extends BasePresenter {
    private Context mContext;
    private ViewListingMovies mViewListingMovies;

    PresenterListingMovies(Context context, ViewListingMovies viewListingMovies) {
        this.mContext = context;
        this.mViewListingMovies = viewListingMovies;
    }

    void getListMovies(String searchFilter) {
        if (ConnectionDetector.isConnectingToInternet(mContext)) {
            mViewListingMovies.showProgressBar(true);
            ServicesHelper.getInstance(mContext).getListMovies(searchFilter, getListMoviesSuccessListener, getListMoviesErrorListener);
        }else
            mViewListingMovies.onMoviesListingFail(mContext.getString(R.string.no_internet));

    }

    private Response.Listener<MainResponse> getListMoviesSuccessListener = new Response.Listener<MainResponse>() {
        @Override
        public void onResponse(MainResponse mainResponse) {
            mViewListingMovies.showProgressBar(false);
            if (mainResponse != null) {
                mViewListingMovies.onMoviesListingSuccess(mainResponse.getListMovies());
            } else
                mViewListingMovies.onMoviesListingFail(mContext.getString(R.string.error_message));
        }
    };

    private Response.ErrorListener getListMoviesErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mViewListingMovies.showProgressBar(false);
            mViewListingMovies.onMoviesListingFail(mContext.getString(R.string.error_message));
        }
    };
}
