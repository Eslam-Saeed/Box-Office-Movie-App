package com.udacity.movieapp.common.helpers.dbandprovider;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


import com.udacity.movieapp.common.models.Movie;

import java.util.ArrayList;

/**
 * Created by Eslam.Mahmoud on 12/02/2017.
 */

public class MoviesDBController {
    private Context mContext;
    private ArrayList<Movie> listOfMovies = new ArrayList<>();
    private static MovieDBHelper movieHelper;

    public MoviesDBController(Context context) {
        this.mContext = context;
        if (movieHelper == null)
            this.movieHelper = new MovieDBHelper(context);
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

        Uri returnUri = null;
        if (mContext != null && mContext.getContentResolver() != null)
            returnUri = mContext.getContentResolver().insert(MovieDBHelper.BASE_CONTENT, contentValues);
        return returnUri == null ? -1 : 1;
    }

    public int deleteFromFavourite(int movieId) {
/*        SQLiteDatabase db = movieHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(movieHelper.MOVIE_ID, movieId);
        db.delete(movieHelper.TABLE_NAME, contentValues.toString(), null)*/
        String select = MovieDBHelper.MOVIE_ID + " = " + movieId;
        if (mContext != null && mContext.getContentResolver() != null)
            return mContext.getContentResolver().delete(MovieDBHelper.BASE_CONTENT, select, null);
        return -1;
    }

    public boolean isFavoriteMovie(int movieId) {
        Cursor c = null;
        String isFavoriteQuery = "Select * FROM " + movieHelper.TABLE_NAME + " WHERE " + movieHelper.MOVIE_ID + " = " + movieId + "";
        if (mContext != null && mContext.getContentResolver() != null)
            c = mContext.getContentResolver().query(MovieDBHelper.BASE_CONTENT.buildUpon().appendEncodedPath(String.valueOf(movieId)).build(), null, isFavoriteQuery, null, null);
        return c != null && c.moveToNext();
    }

    public ArrayList<Movie> getFavouriteMovies() {
        Cursor c = null;
        String select = "Select * FROM " + movieHelper.TABLE_NAME;
        if (mContext != null && mContext.getContentResolver() != null)
            c = mContext.getContentResolver().query(MovieDBHelper.BASE_CONTENT, null, select, null, null);

        Movie tempMovie;
        if (listOfMovies != null)
            listOfMovies.clear();
        while (c != null && c.moveToNext()) {
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
        if (c != null)
            c.close();

        return listOfMovies;
    }
}
