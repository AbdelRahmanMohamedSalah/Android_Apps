package com.mazad.mazadangy.gui.home

import android.content.Context
import com.google.firebase.database.*
import com.mazad.mazadangy.model.AdsModel
import com.mazad.mazadangy.utels.StaticMethod
import com.mazad.mazadangy.utels.ToastUtel

class HomePresenter {
    lateinit var homeInterface: HomeInterface
    var listAds: ArrayList<AdsModel>

    constructor(homeInterface: HomeInterface) {
        this.homeInterface = homeInterface
        listAds = ArrayList()
    }

    fun getDataPosts(typePost: String, context: Context) {
        if (StaticMethod.isConnectingToInternet(context)) {
            var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
            var dataRefrance: DatabaseReference = firebaseDatabase.getReference(typePost)
            dataRefrance.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (data: DataSnapshot in dataSnapshot.children) {
                    //    ToastUtel.errorToast(context,"data is done")

                        var adsModel: AdsModel? = data.getValue(AdsModel::class.java)
                        listAds.add(adsModel!!)

                    }
                    homeInterface.sucuss(listAds)

                }

                override fun onCancelled(p0: DatabaseError) {
                    homeInterface.onCancelled()
                }


            })

        } else {
            homeInterface.noConnection()
        }
    }

}