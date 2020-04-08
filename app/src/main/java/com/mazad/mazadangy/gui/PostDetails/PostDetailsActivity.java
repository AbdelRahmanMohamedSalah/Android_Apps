package com.mazad.mazadangy.gui.PostDetails;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.mazad.mazadangy.adapter.FollowActionAdapter;
import com.mazad.mazadangy.gui.UserDetails.UserDetailsActivity;
import com.mazad.mazadangy.gui.category.CategoryActivity;
import com.mazad.mazadangy.model.AdsModel;
import com.mazad.mazadangy.model.Auction;
import com.mazad.mazadangy.model.UserModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {
    TextView tv_title, tv_desc, tv_backSale, tv_startPrice, tv_EndPrice, tv_endTime, tv_pandNum, tv_username, tv_enter_comp, tv_follow_comp;
    ImageView image, image_favoriteOn, image_favoriteOff, image_back;
    TextView tv_dialogOldPrice, tv_dialogLivePrice, tv_dialognewPrice, tv_counterPrice;
    EditText et_dialogOffer;
    ImageView iv_dialog_add, iv_dialog_remove;
    Button btn_saveOfferPostDetailsActivity;
    public static int final_price = 0;
    public static int counter = 0;
    DatabaseReference DR_setDataAution, DR_setEndPrice, DR_user,DR_favorite;
    FirebaseUser firebaseAuth;
    String aution_userId, aution_firstName, aution_image, favorit_item;
    AdsModel adsModel;
    UserModel userModel;
    String fromActivity;
    de.hdodenhof.circleimageview.CircleImageView user_image;
    LinearLayout layout_user, layout_dialog_makeOffer, layout_dialog_counter;
    //List<String> imageList;
    List<String> imageList;
    Dialog dialogEnterCom, dialogFollowCom;

    androidx.recyclerview.widget.RecyclerView recyclerView_dialog_follow_auction;
    FollowActionAdapter auctionAdapter;
    ArrayList<Auction> arrayAuction;
    Auction action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_post_details);

        intilize();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        adsModel = (AdsModel) bundle.getSerializable("adsModel");
        userModel = (UserModel) bundle.getSerializable("userModel");
        fromActivity = intent.getStringExtra("fromActivity");
        arrayAuction = new ArrayList<>();
        action = new Auction();
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();

        if (fromActivity.equals("posts")) {
            DR_setDataAution = FirebaseDatabase.getInstance().getReference("posts").child(adsModel.id_post + "").child("auction").push();
            DR_setEndPrice = FirebaseDatabase.getInstance().getReference("posts").child(adsModel.id_post + "");
            DR_favorite=FirebaseDatabase.getInstance().getReference("favorite").child(firebaseAuth.getUid().toString()).child("posts");


        } else {

            DR_setDataAution = FirebaseDatabase.getInstance().getReference("mony_post").child(adsModel.id_post + "").child("auction").push();
            DR_setEndPrice = FirebaseDatabase.getInstance().getReference("mony_post").child(adsModel.id_post + "");
            DR_favorite=FirebaseDatabase.getInstance().getReference("favorite").child(firebaseAuth.getUid().toString()).child("mony_post");


        }

        DR_favorite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    favorit_item = dataSnapshot1.child("id_post").getValue(String.class);
                    if (favorit_item.equals(adsModel.getId_post())) {

                        image_favoriteOff.setVisibility(View.GONE);
                        image_favoriteOn.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DR_setEndPrice.child("auction").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    action = dataSnapshot1.getValue(Auction.class);

                    arrayAuction.add(action);
                    // auctionAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        imageList = new ArrayList<String>(Collections.singleton(adsModel.imge.get(1).toString()));
        Picasso.get().load(imageList.get(0)).into(image);
        setValue(adsModel, userModel);

        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetailsActivity.this, UserDetailsActivity.class);
                intent.putExtra("UserModelPostDetailsActivity", userModel);
                intent.putExtra("fromActivity", "postDetailsActivity");

                startActivity(intent);
            }
        });
        dialogEnterAuction();
        dialogFollowAuction();

        image_favoriteOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getUid().toString().equals(null)) {
                    Toast.makeText(PostDetailsActivity.this, "برجاء تسجيل الدخول", Toast.LENGTH_SHORT).show();
                } else {
                    image_favoriteOff.setVisibility(View.GONE);
                    image_favoriteOn.setVisibility(View.VISIBLE);

                    DR_favorite.child(adsModel.id_post + "").setValue(adsModel);
                }
            }
        });


        image_favoriteOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_favoriteOn.setVisibility(View.GONE);
                image_favoriteOff.setVisibility(View.VISIBLE);
                DR_favorite.child(adsModel.id_post.toString()).removeValue();
            }
        });
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }




    public void dialogEnterAuction() {

        tv_enter_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseAuth == null) {

                    Toast.makeText(PostDetailsActivity.this, "برجاء تسجيل الدخول لدخول المزاد", Toast.LENGTH_SHORT).show();
                } else {
                    aution_userId = firebaseAuth.getUid();

                    DR_user = FirebaseDatabase.getInstance().getReference("users").child(aution_userId + "");
                    dialogEnterCom = new Dialog(PostDetailsActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                    dialogEnterCom.setContentView(R.layout.offers_post_details_activity);
                    DR_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //  user=dataSnapshot.getValue(UserModel.class);
                            System.out.println("User details= " + dataSnapshot);
                            aution_firstName = (dataSnapshot.child("firstName").getValue(String.class)) + " " + (dataSnapshot.child("lastName").getValue(String.class));
                            aution_image = (dataSnapshot.child("image_profile").getValue(String.class));
                            System.out.println("datasnapshot  = " + dataSnapshot);

                            System.out.println("User Name  = " + aution_firstName);
                            System.out.println("Data snap shot = " + dataSnapshot.child("firstName").getValue(String.class));
                            System.out.println("Data  uid  = " + dataSnapshot.child("uId").getValue(String.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    layout_dialog_counter = dialogEnterCom.findViewById(R.id.layoutCounterOffersDetailsPostActivity);
                    layout_dialog_makeOffer = dialogEnterCom.findViewById(R.id.layoutMakeOfferDetailsPostActivity);
                    tv_dialogOldPrice = dialogEnterCom.findViewById(R.id.tvStartPriceOffersDetailsPostActivity);
                    tv_dialogLivePrice = dialogEnterCom.findViewById(R.id.tvLivePriceOffersDetailsPostActivity);
                    tv_dialognewPrice = dialogEnterCom.findViewById(R.id.tvnewCounterOffersDetailsPostActivity);
                    tv_counterPrice = dialogEnterCom.findViewById(R.id.tvCounterOffersDetailsPostActivity);
                    et_dialogOffer = dialogEnterCom.findViewById(R.id.etOfferDetailsPostActivity);
                    btn_saveOfferPostDetailsActivity = dialogEnterCom.findViewById(R.id.saveBtnOffersDetailsPostActivity);
                    iv_dialog_add = dialogEnterCom.findViewById(R.id.ivAddCounterOffersDetailsPostActivity);
                    iv_dialog_remove = dialogEnterCom.findViewById(R.id.ivRemoveCounterOffersDetailsPostActivity);


                    tv_dialogOldPrice.setText(adsModel.start_price);
                    tv_dialogLivePrice.setText(adsModel.end_price);

                    if (adsModel.count_price.equals("0")) {
                        layout_dialog_counter.setVisibility(View.GONE);
                        layout_dialog_makeOffer.setVisibility(View.VISIBLE);

//                    et_dialogOffer.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            tv_dialognewPrice.setText(et_dialogOffer.getText().toString());
//
//                        }
//                    });
                        tv_dialognewPrice.setText("0");
                        final_price = 0;
                        et_dialogOffer.addTextChangedListener(new TextWatcher() {

                            public void onTextChanged(CharSequence s, int start, int before,
                                                      int count) {
                                if (s.length() > 0) { //do your work here }
                                    tv_dialognewPrice.setText(et_dialogOffer.getText().toString());

                                }

                            }

                            //
                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                                tv_dialognewPrice.setText("0");

                            }

                            public void afterTextChanged(Editable s) {
                                if (s.length() > 0) { //do your work here }

                                    int x = 0;
                                    x = Integer.parseInt(et_dialogOffer.getText().toString());
                                    int y = Integer.parseInt(adsModel.getStart_price().toString());


                                    tv_dialognewPrice.setText(et_dialogOffer.getText().toString());
                                    final_price = Integer.parseInt(et_dialogOffer.getText().toString());

                                }
                            }

//                        }
                        });


                    } else {
                        layout_dialog_counter.setVisibility(View.VISIBLE);
                        layout_dialog_makeOffer.setVisibility(View.GONE);

                        counter = Integer.parseInt(adsModel.count_price.toString());
                        final_price = Integer.parseInt(adsModel.end_price.toString());

                        tv_counterPrice.setText(counter + "");

                        final_price += counter;
                        tv_dialognewPrice.setText(final_price + "");
                        System.out.println(" First FinalPrice is " + final_price + " Counter= " + counter);
                        System.out.println(" First Counter + final " + (final_price + counter));
                        iv_dialog_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final_price += counter;

                                System.out.println(" Add FinalPrice is " + final_price + " Counter= " + counter);
                                System.out.println("Add Counter + final " + (final_price + counter));
                                tv_counterPrice.setText(counter + "");
                                tv_dialognewPrice.setText(final_price + "");
                            }
                        });

                        iv_dialog_remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                int n = Integer.parseInt(adsModel.getEnd_price().toString());
                                System.out.println("fnal " + final_price);

                                if ((final_price - counter * 2) >= n) {
                                    final_price -= counter;
                                    tv_counterPrice.setText(counter + "");
                                    tv_dialognewPrice.setText(final_price + "");
                                    System.out.println(" remove FinalPrice is " + final_price + " Counter= " + counter);
                                    System.out.println("remove Counter + final " + (final_price + counter));

                                } else {
                                    Toast.makeText(PostDetailsActivity.this, "الرقم اقل من المسموح ", Toast.LENGTH_SHORT).show();


                                }
                            }
                        });
                    }
                    dialogEnterCom.show();


                    btn_saveOfferPostDetailsActivity.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(View v) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Date date = new Date();
                            if (final_price <= Integer.parseInt(adsModel.getEnd_price().toString())) {
                                Toast.makeText(PostDetailsActivity.this, "الرقم اقل من المسموح ", Toast.LENGTH_SHORT).show();
                            } else {
                                DR_setDataAution.child("end_price").setValue(final_price + "");
                                DR_setDataAution.child("uId").setValue(aution_userId + "");
                                DR_setDataAution.child("user_name").setValue(aution_firstName + "");
                                DR_setDataAution.child("user_image").setValue(aution_image + "");
                                DR_setDataAution.child("end_price").setValue(final_price + "");
                                DR_setDataAution.child("time").setValue(formatter.format(date) + "");
                                DR_setEndPrice.child("end_price").setValue(final_price + "").isSuccessful();
                                dialogEnterCom.hide();
                                startActivity(new Intent(PostDetailsActivity.this, CategoryActivity.class));
                                finish();

                            }

                        }
                    });
                }
            }
        });


    }

    public void dialogFollowAuction() {


        dialogFollowCom = new Dialog(PostDetailsActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialogFollowCom.setContentView(R.layout.follow_aucion_details);

        tv_follow_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                recyclerView_dialog_follow_auction = dialogFollowCom.findViewById(R.id.recycleFollowPostDetailsActivity);

                auctionAdapter = new FollowActionAdapter(PostDetailsActivity.this, arrayAuction);

                recyclerView_dialog_follow_auction.setLayoutManager(new LinearLayoutManager(PostDetailsActivity.this));

                recyclerView_dialog_follow_auction.setAdapter(auctionAdapter);
                auctionAdapter.notifyDataSetChanged();

                dialogFollowCom.show();


            }
        });

    }
    public void intilize() {
        layout_user = findViewById(R.id.layoutUserDetailsPostActivity);
        tv_desc = findViewById(R.id.tvDescDetailsPostActivity);
        tv_backSale = findViewById(R.id.tvBackSaleDetailsPostActivity);
        tv_startPrice = findViewById(R.id.tvStartPriceDetailsPostActivity);
        tv_endTime = findViewById(R.id.tvEndTimeDetailsPostActivity);
        tv_pandNum = findViewById(R.id.tvpandNumDetailsPostActivity);
        image = findViewById(R.id.imagePostDetailsPostActivity);
        tv_username = findViewById(R.id.nameUserTvPostDetailsActivity);
        user_image = findViewById(R.id.imgeProfileCvPostDetailsActivity);
        tv_enter_comp = findViewById(R.id.tvEnterCompDetailsPostActivity);
        tv_follow_comp = findViewById(R.id.tvFollowCompDetailsPostActivity);
        tv_EndPrice = findViewById(R.id.tvEndPriceDetailsPostActivity);
        image_favoriteOn = findViewById(R.id.imageFavoriteOnPostDetailsACtivity);
        image_favoriteOff = findViewById(R.id.imageFavoriteOffPostDetailsACtivity);
        image_back = findViewById(R.id.imageBackPostDetailsActivity);
    }

    public void setValue(AdsModel adsModel, UserModel userModel) {

        tv_desc.setText(adsModel.desc_money);
        tv_backSale.setText(adsModel.back_sale);
        tv_startPrice.setText(adsModel.start_price);
        tv_EndPrice.setText(adsModel.end_price);
        tv_endTime.setText(adsModel.end_time);
        tv_pandNum.setText(adsModel.pand_num);
        tv_username.setText(userModel.firstName);
        Picasso.get().load(userModel.image_profile).into(user_image);

    }

}
