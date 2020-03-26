package com.mazad.mazadangy.gui.PostDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mazad.mazadangy.R;

public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_post_details);
    }
}
