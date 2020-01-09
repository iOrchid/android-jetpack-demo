package org.zhiwei.jetpack.nav

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_one.*

/**
 * Author: zhiwei.
 * Date: 2018/11/5 0005,20:35.
 */
class FragmentOne : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Fragment", "FragmentOne onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Fragment", "FragmentOne onCreateView")
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Fragment", "FragmentOne onViewCreated hashCode: ${this.hashCode()} this: $this")
        val s = "hashCode: ${this.hashCode()} this: $this"
        tv_fg_one.text = s
        view.findViewById<View>(R.id.btn_go2)
            .setOnClickListener { v: View? ->
                Navigation.findNavController(view).navigate(R.id.go_fragment2)
            }
    }
}