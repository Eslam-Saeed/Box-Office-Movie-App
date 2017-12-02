package com.udacity.movieapp.common.helpers;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Eslam.Mahmoud on 12/02/2017.
 */

class MovieDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favoriteMovies.db";

    static final String TABLE_NAME = "favourite_movies_table";
    static final String MOVIE_ID = "movie_id";
    static final String MOVIE_TITLE = "movie_title";
    static final String MOVIE_OVERVIEW = "movie_overview";
    static final String MOVIE_POSTER_PATH = "movie_poster_path";
    static final String MOVIE_BACKDROP_PATH = "movie_backdrop_path";
    static final String MOVIE_VOTE_AVERAGE = "movie_vote_average";
    static final String MOVIE_RELEASE_DATE = "movie_release_date";

    private static final int DATABASE_VERSION = 1;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE = "CREATE TABLE " +
                    TABLE_NAME + " ("
                    + MOVIE_ID + " INTEGER PRIMARY KEY, "
                    + MOVIE_TITLE + " VARCHAR(255) , "
                    + MOVIE_OVERVIEW + " VARCHAR(255) , "
                    + MOVIE_POSTER_PATH + " VARCHAR(255) , "
                    + MOVIE_BACKDROP_PATH + " VARCHAR(255) , "
                    + MOVIE_VOTE_AVERAGE + " VARCHAR(255) , "
                    + MOVIE_RELEASE_DATE + " VARCHAR(255) );";
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}