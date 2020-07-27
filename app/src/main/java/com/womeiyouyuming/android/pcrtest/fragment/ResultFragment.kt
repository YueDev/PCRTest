package com.womeiyouyuming.android.pcrtest.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.womeiyouyuming.android.pcrtest.R
import com.womeiyouyuming.android.pcrtest.adapter.ResultAdapter
import com.womeiyouyuming.android.pcrtest.model.Girl
import com.womeiyouyuming.android.pcrtest.viewmodel.JingViewModel
import kotlinx.android.synthetic.main.fragment_result.*
import java.lang.StringBuilder




// 结果界面，展示数据，分享

class ResultFragment : Fragment() {

    private val viewModel by activityViewModels<JingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 以下是统计三星的数据
        val map = mutableMapOf<Girl, Int>()

        val girlTens = viewModel.girlTenLiveData.value ?: return

        //这里用map来存储每一个girl的数量
        girlTens.flatMap { it.girlList }.forEach {

            val value = map.getOrPut(it) { 0 }            //如果get不到girl，则会先put个0进去
            map[it] = value + 1

        }

        //这里将map转换为list后排序,这个list直接给recyclerview使用
        val list = map.toList().sortedWith(Comparator { pair1, pair2 ->
            //按照星星排序，如果星星相等按照数量来排序，注意我们需要降序，因此要加负号
            -(if (pair1.first.star == pair2.first.star) pair1.second.compareTo(pair2.second) else pair1.first.star.compareTo(
                pair2.first.star
            ))
        })


        //计算母猪石的数量

        var pigNum = 0
        list.forEach {
            pigNum += (it.second - 1) * when (it.first.star) {
                3 -> 50
                2 -> 10
                1 -> 1
                else -> 0
            }
        }


        // 各种统计结果
        val drawNum = viewModel.drawNumLiveData.value ?: return
        val diamondNum = drawNum * 150
        val star3Num = viewModel.star3NumLiveData.value ?: return
        val star2Num = viewModel.star2Num
        val star3Rate = "%.2f%%".format(star3Num * 100f / drawNum)
        val star2Rate = "%.2f%%".format(star2Num * 100f / drawNum)


        val result = "亲爱的骑士君：\n" +
                "您共抽取扭蛋${drawNum}次。\n" +
                "花费宝石${diamondNum}个。\n" +
                "三星角色${star3Num}个，三星概率${star3Rate}\n" +
                "二星角色${star2Num}个， 二星概率${star2Rate}\n" +
                "女神石${pigNum}个。\n"

        val resultString = result + "快点击下方按钮，分享您的好运吧！"

        textResult.text = resultString


        // 这个界面处理的数据比较多，并且都是简单的计算，不涉及数据共享，因此没有用viewmodel。
        // 所以也没用databinding。
        resultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ResultAdapter()
        resultRecyclerView.adapter = adapter
        adapter.submitList(list)


        buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        buttonShare.setOnClickListener {


            //遍历三星角色，转化为结果分享


            val lastString = if (star3Num == 0) {
                "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
            } else {
                val build = StringBuilder("====================\n三星角色:\n")
                list.filter { it.first.star == 3 }.forEachIndexed { index, pair ->
                    build.append("${pair.first.name}${pair.second}个")
                    build.append(if (index % 3 == 2) "\n" else "，")
                }
                //删除最后一个逗号，加上换行
                if (build.endsWith("，")) build.deleteCharAt(build.lastIndex).append("\n")
                build.append("====================\n")
                build.append("宝石来之不易，请合理规划。\n祝您单抽必出彩，十连有人权。").toString()
            }


            val shareString = result + lastString

            //SharesSheet分享结果文本
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareString)
                type = "text/plain"
            }
            //利用createChooser返回一个指向ShareSheet的Intent
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }

    }


}