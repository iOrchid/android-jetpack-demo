package org.zhiwei.compose.screen.gesture

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.isUnspecified
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text

/**
 * 作者： iOrchid
 * 主页： [Github](https://github.com/iOrchid)
 * 日期： 2024年05月18日 13:50
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 */
@Composable
internal fun TouchImage_Screen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Title_Text(title = "ColorPicker")
        Title_Sub_Text(title = "从图片Image中，获取触控点的颜色值")
        TouchOnImageExample()
    }
}

@Composable
private fun TouchOnImageExample() {

    val imageBitmap: ImageBitmap = ImageBitmap.imageResource(R.drawable.sexy_girl)

    val bitmapWidth = imageBitmap.width
    val bitmapHeight = imageBitmap.height

    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var imageSize by remember { mutableStateOf(Size.Zero) }

    // These are for debugging
    var text by remember { mutableStateOf("") }
    var colorAtTouchPosition by remember { mutableStateOf(Color.Unspecified) }

    val imageModifier = Modifier
        .background(Color.LightGray)
        .fillMaxWidth()
        // 限定宽高比
        .aspectRatio(3f / 4)
        .pointerInput(Unit) {
            detectTapGestures { offset: Offset ->
                // Touch coordinates on image
                offsetX = offset.x
                offsetY = offset.y

                // Scale from Image touch coordinates to range in Bitmap
                val scaledX = (bitmapWidth / imageSize.width) * offsetX
                val scaledY = (bitmapHeight / imageSize.height) * offsetY

                try {
                    val pixel: Int =
                        imageBitmap
                            .asAndroidBitmap()
                            .getPixel(scaledX.toInt(), scaledY.toInt())


                    val red = android.graphics.Color.red(pixel)
                    val green = android.graphics.Color.green(pixel)
                    val blue = android.graphics.Color.blue(pixel)

                    text = "图片触控 offsetX:$offsetX, offsetY: $offsetY\n" +
                            "图片宽: ${imageSize.width}, 高: ${imageSize.height}\n" +
                            "位图 宽: ${bitmapWidth}, 高: $bitmapHeight\n" +
                            "scaledX: $scaledX, scaledY: $scaledY\n" +
                            "red: $red, green: $green, blue: $blue\n"

                    colorAtTouchPosition = Color(red, green, blue)
                } catch (e: Exception) {
                    println("Exception e: ${e.message}")
                }
            }
        }
        .onSizeChanged { imageSize = it.toSize() }

    Image(
        bitmap = imageBitmap,
        contentDescription = null,
        modifier = imageModifier.border(2.dp, Color.Red),
        contentScale = ContentScale.Crop
    )
    Text(text = text)
    Box(
        modifier = Modifier
            .then(
                if (colorAtTouchPosition.isUnspecified) {
                    Modifier
                } else {
                    Modifier.background(colorAtTouchPosition)
                }
            )
            .size(100.dp)
    )
}

@Preview
@Composable
private fun PreviewTouchImage() {
    TouchImage_Screen()
}