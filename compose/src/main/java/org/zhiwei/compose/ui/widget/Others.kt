package org.zhiwei.compose.ui.widget

import android.net.Uri
import android.widget.VideoView
import androidx.annotation.IdRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 * 简单封装的，用于compose调用Android view的方式，
 * 视频播放的方式 ⚠️，这里演示了compose调用view的。
 * 相反，view体系调用compose可通过composerView包装，xml或者代码均可
 */
@Composable
fun VideoViewCompose(modifier: Modifier = Modifier, @IdRes videoRes: Int) {
    AndroidView(factory = { context ->
        VideoView(context).apply {
            val uri = "android.resource://" + context.packageName + "/" + videoRes
            setVideoURI(Uri.parse(uri))
            setOnPreparedListener { player ->
                player.isLooping = true
            }
            start()
        }
    }, modifier)
}