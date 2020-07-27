package com.womeiyouyuming.android.pcrtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.android.pcrtest.databinding.ItemJingBinding
import com.womeiyouyuming.android.pcrtest.model.GirlTen

/**
 * Created by Yue on 2020/6/1.
 */
class JingAdapter : ListAdapter<GirlTen, JingAdapter.JingHolder>(GirlTenDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JingHolder {
        val binding = ItemJingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JingHolder(binding)
    }

    override fun onBindViewHolder(holder: JingHolder, position: Int) {
        holder.bind(getItem(position))
    }



    class GirlTenDiffCallback: DiffUtil.ItemCallback<GirlTen>() {
        override fun areItemsTheSame(oldItem: GirlTen, newItem: GirlTen) = oldItem == newItem
        override fun areContentsTheSame(oldItem: GirlTen, newItem: GirlTen) = oldItem == newItem
    }

    class JingHolder(private val binding: ItemJingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(girlTen: GirlTen) {
            binding.girlTen = girlTen
        }

    }

}