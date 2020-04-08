package com.mazad.mazadangy.gui.UserDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mazad.mazadangy.R;
import com.mazad.mazadangy.gui.EditProfile.EditProfileActivity;
import com.mazad.mazadangy.model.UserModel;
import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity {
    Button btnEditData;
    TextView tv_userName, tv_nickName, tv_phone, tv_email, tv_interest;
    de.hdodenhof.circleimageview.CircleImageView profile_Image;
    String fromActivity;
    UserModel userModell, userModelRefrence;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_user_details);
        init();

        Intent intent = this.getIntent();
        fromActivity = intent.getStringExtra("fromActivity");
        Bundle bundle = intent.getExtras();
        userModell = (UserModel) bundle.getSerializable("UserModelPostDetailsActivity");
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userModell.uId.toString());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String firstname = dataSnapshot.child("firstName").getValue(String.class);
                String lastname = dataSnapshot.child("lastName").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String phonenumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                String interest = dataSnapshot.child("interest").getValue(String.class);
                String nickname = dataSnapshot.child("nickname").getValue(String.class);
                String uid = dataSnapshot.child("uId").getValue(String.class);
                String image_profile = dataSnapshot.child("image_profile").getValue(String.class);


                userModelRefrence = new UserModel(
                        uid,
                        phonenumber,
                        nickname,
                        lastname,
                        interest,
                        firstname,
                        email,
                        image_profile
                );

                declare(userModelRefrence);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (fromActivity.equals("HomeActivity")) {
            btnEditData.setVisibility(View.VISIBLE);

        } else {
            btnEditData.setVisibility(View.GONE);
        }
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
        tv_email.setText(userModel.email + "");
        tv_phone.setText(userModel.phoneNumber + "");
        tv_interest.setText(userModel.interest + "");
        tv_nickName.setText(userModel.nickname + "");
        Picasso.get().load(userModel.image_profile + "").into(profile_Image);
        tv_userName.setText(userModel.firstName + " " + userModel.lastName);

        btnEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailsActivity.this, EditProfileActivity.class);
                intent.putExtra("userModel", userModelRefrence);
                startActivity(intent);
            }
        });
    }
}
