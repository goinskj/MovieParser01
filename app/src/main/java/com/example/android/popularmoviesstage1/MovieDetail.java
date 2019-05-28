package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popularmoviesstage1.databinding.ActivityMovieDetailBinding;


import com.bumptech.glide.Glide;
import com.example.android.popularmoviesstage1.data.MovieContract;

public class MovieDetail extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    /*
     * In this Activity, you can share the selected day's forecast. No social sharing is complete
     * without using a hashtag. #BeTogetherNotTheSame
     */
    private static final String MOVIES_SHARE_HASHTAG = " #MoviesApp";

    /*
     * The columns of data that we are interested in displaying within our DetailActivity's
     * weather display.
     */
    public static final String[] DETAIL_MOVIE_PROJECTIONS = {
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
    };

    /*
     * We store the indices of the values in the array of Strings above to more quickly be able
     * to access the data from our query. If the order of the Strings above changes, these
     * indices must be adjusted to match the order of the Strings.
     */
    public static final int INDEX_MOVIE_ID = 0;
    public static final int INDEX_MOVIE_TITLE = 1;
    public static final int INDEX_MOVIE_OVERVIEW = 2;
    public static final int INDEX_POSTER_PATH = 3;
    public static final int INDEX_RELEASE_DATE = 4;
    public static final int INDEX_VOTE_AVERAGE = 5;
    /*
     * This ID will be used to identify the Loader responsible for loading the movie details
     * for a particular movie. In some cases, one Activity can deal with many Loaders. However, in
     * our case, there is only one. We will still use this ID to initialize the loader and create
     * the loader for best practice. Please note that 353 was chosen arbitrarily. You can use
     * whatever number you like, so long as it is unique and consistent.
     */
    private static final int ID_DETAIL_LOADER = 353;

    /* A summary of the forecast that can be shared by clicking the share button in the ActionBar */
    private String mMovieSummary;

    /* The URI that is used to access the chosen movie details */
    private Uri mUri;


    /*
     * This field is used for data binding. Normally, we would have to call findViewById many
     * times to get references to the Views in this Activity. With data binding however, we only
     * need to call DataBindingUtil.setContentView and pass in a Context and a layout, as we do
     * in onCreate of this class. Then, we can access all of the Views in our layout
     * programmatically without cluttering up the code with findViewById.
     */
    private ActivityMovieDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        mUri = getIntent().getData();
        if (mUri == null) throw new NullPointerException("URI for DetailActivity cannot be null");

        /* This connects our Activity into the loader lifecycle. */
        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);

    }

    /**
     * Creates and returns a CursorLoader that loads the data for our URI and stores it in a Cursor.
     *
     * @param loaderId The loader ID for which we need to create a loader
     * @param loaderArgs Any arguments supplied by the caller
     *
     * @return A new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle loaderArgs) {

        switch (loaderId) {

            case ID_DETAIL_LOADER:

                return new CursorLoader(this,
                        mUri,
                        DETAIL_MOVIE_PROJECTIONS,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    /**
     * Runs on the main thread when a load is complete. If initLoader is called (we call it from
     * onCreate in DetailActivity) and the LoaderManager already has completed a previous load
     * for this Loader, onLoadFinished will be called immediately. Within onLoadFinished, we bind
     * the data to our views so the user can see the details of the weather on the date they
     * selected from the forecast.
     *
     * @param loader The cursor loader that finished.
     * @param data   The cursor that is being returned.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        /*
         * Before we bind the data to the UI that will display that data, we need to check the
         * cursor to make sure we have the results that we are expecting. In order to do that, we
         * check to make sure the cursor is not null and then we call moveToFirst on the cursor.
         * Although it may not seem obvious at first, moveToFirst will return true if it contains
         * a valid first row of data.
         *
         * If we have valid data, we want to continue on to bind that data to the UI. If we don't
         * have any data to bind, we just return from this method.
         */
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            /* We have valid data, continue on to bind the data to the UI */
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            /* No data to display, simply return and do nothing */
            return;
        }

        /****************
         * Movie Title *
         ****************/

        String mTitle = data.getString(INDEX_MOVIE_TITLE);

        mDetailBinding.primaryInfo.tvTitle.setText(mTitle);

        /****************
         * Movie Image *
         ****************/

        String mPosterPath = data.getString(INDEX_POSTER_PATH);
        String fullImagePathUrl = "http://image.tmdb.org/t/p/w185/"+mPosterPath;

        /* Set the image path on the icon to display the poster */
        Glide
                .with(this)
                .load(fullImagePathUrl)
                .into(mDetailBinding.primaryInfo.ivDetailImage);

        /****************
         * Movie Overview *
         ****************/

        String mOverview = data.getString(INDEX_MOVIE_OVERVIEW);

        mDetailBinding.primaryInfo.tvOverview.setText(mOverview);

        /****************
         * Movie Votes *
         ****************/

        String mVotes = data.getString(INDEX_VOTE_AVERAGE);

        mDetailBinding.primaryInfo.tvRating.setText(mVotes);

        /****************
         * Movie Release Date *
         ****************/

        String mDate = data.getString(INDEX_RELEASE_DATE);

        mDetailBinding.primaryInfo.tvDate.setText(mDate);

    }

    /**
     * Called when a previously created loader is being reset, thus making its data unavailable.
     * The application should at this point remove any references it has to the Loader's data.
     * Since we don't store any of this cursor's data, there are no references we need to remove.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

//    /**
//     * This method will make the View for the movie data visible and
//     * hide the error message.
//     * <p>
//     * Since it is okay to redundantly set the visibility of a View, we don't
//     * need to check whether each view is currently visible or invisible.
//     */
//    private void showMovieDataView() {
//        /* First, make sure the error is invisible */
//        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
//        /* Then, make sure the movie data is visible */
//        mLinearLayout.setVisibility(View.VISIBLE);
//
//    }
//
//    /**
//     * This method will make the error message visible and hide the movie data
//     * View.
//     * <p>
//     * Since it is okay to redundantly set the visibility of a View, we don't
//     * need to check whether each view is currently visible or invisible.
//     */
//    private void showErrorMessage() {
//        /* First, hide the currently visible data */
//        mLinearLayout.setVisibility(View.INVISIBLE);
//        /* Then, show the error */
//        mErrorMessageDisplay.setVisibility(View.VISIBLE);
//    }
}
