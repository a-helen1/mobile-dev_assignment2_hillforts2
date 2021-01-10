package org.wit.hillforts.views.hillfortlist

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.activities.HillfortFavouriteListActivity
import org.wit.hillforts.activities.HillfortMapsActivity
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.hillfort.HillfortView
import org.wit.hillforts.views.hillfortfavouriteslist.HillfortFavouriteListView

class HillfortListPresenter (val view: HillfortListView) {

  var app: MainApp

  init {
    app = view.application as MainApp
  }

  fun getHillforts() = app.hillforts.findAll()

  fun doAddHillfort() {
    view.startActivityForResult<HillfortView>(0)
  }

  fun doEditHillfort(hillfort: HillfortModel) {
    view.startActivityForResult(view.intentFor<HillfortView>().putExtra("hillfort_edit", hillfort), 0)
  }

  fun doShowHillfortkMap() {
    view.startActivity<HillfortMapsActivity>()
  }

  fun doShowFavourites() {
    view.startActivity<HillfortFavouriteListView>()
  }
}