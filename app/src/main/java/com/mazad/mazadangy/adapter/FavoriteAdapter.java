package com.mazad.mazadangy.adapter;

        import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.mazad.mazadangy.R;
        import com.mazad.mazadangy.gui.PostDetails.PostDetailsActivity;
        import com.mazad.mazadangy.model.AdsModel;
        import com.mazad.mazadangy.model.UserModel;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

        import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.Holder> {
    private Context context;

    private ArrayList<AdsModel> arrayList;
    DatabaseReference db_user;
    public String image_profile, firstname;
    List imageList;
    String fromActivity;
    UserModel userModel;

    public FavoriteAdapter(Context context, ArrayList<AdsModel> arrayList,String fromActivity) {
        this.context = context;
        this.arrayList = arrayList;
        this.fromActivity=fromActivity;
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tv_username, tv_time, tv_endPrice, tv_startPrice, tv_title;
        CircleImageView image_user;
        ImageView image_Post;

        public Holder(View itemView) {
            super(itemView);
            System.out.println("Holder");
            tv_username = (TextView) itemView.findViewById(R.id.nameUserTv);
            tv_time = (TextView) itemView.findViewById(R.id.countDownTimerTextView);
            tv_endPrice = (TextView) itemView.findViewById(R.id.endPriceTv);
            tv_startPrice = (TextView) itemView.findViewById(R.id.startPriceTv);
            tv_title = (TextView) itemView.findViewById(R.id.descTv);
            image_user = (CircleImageView) itemView.findViewById(R.id.imgeProfileCv);
            image_Post = (ImageView) itemView.findViewById(R.id.imgeAdIv);

        }
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        System.out.println("on create ");
        View v = LayoutInflater.from(context).inflate(R.layout.item_money, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {


        System.out.println("on bind");
        final AdsModel item_recycle = arrayList.get(position);
        System.out.println("recycle data = " + item_recycle.desc_money.toString());
        System.out.printf("data recycle //////// =" + item_recycle.desc_money.toString());

        db_user = FirebaseDatabase.getInstance().getReference("users").child(item_recycle.userId.toString());
        db_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                firstname = dataSnapshot.child("firstName").getValue(String.class);
                String lastname = dataSnapshot.child("lastName").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String phonenumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                String interest = dataSnapshot.child("interest").getValue(String.class);
                String nickname = dataSnapshot.child("nickname").getValue(String.class);
                String uid = dataSnapshot.child("uId").getValue(String.class);
                image_profile = dataSnapshot.child("image_profile").getValue(String.class);

                System.out.println(firstname + " first name = ");

                holder.tv_username.setText(firstname.toString());

                Picasso.get().load(image_profile).into(holder.image_user);

                userModel = new UserModel(uid,
                        phonenumber,
                        nickname,
                        lastname,
                        interest,
                        firstname,
                        email,
                        image_profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //

        imageList = new ArrayList<>(Collections.singleton(item_recycle.imge.get(1).toString()));
        holder.tv_time.setText(item_recycle.end_time.toString());
        holder.tv_endPrice.setText(item_recycle.end_price.toString().concat(" EGP"));
        holder.tv_title.setText(item_recycle.desc_money.toString());
        holder.tv_startPrice.setText(item_recycle.start_price.toString().concat(" EGP"));
        //    holder.tv_username.setText(firstname.toString());
        Picasso.get().load(String.valueOf(imageList.get(0))).into(holder.image_Post);
        //  Picasso.get().load(image_profile).into(holder.image_user);

        System.out.println("recycle data = " + item_recycle.desc_money.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, PostDetailsActivity.class);

                intent.putExtra("adsModel", item_recycle);
                intent.putExtra("userModel", userModel);
                intent.putExtra("fromActivity", fromActivity);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
