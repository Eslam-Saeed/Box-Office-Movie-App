package com.udacity.movieapp.common.helpers;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udacity.movieapp.R;

/**
 * Created by Eslam on 11/20/2017.
 */

public class MyApplication extends Application {

    private static Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        mGson = new GsonBuilder().create();
        LocalizationHelper.changeAppLanguage(LocalizationHelper.getLanguage(this), this);

    }

    public static Gson getmGson() {
        return mGson;
    }


    public static MyApplication getInstance(Context context) {
        return ((MyApplication) context.getApplicationContext());
    }

}
