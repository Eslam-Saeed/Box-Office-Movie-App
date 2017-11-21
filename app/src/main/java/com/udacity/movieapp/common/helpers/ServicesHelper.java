package com.udacity.movieapp.common.helpers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.udacity.movieapp.common.models.Movie;
import com.udacity.movieapp.common.models.dto.MainResponse;

public class ServicesHelper {
    private static ServicesHelper mInstance;

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String API_KEY = "?api_key=";


    private RequestQueue mRequestQueue;
    private DefaultRetryPolicy defaultRetry = new DefaultRetryPolicy(
            Constants.TIME_OUT_VALUE,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public enum Tag {
        MOVIE_DETAILS, MAIN_LISTING_MOVIES
    }

    private ServicesHelper(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized ServicesHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ServicesHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    //================ Main Movies =======================
    public void getListMovies(String searchFilter, final Response.Listener<MainResponse> getListMoviesSuccessListener, Response.ErrorListener getListMoviesErrorListener) {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL.concat(searchFilter).concat(API_KEY), new Response.Listener<String>() {
                @Override
                public void onResponse(String stringResponse) {
                    if (stringResponse != null)
                        getListMoviesSuccessListener.onResponse(MyApplication.getmGson().fromJson(stringResponse, MainResponse.class));
                }
            }, getListMoviesErrorListener);
            stringRequest.setTag(Tag.MAIN_LISTING_MOVIES);
            stringRequest.setRetryPolicy(defaultRetry);
            addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //=========== Movie Details ======================

    public void getMovieDetails(String movieId, final Response.Listener<Movie> geMovieDetailsSuccessListener, Response.ErrorListener getMovieDetailsErrorListener) {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL.concat(movieId).concat(API_KEY), new Response.Listener<String>() {
                @Override
                public void onResponse(String stringResponse) {
                    if (stringResponse != null)
                        geMovieDetailsSuccessListener.onResponse(MyApplication.getmGson().fromJson(stringResponse, Movie.class));
                }
            }, getMovieDetailsErrorListener);
            stringRequest.setTag(Tag.MOVIE_DETAILS);
            stringRequest.setRetryPolicy(defaultRetry);
            addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    //============ Change Password ====================
    public void changePassword(final Context mContext, final ChangePasswordRequest changePasswordRequest, final Response.Listener<MainResponse> successListener, final Response.ErrorListener errorListener) {
        StringRequest jsonObjectRequest = null;
        jsonObjectRequest = new StringRequest(Request.Method.PUT, BASE_URL.concat("Profile/changepassword"), new Response.Listener<String>() {
            @Override
            public void onResponse(String mainResponse) {
                if (mainResponse != null)
                    successListener.onResponse(MyApplication.getmGson().fromJson(mainResponse, MainResponse.class));
                else
                    errorListener.onErrorResponse(new VolleyError());
            }
        }, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getMyHeaders(mContext);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put(Constants.CURRENT_PASSWORD, changePasswordRequest.getCurrentPassword());
                map.put(Constants.NEW_PASSWORD, changePasswordRequest.getNewPassword());
                return map;
            }
        };

        jsonObjectRequest.setTag(Tag.CHANGE_PASSWORD);
        jsonObjectRequest.setRetryPolicy(defaultRetry);
        addToRequestQueue(jsonObjectRequest);
    }*/

/*    //=============== Forget Password Mail =======================
    public void getForgetPasswordMail(Response.Listener<String> forgetPasswordSuccessListener, Response.ErrorListener forgetPasswordErrorListener) {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL.concat("profile/getforgetpasswordemail"), forgetPasswordSuccessListener, forgetPasswordErrorListener);
            stringRequest.setTag(Tag.FORGET_PASSWORD);
            stringRequest.setRetryPolicy(defaultRetry);
            addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
