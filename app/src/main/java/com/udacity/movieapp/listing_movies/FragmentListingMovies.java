package com.udacity.movieapp.listing_movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.movieapp.R;
import com.udacity.movieapp.common.base.BaseFragment;
import com.udacity.movieapp.common.helpers.ConnectionDetector;
import com.udacity.movieapp.common.helpers.Constants;
import com.udacity.movieapp.common.helpers.dbandprovider.MoviesDBController;
import com.udacity.movieapp.common.helpers.Utilities;
import com.udacity.movieapp.common.interfaces.MovieClickListener;
import com.udacity.movieapp.common.interfaces.ToolbarChangeListener;
import com.udacity.movieapp.common.models.Movie;
import com.udacity.movieapp.movie_details.FragmentMovieDetails;

import java.util.ArrayList;

/**
 * Created by Eslam.Mahmoud on 11/20/2017.
 */

public class FragmentListingMovies extends BaseFragment implements ViewListingMovies, MovieClickListener {
    private String searchFilter;
    private View view;
    private ToolbarChangeListener mToolbarChangeListener;
    private Context mContext;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerViewListingMovies;
    private PresenterListingMovies mPresenterListingMovies;
    private GridLayoutManager mGridLayoutManager;
    private ArrayList<Movie> mListMovies;
    private AdapterMoviesListing mAdapterMoviesListing;
    private MoviesDBController mDBController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mPresenterListingMovies = new PresenterListingMovies(mContext, this);
        mDBController = new MoviesDBController(mContext);
        if (getArguments() != null)
            searchFilter = getArguments().getString(Constants.SEARCH_FILTER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listing_movies, null);
        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mToolbarChangeListener = (ToolbarChangeListener) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mToolbarChangeListener != null)
            mToolbarChangeListener.changeToolbar(getString(R.string.app_name), false, true);
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.MOVIES_LIST)) {
            ArrayList<Movie> tempList = savedInstanceState.getParcelableArrayList(Constants.MOVIES_LIST);
            onMoviesListingSuccess(tempList);
        } else
            mPresenterListingMovies.getListMovies(searchFilter);

    }

    @Override
    protected void initializeViews(View v) {
        mProgressBar = v.findViewById(R.id.progressBarMainListing);
        mRecyclerViewListingMovies = v.findViewById(R.id.rvListingMovies);
        mGridLayoutManager = new GridLayoutManager(mContext, Utilities.calculateNoOfColumns(mContext));
        mRecyclerViewListingMovies.setLayoutManager(mGridLayoutManager);
        mRecyclerViewListingMovies.setHasFixedSize(true);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void showProgressBar(boolean shouldShow) {
        mProgressBar.setVisibility(shouldShow ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onMoviesListingSuccess(ArrayList<Movie> listMovies) {
        if (mListMovies != null) {
            mListMovies.clear();
            mListMovies.addAll(listMovies);
        } else
            mListMovies = listMovies;

        if (mAdapterMoviesListing == null) {
            mAdapterMoviesListing = new AdapterMoviesListing(mContext, mListMovies, this);
            mRecyclerViewListingMovies.setAdapter(mAdapterMoviesListing);
        } else {
            mRecyclerViewListingMovies.setAdapter(mAdapterMoviesListing);
            mAdapterMoviesListing.notifyDataSetChanged();
        }
    }

    @Override
    public void onMoviesListingFail(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    public void refresh(String mySearchFilter) {
        if (Utilities.isTablet(mContext))
            navigateToDetailsScreen(null);
        if (mPresenterListingMovies == null)
            mPresenterListingMovies = new PresenterListingMovies(mContext, this);
        searchFilter = mySearchFilter;
        mPresenterListingMovies.getListMovies(searchFilter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.MOVIES_LIST, mListMovies);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void navigateToDetailsScreen(Movie movie) {
        if (ConnectionDetector.isConnectingToInternet(mContext)) {
            getFragmentManager().beginTransaction().replace(Utilities.isTablet(mContext) ? R.id.containerMovieDetails : R.id.containerMainListing,
                    FragmentMovieDetails.newInstance(movie)).addToBackStack(Constants.FRAGMENT_MOVIE_DETAILS_TAG).commit();
        } else
            onMoviesListingFail(getString(R.string.no_internet));
    }

    public static FragmentListingMovies newInstance(Context context, String searchFilter) {
        FragmentListingMovies fragment = new FragmentListingMovies();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SEARCH_FILTER, searchFilter);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void loadFavorites() {
        if (Utilities.isTablet(mContext))
            navigateToDetailsScreen(null);
        ArrayList<Movie> tempList = mDBController.getFavouriteMovies();
        if (tempList != null && tempList.size() > 0)
            onMoviesListingSuccess(tempList);
        else
            onMoviesListingFail(getString(R.string.no_favorite_movies));
    }
}
