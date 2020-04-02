package com.mazad.mazadangy.adapter

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.mazad.mazadangy.R
import com.mazad.mazadangy.gui.PostDetails.PostDetailsActivity
import com.mazad.mazadangy.gui.UserDetails.UserDetailsActivity
import com.mazad.mazadangy.model.AdsModel
import com.mazad.mazadangy.model.UserModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*


class AdsAdapter() : RecyclerView.Adapter<AdsAdapter.VH>() {
    lateinit var adsList: ArrayList<AdsModel>
    lateinit var context: Context
    lateinit var database: FirebaseDatabase
    lateinit var databaseRefrance: DatabaseReference
    lateinit var databaseRefranceUser: DatabaseReference


    private lateinit var countDownTimer: CountDownTimer


    constructor(context: Context, adsList: ArrayList<AdsModel>) : this() {
        this.adsList = adsList
        this.context = context
        database = FirebaseDatabase.getInstance()
        databaseRefrance = database.getReference("mony_post")
        databaseRefranceUser = database.getReference("users")

    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameUserTv: TextView
        var descTv: TextView
        var endPriceTv: TextView
        var startPriceTv: TextView
        var countDownTimerTextView: TextView
        var imgeAdIv: ImageView
        var interMazadBtn: Button
        var imgeProfileCv: CircleImageView

        init {
            nameUserTv = itemView.findViewById(R.id.nameUserTv)
            descTv = itemView.findViewById(R.id.descTv)
            startPriceTv = itemView.findViewById(R.id.startPriceTv)
            endPriceTv = itemView.findViewById(R.id.endPriceTv)
            countDownTimerTextView = itemView.findViewById(R.id.countDownTimerTextView)
            imgeAdIv = itemView.findViewById(R.id.imgeAdIv)
            interMazadBtn = itemView.findViewById(R.id.interMazadBtn)
            imgeProfileCv = itemView.findViewById(R.id.imgeProfileCv)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view: View = LayoutInflater.from(context).inflate(R.layout.item_money, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return adsList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var userModel: UserModel
        val adsModel: AdsModel = adsList.get(position)
        var uid = adsList[position].userId

        holder.descTv.text = adsList[position].desc_money
        holder.countDownTimerTextView.text = adsList[position].end_time

        var s: String = adsList[position].start_price
        holder.startPriceTv.text="السهر يبدأ من "+adsList[position].start_price
        holder.endPriceTv.text="السهر الحالى "+adsList[position].end_price

        var st:String=adsList[position].end_ads

        printDifferenceDateForHours(adsList[position].end_ads,holder.countDownTimerTextView)
       // holder.nameUserTv.text = "" + name
        var imgeList: List<String> =adsList[position].imge
        Picasso.get().load(imgeList[1]).into(holder.imgeAdIv)
        Picasso.get().load(R.drawable.profile_ph).into(holder.imgeProfileCv)

        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user: ArrayList<String>;
                //  menu.clear()
                // dataSnapshot.children.mapNotNullTo(menu) { it.getValue<user>(user::class.java) }

//                var u: UserModel = dataSnapshot.getValue(UserModel::class.java)!!
                var firstname = dataSnapshot.child("firstName").getValue(String::class.java)
                var lastname = dataSnapshot.child("lastName").getValue(String::class.java)
                var email = dataSnapshot.child("email").getValue(String::class.java)
                var phonenumber = dataSnapshot.child("phoneNumber").getValue(String::class.java)
                var interest = dataSnapshot.child("interest").getValue(String::class.java)
                var nickname = dataSnapshot.child("nickname").getValue(String::class.java)
                var uid = dataSnapshot.child("uId").getValue(String::class.java)

                userModel = UserModel(
                    uid!!,
                    phonenumber!!,
                    nickname!!,
                    lastname!!,
                    interest!!,
                    firstname!!,
                    email!!
                )
                holder.nameUserTv.text = "" + userModel.firstName

                holder.imgeProfileCv.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent = Intent(context, UserDetailsActivity::class.java)

                        intent.putExtra("UserModelPostDetailsActivity", userModel);
                        context.startActivity(intent)

                    }
                })

//                holder.interMazadBtn.setOnClickListener(object : View.OnClickListener {
//                    override fun onClick(v: View?) {
//                        val intent = Intent(context, PostDetailsActivity::class.java)
//
//                        intent.putExtra("UserModelPostDetailsActivity", userModel);
//                        context.startActivity(intent)
//
//                    }
//                })

                holder.itemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {

                        val intent = Intent(context, PostDetailsActivity::class.java)

                        intent.putExtra("adsModel", adsModel);
                        intent.putExtra("userModel", userModel);

                        context.startActivity(intent)

                    }
                })
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        databaseRefranceUser.child(uid.toString()).addListenerForSingleValueEvent(menuListener)


    }



    fun printDifferenceDateForHours(endDateDay:String,dateTv:TextView) {

        val currentTime = Calendar.getInstance().time
//        val endDateDay = "19/03/2020 21:00:00"
        val format1 = SimpleDateFormat("dd/MM/yyyy hh:mm:ss",Locale.getDefault())
//        val format1 = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())

        val endDate = format1.parse(endDateDay)

        //milliseconds
        var different = endDate.time - currentTime.time
        countDownTimer = object : CountDownTimer(different, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                dateTv.text = "$elapsedDays days $elapsedHours hs $elapsedMinutes min $elapsedSeconds sec"
            }

            override fun onFinish() {
                dateTv.text = "Saled!"
            }
        }.start()
    }


}