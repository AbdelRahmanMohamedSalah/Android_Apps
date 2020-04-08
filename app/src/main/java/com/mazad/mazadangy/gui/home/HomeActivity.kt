package com.mazad.mazadangy.gui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mazad.mazadangy.R
import com.mazad.mazadangy.adapter.AdsAdapter
import com.mazad.mazadangy.gui.AddAds.AddAdsActivity
import com.mazad.mazadangy.gui.AddAds.AddPostActivity
import com.mazad.mazadangy.gui.UserDetails.UserDetailsActivity
import com.mazad.mazadangy.gui.category.CategoryActivity
import com.mazad.mazadangy.gui.favorite.FavoritePostActivity
import com.mazad.mazadangy.model.AdsModel
import com.mazad.mazadangy.model.UserModel
import com.mazad.mazadangy.utels.ToastUtel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header.view.*


class HomeActivity : AppCompatActivity(), HomeInterface,
    NavigationView.OnNavigationItemSelectedListener {
    lateinit var homePresenter: HomePresenter
    lateinit var adsAdapter: AdsAdapter
    lateinit var intent_obj: Intent
    lateinit var toolbar: Toolbar
    lateinit var catCheck: String
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var datarefrence: DatabaseReference
    lateinit var currentFirebaseUser: FirebaseAuth
    lateinit var userModel: UserModel


    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_home)
//        recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        val user = FirebaseAuth.getInstance().currentUser


        datarefrence =
            FirebaseDatabase.getInstance().getReference("users").child(user?.uid.toString() + "")
        PostsRecycler.layoutManager = LinearLayoutManager(this)
        homePresenter = HomePresenter(this)
        checkCat()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        nav_btn.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("WrongConstant")
            override fun onClick(v: View?) {

//                drawerLayout.openDrawer(Gravity.END)
            }

        })

        val header = nav_view.getHeaderView(0) as LinearLayout


        val menuListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }


            override fun onDataChange(dataSnapshot: DataSnapshot) {
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

                Picasso.get().load(dataSnapshot.child("image_profile").getValue(String::class.java))
                Picasso.get().load(userModel.image_profile.toString())
                    .into(header.imgeProfileCvNavHeader)

                header.nameUserTvNavHeader.setText(userModel.firstName.toString())

            }
        }
        datarefrence.addListenerForSingleValueEvent(menuListener)


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
                    R.id.favorit -> {

                        if (catCheck.equals("posts")) {
                            intent_obj = Intent(this@HomeActivity, FavoritePostActivity::class.java)
                            intent_obj.putExtra("fromActivity", "posts")
                            startActivity(intent_obj)


                        } else {
                            intent_obj = Intent(this@HomeActivity, FavoritePostActivity::class.java)
                            intent_obj.putExtra("fromActivity", "money_post")
                            startActivity(intent_obj)


                        }


                    }
                }
                return true
            }

        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                intent_obj = Intent(this@HomeActivity, UserDetailsActivity::class.java)
                intent_obj.putExtra("UserModelPostDetailsActivity", userModel)
                intent_obj.putExtra("fromActivity", "HomeActivity")

                startActivity(intent_obj)
            }
            R.id.nav_messages -> {
                Toast.makeText(this, "اللغه  clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_friends -> {
                Toast.makeText(this, "تعليمات clicked", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true


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
