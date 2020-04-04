package com.mazad.mazadangy.gui.UserDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mazad.mazadangy.R;
import com.mazad.mazadangy.gui.EditProfile.EditProfileActivity;
import com.mazad.mazadangy.model.UserModel;
import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity {
    Button btnEditData;
    TextView tv_userName, tv_nickName, tv_phone, tv_email, tv_interest;
    de.hdodenhof.circleimageview.CircleImageView profile_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_user_details);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        UserModel userModel = (UserModel) bundle.getSerializable("UserModelPostDetailsActivity");

        init();
        declare(userModel);
    }

    public void init() {
        tv_userName = findViewById(R.id.tvUserNameDetailsUserActivity);
        tv_nickName = findViewById(R.id.tvNickNameDetailsUserActivity);
        tv_interest = findViewById(R.id.tvInterestDetailsUserActivity);
        tv_phone = findViewById(R.id.tvPhoneDetailsUserActivity);
        tv_email = findViewById(R.id.tvEmailDetailsUserActivity);
        btnEditData = findViewById(R.id.btnEditDetailsUserActivity);
        profile_Image=findViewById(R.id.imageProfileDetailsUserActivity);
    }

    public void declare(UserModel userModel) {
        tv_email.setText(userModel.email);
        tv_phone.setText(userModel.phoneNumber);
        tv_interest.setText(userModel.interest);
        tv_nickName.setText(userModel.nickname);
        Picasso.get().load(userModel.image_profile).into(profile_Image);
        tv_userName.setText(userModel.firstName + " " + userModel.lastName);
        btnEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailsActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
