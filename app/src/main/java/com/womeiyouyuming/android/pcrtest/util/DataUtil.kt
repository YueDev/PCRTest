package com.womeiyouyuming.android.pcrtest.util

import com.womeiyouyuming.android.pcrtest.R
import com.womeiyouyuming.android.pcrtest.model.Girl

/**
 * Created by Yue on 2020/5/19.
 */


//10抽的总概率 3 2.5%  2 25.95% 1 71.55%



//up角色的序号、概率，概率是万分制  注意2星在第10抽的时候有额外概率
//已经在IDEA里测试过很多次，每次都是以千万次测算，这个算法没有问题
private val upStar3s = listOf(17 to 70)
private val upStar2s = listOf<Pair<Int, Int>>()
private val upStar2sTenth = listOf<Pair<Int, Int>>()
private val upStar1s = listOf<Pair<Int, Int>>()


//1-9抽的概率 万分制
//第10抽的概率 3星是25  2星是975， 不会出1星
private const val RATE_STAR_3 = 250
private const val RATE_STAR_2 = 1800
private const val RATE_STAR_1 = 7950



//10连抽
fun drawTen(): List<Girl> {

    val girls = mutableListOf<Girl>()

    repeat(9) {
        girls.add(drawOnce())
    }
    girls.add(drawTenth())

    return girls
}

//单抽
private fun drawOnce(): Girl {

    val random = (0 until 10000).random()

    return when {
        random < RATE_STAR_3 -> generateGirl(random, upStar3s, star3Girls)

        random < RATE_STAR_2 + RATE_STAR_3 -> generateGirl(random - RATE_STAR_3, upStar2s, star2Girls)

        else -> generateGirl(random - RATE_STAR_2 - RATE_STAR_3, upStar1s, star1Girls)

    }
}

// 10连抽的第10抽，不会出现1星角色
private fun drawTenth(): Girl {

    val random = (0 until 10000).random()


    return when {
        random < RATE_STAR_3 -> generateGirl(random, upStar3s, star3Girls)

        else -> generateGirl(random - RATE_STAR_3, upStar2sTenth, star2Girls)

    }

}



//这个方法是抽取的drawOnce和drawTenth的when部分，也就是实际的抽卡方法。

private fun generateGirl(random: Int, upGirls: List<Pair<Int, Int>>, girls: List<Girl>): Girl {


    //这里考虑多up的情况，
    //假设up1的概率30， up2的概率是50， up3的概率是20
    //random的范围在(0..29) 则是up1，(30..79)则是up2，(79..99)则是up3
    //因此判断完第一次，如果random > 30不是up1，则需要减去30，再判断第二次，即random - 30是否小于50，依次类推
    //所以这里用变量r来处理。

    var r = random
    upGirls.forEach {
        if (r < it.second) return girls[it.first] else r -= it.second
    }

    //遍历完up角色后，如果没有up角色，则为普通角色，随机出一个非up即可

    val upIndexes = upGirls.map { it.first }

    return girls.filterIndexed { index, _ ->
        (index in upIndexes).not()
    }.random()

}






//   数据类，这里数据比较简单，就不用载数据库了

val star3Girls = listOf(
    Girl("杏奈", 3, R.mipmap.xingnai),
    Girl("真步", 3, R.mipmap.zhenbu),
    Girl("璃乃", 3, R.mipmap.linai),
    Girl("初音", 3, R.mipmap.chuyin),
    Girl("伊绪", 3, R.mipmap.yixu),
    Girl("咲恋", 3, R.mipmap.xiaolian),
    Girl("望", 3, R.mipmap.wang),
    Girl("妮侬", 3, R.mipmap.ninong),
    Girl("秋乃", 3, R.mipmap.qiunai),
    Girl("真琴", 3, R.mipmap.zhenqin),
    Girl("静流", 3, R.mipmap.jingliu),
    Girl("莫妮卡", 3, R.mipmap.monika),
    Girl("姬塔", 3, R.mipmap.jita),
    Girl("纯", 3, R.mipmap.chun),
    Girl("亚里莎", 3, R.mipmap.yalisha),
    Girl("镜华", 3, R.mipmap.jinghua),
    Girl("伊莉亚", 3, R.mipmap.yiliya),
    Girl("铃莓（夏日）", 3, R.mipmap.lingmei_xiari)
)

val star2Girls = listOf(
    Girl("茜里", 2, R.mipmap.xili),
    Girl("宫子", 2, R.mipmap.gongzi),
    Girl("雪", 2, R.mipmap.xue),
    Girl("铃奈", 2, R.mipmap.lingnai),
    Girl("香织", 2, R.mipmap.xiangzhi),
    Girl("美美", 2, R.mipmap.meimei),
    Girl("绫音", 2, R.mipmap.lingyin),
    Girl("惠理子", 2, R.mipmap.huilizi),
    Girl("忍", 2, R.mipmap.ren),
    Girl("真阳", 2, R.mipmap.zhenyang),
    Girl("栞", 2, R.mipmap.kan),
    Girl("千歌", 2, R.mipmap.qiange),
    Girl("空花", 2, R.mipmap.konghua),
    Girl("珠希", 2, R.mipmap.zhuxi),
    Girl("美东", 2, R.mipmap.meidong),
    Girl("深月", 2, R.mipmap.shenyue),
    Girl("铃", 2, R.mipmap.ling),
    Girl("纺希", 2, R.mipmap.fangxi),
    Girl("美里", 2, R.mipmap.meili)
)

val star1Girls = listOf(
    Girl("日和莉", 1, R.mipmap.riheli),
    Girl("怜", 1, R.mipmap.lian),
    Girl("未奏希", 1, R.mipmap.weizouxi),
    Girl("胡桃", 1, R.mipmap.hutao),
    Girl("依里", 1, R.mipmap.yili),
    Girl("铃莓", 1, R.mipmap.lingmei),
    Girl("由加莉", 1, R.mipmap.youjiali),
    Girl("碧", 1, R.mipmap.bi),
    Girl("美咲", 1, R.mipmap.meixiao),
    Girl("莉玛", 1, R.mipmap.lima)
)

//不在池子里的角色，一般为限定角
val otherGirls = listOf(
    Girl("佩可（夏日）", 3, R.mipmap.peike_xiari)
)



// 随机看板图
fun getRandomImgRes() = listOf(
    R.mipmap.img_random_1,
    R.mipmap.img_random_2,
    R.mipmap.img_random_3,
    R.mipmap.img_random_4,
    R.mipmap.img_random_5,
    R.mipmap.img_random_6,
    R.mipmap.img_random_7,
    R.mipmap.img_random_8,
    R.mipmap.img_random_9,
    R.mipmap.img_random_10,
    R.mipmap.img_random_11,
    R.mipmap.img_random_12
).random()
