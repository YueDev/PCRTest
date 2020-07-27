package com.womeiyouyuming.android.pcrtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.womeiyouyuming.android.pcrtest.R
import kotlinx.android.synthetic.main.fragment_title.*

/**
 * 标题界面，可以选择玩法，这里只做了300连抽。
 */
class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_title, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toast = Toast.makeText(requireContext(), "亲爱的骑士君，系统检测到您是土豪，请点击下井按钮。", Toast.LENGTH_SHORT)

        buttonGoto10Draw.setOnClickListener {
            toast.show()
        }

        buttonGotoJing.setOnClickListener {
//            val viewModel by activityViewModels<JingViewModel>()
//            viewModel.reset()
            toast.cancel()
            findNavController().navigate(R.id.action_titleFragment_to_jingFragment)
        }
    }

}
