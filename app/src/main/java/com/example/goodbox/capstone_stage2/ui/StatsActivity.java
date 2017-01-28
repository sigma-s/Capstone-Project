package com.example.goodbox.capstone_stage2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodbox.capstone_stage2.R;
import com.example.goodbox.capstone_stage2.rest.UserStats;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Goodbox on 27-01-2017.
 */

public class StatsActivity extends AppCompatActivity {

    @Bind(R.id.numQuiz)
    TextView numQuiz;
    @Bind(R.id.ansCorrect)
    TextView ansCorrect;
    @Bind(R.id.ansIncorrect)
    TextView ansIncorrect;
    @Bind(R.id.avgTime)
    TextView avgTime;
    @Bind(R.id.totalScore)
    TextView totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        numQuiz.setText(Integer.toString(UserStats.getNumQuiz()));
        ansCorrect.setText(Integer.toString(UserStats.getNumCorrect()));
        ansIncorrect.setText(Integer.toString(UserStats.getNumIncorrect()));
        avgTime.setText(Long.toString(UserStats.getAvgTime()));
        totalScore.setText(Integer.toString(UserStats.getScore()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
