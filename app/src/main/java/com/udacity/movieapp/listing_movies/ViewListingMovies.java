package com.udacity.movieapp.listing_movies;

import com.udacity.movieapp.common.base.BaseView;
import com.udacity.movieapp.common.models.Movie;

import java.util.ArrayList;

/**
 * Created by Eslam.Mahmoud on 11/20/2017.
 */

public interface ViewListingMovies extends BaseView {

    void showProgressBar(boolean shouldShow);

    void onMoviesListingSuccess(ArrayList<Movie> listMovies);

    void onMoviesListingFail(String message);
}
