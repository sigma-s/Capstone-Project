package com.example.goodbox.capstone_stage2.rest;

import android.database.Cursor;

import com.example.goodbox.capstone_stage2.data.MovieColumns;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Goodbox on 28-01-2017.
 */

public class QuestionSetter {
    private Movie movie;
    private Cursor cursor;
    private Random random = new Random();

    public QuestionSetter(Movie movie, Cursor cursor){
        this.movie = movie;
        this.cursor = cursor;
    }
    public ArrayList<String> getQuestion1(){
        String question;
        String movieReleaseYear = null;
        String movieReleaseDate = null;
        String movieReleaseYear2 = null;
        ArrayList<String> list = new ArrayList<String>();
        if(movie!=null) {
            question = "When was the movie " + movie.getTitle() + " released?";
            list.add(question);

            String[] dateParts = movie.getRelease_date().split("-");
            if (dateParts != null) {
                if (dateParts.length > 0) {
                    movieReleaseYear = dateParts[0];
                }
            }
            list.add(movieReleaseYear);

            while (cursor.moveToNext()) {
                movieReleaseDate = cursor.getString(cursor.getColumnIndex(MovieColumns.RELEASE_DATE));
                dateParts = movieReleaseDate.split("-");
                if (dateParts != null) {
                    if (dateParts.length > 0) {
                        movieReleaseYear2 = dateParts[0];
                    }
                }
                while (movieReleaseYear.contains(movieReleaseYear2)) {
                    movieReleaseYear2 = Integer.toString(1980 + random.nextInt(37));
                }
                list.add(movieReleaseYear2);
                movieReleaseYear = movieReleaseYear + movieReleaseYear2;
            }
        }

        return list;
    }


}
