package com.example.android.popularmoviesstage1.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.popularmoviesstage1.data.MovieContract;
import com.example.android.popularmoviesstage1.utilities.MovieJsonUtils;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;

import java.net.URL;

public class MoviesSyncTask {
    /**
     * Performs the network request for updated movie, parses the JSON from that request, and
     * inserts the new movie information into our ContentProvider.
     *
     * @param context Used to access utility methods and the ContentResolver
     */
    synchronized public static void syncMovies(Context context) {

        try {
            /*
             * The getUrl method will return the URL that we need to get the JSON for the
             * movies. It will decide whether to create a URL based off of the popular sort
             * preference or best rated sort preference.
             */
            URL movieRequestUrl = NetworkUtils.getUrl(context);

            /* Use the URL to retrieve the JSON */
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

            /* Parse the JSON into a list of weather values */
            ContentValues[] movieValues = MovieJsonUtils
                    .getMovieStringsFromJson(context, jsonMovieResponse);

            /*
             * In cases where our JSON contained an error code, getMovieStringsFromJson
             * would have returned null. We need to check for those cases here to prevent any
             * NullPointerExceptions being thrown. We also have no reason to insert fresh data if
             * there isn't any to insert.
             */
            if (movieValues != null && movieValues.length != 0) {
                /* Get a handle on the ContentResolver to delete and insert data */
                ContentResolver moviesContentResolver = context.getContentResolver();

                /* Delete old movie data because we don't need to keep multiple days' data */
                moviesContentResolver.delete(
                        MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null);

                /* Insert our new weather data into Sunshine's ContentProvider */
                moviesContentResolver.bulkInsert(
                        MovieContract.MovieEntry.CONTENT_URI,
                        movieValues);


                /* If the code reaches this point, we have successfully performed our sync */

            }

        } catch (Exception e) {
            /* Server probably invalid */
            e.printStackTrace();
        }

    }
}
