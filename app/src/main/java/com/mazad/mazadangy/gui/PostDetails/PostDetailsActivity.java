package com.mazad.mazadangy.gui.PostDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mazad.mazadangy.R;
import com.mazad.mazadangy.model.AdsModel;

public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_post_details);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        AdsModel model =  (AdsModel)bundle.getSerializable("MyClass");

        Toast.makeText(this, "Mode = "+model.desc_money , Toast.LENGTH_SHORT).show();
    }
}
