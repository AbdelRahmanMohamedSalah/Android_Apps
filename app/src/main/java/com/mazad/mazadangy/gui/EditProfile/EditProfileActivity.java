package com.mazad.mazadangy.gui.EditProfile;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mazad.mazadangy.R;

public class EditProfileActivity extends AppCompatActivity {
    LinearLayout layout_changePassword, layout_information;
    TextView text_changePass;
    private int count = 0;
    Dialog dialog;
    Button change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);
        init();
        declare();

        dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        dialog.setContentView(R.layout.changepassword);
        change = dialog.findViewById(R.id.btnchangepss);

        text_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

            }
        });


    }

    public void init() {
        layout_information = findViewById(R.id.layoutInformationEditProfile);
        text_changePass = findViewById(R.id.textChangePasswordEditProfileActivity);

    }

    public void declare() {


    }


}
