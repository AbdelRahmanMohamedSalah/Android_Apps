package com.mazad.mazadangy.model

import java.io.Serializable

class AdsModel : Serializable {

    lateinit var back_sale:String
    lateinit var count_price:String
    lateinit var desc_money:String
    lateinit var end_ads:String
    lateinit var end_time:String
    lateinit var pand_num:String
    lateinit var start_price:String
    lateinit var end_price:String
    lateinit var id_post: String
    // lateinit var auction:List<String>
   // lateinit var auction: String

    lateinit var status_money:String
    lateinit var userId:String
    lateinit var stop_ad: String
    lateinit var imge: List<String>


    constructor()
    constructor(
        back_sale: String,
        count_price: String,
        desc_money: String,
        end_ads: String,
        end_time: String,
        pand_num: String,
        start_price: String,
        end_price: String,
        id_post: String,
        status_money: String,
        userId: String,
        stop_ad: String,
        imge: List<String>
    ) {
        this.back_sale = back_sale
        this.count_price = count_price
        this.desc_money = desc_money
        this.end_ads = end_ads
        this.end_time = end_time
        this.pand_num = pand_num
        this.start_price = start_price
        this.end_price = end_price
        this.id_post = id_post
        this.status_money = status_money
        this.userId = userId
        this.stop_ad = stop_ad
        this.imge = imge
    }

    constructor(desc_money: String, end_time: String, start_price: String, end_price: String) {
        this.desc_money = desc_money
        this.end_time = end_time
        this.start_price = start_price
        this.end_price = end_price
    }

}