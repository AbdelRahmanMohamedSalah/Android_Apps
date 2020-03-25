package com.mazad.mazadangy.gui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mazad.mazadangy.R
import com.mazad.mazadangy.gui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        listnerView()

    }

    private fun listnerView() {
        moneyCard.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intentMoney:Intent=(Intent(this@CategoryActivity,HomeActivity::class.java))
                intentMoney.putExtra("category","mony_post")
                startActivity(intentMoney)


            }
        })

        otherCard.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intentother:Intent=(Intent(this@CategoryActivity,HomeActivity::class.java))
                intentother.putExtra("category","other")
                startActivity(intentother)

            }
        })
    }
}
