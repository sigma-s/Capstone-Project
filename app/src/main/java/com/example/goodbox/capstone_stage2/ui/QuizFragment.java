package com.example.goodbox.capstone_stage2.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goodbox.capstone_stage2.R;
import com.example.goodbox.capstone_stage2.data.MovieColumns;
import com.example.goodbox.capstone_stage2.data.MovieProvider;
import com.example.goodbox.capstone_stage2.rest.Movie;
import com.example.goodbox.capstone_stage2.rest.MovieDataFetch;
import com.example.goodbox.capstone_stage2.rest.MovieItem;
import com.example.goodbox.capstone_stage2.rest.MyTimer;
import com.example.goodbox.capstone_stage2.rest.QuestionSetter;
import com.example.goodbox.capstone_stage2.widget.QuizWidgetProvider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Goodbox on 22-01-2017.
 */

public class QuizFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private int numScore;
    private String answer;
    private String background;
    private Random random = new Random();
    private RelativeLayout mainView;
    private boolean correct;
    private ArrayList<RadioButton> buttons;
    private int numCorrect;
    private int numWrong;
    private Context mContext;
    private static final int LIST_ID = 1;
    private Movie movie = new Movie();
    private String title, movieImage, movieReleaseDate, movieLanguage,movieRating,movieOverview,
            movieReleaseYear,movieReleaseYear2;
    public static final String BaseURL = "https://image.tmdb.org/t/p";
    public static final String posterSize = "w342";
    private static final String LOG_TAG = QuizFragment.class.toString();
    public static final String QUIZ_URI = "QUIZ_URI";
    private String from;
    private MyTimer myTimer;

    @Bind(R.id.timer)
    TextView timer;
    @Bind(R.id.score)
    TextView score;
    @Bind(R.id.question)
    TextView question;
    @Bind(R.id.ans1)
    RadioButton ans1;
    @Bind(R.id.ans2)
    RadioButton ans2;
    @Bind(R.id.ans3)
    RadioButton ans3;
    @Bind(R.id.ans4)
    RadioButton ans4;
    @Bind(R.id.radioGroup)
    RadioGroup group;
    @Bind(R.id.Check)
    Button checkButton;
    @Bind(R.id.btnNext)
    Button btnNext;
    @Bind(R.id.adView)
    AdView mAdView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        ButterKnife.bind(this, rootView);
        rootView.setVisibility(View.GONE);
        Bundle arguments = getArguments();

        buttons = new ArrayList<RadioButton>();

        buttons.add(ans1);
        buttons.add(ans2);
        buttons.add(ans3);
        buttons.add(ans4);

        mContext = this.getActivity();
       // insertData();
        if(arguments!=null)
        {
            from = arguments.getString(QuizFragment.QUIZ_URI);
            if(from!=null) {
                if (from.equals("Start")) {
                    //load movie data in the database
                    MovieDataFetch moviedata = new MovieDataFetch(this.getActivity());
                    moviedata.getData();
                    Cursor mCursor = mContext.getContentResolver().query(
                            MovieProvider.Movies.CONTENT_URI,
                            new String[] {MovieColumns._ID},
                            null,
                            null,
                            "RANDOM() limit 1");
                    if(mCursor == null){
                        insertData();
                    }
                    //fetch data from the database
                    numCorrect = 0;
                    numWrong = 0;
                }
            }
        }
        score.setText(Integer.toString(numScore));

        //getLoaderManager().initLoader(LIST_ID,null,this);
        myTimer = new MyTimer(getActivity(),timer);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        checkButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        clickCheck(v);
                    }
                });

        btnNext.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Bundle args = new Bundle();
                        args.putParcelable(ResultFragment.RESULT_URI,movie);
                        Intent intent = new Intent(mContext, ResultActivity.class);
                        intent.putExtra("id",args);
                        startActivity(intent);
                    }
                });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id,Bundle args){
        String[] projection = new String[]{MovieColumns._ID,MovieColumns.NAME,MovieColumns.IMAGE,MovieColumns.RELEASE_DATE,
                MovieColumns.LANGUAGE,MovieColumns.RATING,MovieColumns.OVERVIEW};

        CursorLoader loader = new CursorLoader(
                this.getActivity(),
                MovieProvider.Movies.CONTENT_URI,
                projection,
                null,
                null,
                "RANDOM() limit 4");
        //ViewParent vp = getView().getParent();
        //((View)vp).setVisibility(View.INVISIBLE);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader,Cursor cursor){
        //ViewParent vp=getView().getParent();
        //((View)vp).setVisibility(View.VISIBLE);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieColumns.NAME)));
            movie.setPoster_path(BaseURL + "/" + posterSize + cursor.getString(cursor.getColumnIndex(MovieColumns.IMAGE)));
            movie.setOriginal_language(cursor.getString(cursor.getColumnIndex(MovieColumns.LANGUAGE)));
            movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(MovieColumns.RATING)));
            movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieColumns.OVERVIEW)));
            movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieColumns.RELEASE_DATE)));

            QuestionSetter qs = new QuestionSetter(movie,cursor);
            ArrayList<String> list = qs.getQuestion1();
            setQuestion(list);
        }
        updateQuizWidget();
        getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LIST_ID, null, this);
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null) {
            ans1.setText(savedInstanceState.getString("a1"));
            ans2.setText(savedInstanceState.getString("a2"));
            ans3.setText(savedInstanceState.getString("a3"));
            ans4.setText(savedInstanceState.getString("a4"));
            question.setText(savedInstanceState.getString("curQ"));
            answer = savedInstanceState.getString("answer");
            //  timeRemaining = savedInstanceState.getLong("time");
            numCorrect = savedInstanceState.getInt("numCorrect");
            numWrong = savedInstanceState.getInt("numWrong");
            //  offset = savedInstanceState.getLong("offset");
            //  pauseCount = savedInstanceState.getInt("pauseCount");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState)
    {
        String a1 = ans1.getText().toString();
        String a2 = ans2.getText().toString();
        String a3 = ans3.getText().toString();
        String a4 = ans4.getText().toString();
        String curQuestion = question.getText().toString();
        //savedInstanceState.putInt("pauseCount", myTimer.pauseCount);
        //savedInstanceState.putLong("offset", offset);
        savedInstanceState.putString("a1", a1);
        savedInstanceState.putString("a2", a2);
        savedInstanceState.putString("a3", a3);
        savedInstanceState.putString("a4", a4);
        savedInstanceState.putString("curQ", curQuestion);
        savedInstanceState.putString("answer", answer);
        //savedInstanceState.putLong("time", timeRemaining);
        savedInstanceState.putInt("numCorrect", numCorrect);
        savedInstanceState.putInt("numWrong", numWrong);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void insertData(){
        Movie[] movies ={
                new Movie(209112, "Batman v Superman: Dawn of Justice", "/6bCplVkhowCjTHXWv49UjRPn0eK.jpg","2016-03-23","en",5.8,"Fearing the actions of a god-like Super Hero left unchecked, Gotham City’s own formidable, forceful vigilante takes on Metropolis’s most revered, modern-day savior, while the world wrestles with what sort of hero it really needs. And with Batman and Superman at war with one another, a new threat quickly arises, putting mankind in greater danger than it’s ever known before."),
                new Movie(140607, "Star Wars: The Force Awakens", "/vZpB8ezB1IqpxI9rx553TuGwDzJ.jpg","2015-12-15","en",7.7,"Thirty years after defeating the Galactic Empire, Han Solo and his allies face a new threat from the evil Kylo Ren and his army of Stormtroopers."),
                new Movie(135397, "Jurassic World", "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg","2015-06-09","en",6.6,"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond."),
                new Movie(293660, "Deadpool", "/inVq3FRqcYIRl2la8iZikYYxFNR.jpg","2016-02-09","en",7.2,"Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life."),
                new Movie(131634, "The Hunger Games: Mockingjay - Part 2", "/w93GAiq860UjmgR6tU9h2T24vaV.jpg","2015-11-18","en",6.7,"With the nation of Panem in a full scale war, Katniss confronts President Snow in the final showdown. Teamed with a group of her closest friends – including Gale, Finnick, and Peeta – Katniss goes off on a mission with the unit from District 13 as they risk their lives to stage an assassination attempt on President Snow who has become increasingly obsessed with destroying her. The mortal traps, enemies, and moral choices that await Katniss will challenge her more than any arena she faced in The Hunger Games."),
                new Movie(336004, "Heist", "/t5tGykRvvlLBULIPsAJEzGg1ylm.jpg","2015-11-13","en",5.1,"A father is without the means to pay for his daughter's medical treatment. As a last resort, he partners with a greedy co-worker to rob a casino. When things go awry they're forced to hijack a city bus.")
        };

        Log.d(LOG_TAG, "insert");
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(movies.length);
        //  final Context appContext = this.getApplicationContext();
        for (Movie movie : movies){
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                    MovieProvider.Movies.CONTENT_URI);
            builder.withValue(MovieColumns._ID, movie.getId());
            builder.withValue(MovieColumns.NAME, movie.getTitle());
            builder.withValue(MovieColumns.IMAGE, movie.getPoster_path());
            builder.withValue(MovieColumns.RELEASE_DATE, movie.getRelease_date());
            builder.withValue(MovieColumns.LANGUAGE, movie.getOriginal_language());
            builder.withValue(MovieColumns.RATING, movie.getVote_average());
            builder.withValue(MovieColumns.OVERVIEW, movie.getOverview());
            batchOperations.add(builder.build());
        }

        try{
            getActivity().getContentResolver().applyBatch(MovieProvider.AUTHORITY, batchOperations);
        } catch(RemoteException | OperationApplicationException e){
            Log.e(LOG_TAG, "Error applying batch insert", e);
        }

    }

    public void clickCheck(View view)
    {
        correct = false;
        for(RadioButton but: buttons)
        {
            if(but.isChecked()) {
                System.out.println("Choice :" + but.getText().toString());
                System.out.println("Answer is:" + answer);

                if (but.getText().toString().equalsIgnoreCase(answer)) {
                    numCorrect++;

                    System.out.println("Correct!");
                    Toast toast = Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.show();
                    correct = true;
                    but.setBackgroundColor(ContextCompat.getColor(mContext,R.color.correct));



                } else {
                    numWrong++;
                    System.out.println("Wrong!");
                    Toast toast = Toast.makeText(getActivity(), "Incorrect!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.show();
                    but.setBackgroundColor(ContextCompat.getColor(mContext,R.color.incorrect));

                }
            }
            if (but.getText().toString().equalsIgnoreCase(answer)) {
                but.setBackgroundColor(ContextCompat.getColor(mContext,R.color.correct));
            }
        }
        numScore = numCorrect*10;
        score.setText(Integer.toString(numScore));
        btnNext.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        myTimer.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        myTimer.onResume();
    }

    private void updateQuizWidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext.getApplicationContext());
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity(), QuizWidgetProvider.class));
        if(ids.length > 0) {

            appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.quiz_widget);
        }
    }

    private void setQuestion(ArrayList<String> list){
        if(list!=null) {
            if(list.size()==5) {
                question.setText(list.get(0));
                answer = list.get(1);
                list.remove(0);

                int ans = random.nextInt(list.size());
                ans1.setText(list.get(ans));
                list.remove(ans);
                ans = random.nextInt(list.size());
                ans2.setText(list.get(ans));
                list.remove(ans);
                ans = random.nextInt(list.size());
                ans3.setText(list.get(ans));
                list.remove(ans);
                ans4.setText(list.get(0));
            }
        }

    }

}
