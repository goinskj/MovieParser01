/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.popularmoviesstage1.utilities;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.data.MoviesPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the movie servers.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * Retrieves the proper URL to query for the weather data. The reason for both this method as
     * well as {@link #buildQueryforHighestRatedSortUrl(String,String)} and
     * {@link #buildQueryforPopularSortUrl(String, String)} is the following.
     * <p>
     * 1) You should be able to just use one method when you need to create the URL within the
     * app instead of calling both methods. Also, by using the preferences, we mitigate the amount
     * of pasthrough parameters allowing for cleaner code. We decide whether the sort is in the
     * popular state or the rated state by checking the Shared Preferences.
     *
     * @param context used to access other Utility methods
     * @return URL to query weather service
     */
    public static URL getUrl(Context context) {
        if (MoviesPreferences.isPopular(context)) {
            String sortQuery = context.getResources().getString(R.string.pref_popular);
            String api = context.getResources().getString(R.string.movies_query_api);
            return buildQueryforPopularSortUrl(sortQuery, api);
        } else {
            String sortQuery = context.getResources().getString(R.string.pref_rated);
            String api = context.getResources().getString(R.string.movies_query_api);
            return buildQueryforHighestRatedSortUrl(sortQuery, api);
        }
    }

    /**
     * Builds the URL used to talk to the movie server using a sort type. This sort type is based
     * on the query capabilities of the movie provider that we are using.
     *
     * @return The URL to use to query the movie server.
     */
    public static URL buildQueryforPopularSortUrl(String sortQuery, String apiKey) {
        String queryUrl = "http://api.themoviedb.org/3/movie/"+sortQuery+"?api_key="+apiKey;
        Uri builtUri = Uri.parse(queryUrl);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildQueryforHighestRatedSortUrl(String sortQuery, String apiKey) {
        String queryUrl = "http://api.themoviedb.org/3/movie/"+sortQuery+"?api_key="+apiKey;
        Uri builtUri = Uri.parse(queryUrl);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
