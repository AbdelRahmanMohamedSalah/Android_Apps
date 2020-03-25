package com.mazad.mazadangy.gui.home

import com.mazad.mazadangy.model.AdsModel

interface HomeInterface {
        fun noConnection()
        fun sucuss(adsList:ArrayList<AdsModel>)
        fun onCancelled()
}