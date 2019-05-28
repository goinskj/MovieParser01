package com.example.android.popularmoviesstage1.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.android.popularmoviesstage1.R;

public class MoviesPreferences {

    /**
     * Returns true if the user has selected metric temperature display.
     *
     * @param context Context used to get the SharedPreferences
     * @return true if metric display should be used, false if imperial display should be used
     */
    public static boolean isPopular(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForSort = context.getString(R.string.pref_sort_key);
        String defaultSort = context.getString(R.string.pref_popular);
        String preferredSort = sp.getString(keyForSort, defaultSort);
        String popular = context.getString(R.string.pref_popular);

        boolean userPrefersPopular = false;
        if (popular.equals(preferredSort)) {
            userPrefersPopular = true;
        }

        return userPrefersPopular;
    }


}
