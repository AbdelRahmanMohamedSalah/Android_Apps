package com.mazad.mazadangy.gui.PostDetails;

import android.app.Dialog;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.mazad.mazadangy.R;
import com.mazad.mazadangy.gui.UserDetails.UserDetailsActivity;
import com.mazad.mazadangy.model.AdsModel;
import com.mazad.mazadangy.model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {
    TextView tv_title, tv_desc, tv_backSale, tv_startPrice, tv_endTime, tv_pandNum, tv_username, tv_enter_comp;
    ImageView image;
    TextView tv_dialogOldPrice, tv_dialogLivePrice, tv_dialognewPrice, tv_counterPrice;
    EditText et_dialogOffer;
    ImageView iv_dialog_add, iv_dialog_remove;
    Button btn_saveOfferPostDetailsActivity;
    public static int final_price = 0;
    public static int counter = 0;

    de.hdodenhof.circleimageview.CircleImageView user_image;
    LinearLayout layout_user, layout_dialog_makeOffer, layout_dialog_counter;
    //List<String> imageList;
    List<String> imageList;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_post_details);
        intilize();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final AdsModel model = (AdsModel) bundle.getSerializable("adsModel");
        final UserModel userModel = (UserModel) bundle.getSerializable("userModel");
        Toast.makeText(this, "Mode = " + model.desc_money, Toast.LENGTH_SHORT).show();

        imageList = new ArrayList<String>(Collections.singleton(model.imge.get(1).toString()));
        Picasso.get().load(imageList.get(0)).into(image);
        setValue(model, userModel);

        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetailsActivity.this, UserDetailsActivity.class);
                intent.putExtra("UserModelPostDetailsActivity", userModel);
                startActivity(intent);
            }
        });
        tv_enter_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PostDetailsActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
                dialog.setContentView(R.layout.offers_post_details_activity);

                layout_dialog_counter = dialog.findViewById(R.id.layoutCounterOffersDetailsPostActivity);
                layout_dialog_makeOffer = dialog.findViewById(R.id.layoutMakeOfferDetailsPostActivity);
                tv_dialogOldPrice = dialog.findViewById(R.id.tvStartPriceOffersDetailsPostActivity);
                tv_dialogLivePrice = dialog.findViewById(R.id.tvLivePriceOffersDetailsPostActivity);
                tv_dialognewPrice = dialog.findViewById(R.id.tvnewCounterOffersDetailsPostActivity);
                tv_counterPrice = dialog.findViewById(R.id.tvCounterOffersDetailsPostActivity);
                et_dialogOffer = dialog.findViewById(R.id.etOfferDetailsPostActivity);
                btn_saveOfferPostDetailsActivity = dialog.findViewById(R.id.saveBtnOffersDetailsPostActivity);
                iv_dialog_add = dialog.findViewById(R.id.ivAddCounterOffersDetailsPostActivity);
                iv_dialog_remove = dialog.findViewById(R.id.ivRemoveCounterOffersDetailsPostActivity);

                tv_dialogOldPrice.setText(model.start_price);
                tv_dialogLivePrice.setText(model.end_price);

                if (model.count_price.equals("0")) {
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
                                int y = Integer.parseInt(model.getStart_price().toString());


                                tv_dialognewPrice.setText(et_dialogOffer.getText().toString());
                                final_price = Integer.parseInt(et_dialogOffer.getText().toString());

                            }
                        }

//                        }
                    });


                } else {
                    layout_dialog_counter.setVisibility(View.VISIBLE);
                    layout_dialog_makeOffer.setVisibility(View.GONE);

                    counter = Integer.parseInt(model.count_price.toString());
                    final_price = Integer.parseInt(model.end_price.toString());

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

                            int n = Integer.parseInt(model.getEnd_price().toString());
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
                dialog.show();
                btn_saveOfferPostDetailsActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (final_price <= Integer.parseInt(model.getEnd_price().toString())) {
                            Toast.makeText(PostDetailsActivity.this, "الرقم اقل من المسموح ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PostDetailsActivity.this, "ok", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
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
    }

    public void setValue(AdsModel adsModel, UserModel userModel) {

        tv_desc.setText(adsModel.desc_money);
        tv_backSale.setText(adsModel.back_sale);
        tv_startPrice.setText(adsModel.start_price);
        tv_endTime.setText(adsModel.end_time);
        tv_pandNum.setText(adsModel.pand_num);
        tv_username.setText(userModel.firstName);


    }

}
