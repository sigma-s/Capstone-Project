package com.example.goodbox.capstone_stage2.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Goodbox on 20-01-2017.
 */

public class MovieColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull
    public static final String NAME = "name";
    @DataType(DataType.Type.TEXT)
    public static final String IMAGE = "image";
    @DataType(DataType.Type.TEXT)
    public static final String RELEASE_DATE = "release_date";
    @DataType(DataType.Type.TEXT)
    public static final String LANGUAGE = "language";
    @DataType(DataType.Type.REAL)
    public static final String RATING = "rating";
    @DataType(DataType.Type.TEXT)
    public static final String OVERVIEW = "overview";
    @DataType(DataType.Type.TEXT)
    public static final String DIRECTOR = "director";
    @DataType(DataType.Type.TEXT)
    public static final String TRAILER = "trailer";
    @DataType(DataType.Type.TEXT)
    public static final String STARS = "stars";
}
