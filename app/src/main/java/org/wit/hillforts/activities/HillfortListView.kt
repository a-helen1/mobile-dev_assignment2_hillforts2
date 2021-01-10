package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.wit.hillforts.R
import org.wit.hillforts.models.HillfortModel

class HillfortListView : AppCompatActivity(), HillfortListener {

  lateinit var presenter: HillfortListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    toolbar.title=title
    setSupportActionBar(toolbar)

    presenter = HillfortListPresenter(this)
    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter =
        HillfortAdapter(presenter.getHillforts(), this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_add -> presenter.doAddHillfort()
      R.id.item_map -> presenter.doShowHillfortkMap()
      R.id.item_favorite -> presenter.doShowFavourites()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
    presenter.doEditHillfort(hillfort)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    recyclerView.adapter?.notifyDataSetChanged()
    super.onActivityResult(requestCode, resultCode, data)
  }
}