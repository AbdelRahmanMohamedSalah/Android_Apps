package com.mazad.mazadangy.gui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mazad.mazadangy.R;
import com.mazad.mazadangy.adapter.FavoriteAdapter;
import com.mazad.mazadangy.model.AdsModel;
import com.mazad.mazadangy.model.UserModel;

import java.util.ArrayList;

public class FavoritePostActivity extends AppCompatActivity {
    FavoriteAdapter favoriteAdapter;
    androidx.recyclerview.widget.RecyclerView
            recyclerView;
    AdsModel adsModel;
    UserModel userModel;
    ArrayList<AdsModel> arrayList;
    DatabaseReference db_favorite;
    FirebaseUser firebaseUser;
    String from_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_favorite_post);
        recyclerView = findViewById(R.id.recycleFavoritePostActivity);
        adsModel = new AdsModel();
        arrayList = new ArrayList<AdsModel>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        from_activity = intent.getStringExtra("fromActivity");

        if (firebaseUser.getUid().equals(null)) {

            Toast.makeText(this, "عفولا يرجى تسجيل الدخول", Toast.LENGTH_SHORT).show();
        } else {

            if (from_activity.equals("posts")) {
                db_favorite = FirebaseDatabase.getInstance().getReference("favorite").child(firebaseUser.getUid() + "").child("posts");
            } else {

                db_favorite = FirebaseDatabase.getInstance().getReference("favorite").child(firebaseUser.getUid() + "").child("mony_post");
            }

            db_favorite.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        adsModel = dataSnapshot1.getValue(AdsModel.class);
                        arrayList.add(adsModel);
                        favoriteAdapter.notifyDataSetChanged();


                        System.out.println("Ads model = " + adsModel.desc_money);
                        System.out.println("array list size 1= " + arrayList.size());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            System.out.println("array list size2 = " + arrayList.size());
            favoriteAdapter = new FavoriteAdapter(this, arrayList,from_activity);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(favoriteAdapter);
            favoriteAdapter.notifyDataSetChanged();
        }
    }
}
