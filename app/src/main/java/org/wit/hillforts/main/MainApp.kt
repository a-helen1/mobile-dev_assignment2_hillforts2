package org.wit.hillforts.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillforts.models.*
import org.wit.hillforts.models.firebase.HillfortFireStore

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore


  override fun onCreate() {
    super.onCreate()
    //hillforts = HillfortJSONStore(applicationContext)
    hillforts = HillfortFireStore(applicationContext)
    info("Hillfort Started")
  }
}