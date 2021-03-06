package org.wit.hillforts.views.hillfort

import android.content.Intent
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillforts.helpers.showImagePicker
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.Location
import org.wit.hillforts.views.*

class HillfortPresenter(view: BaseView) : BasePresenter(view) {

    //val IMAGE_REQUEST = 1
    //val LOCATION_REQUEST = 2

    var hillfort = HillfortModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false;

    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        }
    }

    fun cacheHillfort (title: String, description: String, visited: Boolean, isFavourite: Boolean, hillfortRating: Float) {
        hillfort.title = title;
        hillfort.description = description;
        hillfort.visited = visited;
        hillfort.isFavorite = isFavourite;
        hillfort.rating = hillfortRating
    }

    fun doAddOrSave(title: String, description: String, isFavourite: Boolean, visitedHillfort: Boolean, hillfortRating: Float) {
        hillfort.title = title
        hillfort.description = description
        hillfort.isFavorite = isFavourite
        hillfort.visited = visitedHillfort
        hillfort.rating = hillfortRating
        doAsync {
            if (edit) {
                app.hillforts.update(hillfort)
            } else {
                app.hillforts.create(hillfort)
            }
            uiThread {
                view?.finish()
            }
        }
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
        view?.finish()
    }

    fun doSelectImage1() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSelectImage2() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(hillfort.lat, hillfort.lng, hillfort.zoom)
            )
        }
        /*if (hillfort.zoom != 0f) {
            location.lat = hillfort.lat
            location.lng = hillfort.lng
            location.zoom = hillfort.zoom
        }
        view.startActivityForResult(view.intentFor<EditLocationView>().putExtra("location", location), LOCATION_REQUEST)*/
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                hillfort.image1 = data.data.toString()
                hillfort.image2 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
            }
        }
    }
}