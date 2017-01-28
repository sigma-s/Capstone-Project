package com.example.goodbox.capstone_stage2.rest;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Goodbox on 27-01-2017.
 */

public class MyTimer {

    private Handler qHandler = new Handler();
    private long timeRemaining;
    private long qStart = 0L;
    private long offset =0L;
    private long elapsed =0L;
    private long time = 0L;
    private static int duration = 15000;
    private int pauseCount;
    private TextView timer;
    private Activity mActivity;

    public MyTimer(FragmentActivity v, TextView timer)
    {
        pauseCount = 0;
        offset = 0;
        qStart = SystemClock.uptimeMillis();
        qHandler.post(updateTask);
        this.timer = timer;
        mActivity = v;
    }

    private Runnable updateTask = new Runnable()
    {
        public void run()
        {
            long now = SystemClock.uptimeMillis();
            time = duration - (now - qStart);
            elapsed = time;

            if(offset > time)
            {
                elapsed = time + (offset - time);
                offset -=1000;
            }

            if (elapsed > 0)
            {
                int seconds = (int) (elapsed / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                if (seconds < 10) {
                    timer.setText("" + minutes + ":0" + seconds);
                } else {
                    timer.setText("" + minutes + ":" + seconds);
                }

                qHandler.postAtTime(this, now + 1000);
            }
            else
            {
                qHandler.removeCallbacks(this);
                timer.setText("Times up!");

                Toast.makeText(mActivity, "Times up!", Toast.LENGTH_SHORT).show();

                mActivity.finish();
                elapsed = duration;
            }
        }
    };

    public void onResume()
    {
        qHandler.postDelayed(updateTask,0);
    }

    public void onPause()
    {
        if(pauseCount==0)
        {
            offset = time;
        }

        pauseCount++;
        qHandler.removeCallbacks(updateTask);
    }
}
