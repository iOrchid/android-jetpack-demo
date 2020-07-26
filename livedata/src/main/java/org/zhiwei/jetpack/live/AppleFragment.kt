package org.zhiwei.jetpack.live

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import kotlinx.android.synthetic.main.fg_apple.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 *
 */
class AppleFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fg_apple, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        (requireActivity() as LiveActivity).apply {
            liveAppleData.observe(viewLifecycleOwner, Observer {
                tv_live_apple.text = it
                Log.i("AppleFragment", "LiveData在AppleFragment中 $it")
            })

            val liveMapApple = liveAppleData.map {
                Log.d("AppleFragment", "LiveData在AppleFragment中 map $it")
                "it mapped it ${it.takeLast(4)}"
            }
            //在inActive状态下，是不会感知数据的，但是一旦resume，就会得到最新的数据
            liveMapApple.observe(viewLifecycleOwner, Observer {
                tv_mapped_live_apple.text = it.toString()
                Log.w("AppleFragment", "LiveData在AppleFragment中 map后的数据 $it")
            })

            //mediator
            mediatorLive.observe(this, Observer {
                //如果在inactive状态下，one two都变化了，它resume后只接受最新的
                tv_media_live_apple.text = it.toString()
                Log.w("AppleFragment", "AppleFragment中 mediatorLive ---> $it")
            })

            //switch map 结合mediator，通过条件，控制选择数据源,这里模拟的是，it的数字奇偶，控制最终输出
            val swLive = mediatorLive.switchMap {
                if (it.second.takeLast(1).toInt() % 2 == 0) liveOne else liveTwo
            }
            //UI可以看出，不论是one，还是 two，改变的话，只有满足条件，才会生效。
            swLive.observe(viewLifecycleOwner, Observer {
                tv_switch_live_apple.text = it
                Log.w("AppleFragment", "AppleFragment中 switchMap ---> $it")
            })

        }

    }
}