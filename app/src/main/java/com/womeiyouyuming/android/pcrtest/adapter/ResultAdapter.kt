package com.womeiyouyuming.android.pcrtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.android.pcrtest.R
import com.womeiyouyuming.android.pcrtest.model.Girl
import kotlinx.android.synthetic.main.item_result.view.*


/**
 * Created by Yue on 2020/6/3.
 */
class ResultAdapter : ListAdapter<Pair<Girl, Int>, ResultAdapter.ResultHolder>(PairDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return ResultHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PairDiffCallback : DiffUtil.ItemCallback<Pair<Girl, Int>>() {
        override fun areItemsTheSame(oldItem: Pair<Girl, Int>, newItem: Pair<Girl, Int>) =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Pair<Girl, Int>,
            newItem: Pair<Girl, Int>
        ) = oldItem == newItem
    }


    class ResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textName = itemView.textName
        private val imageGirl = itemView.imageGirl
        private val imageStar = itemView.imageStar
        private val textNum = itemView.textNum


        fun bind(pair: Pair<Girl, Int>) {

            with(pair) {
                textName.text = first.name
                textNum.text = "X  $second"
                imageGirl.setImageResource(first.src)
                imageStar.setImageResource(
                    when (first.star) {
                        3 -> R.mipmap.star_3
                        2 -> R.mipmap.star_2
                        else -> R.mipmap.star_1
                    }
                )

            }

        }

    }
}