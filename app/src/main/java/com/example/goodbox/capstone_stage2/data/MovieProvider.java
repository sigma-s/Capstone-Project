package com.example.goodbox.capstone_stage2.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Goodbox on 20-01-2017.
 */
@ContentProvider(authority = MovieProvider.AUTHORITY, database = MovieDatabase.class)
public final class MovieProvider {

    public static final String AUTHORITY = "com.example.goodbox.capstone_stage2.data.MovieProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String MOVIES = "movies";
        String STARS = "stars";
        String STARS_IN_MOVIES = "stars_in_movies";
    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = MovieDatabase.MOVIES) public static class Movies {

        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor.dir/movies"
        )
        public static final Uri CONTENT_URI = buildUri(Path.MOVIES);

        @InexactContentUri(
                name = "MOVIE_ID",
                path = Path.MOVIES + "/#",
                type = "vnd.android.cursor.item/movie",
                whereColumn = MovieColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.MOVIES, String.valueOf(id));
        }
    }

    @TableEndpoint(table = MovieDatabase.STARS) public static class Stars {

        @ContentUri(
                path = Path.STARS,
                type = "vnd.android.cursor.dir/star"
        )
        public static final Uri CONTENT_URI = buildUri(Path.STARS);

        @InexactContentUri(
                name = "STAR_ID",
                path = Path.STARS + "/#",
                type = "vnd.android.cursor.item/star",
                whereColumn = StarColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.STARS, String.valueOf(id));
        }
    }

    @TableEndpoint(table = MovieDatabase.STARS_IN_MOVIES) public static class StarsInMovies {

        @ContentUri(
                path = Path.STARS_IN_MOVIES,
                type = "vnd.android.cursor.dir/starinmovies"
        )
        public static final Uri CONTENT_URI = buildUri(Path.STARS_IN_MOVIES);

        @InexactContentUri(
                name = "STAR_IN_MOVIE_ID",
                path = Path.STARS_IN_MOVIES + "/#",
                type = "vnd.android.cursor.item/starinmovies",
                whereColumn = StarsinMoviesColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.STARS_IN_MOVIES, String.valueOf(id));
        }
    }
}
