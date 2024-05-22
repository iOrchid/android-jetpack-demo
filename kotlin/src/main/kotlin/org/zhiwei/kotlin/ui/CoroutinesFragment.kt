package org.zhiwei.kotlin.ui

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.zhiwei.kotlin.R
import kotlin.random.Random

/**
 * 演示kotlin基础语法的协程coroutines部分的UI页面
 */
internal class CoroutinesFragment : Fragment() {

    private val tvTickerText: TextView? by lazy { view?.findViewById(R.id.tv_text_ticker_cor_kotlin) }
    private val btnProduce: Button? by lazy { view?.findViewById(R.id.btn_produce_cor_kotlin) }
    private val tvProduceText: TextView? by lazy { view?.findViewById(R.id.tv_produce_text_cor_kotlin) }
    private val btnActor: Button? by lazy { view?.findViewById(R.id.btn_actor_cor_kotlin) }
    private val tvActorText: TextView? by lazy { view?.findViewById(R.id.tv_actor_text_cor_kotlin) }
    private val btnChannelSend: Button? by lazy { view?.findViewById(R.id.btn_channel_send_cor_kotlin) }
    private val btnChannelReceive: Button? by lazy { view?.findViewById(R.id.btn_channel_rec_cor_kotlin) }
    private val tvChannelText: TextView? by lazy { view?.findViewById(R.id.tv_channel_text_cor_kotlin) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_coroutines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configData()
    }

    private fun configData() {
        configSuspendMethod()
        //produce是可以自动关闭的channel，发送者，可在其他地方接收
        val produce = MainScope().produce<String> {
            repeat(10) {
                delay(500)
                send("produce产生📖 $it")
            }
        }
        //只有点击触发receive才会继续发送send
        btnProduce?.setOnClickListener {
            //点一次，收一次
            val charSequence =
                produce.tryReceive().getOrNull() ?: "这个时间段里面有send数据产生"
            tvProduceText?.text =
                if (produce.isClosedForReceive) "⚠️Channel已经关闭" else charSequence
        }
        //自动接收，或者用上面的点击一次，收一次
//            MainScope().launch {
//                produce.consumeEach {
//                    Log.d(TAG, "configSuspendMethod: 接收 $it")
//                    val str = "接收一次 $it"
//                    tvProduceText?.text = str
//                }
//            }
        //actor 接收者，可在其他地方发送
        val actor = MainScope().actor<String> {
            consumeEach { str ->
                tvActorText?.text = if (isClosedForReceive) "⚠️Channel已经关闭" else str
            }
        }
        btnActor?.setOnClickListener {
            //使用trySend为的是不需要在协程作用域内，send需要
            val nextInt = Random.nextInt(100)
            //如果已经关闭channel，再send无效
            actor.trySend("发送个😧 $nextInt")
            if (nextInt in 40..49) actor.close()//随机数40..49的时候，close
        }
        //channel 可生产，可消费,先send，点收才有数据，多次send之后，不会阻塞，会挂起，在receive后会从channel取数据
        val channel = Channel<String>()//创建channel不需要在协程内
        btnChannelSend?.setOnClickListener {
            MainScope().launch {
                channel.send("🛣️🚀🔥${Random.nextInt(100)}✍ ")
            }
        }

        btnChannelReceive?.setOnClickListener {
            MainScope().launch {
                //更新UI是在主线程，这里是协程，也是主线程内的协程
                tvChannelText?.text = "🉑接收Channel： ${channel.receive()}"
            }
        }

    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun configSuspendMethod() = MainScope().launch {
        ticker(3 * 1000).consumeEach {
            val str = "使用Ticker每3秒变化一次 ${SystemClock.elapsedRealtime()}"
            tvTickerText?.text = str
        }
    }

}