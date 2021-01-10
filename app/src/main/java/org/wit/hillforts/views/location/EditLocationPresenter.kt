package org.wit.hillforts.views.location

import android.app.Activity
import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hillforts.models.Location
import org.wit.hillforts.views.BasePresenter

class EditLocationPresenter(view: EditLocationView) : BasePresenter(view) {

  var location = Location()

  init {
    location = view.intent.extras?.getParcelable<Location>("location")!!
  }

  fun doConfigureMap(map: GoogleMap) {
    val loc = LatLng(location.lat, location.lng)
    val options = MarkerOptions()
        .title("HIllfort")
        .snippet("GPS : " + loc.toString())
        .draggable(true)
        .position(loc)
    map.addMarker(options)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
  }

  /*fun initMap(map: GoogleMap) {
    val loc = LatLng(location.lat, location.lng)
    val options = MarkerOptions()
        .title("Hillfort")
        .snippet("GPS : " + loc.toString())
        .draggable(true)
        .position(loc)
    map.addMarker(options)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
  }*/

  fun doUpdateLocation(lat: Double, lng: Double, zoom: Float) {
    location.lat = lat
    location.lng = lng
    location.zoom = zoom
  }

  fun doSave() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    view?.setResult(0, resultIntent)
    view?.finish()
  }

  /*fun doOnBackPressed() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    view.setResult(Activity.RESULT_OK, resultIntent)
    view.finish()
  }*/

  fun doUpdateMarker(marker: Marker) {
    val loc = LatLng(location.lat, location.lng)
    marker.setSnippet("GPS : " + loc.toString())
  }
}
