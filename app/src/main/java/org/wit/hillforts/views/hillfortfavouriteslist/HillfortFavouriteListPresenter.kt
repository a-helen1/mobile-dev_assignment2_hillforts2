package org.wit.hillforts.views.hillfortfavouriteslist

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.activities.HillfortFavouriteListActivity
import org.wit.hillforts.activities.HillfortMapsActivity
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.BasePresenter
import org.wit.hillforts.views.BaseView
import org.wit.hillforts.views.VIEW
import org.wit.hillforts.views.hillfort.HillfortView
import org.wit.hillforts.views.hillfortlist.HillfortListView
import org.wit.hillforts.views.map.HillfortMapView

class HillfortFavouriteListPresenter (view: BaseView ) : BasePresenter(view){

  fun getHillforts(): List<HillfortModel> {
    val allHillforts = app.hillforts.findAll()
    val favHillforts = allHillforts.filter { it.isFavorite }
    return favHillforts
  }

  fun doAddHillfort() {
    view?.navigateTo(VIEW.HILLFORT)
    //view.startActivityForResult<HillfortView>(0)
  }

  fun doEditHillfort(hillfort: HillfortModel) {
    view?.navigateTo(VIEW.HILLFORT,0,"hillfort_edit", hillfort)
    //view.startActivityForResult(view.intentFor<HillfortView>().putExtra("hillfort_edit", hillfort), 0)
  }

  fun doShowHillfortMap() {
    view?.navigateTo(VIEW.MAPS)
    //view.startActivity<HillfortMapView>()
  }

  fun doShowFavourites() {
    view?.navigateTo(VIEW.FAVOURTITE_LIST)
    //view.startActivity<HillfortFavouriteListView>()
  }

  fun loadHillforts() {
    view?.showHillforts(getHillforts())
  }
}