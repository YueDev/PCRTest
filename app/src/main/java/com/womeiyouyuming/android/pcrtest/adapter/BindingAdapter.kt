package com.womeiyouyuming.android.pcrtest.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.facebook.shimmer.ShimmerFrameLayout
import com.womeiyouyuming.android.pcrtest.R
import com.womeiyouyuming.android.pcrtest.model.Girl

/**
 * Created by Yue on 2020/6/1.
 */

@BindingAdapter("star3Num", "drawNum")
fun getStar3Rate(textView:TextView, star3NumLiveData: LiveData<Int>, drawNumLiveData: LiveData<Int>) {

    val star3Num = star3NumLiveData.value ?: return
    val drawNum = drawNumLiveData.value ?: return

    if (drawNum != 0) {
        val rate = star3Num * 100f / drawNum
        textView.text = "%.2f%%".format(rate)
    } else {
        textView.text = "0.00%"
    }

}

@BindingAdapter("setGirlImg")
fun setGirlImg(imageView: ImageView, girl: Girl) {

    imageView.setImageResource(girl.src)

    val fg = when (girl.star) {
        1 -> R.mipmap.fg_star_1
        2 -> R.mipmap.fg_star_2
        3 -> R.mipmap.fg_star_3
        else -> return
    }

    imageView.foreground = imageView.resources.getDrawable(fg, null)

}

@BindingAdapter("setShimmer")
fun setShimmer(shimmerFrameLayout: ShimmerFrameLayout, girl: Girl) {
    if (girl.star == 3) shimmerFrameLayout.startShimmer() else shimmerFrameLayout.stopShimmer()
}