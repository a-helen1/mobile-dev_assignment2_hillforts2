package org.wit.hillforts.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_hillfort.view.*
import kotlinx.android.synthetic.main.card_hillfort.view.*
import kotlinx.android.synthetic.main.card_hillfort.view.hillfortDescription
import kotlinx.android.synthetic.main.card_hillfort.view.hillfortTitle
import org.wit.hillforts.R
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.models.HillfortModel

interface HillfortListener {
  fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(
    private var hillforts: List<HillfortModel>,
    private val listener: HillfortListener
) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(
        LayoutInflater.from(parent?.context).inflate(
            R.layout.card_hillfort,
            parent,
            false
        )
    )
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort, listener)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(hillfort: HillfortModel, listener: HillfortListener) {
      itemView.hillfortTitle.text = hillfort.title
      itemView.hillfortDescription.text = hillfort.description

       //if one image is null show the other on the  hillfort card

        if (hillfort.image1.isNotEmpty()) {
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image1))
        } else {
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image2))
        }

      itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
    }
  }
}