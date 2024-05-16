package org.zhiwei.compose.screen.gesture

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Clickable_Screen(modifier: Modifier = Modifier) {
    var show by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { show = show.not() }) {
            Text(text = "切换")
        }
        if (show) {
            LifecycleEffect()
        }
    }

}

/**
 * lifecycle的compose组件，提供的几个函数，用于compose组件声明周期的感知
 */
@Composable
private fun LifecycleEffect(modifier: Modifier = Modifier) {
    val TAG = "LifecycleEffect"
    Log.v(TAG, "🚀： ------------")
    //不可用于监听onDestroy，否则报错,
    //This function should not be used to listen for Lifecycle.Event.ON_DESTROY
    // because Compose stops recomposing after receiving a Lifecycle.Event.ON_STOP
    // and will never be aware of an ON_DESTROY to launch onEvent.
    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
        Log.d(TAG, "🚄： ------创建------")
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_STOP) {
        Log.d(TAG, "♻️： ------停止------")
    }

    LifecycleStartEffect(key1 = Lifecycle.Event.ON_START) {
        Log.i(TAG, "🍊： ------启动------")
        //这里会在onStop和onDestroy都触发
        onStopOrDispose {
            Log.i(TAG, "🍊： ------启动内>> 停止/销毁------")
        }
    }

    LifecycleResumeEffect(key1 = Lifecycle.Event.ON_RESUME) {
        Log.w(TAG, "🍎： ------显示------")
        //这里会在onPause和onDestroy都触发
        onPauseOrDispose {
            Log.w(TAG, "🍎： ------显示内>> 暂停/销毁------")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewClickable() {
    Clickable_Screen()
}