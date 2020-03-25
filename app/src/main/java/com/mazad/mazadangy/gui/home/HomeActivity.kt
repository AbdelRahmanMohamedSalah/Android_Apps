package com.mazad.mazadangy.gui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.*
import com.mazad.mazadangy.R
import com.mazad.mazadangy.SpaceItemDecoration
import com.mazad.mazadangy.adapter.AdsAdapter
import com.mazad.mazadangy.adapter.AdsRecycleAdapter
import com.mazad.mazadangy.gui.ads.AdsFragment
import com.mazad.mazadangy.model.AdsModel
import com.mazad.mazadangy.model.UploadModel
import com.mazad.mazadangy.utels.ToastUtel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeInterface {
    lateinit var homePresenter: HomePresenter
    lateinit var adsAdapter: AdsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        PostsRecycler.layoutManager = LinearLayoutManager(this)
        homePresenter = HomePresenter(this)
        checkCat()

    }

    private fun checkCat() {
        var checkIntent: Intent = getIntent()
        val catCheck: String = checkIntent.getStringExtra("category")
        homePresenter.getDataPosts(catCheck, this)

    }

//    private fun setFragment(fragment: Fragment) {
//        var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentConteinr, fragment)
//        fragmentTransaction.commit()
//
//
//    }

    override fun noConnection() {
        ToastUtel.errorToast(this,"تحقق من وجود الانترنت")
    }

    override fun sucuss(adsList: ArrayList<AdsModel>) {
        adsAdapter = AdsAdapter(this, adsList)
        PostsRecycler.adapter = adsAdapter
        ToastUtel.errorToast(this,"data is done")


    }

    override fun onCancelled() {
        ToastUtel.errorToast(this,"cancelled")
    }
}
