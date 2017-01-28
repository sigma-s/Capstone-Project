package com.example.goodbox.capstone_stage2.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.goodbox.capstone_stage2.R;

/**
 * Created by Goodbox on 18-01-2017.
 */

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle arguments = new Bundle();
        Bundle args = getIntent().getBundleExtra("id");
        arguments.putParcelable(ResultFragment.RESULT_URI, args.getParcelable(ResultFragment.RESULT_URI));

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_result, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
