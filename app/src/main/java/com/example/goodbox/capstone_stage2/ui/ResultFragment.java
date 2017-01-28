package com.example.goodbox.capstone_stage2.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.goodbox.capstone_stage2.R;
import com.example.goodbox.capstone_stage2.rest.Movie;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.goodbox.capstone_stage2.ui.QuizFragment.QUIZ_URI;

/**
 * Created by Goodbox on 22-01-2017.
 */

public class ResultFragment extends Fragment {

    public static final String RESULT_URI = "RESULT_URI";
    private Movie movie;
    Context mContext;

    @Bind(R.id.movie_name_date)
    TextView movie_name_date;
    @Bind(R.id.movie_rating)
    TextView movie_rating;
    @Bind(R.id.movie_poster)
    ImageView movie_poster;
    @Bind(R.id.movie_overview)
    TextView movie_overview;
    @Bind(R.id.share_button)
    ImageView share_button;
    @Bind(R.id.movie_language)
    TextView movie_language;
    @Bind(R.id.next)
    Button next;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, rootView);
        mContext = this.getActivity();
        Bundle arguments = getArguments();

        View fragment_result = (View)rootView.findViewById(R.id.fragment_result);
        fragment_result.setVisibility(View.INVISIBLE);
        if(arguments!=null){
            fragment_result.setVisibility(View.VISIBLE);
            movie = arguments.getParcelable(ResultFragment.RESULT_URI);
            try{
                movie_name_date.setText(movie.getTitle()+"(" + movie.getRelease_date().substring(0,4) +")");
                movie_rating.setText(movie.getVote_average().toString());
                Picasso.with(getActivity())
                        .load(movie.getPoster_path())
                        .into(movie_poster);
                movie_overview.setText(movie.getOverview());
                movie_language.setText(getLanguage(movie.getOriginal_language()));

            } catch (Exception e){
                e.printStackTrace();
                Log.e("ERROR", e.getMessage(), e);
            }
        }

        next.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(mContext, QuizActivity.class);
                        intent.putExtra(QuizFragment.QUIZ_URI,"Next");
                        startActivity(intent);
                    }
                });

        share_button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        shareIt();
                    }
                });

       return rootView;
    }

    private void shareIt(){
        Drawable mDrawable = movie_poster.getDrawable();
        Bitmap mBitmap = null;
        if(mDrawable!=null) {
             mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
        }
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),mBitmap,"Image Description",null);

        Uri uri = Uri.parse(path);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/jpeg");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Movie poster from app Movie Mania");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public String getLanguage(String languageID){
        String language;
        if(languageID.equals("en")) {
            language = "English";
        }
        else if(languageID.equals("es")) {
            language = "Spanish";
        }
        else if(languageID.equals("uk")) {
            language = "Ukranian";
        }
        else if(languageID.equals("de")) {
            language = "German";
        }
        else if(languageID.equals("pt")) {
            language = "Portuguese";
        }
        else if(languageID.equals("fr")) {
            language = "French";
        }
        else if(languageID.equals("nl")) {
            language = "Dutch";
        }
        else if(languageID.equals("hu")) {
            language = "Hungarian";
        }
        else if(languageID.equals("ru")) {
            language = "Russian";
        }
        else if(languageID.equals("it")) {
            language = "Italian";
        }
        else if(languageID.equals("tr")) {
            language = "Turkish";
        }
        else if(languageID.equals("zh")) {
            language = "Mandarin";
        }
        else if(languageID.equals("da")) {
            language = "Danish";
        }
        else if(languageID.equals("sv")) {
            language = "Swedish";
        }
        else if(languageID.equals("pl")) {
            language = "Polish";
        }
        else if(languageID.equals("fi")) {
            language = "Finnish";
        }
        else if(languageID.equals("he")) {
            language = "Hebrew";
        }
        else{
            language = "Not known";
        }

        return language;
    }

}
