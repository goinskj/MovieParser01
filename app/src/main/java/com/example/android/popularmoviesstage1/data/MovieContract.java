package com.example.android.popularmoviesstage1.data;


import android.content.Intent;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the movie database. This class is not necessary, but keeps
 * the code organized.
 */
public class MovieContract {

    /*
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website. A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * Play Store.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmoviesstage1";

    /*
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider for Sunshine.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /*
     * Possible paths that can be appended to BASE_CONTENT_URI to form valid URI's that PM
     * can handle. For instance,
     *
     *     content://com.example.android.sunshine/weather/
     *     [           BASE_CONTENT_URI         ][ PATH_MOVIES ]
     *
     * is a valid path for looking at weather data.
     *
     *      content://com.example.android.sunshine/givemeroot/
     *
     * will fail, as the ContentProvider hasn't been given any information on what to do with
     * "givemeroot". At least, let's hope not. Don't be that dev, reader. Don't be that dev.
     */
    public static final String PATH_MOVIE = "movies";

    /* Inner class that defines the table contents of the weather table */
    public static final class MovieEntry implements BaseColumns {

        /* The base CONTENT_URI used to query the Movies table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        /* Used internally as the name of our movies table. */
        public static final String TABLE_NAME = "movies";

        /*
         * The date column will store the UTC date that correlates to the local date for which
         * each particular weather row represents. For example, if you live in the Eastern
         * Standard Time (EST) time zone and you load weather data at 9:00 PM on September 23, 2016,
         * the UTC time stamp for that particular time would be 1474678800000 in milliseconds.
         * However, due to time zone offsets, it would already be September 24th, 2016 in the GMT
         * time zone when it is 9:00 PM on the 23rd in the EST time zone. In this example, the date
         * column would hold the date representing September 23rd at midnight in GMT time.
         * (1474588800000)
         *
         * The reason we store GMT time and not local time is because it is best practice to have a
         * "normalized", or standard when storing the date and adjust as necessary when
         * displaying the date. Normalizing the date also allows us an easy way to convert to
         * local time at midnight, as all we have to do is add a particular time zone's GMT
         * offset to this date to get local time at midnight on the appropriate date.
         */
        public static final String COLUMN_MOVIE_ID = "id";

        /* Movie ID as returned by API, used to identify the icon to be used */
        public static final String COLUMN_MOVIE_TITLE = "title";

        /* Overview of movie as returned by API*/
        public static final String COLUMN_OVERVIEW = "overview";

        /* Image path for movie as returned by API */
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /* release date of movie as returned by API */
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /* Rating of movie as returned by API*/
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";


        /**
         * Builds a URI that adds the movie id to the end of the movie content URI path.
         * This is used to query details about a single movie entry by date. This is what we
         * use for the detail view query. We assume an id is passed to this method.
         *
         * @param id id is an int
         * @return Uri to query details about a single weather entry
         */
        public static Uri buildMovieUriWithId(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }

        /**
         * Returns just the selection part of the movie query.This should select all movies in
         * table.
         * @return The selection part of the movie query for all movies in the table.
         */
        public static String getSqlSelectForMovies() {
            return MovieEntry.COLUMN_MOVIE_ID;
        }

    }

}
