package org.wit.hillforts.views.hillfortlist

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.*
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.BasePresenter
import org.wit.hillforts.views.BaseView
import org.wit.hillforts.views.VIEW

class HillfortListPresenter ( view: BaseView) : BasePresenter(view) {

  fun getHillforts() = app.hillforts.findAll()

  fun doLogout() {
    FirebaseAuth.getInstance().signOut()
    app.hillforts.clear()
    view?.navigateTo(VIEW.LOGIN)
  }

  fun doAddHillfort() {
    view?.navigateTo(VIEW.HILLFORT)
    //view?.startActivityForResult<HillfortView>(0)
  }

  fun doEditHillfort(hillfort: HillfortModel) {
    view?.navigateTo(VIEW.HILLFORT,0,"hillfort_edit", hillfort)
    //view?.startActivityForResult(view.intentFor<HillfortView>().putExtra("hillfort_edit", hillfort), 0)
  }

  fun doShowHillfortMap() {
    view?.navigateTo(VIEW.MAPS)
    //view?.startActivity<HillfortMapView>()
  }

  fun doShowFavourites() {
    view?.navigateTo(VIEW.FAVOURTITE_LIST)
    //view?.startActivity<HillfortFavouriteListView>()
  }

  fun loadHillforts() {
    doAsync {
      val hillforts = app.hillforts.findAll()
      uiThread {
        view?.showHillforts(hillforts)
      }
    }

  }
}