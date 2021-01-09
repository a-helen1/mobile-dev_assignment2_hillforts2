package org.wit.hillforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.helpers.readImage
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.helpers.showImagePicker
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.Location

class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  lateinit var app: MainApp

  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  //var location = Location(52.245696, -7.139102, 15f)

  //flag to set image 2
  var image1 = false
  var edit = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    app = application as MainApp

    if (intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
      hillfortTitle.setText(hillfort.title)
      hillfortDescription.setText(hillfort.description)
      hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))
      hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))
      hillfortRating.rating = hillfort.rating
      ratingVal.text = hillfort.rating.toString()
      isFavorite.isChecked = hillfort.isFavorite
      visitedHillfort.isChecked = hillfort.visited

      // change button text if an image exisis

      if (hillfort.image1 != null) {
        chooseImage1.setText(R.string.Change_hillfort_image)
      } else {
        chooseImage2.setText(R.string.Change_hillfort_image)
      }
    }

    chooseImage1.setOnClickListener {
      image1 = true
      showImagePicker(this, IMAGE_REQUEST)
    }

    chooseImage2.setOnClickListener {
      image1 = false
      showImagePicker(this, IMAGE_REQUEST)
    }

    hillfortRating.setOnRatingBarChangeListener { _, fl, _ ->
     hillfort.rating = fl
      ratingVal.text = "$fl"

    }

    hillfortLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (hillfort.zoom != 0f) {
        location.lat = hillfort.lat
        location.lng = hillfort.lng
        location.zoom = hillfort.zoom
      }
      startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }
  }

  fun onCheckboxClicked(view: View) {
    if (view is CheckBox) {
      val checked: Boolean = view.isChecked
      when (view.id) {
        R.id.visitedHillfort -> {
          if (checked) {
            hillfort.visited = true
          } else if (!checked) {
            hillfort.visited = false
          }
        }
        R.id.isFavorite -> {
          if (checked) {
            hillfort.isFavorite = true
          }else if (!checked) {
            hillfort.isFavorite = false
          }
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_save -> {
        hillfort.title = hillfortTitle.text.toString()
        hillfort.description = hillfortDescription.text.toString()
        if (hillfort.title.isEmpty()) {
          toast(R.string.enter_hillfort_title)
        } else {
          if (edit) {
            app.hillforts.update(hillfort.copy())
          } else {
            app.hillforts.create(hillfort.copy())
          }
        }
        info("add button pressed: ${hillfort}")
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
      R.id.item_cancel -> {
        finish()
      }
      R.id.item_delete -> {
        app.hillforts.delete(hillfort)
        finish()
      }

    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          if (image1) {
            hillfort.image1 = data.getData().toString()
            hillfortImage1.setImageBitmap(readImage(this, resultCode, data))
            chooseImage1.setText(R.string.Change_hillfort_image)
          } else {
            hillfort.image2 = data.getData().toString()
            hillfortImage2.setImageBitmap(readImage(this, resultCode, data))
            chooseImage2.setText(R.string.Change_hillfort_image)
          }
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras?.getParcelable<Location>("location")!!
          hillfort.lat = location.lat
          hillfort.lng = location.lng
          hillfort.zoom = location.zoom
        }
      }
    }
  }
}

