package com.example.testseekeritunes.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Result
import com.example.testseekeritunes.R
import com.example.testseekeritunes.core.BaseOnSelectItem

class MainAdapter(private val context: Context, private val listener: BaseOnSelectItem<Result>) :
    RecyclerView.Adapter<MainViewHolder>() {
    private var resultList = listOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.list_item,
            parent,
            false
        )

        val holder = MainViewHolder(view)

        holder.itemRoot.setOnClickListener {
            listener.onSelectItem(resultList[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val result = resultList[position]
        holder.trackNameTv.text = result.trackName
        holder.artistNameTv.text = result.artistName
    }

    fun setData(newList: List<Result>) {
        resultList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = resultList.size
}