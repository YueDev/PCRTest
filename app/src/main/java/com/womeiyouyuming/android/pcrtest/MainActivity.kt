package com.womeiyouyuming.android.pcrtest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 透明化状态栏，api30弃用
         window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        //API30透明化状态栏新方法 api23崩溃
        //window.setDecorFitsSystemWindows(false)


        //这里直接会把状态栏的文字也一快隐藏掉 这个API23也崩溃
        //window.decorView.rootView.doOnLayout {
        //    it.windowInsetsController?.hide(WindowInsets.Type.statusBars())
        //}

        setContentView(R.layout.activity_main)



    }
}
