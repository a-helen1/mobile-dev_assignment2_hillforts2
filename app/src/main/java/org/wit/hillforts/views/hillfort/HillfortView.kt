package org.wit.hillforts.views.hillfort

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.BaseView

class HillfortView : BaseView(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    var hillfort = HillfortModel()
    var image_1 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        super.init(toolbarAdd, true)

        presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

        chooseImage1.setOnClickListener {
            presenter.cacheHillfort(
                hillfortTitle.text.toString(),
                hillfortDescription.text.toString(),
                visitedHillfort.isChecked,
                isFavorite.isChecked,
                hillfortRating.rating

            )
            presenter.doSelectImage1()
        }
        chooseImage2.setOnClickListener {
            presenter.cacheHillfort(
                hillfortTitle.text.toString(),
                hillfortDescription.text.toString(),
                visitedHillfort.isChecked,
                isFavorite.isChecked,
                hillfortRating.rating
            )
            presenter.doSelectImage2()
        }

        hillfortLocation.setOnClickListener {
            presenter.cacheHillfort(
                hillfortTitle.text.toString(),
                hillfortDescription.text.toString(),
                visitedHillfort.isChecked,
                isFavorite.isChecked,
                hillfortRating.rating
            )
            presenter.doSetLocation()
        }


        hillfortRating.setOnRatingBarChangeListener { _, fl, _ ->
            hillfort.rating = fl
            ratingVal.text = "$fl"

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

    override fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        hillfortDescription.setText(hillfort.description)
        hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))
        hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))
        hillfortRating.rating = hillfort.rating
        ratingVal.text = hillfort.rating.toString()
        isFavorite.isChecked = hillfort.isFavorite
        visitedHillfort.isChecked = hillfort.visited
        if (hillfort.image1 != null) {
            chooseImage1.setText(R.string.Change_hillfort_image)
        }else {
            chooseImage2.setText(R.string.Change_hillfort_image)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        if(presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_save -> {
                if (hillfortTitle.toString().isEmpty()) {
                    toast(R.string.enter_hillfort_title)
                } else {
                    presenter.doAddOrSave(
                        hillfortTitle.text.toString(),
                        hillfortDescription.text.toString(),
                        isFavorite.isChecked,
                        visitedHillfort.isChecked,
                        hillfortRating.rating
                    )
                }
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data !=null) {
            presenter.doActivityResult(requestCode,resultCode, data)
        }
    }
}