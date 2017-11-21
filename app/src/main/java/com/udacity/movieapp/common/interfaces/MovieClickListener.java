package com.udacity.movieapp.common.interfaces;

import com.udacity.movieapp.common.models.Movie;

/**
 * Created by eslam on 11/21/17.
 */

public interface MovieClickListener {
    void navigateToDetailsScreen(Movie movie);
}
