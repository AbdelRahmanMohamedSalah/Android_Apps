package com.mazad.mazadangy.gui.ads


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

import com.mazad.mazadangy.R
import com.mazad.mazadangy.SpaceItemDecoration
import com.mazad.mazadangy.adapter.AdsRecycleAdapter
import com.mazad.mazadangy.model.UploadModel
import kotlinx.android.synthetic.main.fragment_ads.*

/**
 * A simple [Fragment] subclass.
 */
class AdsFragment : Fragment() {
    lateinit var databaseReference: DatabaseReference
    lateinit var array: ArrayList<UploadModel>
    lateinit var adsAdapter: AdsRecycleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view:View=inflater.inflate(R.layout.fragment_ads,container,false)
        var recyclerView:RecyclerView=view.findViewById(R.id.recyclerView)
        array = ArrayList()!!
        recyclerView.layoutManager = GridLayoutManager(this.context, 2).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % 3 == 0)
                        2
                    else
                        1
                }
            }
        }

        recyclerView.addItemDecoration(SpaceItemDecoration(array.size));

        adsAdapter = AdsRecycleAdapter(activity?.applicationContext!!, array)
        recyclerView.adapter = adsAdapter
        databaseReference = FirebaseDatabase.getInstance().getReference("posts")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data: DataSnapshot in p0.children) {
                    var uploadModelList: UploadModel? = data.getValue(UploadModel::class.java)
                    array.add(uploadModelList!!)
                    adsAdapter.notifyDataSetChanged()
                }
            }
        })

        
        return view



    }


}
