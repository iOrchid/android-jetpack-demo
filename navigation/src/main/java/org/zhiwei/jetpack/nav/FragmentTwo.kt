package org.zhiwei.jetpack.nav

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_two.*

/**
 * Author: zhiwei.
 * Date: 2018/11/5 0005,20:35.
 */
class FragmentTwo : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Fragment", "FragmentTwo onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Fragment", "FragmentTwo onCreateView")
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("Fragment", "FragmentTwo onViewCreate hashCode: ${this.hashCode()} this: $this")
        val s = "hashCode: ${this.hashCode()} this: $this"
        tv_fg_two.text = s
        //返回
        view.findViewById<View>(R.id.btn_back1)
            .setOnClickListener { v: View? ->
                Navigation.findNavController(v!!).navigateUp()
            }
        //跳 3
        view.findViewById<View>(R.id.btn_go3)
            .setOnClickListener { v: View? ->
                Navigation.findNavController(v!!).navigate(R.id.go_fragment3)
            }
    }
}