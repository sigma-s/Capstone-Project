package com.example.goodbox.capstone_stage2.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.ForeignKeyConstraint;
import net.simonvt.schematic.annotation.References;

import static com.example.goodbox.capstone_stage2.data.StarsinMoviesColumns.STAR_ID;

/**
 * Created by Goodbox on 20-01-2017.
 */

public class StarsinMoviesColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.INTEGER)
    @References(table = MovieDatabase.MOVIES, column = MovieColumns._ID)
    @NotNull
    public static final String MOVIE_ID = "movie_id";
    @DataType(DataType.Type.INTEGER)
    @References(table = MovieDatabase.STARS, column = StarColumns._ID)
    @NotNull
    public static final String STAR_ID = "star_id";
}