package com.example.android.popularmoviesstage1.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class MoviesSyncIntentService extends IntentService {

    public MoviesSyncIntentService() {
        super("MoviesSyncIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        MoviesSyncTask.syncMovies(this);

    }
}
