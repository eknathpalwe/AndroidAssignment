package com.androidassignment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidassignment.R
import com.androidassignment.extension.loadImage
import com.androidassignment.model.Facts

class FactsListAdapter(private var factsList: List<Facts>) : RecyclerView.Adapter<FactsListAdapter.FactsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FactsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_facts, parent, false)
        return FactsViewHolder(view)
    }

    override fun onBindViewHolder(vh: FactsViewHolder, position: Int) {
        val facts = factsList[position]

        vh.textViewTitle.text = facts.title
        vh.textViewDescription.text = facts.description
        vh.imageViewRef.visibility =
            facts.imageHref?.let {
                vh.imageViewRef.loadImage(facts.imageHref)
                View.VISIBLE
            } ?: View.GONE
    }

    override fun getItemCount(): Int {
        return factsList.size
    }

    fun update(data: List<Facts>) {
        factsList = data
        notifyDataSetChanged()
    }

    class FactsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.text_view_title)
        val textViewDescription: TextView = view.findViewById(R.id.text_view_description)
        val imageViewRef: ImageView = view.findViewById(R.id.image_view_ref)
    }
}