package com.example.goodbox.capstone_stage2.rest;

import android.content.ContentProviderOperation;
import android.util.Log;

import com.example.goodbox.capstone_stage2.data.MovieColumns;
import com.example.goodbox.capstone_stage2.data.MovieProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Goodbox on 21-01-2017.
 */

public class Utils {
    private static String LOG_TAG = Utils.class.getSimpleName();

    public static ArrayList movieJsonToContentVals(MovieItem movieItem) throws JSONException {
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        ContentProviderOperation cpo = null;
        List<Movie> movies;

        if(movieItem != null) {
                movies = movieItem.getMovies();
                if (movies != null) {
                    if (movies.size() != 0) {
                        for (int i = 0; i < movies.size(); i++) {
                            cpo = buildBatchOperation(movies.get(i));
                            if (cpo != null) {
                                batchOperations.add(cpo);
                            }
                        }
                    }
                }
        }
        return batchOperations;
    }

    public static ContentProviderOperation buildBatchOperation(Movie movie) throws JSONException{
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                MovieProvider.Movies.CONTENT_URI);

            builder.withValue(MovieColumns.NAME, movie.getTitle());
            builder.withValue(MovieColumns.IMAGE,movie.getPoster_path());
            builder.withValue(MovieColumns.RELEASE_DATE, movie.getRelease_date());
            builder.withValue(MovieColumns.LANGUAGE, movie.getOriginal_language());
            builder.withValue(MovieColumns.RATING, movie.getVote_average());
            builder.withValue(MovieColumns.OVERVIEW, movie.getOverview());

        return builder.build();
    }

}
