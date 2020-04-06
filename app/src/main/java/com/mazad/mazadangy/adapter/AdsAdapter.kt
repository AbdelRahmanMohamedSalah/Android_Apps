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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AdsAdapter() : RecyclerView.Adapter<AdsAdapter.VH>() {
    lateinit var adsList: ArrayList<AdsModel>
    lateinit var context: Context
    lateinit var database: FirebaseDatabase
    lateinit var databaseRefrance: DatabaseReference
    lateinit var databaseRefranceUser: DatabaseReference
    lateinit var UID: String
    lateinit var fromActivity: String

    lateinit var EndTimeDetails: String
    private lateinit var countDownTimer: CountDownTimer


    constructor(context: Context, adsList: ArrayList<AdsModel>, fromActivity: String) : this() {
        this.adsList = adsList
        this.context = context
        this.fromActivity = fromActivity

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
        holder.startPriceTv.text="السعر يبدأ من "+adsList[position].start_price
        holder.endPriceTv.text="السعر الحالى "+adsList[position].end_price

        var st:String=adsList[position].end_ads
        UID = adsList[position].id_post
        EndTimeDetails = adsList[position].end_time
        printDifferenceDateForHours(adsList[position].end_ads,holder.countDownTimerTextView)
       // holder.nameUserTv.text = "" + name
        var imgeList: List<String> =adsList[position].imge
        Picasso.get().load(imgeList[1]).into(holder.imgeAdIv)

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
                var image_profile = dataSnapshot.child("image_profile").getValue(String::class.java)


                userModel = UserModel(
                    uid!!,
                    phonenumber!!,
                    nickname!!,
                    lastname!!,
                    interest!!,
                    firstname!!,
                    email!!,
                    image_profile!!
                )
                holder.nameUserTv.text = "" + userModel.firstName
                Picasso.get().load(userModel.image_profile).into(holder.imgeProfileCv)

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
                        intent.putExtra("fromActivity", fromActivity);


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

        if (EndTimeDetails.equals("العاشرة مساء")) {
            System.out.println("End Time Data =  10:00" + EndTimeDetails)

        } else {

            System.out.println("End Time Data =  12:00" + EndTimeDetails)

        }

        val date_s = "2017-03-08 13:27:00"

        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
        // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
//        val dt = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
//        val date = dt.parse(date_s)
//
//        // *** same for the format String below
//        // *** same for the format String below
//        var dt1 = SimpleDateFormat("yyyy-MM-dd")
//        println("Date :" + dt1.format(date))
//
//        dt1 = SimpleDateFormat("HH:mm:ss")
//        println("Time :" + dt1.format(date))
//
//        val timeSpent = ("" + dt1)
//        val seperatedTime = timeSpent.split(":").toTypedArray()
////        val hours = seperatedTime[0].toInt()
//        val minutes = seperatedTime[1].toInt()
//        val seconds = seperatedTime[2].toInt()
////        val duration = 3600 * hours + 60 * minutes + seconds
//
//        println("end date Day" + endDateDay);
////        println("Hours= "+hours);
//        val df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
//
//        val d = df.parse(endDateDay)
//
//        val hours = d.hours
//        println("Hours = " + hours.toString())

        if (EndTimeDetails.equals("العاشرة مساء")) {
            System.out.println("End Time Data =  10:00" + EndTimeDetails)

        } else {

            System.out.println("End Time Data =  12:00" + EndTimeDetails)

        }

        ///////////////////////////////////
        var copy: String
        copy = endDateDay.toString();
        // val dtStart = "2010-10-15T09:27:37Z"
        val format =
            SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        try {
            val date = format.parse(copy)
            System.out.println("date now = " + date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


//        endDateDay = Calendar.getInstance()
//        endDateDay[Calendar.HOUR_OF_DAY] = 10
//        val date = cal.time








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
                System.out.println("UID = " + UID);
                databaseRefrance.child(UID + "").removeValue();
//                context.startActivity(getIntent());

            }
        }.start()
    }


}