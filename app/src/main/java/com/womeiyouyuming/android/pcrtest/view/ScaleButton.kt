package com.womeiyouyuming.android.pcrtest.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat


/**
 * Created by Yue on 2020/5/21.
 * 模仿PCR游戏内按钮的按下缩放效果
 */
class ScaleButton(context: Context?, attrs: AttributeSet?) : AppCompatButton(context, attrs) {




    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> ViewCompat.animate(this).scaleX(0.9f).scaleY(0.9f).setDuration(100).start()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> ViewCompat.animate(this).scaleX(1f).scaleY(1f).setDuration(100).start()
        }


        return super.onTouchEvent(event)
    }

}