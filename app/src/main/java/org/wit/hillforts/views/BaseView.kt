package org.wit.hillforts.views

import android.content.Intent
import android.os.Parcelable
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.hillfort.HillfortView
import org.wit.hillforts.views.hillfortfavouriteslist.HillfortFavouriteListView
import org.wit.hillforts.views.hillfortlist.HillfortListView
import org.wit.hillforts.views.location.EditLocationView
import org.wit.hillforts.views.map.HillfortMapView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
  LOCATION, HILLFORT, MAPS, LIST, FAVOURTITE_LIST
}


open abstract class BaseView () : AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null

  fun navigateTo(view: VIEW, code: Int =0, key: String = "", value: Parcelable? = null ) {
    var intent = Intent(this, HillfortListView::class.java)
    when (view) {
      VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
      VIEW.HILLFORT -> intent = Intent(this, HillfortView::class.java)
      VIEW.MAPS -> intent = Intent(this, HillfortMapView::class.java)
      VIEW.LIST -> intent = Intent(this, HillfortListView::class.java)
      VIEW.FAVOURTITE_LIST -> intent = Intent(this, HillfortFavouriteListView::class.java )
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter{
    basePresenter = presenter
    return presenter
    }

  fun init(toolbar: androidx.appcompat.widget.Toolbar) {
    toolbar.title = title
    setSupportActionBar(toolbar)
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
   basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showHillfort(hillfort: HillfortModel) {}
  open fun showHillforts(hillforts: List<HillfortModel>) {}
  open fun showProgress() {}
  open fun hideProgress() {}



}