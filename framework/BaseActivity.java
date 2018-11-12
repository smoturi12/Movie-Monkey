package com.practice.sowmya.movie_monkey.framework;

import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected void setToolbar(int StringResId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(StringResId);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }
}
