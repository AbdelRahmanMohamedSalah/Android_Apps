package com.mazad.mazadangy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mazad.mazadangy.R
import com.mazad.mazadangy.model.UploadModel
import com.squareup.picasso.Picasso

class AdsRecycleAdapter() : RecyclerView.Adapter<AdsRecycleAdapter.VH>() {
    lateinit var adsList:ArrayList<UploadModel>
    lateinit var context:Context
    constructor(context: Context, adsList: ArrayList<UploadModel>) : this() {
        this.adsList=adsList
        this.context=context
    }
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var titelId: TextView
        internal var date: TextView
        internal var imge: ImageView
        internal var price: TextView

        init {
            titelId = itemView.findViewById(R.id.titleTv)
            date = itemView.findViewById(R.id.date)
            imge = itemView.findViewById(R.id.imgeItem)
            price=itemView.findViewById(R.id.priceId)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view: View = LayoutInflater.from(context).inflate(R.layout.item_ads, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return adsList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.titelId.text = adsList[position].titleAds
        holder.date.text = adsList[position].postedDate
        holder.price.text=adsList[position].lastPrice
        Picasso.get().load(adsList[position].photo).into(holder.imge)


    }
}