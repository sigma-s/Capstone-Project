package com.example.goodbox.capstone_stage2.rest;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Goodbox on 21-01-2017.
 */

public class MovieItem {
    private String page;
    private ArrayList<Movie> results;
    private String total_pages;
    private String total_results;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ArrayList<Movie> getMovies() {
        return results;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.results = movies;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

}
