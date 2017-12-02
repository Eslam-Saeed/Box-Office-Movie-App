package com.udacity.movieapp.movie_details;

import com.udacity.movieapp.common.base.BaseView;
import com.udacity.movieapp.common.models.Movie;
import com.udacity.movieapp.common.models.Reviews;
import com.udacity.movieapp.common.models.Trailers;

import java.util.ArrayList;

/**
 * Created by eslam on 11/21/17.
 */

public interface ViewMovieDetails extends BaseView {
    void showProgressBar(boolean shouldShow);

    void onMovieDetailsSuccess(Movie movieDetails);

    void onMovieDetailsFail(String message);

    void onTrailersSuccess(Trailers trailers);

    void onReviewsSuccess(Reviews reviews);
}
