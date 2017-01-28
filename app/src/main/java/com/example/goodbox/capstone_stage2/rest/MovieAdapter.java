package com.example.goodbox.capstone_stage2.rest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goodbox.capstone_stage2.R;

import java.util.ArrayList;

/**
 * Created by Goodbox on 21-01-2017.
 */
/**
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private ArrayList<MovieItem.Movie> movieList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView author;
        public TextView content;

        public MyViewHolder(View v){
            super(v);
            author = (TextView)v.findViewById(R.id.review_author);
            content = (TextView)v.findViewById(R.id.review_content);

        }
    }

    public void add(int position, MovieItem.Movie item){
        movieList.add(position,item);
        notifyItemInserted(position);
    }

    public void remove(MovieItem.Movie item){
        int position = movieList.indexOf(item);
        movieList.remove(position);
        notifyItemRemoved(position);
    }

    public MovieAdapter(ArrayList<MovieItem.Movie> myMovieList){
        this.movieList = myMovieList;
    }

    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.author.setText(movieList.get(position).getAuthor());
        holder.content.setText(movieList.get(position).getContent());
    }

    @Override
    public int getItemCount(){
        return movieList.size();
    }

}
**/