package com.udacity.movieapp.common.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import com.udacity.movieapp.R;


/**
 * Created by Eslam on 11/20/2017.
 */

public class Utilities {

    public static Spanned fromHTML(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        else
            return Html.fromHtml(text);

    }


    public static void hideSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (((Activity) context).getCurrentFocus() != null && ((Activity) context).getCurrentFocus().getWindowToken() != null)
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (isTablet(context))
            return (int) (dpWidth / 220);
        else
            return (int) (dpWidth / 180);
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    public static void openVideo(Context context, String videoKey) {
        String video_path = "http://www.youtube.com/watch?v=" + videoKey;
        Uri uri = Uri.parse(video_path);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

}
