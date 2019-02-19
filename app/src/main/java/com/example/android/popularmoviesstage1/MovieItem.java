package com.example.android.popularmoviesstage1;

import android.widget.ImageView;

public class MovieItem {

    String title;
    String imageUrl;
    String overview;
    int vote_average;
    String release_date;

    public MovieItem(String vTitle, String vImageUrl, String vOverview, int vote_average,
                     String release_date) {

        this.title = vTitle;
        this.imageUrl = vImageUrl;
        this.overview = vOverview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }
}
