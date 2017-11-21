package com.udacity.movieapp.listing_movies;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.movieapp.R;
import com.udacity.movieapp.common.base.BaseActivity;
import com.udacity.movieapp.common.helpers.Constants;
import com.udacity.movieapp.common.interfaces.ToolbarChangeListener;

public class MainActivity extends BaseActivity implements ToolbarChangeListener {
    private Toolbar mToolbarMainListing;
    private String searchFilter = Constants.POPULAR;
    private FragmentListingMovies mFragmentListingMovies;
    private boolean mShowMenuIcons = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setListeners();
        setToolbar(mToolbarMainListing, getString(R.string.app_name), false, false);
        if (savedInstanceState == null)
            loadFragments();
        else
            mFragmentListingMovies = (FragmentListingMovies) getSupportFragmentManager().getFragment(savedInstanceState, Constants.FRAGMENT_TAG);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initializeViews() {
        mToolbarMainListing = findViewById(R.id.toolbarMainListing);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void loadFragments() {
        if (mFragmentListingMovies == null)
            mFragmentListingMovies = FragmentListingMovies.newInstance(this, searchFilter);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerMainListing,
                mFragmentListingMovies).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        menu.findItem(R.id.action_popular).setVisible(mShowMenuIcons);
        menu.findItem(R.id.action_top_rated).setVisible(mShowMenuIcons);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mFragmentListingMovies == null)
            mFragmentListingMovies = FragmentListingMovies.newInstance(this, searchFilter);
        switch (item.getItemId()) {
            case R.id.action_top_rated:
                searchFilter = Constants.TOP_RATED;
                mFragmentListingMovies.refresh(searchFilter);
                break;
            case R.id.action_popular:
                searchFilter = Constants.POPULAR;
                mFragmentListingMovies.refresh(searchFilter);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, Constants.FRAGMENT_TAG, mFragmentListingMovies);
    }

    @Override
    public void changeToolbar(String title, boolean showBackButton, boolean showMenuIcons) {
        mShowMenuIcons = showMenuIcons;
        setToolbar(mToolbarMainListing, title, showBackButton, showBackButton);
        invalidateOptionsMenu();
    }

}
