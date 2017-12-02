package com.udacity.movieapp.common.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.udacity.movieapp.common.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eslam.Mahmoud on 12/02/2017.
 */

public class MoviesDBController {

    private ArrayList<Movie> listOfMovies = new ArrayList<>();
    private static MovieDBHelper movieHelper;

    public MoviesDBController(Context context) {
        if (movieHelper == null)
            movieHelper = new MovieDBHelper(context);
    }

    public long addFavouriteMovie(Movie movie) {
        SQLiteDatabase db = movieHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(movieHelper.MOVIE_ID, movie.getId());
        contentValues.put(movieHelper.MOVIE_TITLE, movie.getTitle());
        contentValues.put(movieHelper.MOVIE_OVERVIEW, movie.getOverview());
        contentValues.put(movieHelper.MOVIE_POSTER_PATH, movie.getPosterPath());
        contentValues.put(movieHelper.MOVIE_BACKDROP_PATH, movie.getBackdropPath());
        contentValues.put(movieHelper.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(movieHelper.MOVIE_RELEASE_DATE, movie.getReleaseDate());

        return db.insert(movieHelper.TABLE_NAME, null, contentValues);
    }

    public int deleteFromFavourite(int movieId) {
        SQLiteDatabase db = movieHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(movieHelper.MOVIE_ID, movieId);
        return db.delete(movieHelper.TABLE_NAME, contentValues.toString(), null);
    }

    public boolean isFavoriteMovie(int movieId) {
        SQLiteDatabase db = movieHelper.getReadableDatabase();
        String isFavoriteQuery = "Select * FROM " + movieHelper.TABLE_NAME + " WHERE " + movieHelper.MOVIE_ID + " = " + movieId + "";
        Cursor c = db.rawQuery(isFavoriteQuery, null);
        return c.moveToNext();

    }

    public ArrayList<Movie> getFavouriteMovies() {
        SQLiteDatabase db = movieHelper.getReadableDatabase();
        String select = "Select * FROM " + movieHelper.TABLE_NAME;
        Cursor c = db.rawQuery(select, null);
        Movie tempMovie;
        if (listOfMovies != null)
            listOfMovies.clear();
        while (c.moveToNext()) {
            tempMovie = new Movie();
            tempMovie.setId(c.getInt(0));
            tempMovie.setTitle(c.getString(1));
            tempMovie.setOverview(c.getString(2));
            tempMovie.setPosterPath(c.getString(3));
            tempMovie.setBackdropPath(c.getString(4));
            tempMovie.setVoteAverage(Double.parseDouble(c.getString(5)));
            tempMovie.setReleaseDate(c.getString(6));

            listOfMovies.add(tempMovie);
        }
        c.close();
        db.close();
        return listOfMovies;
    }
}
