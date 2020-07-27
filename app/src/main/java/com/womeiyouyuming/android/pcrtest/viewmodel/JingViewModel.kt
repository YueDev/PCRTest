package com.womeiyouyuming.android.pcrtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.womeiyouyuming.android.pcrtest.model.Girl
import com.womeiyouyuming.android.pcrtest.model.GirlTen
import com.womeiyouyuming.android.pcrtest.util.drawTen

/**
 * Created by Yue on 2020/5/28.
 */
class JingViewModel : ViewModel() {

    // 抽取的次数 以及 三星数量 ， 其他的三星概率以及钻石数可以计算出来
    private val _drawNumLiveData = MutableLiveData(0)
    val drawNumLiveData: LiveData<Int> = _drawNumLiveData

    private val _star3NumLiveData = MutableLiveData(0)
    val star3NumLiveData: LiveData<Int> = _star3NumLiveData


    //recyclerview的list adapter使用这个LiveData
    private val _girlTenLiveData = MutableLiveData(listOf<GirlTen>())
    val girlTenLiveData = _girlTenLiveData


    //用map来存储girl和个数
    private val girlMap = mutableMapOf<Girl, Int>()

    var star2Num = 0
    var star1Num = 0



//    init {
//
//        //测试方法
//        viewModelScope.launch {
//            var draw = 0
//            var star3 = 0
//            while (true) {
//                delay(1000)
//                draw += 300
//                star3 += (5..200).random()
//                _drawNumLiveData.value = draw
//                _star3NumLiveData.value = star3
//
//            }
//        }
//    }






    fun jing() {

        val girlTens = mutableListOf<GirlTen>()

        var star3Num = 0




        repeat(30) {

            val girlList = drawTen()

            //GirlTen的titleText
            val startInt = _drawNumLiveData.value?.plus(it * 10 + 1)
            val endInt = startInt?.plus(9)

            girlTens.add(GirlTen("$startInt  -  $endInt", girlList))



            girlList.forEach { girl ->
                val value = girlMap.getOrPut(girl) { 0 }
                girlMap[girl] = value + 1
                //计算各个星级的数量，写在这里可以不用遍历map

                when (girl.star) {
                    3 -> star3Num += 1
                    2 -> star2Num += 1
                    1 -> star1Num += 1
                }
            }

        }



        _drawNumLiveData.value = _drawNumLiveData.value?.plus(300)

        _star3NumLiveData.value = _star3NumLiveData.value?.plus(star3Num)

        _girlTenLiveData.value = _girlTenLiveData.value?.plus(girlTens)


    }

    fun reset() {

        _drawNumLiveData.value = 0
        _star3NumLiveData.value = 0



        _girlTenLiveData.value = listOf()

        girlMap.clear()

        star2Num = 0
        star1Num = 0
    }


}