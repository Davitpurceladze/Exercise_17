package com.example.exercise_17

import android.app.Application

class App:Application() {

    companion object{
        lateinit var application: Application
        private set
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}