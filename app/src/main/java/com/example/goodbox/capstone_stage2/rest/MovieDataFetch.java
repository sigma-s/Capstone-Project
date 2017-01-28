package com.example.goodbox.capstone_stage2.rest;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.example.goodbox.capstone_stage2.BuildConfig;
import com.example.goodbox.capstone_stage2.data.MovieProvider;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Goodbox on 22-01-2017.
 */

public class MovieDataFetch {
    private Context mContext;
    private String api_key = BuildConfig.THE_MOVIE_DB_API_KEY;
    private String language = "en-US";
    private String sort_by = "vote_count.desc";
    private String adult = "false";
    private String video = "false";
    private String page = "1";
    private String vote_count_min = "2000";
    private String original_language = "en";
    private MovieItem callResult;

    public MovieDataFetch(Context context)
    {
        mContext = context;
    }

    public void getData(){

        if(api_key.isEmpty()){
            Toast.makeText(mContext, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
        }
        TheMovieDBAPI movieApiService =
                ApiClient.getClient().create(TheMovieDBAPI.class);
        Call<MovieItem> call = movieApiService.getMovies(api_key);
                /**language,sort_by,adult,video,page,vote_count_min,original_language);**/

        call.enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem>call, Response<MovieItem> response) {
                //List<MovieItem.Movie> movies = response.body().getMovies();
                //Log.d(TAG, "Number of movies received: " + movies.size());
                System.out.println("Response status code:"+ response.code());
                try{
                if(!response.isSuccessful()){
                    Log.e(TAG,"Response string:"+ response.errorBody().string());
                }}catch(IOException e){
                    e.printStackTrace();
                }
                if(response.isSuccessful()) {
                    callResult = response.body();

                    try {
                        ArrayList<ContentProviderOperation> batchOperations = Utils.movieJsonToContentVals(callResult);
                        if (batchOperations != null) {
                            if (batchOperations.size() != 0) {
                                mContext.getContentResolver().applyBatch(MovieProvider.AUTHORITY,
                                        batchOperations);
                            }
                        } else {
                            Toast.makeText(mContext, "No movie found at MovieDBAPI", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "Json exception", Toast.LENGTH_LONG).show();
                    } catch (RemoteException | OperationApplicationException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "Remote/OperationApplication exception", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MovieItem>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

}
