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

import android.content.ContentValues;
import android.content.Context;

import com.example.android.popularmoviesstage1.MovieItem;
import com.example.android.popularmoviesstage1.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Utility functions to handle movie JSON data.
 */

public class MovieJsonUtils {

    /* Movie item information we want to parse*/
    private static final String MDB_ID = "id";
    private static final String MDB_TITLE = "title";
    private static final String MDB_OVERVIEW = "overview";
    private static final String MDB_POSTER_PATH = "poster_path";
    private static final String MDB_RELEASE_DATE = "release_date";
    private static final String MDB_VOTE_AVERAGE = "vote_average";


    private static final String LOG_TAG = MovieJsonUtils.class.getSimpleName();

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the movies over various sort criteria.
     * <p/>
     *
     * @param moviesJsonStr JSON response from server
     *
     * @return Array of Strings describing movie data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */

    public static ContentValues[] getMovieStringsFromJson(Context context, String moviesJsonStr)
            throws JSONException {


        JSONObject movieJson = new JSONObject(moviesJsonStr);

        if (movieJson != null) {

            JSONArray movieArray = movieJson.getJSONArray("results");

            ContentValues[] movieContentValues = new ContentValues[movieArray.length()];

            for (int i = 0; i < movieArray.length() - 1; i++) {

                JSONObject item = movieArray.getJSONObject(i);

                int id = item.getInt(MDB_ID);
                String title = item.getString(MDB_TITLE);
                String overview = item.getString(MDB_OVERVIEW);
                String poster_path = item.getString(MDB_POSTER_PATH);
                String release_date = item.getString(MDB_RELEASE_DATE);
                int vote_average = item.getInt(MDB_VOTE_AVERAGE);

                ContentValues movieValues = new ContentValues();
                movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, id);
                movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, title);
                movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overview);
                movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, poster_path);
                movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, release_date);
                movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, vote_average);

                movieContentValues[i] = movieValues;
            }

            return movieContentValues;

        } else {

            return null;

        }

    }

}
