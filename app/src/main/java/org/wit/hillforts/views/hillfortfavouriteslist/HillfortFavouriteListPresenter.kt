package org.wit.hillforts.views.hillfortfavouriteslist

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.activities.HillfortFavouriteListActivity
import org.wit.hillforts.activities.HillfortMapsActivity
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.hillfort.HillfortView
import org.wit.hillforts.views.hillfortlist.HillfortListView

class HillfortFavouriteListPresenter (val view: HillfortFavouriteListView) {

  var app: MainApp

  init {
    app = view.application as MainApp
  }

  fun getHillforts(): List<HillfortModel> {
    val allHillforts = app.hillforts.findAll()
    val favHillforts = allHillforts.filter { it.isFavorite }
    return favHillforts
  }

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
    view.startActivity<HillfortFavouriteListActivity>()
  }
}