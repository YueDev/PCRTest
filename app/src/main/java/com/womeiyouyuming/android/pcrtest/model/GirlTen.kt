package com.womeiyouyuming.android.pcrtest.model

/**
 * Created by Yue on 2020/5/28.
 *
 * title是第几抽  比如11-20抽
 * girlList就是10连的结果
 *
 * 这里封装下数据类，recyclerview的adapter直接拿来用很方便
 */
data class GirlTen(
    val titleText: String,
    val girlList: List<Girl>
)