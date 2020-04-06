package com.mazad.mazadangy.gui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mazad.mazadangy.R
import com.mazad.mazadangy.adapter.AdsAdapter
import com.mazad.mazadangy.gui.AddAds.AddAdsActivity
import com.mazad.mazadangy.gui.AddAds.AddPostActivity
import com.mazad.mazadangy.gui.category.CategoryActivity
import com.mazad.mazadangy.gui.favorite.FavoritePostActivity
import com.mazad.mazadangy.model.AdsModel
import com.mazad.mazadangy.utels.ToastUtel
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), HomeInterface {
    lateinit var homePresenter: HomePresenter
    lateinit var adsAdapter: AdsAdapter
    lateinit var intent_obj: Intent
    lateinit var catCheck: String
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        PostsRecycler.layoutManager = LinearLayoutManager(this)
        homePresenter = HomePresenter(this)
        checkCat()

        val bottomNavigationView =
            findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.getItemId()) {
                    R.id.upload -> {

                        if (catCheck.equals("posts")) {
                            intent_obj = Intent(this@HomeActivity, AddPostActivity::class.java)
                            startActivity(intent_obj)

                        } else {
                            intent_obj = Intent(this@HomeActivity, AddAdsActivity::class.java)
                            startActivity(intent_obj)

                        }
                    }
                    R.id.home -> {
                        intent_obj = Intent(this@HomeActivity, CategoryActivity::class.java)
                        startActivity(intent_obj)
                        finish()
                    }
                    R.id.favorit ->{

                        if (catCheck.equals("posts")) {
                            intent_obj = Intent(this@HomeActivity, FavoritePostActivity::class.java)
                            intent_obj.putExtra("fromActivity","posts")
                            startActivity(intent_obj)




                        } else{
                            intent_obj = Intent(this@HomeActivity, FavoritePostActivity::class.java)
                            intent_obj.putExtra("fromActivity","money_post")
                            startActivity(intent_obj)


                        }



                    }
                }
                return true
            }

        })


    }


    private fun checkCat() {
        var checkIntent: Intent = getIntent()
        catCheck = checkIntent.getStringExtra("category")
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
        adsAdapter = AdsAdapter(this, adsList, catCheck)
        PostsRecycler.adapter = adsAdapter
      //  ToastUtel.errorToast(this,"data is done")


    }

    override fun onCancelled() {
        ToastUtel.errorToast(this,"cancelled")
    }


}
