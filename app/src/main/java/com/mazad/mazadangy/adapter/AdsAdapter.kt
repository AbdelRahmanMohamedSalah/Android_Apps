package com.mazad.mazadangy.adapter

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mazad.mazadangy.R
import com.mazad.mazadangy.model.AdsModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AdsAdapter() : RecyclerView.Adapter<AdsAdapter.VH>() {
    lateinit var adsList: ArrayList<AdsModel>
    lateinit var context: Context
    lateinit var database:FirebaseDatabase
    lateinit var databaseRefrance:DatabaseReference


    private lateinit var countDownTimer:CountDownTimer


    constructor(context: Context, adsList: ArrayList<AdsModel>) : this() {
        this.adsList = adsList
        this.context = context
        database= FirebaseDatabase.getInstance()
        databaseRefrance=database.getReference("mony_post")
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameUserTv: TextView
        var descTv: TextView
        var startPriceTv: TextView
        var countDownTimerTextView: TextView
        var imgeAdIv: ImageView
       // var interMazadBtn: Button
       var imgeProfileCv: CircleImageView

        init {
            nameUserTv = itemView.findViewById(R.id.nameUserTv)
            descTv = itemView.findViewById(R.id.descTv)
            startPriceTv = itemView.findViewById(R.id.startPriceTv)
            countDownTimerTextView = itemView.findViewById(R.id.countDownTimerTextView)
            imgeAdIv = itemView.findViewById(R.id.imgeAdIv)
            //interMazadBtn = itemView.findViewById(R.id.imgeAdIv)
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
        val adsModel:AdsModel=adsList.get(position)
        holder.descTv.text=adsList[position].desc_money
        var s:String=adsList[position].start_price
        holder.startPriceTv.text="السهر يبدأ من "+adsList[position].start_price
        var st:String=adsList[position].end_ads
        printDifferenceDateForHours(adsList[position].end_ads,holder.countDownTimerTextView)
        holder.nameUserTv.text="كريم أحمد"
        var imgeList: List<String> =adsList[position].imge
        Picasso.get().load(imgeList[1]).into(holder.imgeAdIv)
        Picasso.get().load(R.drawable.profile_ph).into(holder.imgeProfileCv)
        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
//                var intent:Intent= Intent(context,DisplayAdsActivity::class.java)
            }
        })
    }



    fun printDifferenceDateForHours(endDateDay:String,dateTv:TextView) {

        val currentTime = Calendar.getInstance().time
//        val endDateDay = "19/03/2020 21:00:00"
        val format1 = SimpleDateFormat("dd/MM/yyyy hh:mm:ss",Locale.getDefault())
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