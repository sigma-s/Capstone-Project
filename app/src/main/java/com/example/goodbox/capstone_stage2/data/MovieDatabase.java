package com.example.goodbox.capstone_stage2.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Goodbox on 20-01-2017.
 */

@Database(version = MovieDatabase.VERSION)
public final class MovieDatabase {
    private MovieDatabase(){}

    public static final int VERSION = 1;

    @Table(MovieColumns.class) public static final String MOVIES = "movies";
    @Table(StarColumns.class) public static final String STARS = "stars";
    @Table(StarsinMoviesColumns.class) public static final String STARS_IN_MOVIES = "stars_in_movies";

}