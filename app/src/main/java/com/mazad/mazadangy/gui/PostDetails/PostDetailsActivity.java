package com.mazad.mazadangy.gui.PostDetails;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mazad.mazadangy.R;
import com.mazad.mazadangy.model.AdsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {
    TextView tv_title, tv_desc, tv_backSale, tv_startPrice, tv_endTime, tv_pandNum;
    ImageView image;
    //List<String> imageList;
    List<String> imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_post_details);
        intilize();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        AdsModel model =  (AdsModel)bundle.getSerializable("MyClass");
        Toast.makeText(this, "Mode = " + model.desc_money, Toast.LENGTH_SHORT).show();

        imageList = new ArrayList<String>(Collections.singleton(model.imge.get(1).toString()));
        Picasso.get().load(imageList.get(0)).into(image);
        setValue(model);
    }

    public void intilize() {
        tv_title = findViewById(R.id.tvTitleDetailsPostActivity);
        tv_desc = findViewById(R.id.tvDescDetailsPostActivity);
        tv_backSale = findViewById(R.id.tvBackSaleDetailsPostActivity);
        tv_startPrice = findViewById(R.id.tvStartPriceDetailsPostActivity);
        tv_endTime = findViewById(R.id.tvEndTimeDetailsPostActivity);
        tv_pandNum = findViewById(R.id.tvpandNumDetailsPostActivity);
        image = findViewById(R.id.imagePostDetailsPostActivity);
    }

    public void setValue(AdsModel adsModel) {
        tv_title.setText(adsModel.desc_money);
        tv_desc.setText(adsModel.desc_money);
        tv_backSale.setText(adsModel.back_sale);
        tv_startPrice.setText(adsModel.start_price);
        tv_endTime.setText(adsModel.end_time);
        tv_pandNum.setText(adsModel.pand_num);


    }

}
