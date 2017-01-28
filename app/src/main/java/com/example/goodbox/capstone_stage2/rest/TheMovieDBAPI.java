package com.example.goodbox.capstone_stage2.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Goodbox on 22-01-2017.
 */

public interface TheMovieDBAPI {
    @GET("movie")
    Call<MovieItem> getMovies(@Query("api_key")String key);
                             /** @Query("language")String language,
                              @Query("sort_by")String sort_by,
                              @Query("include_adult")String adult,
                              @Query("include_video")String video,
                              @Query("page")String page,
                              @Query("vote_count.gte")String vote_count_min,
                              @Query("with_original_language")String original_language
    );**/

}