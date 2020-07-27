package com.womeiyouyuming.android.pcrtest.fragment

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.womeiyouyuming.android.pcrtest.R
import com.womeiyouyuming.android.pcrtest.adapter.JingAdapter
import com.womeiyouyuming.android.pcrtest.databinding.FragmentJingBinding
import com.womeiyouyuming.android.pcrtest.util.getRandomImgRes
import com.womeiyouyuming.android.pcrtest.viewmodel.JingViewModel
import kotlinx.android.synthetic.main.fragment_jing.*

/**
 * 300抽的主界面
 */
class JingFragment : Fragment() {


    private val viewModel by activityViewModels<JingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentJingBinding>(
            inflater,
            R.layout.fragment_jing,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  模仿游戏内标题文字 渐变色 fff0ab -> d56001

        val textHeight = titleText.paint.descent() - titleText.paint.ascent()

        val linearGradient = LinearGradient(
            0f,
            0f,
            0f,
            textHeight,
            Color.parseColor("#fff0ab"),
            Color.parseColor("#d56001"),
            Shader.TileMode.CLAMP
        )
        titleText.paint.shader = linearGradient


        initImageViewJing()


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        jingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = JingAdapter()

        jingRecyclerView.adapter = adapter

        viewModel.girlTenLiveData.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it) {
                if (adapter.itemCount > 0) jingRecyclerView.scrollToPosition(adapter.itemCount - 1)
            }

        })



        buttonReset.setOnClickListener {
            viewModel.reset()
            initImageViewJing()
        }

        buttonJing.setOnClickListener {

            viewModel.jing()
            imageViewJing.setImageResource(getRandomImgRes())

            //导航到dialog，按钮连点两次会崩溃，这里确认下当前ID，或者catch 错误

//            try {
//                findNavController().navigate(R.id.action_jingFragment_to_jingDialogFragment)
//            } catch (e: Exception) {
//                Log.d("YUEDEVTAG", "JingFragment -> onActivityCreated(): 不要连续点击这个按钮")
//            }

            if (findNavController().currentDestination?.id == R.id.jingFragment) {
                findNavController().navigate(R.id.action_jingFragment_to_jingDialogFragment)
            }

        }

        buttonResult.setOnClickListener {

            if (viewModel.drawNumLiveData.value?.equals(0) != false) {
                Toast.makeText(requireContext(), "请先抽取扭蛋", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (findNavController().currentDestination?.id == R.id.jingFragment) {
                findNavController().navigate(R.id.action_jingFragment_to_resultFragment)
            }
        }

    }


    //初始化默认imageView，一般是当期up角色
    private fun initImageViewJing() {
        imageViewJing.setImageResource(R.mipmap.img_random_12)
    }

}
