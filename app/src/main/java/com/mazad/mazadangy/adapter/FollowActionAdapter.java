package com.mazad.mazadangy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mazad.mazadangy.R;
import com.mazad.mazadangy.model.Auction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowActionAdapter extends RecyclerView.Adapter<FollowActionAdapter.Holder> {
    private Context context;
    private ArrayList<Auction> arrayList;

    public FollowActionAdapter(Context context, ArrayList<Auction> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_username, tv_time, tv_endPrice;
        CircleImageView image_user;
        public Holder(View itemView) {
            super(itemView);

            tv_username = (TextView) itemView.findViewById(R.id.tvUserNameFollowAuctionDetails);
            tv_time = (TextView) itemView.findViewById(R.id.tvTimeFollowAuctionDetails);
            tv_endPrice = (TextView) itemView.findViewById(R.id.tvEndPriceFollowAuctionDetails);
            image_user = (CircleImageView) itemView.findViewById(R.id.imageUserFollowAuctionDetails);
        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(context).inflate(R.layout.item_follow_recycle_auction, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final Auction item_recycle = arrayList.get(position);
        holder.tv_username.setText(item_recycle.getUser_name().toString());
        holder.tv_time.setText(item_recycle.getTime().toString());
        holder.tv_endPrice.setText(item_recycle.getEnd_price().toString().concat(" EGP"));
        Picasso.get().load(item_recycle.getUser_image()).into(holder.image_user);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
