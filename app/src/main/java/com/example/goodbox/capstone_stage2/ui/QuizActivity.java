package com.example.goodbox.capstone_stage2.ui;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.goodbox.capstone_stage2.R;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_quiz);

        Bundle arguments = new Bundle();
        String args = getIntent().getStringExtra(QuizFragment.QUIZ_URI);
        if(args!=null) {
            arguments.putString(QuizFragment.QUIZ_URI, args);
        }
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_quiz, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
