package com.hashem.mousavi.mywhichappandroidtest

import android.app.Application

class WhichApp : Application(){

    override fun onCreate() {
        super.onCreate()

        Singleton.density = resources.displayMetrics.density
    }

}