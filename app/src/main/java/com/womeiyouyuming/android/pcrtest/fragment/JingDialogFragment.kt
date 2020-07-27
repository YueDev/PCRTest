package com.womeiyouyuming.android.pcrtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.womeiyouyuming.android.pcrtest.R
import com.womeiyouyuming.android.pcrtest.adapter.JingAdapter
import com.womeiyouyuming.android.pcrtest.viewmodel.JingViewModel
import kotlinx.android.synthetic.main.fragment_jing_dialog.*

/**
 * Created by Yue on 2020/5/26.
 * 抽取完成后弹出的对话框
 */
class JingDialogFragment : DialogFragment() {


    val viewModel by activityViewModels<JingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jing_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //获取最后300次的数据，注意要改titleText，注意subList不包含第二个参数
        val allList = viewModel.girlTenLiveData.value ?: return
        val list = allList.subList(allList.size - 30, allList.size).mapIndexed { index, girlTen ->
            girlTen.copy(titleText = "${index * 10 + 1}  -  ${index * 10 + 10}")
        }



        //计算三星数量
        val star3Num = list.flatMap { it.girlList }.filter { it.star == 3 }.size
        //因为是百分数显示，所以直接除以3即可
        val star3Rate = "%.2f%%".format(star3Num / 3f)
        val pingjia = when {
            star3Num < 1 -> "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
            star3Num < 4 -> "这边建议您转生呢。"
            star3Num < 7 -> "有一点小非，建议平时多扶老奶奶过马路。"
            star3Num < 10 -> "普普通通的水准，请再接再厉。"
            star3Num < 13 -> "欧洲来的骑士君，您好！"
            else -> "会长快把这个人踢了！！！"
        }


        val resultString = "本次300抽结果如下：\n" +
                "3星${star3Num}个， 3星概率${star3Rate}\n" + pingjia

        resultTextView.text = resultString


        jingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = JingAdapter()
        jingRecyclerView.adapter = adapter
        adapter.submitList(list)


        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

    }


}