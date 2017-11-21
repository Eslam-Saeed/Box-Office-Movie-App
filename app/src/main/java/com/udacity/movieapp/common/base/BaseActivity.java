package com.udacity.movieapp.common.base;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.udacity.movieapp.R;


public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar myToolbar;

    protected void setToolbar(Toolbar toolbar, String title, boolean showUpButton, boolean showBackButton) {
        myToolbar = toolbar;
        myToolbar.setTitle(title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setElevation(getResources().getDimension(R.dimen.padding_small));

        if (showBackButton)
            setToolbarNavigationIcon(toolbar, R.drawable.ic_back);
        else
            toolbar.setNavigationIcon(null);
        setSupportActionBar(myToolbar);

        if (showUpButton) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }


    protected void setToolbarNavigationIcon(Toolbar toolbar, int iconId) {
        toolbar.setNavigationIcon(iconId);
    }

    protected abstract void initializeViews();

    protected abstract void setListeners();

    protected abstract void loadFragments();

}
