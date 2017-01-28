package com.example.goodbox.capstone_stage2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.goodbox.capstone_stage2.R;

/**
 * Created by Goodbox on 19-01-2017.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    public void takeQuiz(View view)
    {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(QuizFragment.QUIZ_URI,"Start");
        startActivity(intent);
    }

}
