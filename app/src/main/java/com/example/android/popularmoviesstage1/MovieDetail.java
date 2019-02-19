package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetail extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetail.class.getSimpleName();

    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private String mVotes;
    private String mDate;


    private TextView mTitleDisplay;
    private ImageView mPosterDisplay;
    private TextView mOverviewDisplay;
    private TextView mVotesDisplay;
    private TextView mDateDisplay;
    private TextView mErrorMessageDisplay;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitleDisplay = (TextView) findViewById(R.id.tv_title);

        mDateDisplay = (TextView) findViewById(R.id.tv_date);

        mPosterDisplay = (ImageView) findViewById(R.id.iv_detailImage);

        mOverviewDisplay = (TextView) findViewById(R.id.tv_overview);

        mVotesDisplay = (TextView) findViewById(R.id.tv_rating);

        mLinearLayout = (LinearLayout) findViewById(R.id.ll_main);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_COMPONENT_NAME)) {
                Context context = MovieDetail.this;

                Bundle extras = intentThatStartedThisActivity.getBundleExtra(Intent.EXTRA_COMPONENT_NAME);

                if (extras == null) {
                    showErrorMessage();
                } else {
                    showMovieDataView();
                    mTitle = extras.getString("title");
                    mPosterPath = extras.getString("image");
                    String fullImagePathUrl = "http://image.tmdb.org/t/p/w185/"+mPosterPath;
                    mOverview = extras.getString("overview");
                    mVotes = extras.getString("vote_average");
                    mDate = extras.getString("release_date");
                    mTitleDisplay.setText(mTitle);

                    Glide
                            .with(context)
                            .load(fullImagePathUrl)
                            .into(mPosterDisplay);

                    mOverviewDisplay.setText(mOverview);
                    mVotesDisplay.setText(mVotes + "/10");
                    mDateDisplay.setText(mDate);

                }
            }
        }
    }

    /**
     * This method will make the View for the movie data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the movie data is visible */
        mLinearLayout.setVisibility(View.VISIBLE);

    }

    /**
     * This method will make the error message visible and hide the movie data
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mLinearLayout.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
