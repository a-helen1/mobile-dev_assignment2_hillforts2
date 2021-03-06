package org.wit.hillforts.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlin.math.log

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class HillfortMemStore: HillfortStore, AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()

  override fun findAll(): List<HillfortModel> {
    return hillforts
  }

  override fun clear() {
    hillforts.clear()
  }

  override fun findById(id: Long): HillfortModel? {
    val foundHillfort: HillfortModel? = hillforts.find { it.id == id }
    return foundHillfort
  }

  override fun create(hillfort: HillfortModel) {
    hillfort.id = getId()
    hillforts.add(hillfort)
    logAll();
  }

  override fun update(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }
    if (foundHillfort != null) {
      foundHillfort.title = hillfort.title
      foundHillfort.description = hillfort.description
      foundHillfort.image1 = hillfort.image1
      foundHillfort.image2 = hillfort.image2
      foundHillfort.lat = hillfort.lat
      foundHillfort.lng = hillfort.lng
      foundHillfort.zoom = hillfort.zoom
      foundHillfort.visited = hillfort.visited
      logAll()
    }
  }

  override fun delete(hillfort: HillfortModel) {
    hillforts.remove(hillfort)
  }

  fun logAll() {
    hillforts.forEach({ info("${it}")})
  }
}