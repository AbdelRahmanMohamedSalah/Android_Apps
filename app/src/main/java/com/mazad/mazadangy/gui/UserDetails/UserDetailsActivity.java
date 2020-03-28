package com.mazad.mazadangy.gui.UserDetails;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mazad.mazadangy.R;

public class UserDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_user_details);
    }
}
