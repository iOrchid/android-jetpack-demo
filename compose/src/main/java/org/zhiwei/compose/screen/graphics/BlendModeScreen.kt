package org.zhiwei.compose.screen.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zhiwei.compose.R
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
internal fun BlendMode_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Text(title = "Blend (PorterDuff) Modes")
        Title_Sub_Text(
            "Blend modes are used to clip, change position of destination/source " +
                    "or manipulate pixels." +
                    "\nFirst drawn shape/image is **Destination**, second one that drawn with " +
                    "blend mode is Source",
        )
        Title_Desc_Text("Draw Shapes with Blend Mode")
        DrawShapeBlendMode()
        Title_Desc_Text("Draw Images with Blend Mode")
        DrawImageBlendMode()
        Title_Desc_Text("Clip Image with Blend Mode Via Path")
        ClipImageWithBlendModeViaPath()
        Title_Desc_Text("Clip Image with Blend Mode Via Image")
        ClipImageWithBlendModeViaAnotherImage()

        Title_Desc_Text("Reveal Shape drawn below transparent")
        RevealShapeWithBlendMode()
        Title_Desc_Text("Reveal Shape drawn above transparent")
        RevealShapeWithBlendMode2()
        Title_Desc_Text("Add colors with BlendMode.Plus")
        ColorAddBlendMode()
    }
}


/**
 * In this example destination path is drawn first, then source path with blend mode.
 * Since we don't have full screen paths blend modes are applied to intersection of these
 * shapes.
 */
@Composable
private fun DrawShapeBlendMode() {
    var selectedIndex by remember { mutableIntStateOf(3) }
    var blendMode: BlendMode by remember { mutableStateOf(BlendMode.SrcOver) }

    var dstColor by remember { mutableStateOf(Color(0xffEC407A)) }
    var srcColor by remember { mutableStateOf(Color(0xff29B6F6)) }

    var showDstColorDialog by remember { mutableStateOf(false) }
    var showSrcColorDialog by remember { mutableStateOf(false) }


    Canvas(modifier = canvasModifier) {

        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = canvasHeight / 2 - 100

        val horizontalOffset = 70f
        val verticalOffset = 50f

        val cx = canvasWidth / 2 - horizontalOffset
        val cy = canvasHeight / 2 + verticalOffset
        val srcPath = createPolygonPath(cx, cy, 5, radius)

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            // Destination
            drawCircle(
                color = dstColor,
                radius = radius,
                center = Offset(
                    canvasWidth / 2 + horizontalOffset,
                    canvasHeight / 2 - verticalOffset
                ),
            )

            // Source
            drawPath(path = srcPath, color = srcColor, blendMode = blendMode)

            restoreToCount(checkPoint)
        }
    }

    OutlinedButton(onClick = { showDstColorDialog = true }) {
        Text(
            text = "Dst Color",
            fontSize = 16.sp,
            color = dstColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

    OutlinedButton(onClick = {
        showSrcColorDialog = true
    }) {
        Text(
            text = "Src Color",
            fontSize = 16.sp,
            color = srcColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

    Text(
        text = "Src BlendMode: $blendMode",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )

    BlendModeSelection(
        modifier = Modifier
            .height(200.dp)
            .verticalScroll(rememberScrollState()),
        selectedIndex = selectedIndex,
        onBlendModeSelected = { index, mode ->
            blendMode = mode
            selectedIndex = index
        }
    )

    if (showSrcColorDialog) {
        CustomDialogWithResultExample(
            initialColor = srcColor,
            onDismiss = {
                showSrcColorDialog = false
            },
            onNegativeClick = {
                showSrcColorDialog = false
            },
            onPositiveClick = {
                showSrcColorDialog = false
                srcColor = it
            }
        )
    }

    if (showDstColorDialog) {
        CustomDialogWithResultExample(
            initialColor = dstColor,
            onDismiss = {
                showDstColorDialog = false
            },
            onNegativeClick = {
                showDstColorDialog = false
            },
            onPositiveClick = {
                showDstColorDialog = false
                dstColor = it
            }
        )
    }
}

/**
 * Images are overlapped on other. src is drawn on top of dst image, both images have transparent
 * pixels. Setting blend mode and color of dst and/or src will change clipping or coloring
 * of canvas.
 */
@Composable
private fun DrawImageBlendMode() {

    var selectedIndex by remember { mutableStateOf(3) }
    var blendMode: BlendMode by remember { mutableStateOf(BlendMode.SrcOver) }

    val dstImage = ImageBitmap.imageResource(id = R.drawable.composite_dst)
    val srcImage = ImageBitmap.imageResource(id = R.drawable.composite_src)

    Canvas(modifier = canvasModifier) {

        val canvasWidth = size.width.roundToInt()
        val canvasHeight = size.height.roundToInt()

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            // Destination
            drawImage(
                image = dstImage,
                srcSize = IntSize(canvasWidth / 2, canvasHeight / 2),
                dstSize = IntSize(canvasWidth, canvasHeight),
            )

            // Source
            drawImage(
                image = srcImage,
                srcSize = IntSize(canvasWidth / 2, canvasHeight / 2),
                dstSize = IntSize(canvasWidth, canvasHeight),
                blendMode = blendMode
            )
            restoreToCount(checkPoint)
        }
    }

    Text(
        text = "Src BlendMode: $blendMode",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )

    BlendModeSelection(
        modifier = Modifier
            .height(200.dp)
            .verticalScroll(rememberScrollState()),
        selectedIndex = selectedIndex,
        onBlendModeSelected = { index, mode ->
            blendMode = mode
            selectedIndex = index
        }
    )
}

/**
 * src image is clipped using shape drawn behind as dst.
 */
@Composable
private fun ClipImageWithBlendModeViaPath() {
    var sides by remember { mutableFloatStateOf(6f) }
    val srcBitmap = ImageBitmap.imageResource(id = R.drawable.img_moto_girl)

    var selectedIndex by remember { mutableIntStateOf(5) }
    var blendMode: BlendMode by remember { mutableStateOf(BlendMode.SrcIn) }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width.roundToInt()
        val canvasHeight = size.height.roundToInt()
        val cx = canvasWidth / 2
        val cy = canvasHeight / 2
        val radius = (canvasHeight - 20.dp.toPx()) / 2
        val path = createPolygonPath(cx.toFloat(), cy.toFloat(), sides.roundToInt(), radius)


        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            // Destination
            drawPath(
                color = Color.Blue,
                path = path
            )
            // Source
            drawImage(
                blendMode = blendMode,
                image = srcBitmap,
                srcSize = IntSize(srcBitmap.width, srcBitmap.height),
                dstSize = IntSize(canvasWidth, canvasHeight)
            )

            restoreToCount(checkPoint)
        }
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {

        Text(text = "Sides ${sides.roundToInt()}")
        Slider(
            value = sides,
            onValueChange = { sides = it },
            valueRange = 3f..12f,
            steps = 10
        )

        Text(
            text = "Src BlendMode: $blendMode",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        BlendModeSelection(
            modifier = Modifier
                .height(200.dp)
                .verticalScroll(rememberScrollState()),
            selectedIndex = selectedIndex,
            onBlendModeSelected = { index, mode ->
                blendMode = mode
                selectedIndex = index
            }
        )
    }
}

/**
 * Clip landscape using transparent bubbles. Depending on blending mode both, src, dst or neither
 * of them are clipped or pixels are transformed.
 */
@Composable
private fun ClipImageWithBlendModeViaAnotherImage() {
    val srcBitmap = ImageBitmap.imageResource(id = R.drawable.moto_run)
    val dstBitmap = ImageBitmap.imageResource(id = R.drawable.dots_transparent)

    var selectedIndex by remember { mutableIntStateOf(5) }
    var blendMode: BlendMode by remember { mutableStateOf(BlendMode.SrcIn) }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width.roundToInt()
        val canvasHeight = size.height.roundToInt()


        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            // Destination
            drawImage(
                image = dstBitmap,
                srcSize = IntSize(dstBitmap.width, dstBitmap.height),
                dstSize = IntSize(canvasWidth, canvasHeight)
            )

            // Source
            drawImage(
                blendMode = blendMode,
                image = srcBitmap,
                srcSize = IntSize(srcBitmap.width, srcBitmap.height),
                dstSize = IntSize(canvasWidth, canvasHeight)
            )

            restoreToCount(checkPoint)
        }
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "Src BlendMode: $blendMode",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        BlendModeSelection(
            modifier = Modifier
                .height(200.dp)
                .verticalScroll(rememberScrollState()),
            selectedIndex = selectedIndex,
            onBlendModeSelected = { index, mode ->
                blendMode = mode
                selectedIndex = index
            }
        )
    }
}

/**
 *
 * Remove circle from transparent rectangle over image using [BlendMode]
 *
 * This example uses [BlendMode.SrcOut] to clip circle path from transparent rectangle over image.
 *
 * Circle that removed from transparent rectangle is drawn as Destination.
 *
 * Transparent rectangle is drawn as Source with Blend Mode.
 */
@Composable
private fun RevealShapeWithBlendMode() {

    val dstBitmap = ImageBitmap.imageResource(id = R.drawable.moto_run)

    var selectedIndex by remember { mutableIntStateOf(7) }
    var blendMode: BlendMode by remember { mutableStateOf(BlendMode.SrcOut) }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width.roundToInt()
        val canvasHeight = size.height.roundToInt()

        drawImage(
            image = dstBitmap,
            srcSize = IntSize(dstBitmap.width, dstBitmap.height),
            dstSize = IntSize(canvasWidth, canvasHeight)
        )

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            // Destination
            drawCircle(
                color = Color.Red,
                radius = 200f,
            )

            // Source
            drawRect(Color(0xaa000000), blendMode = blendMode)

            restoreToCount(checkPoint)
        }
    }

    Text(
        text = "Src BlendMode: $blendMode",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )

    BlendModeSelection(
        modifier = Modifier
            .height(200.dp)
            .verticalScroll(rememberScrollState()),
        selectedIndex = selectedIndex,
        onBlendModeSelected = { index, mode ->
            blendMode = mode
            selectedIndex = index
        }
    )
}

/**
 *
 * Remove circle from transparent rectangle over image using [BlendMode]
 *
 * This example uses [BlendMode.Clear] to clip circle path from transparent rectangle over image.
 *
 * Circle that removed from transparent rectangle is drawn as Source with BlendMode
 */
@Composable
private fun RevealShapeWithBlendMode2() {

    val dstBitmap = ImageBitmap.imageResource(id = R.drawable.img_moto_girl)

    var selectedIndex by remember { mutableStateOf(0) }
    var blendMode: BlendMode by remember { mutableStateOf(BlendMode.Clear) }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width.roundToInt()
        val canvasHeight = size.height.roundToInt()

        drawImage(
            image = dstBitmap,
            srcSize = IntSize(dstBitmap.width, dstBitmap.height),
            dstSize = IntSize(canvasWidth, canvasHeight)
        )

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            // Destination
            drawRect(Color(0xaa000000))

            // Source
            drawCircle(
                color = Color.Transparent,
                radius = 200f,
                blendMode = blendMode
            )
            restoreToCount(checkPoint)
        }
    }

    Text(
        text = "Src BlendMode: $blendMode",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )

    BlendModeSelection(
        modifier = Modifier
            .height(200.dp)
            .verticalScroll(rememberScrollState()),
        selectedIndex = selectedIndex,
        onBlendModeSelected = { index, mode ->
            blendMode = mode
            selectedIndex = index
        }
    )
}

@Composable
private fun ColorAddBlendMode() {

    Canvas(
        modifier = Modifier
            .padding(8.dp)
            .shadow(1.dp)
            .background(Color.White)
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {

        val canvasWidth = size.width
        val canvasHeight = size.height
        val cx = canvasWidth / 2f
        val cy = canvasHeight / 2f
        val r = canvasWidth / 7f

        val tx = (r * cos(30 * Math.PI / 180)).toFloat()
        val ty = (r * sin(30 * Math.PI / 180)).toFloat()
        val expand = 1.5f

        with(drawContext.canvas.nativeCanvas) {
            val checkPoint = saveLayer(null, null)

            drawCircle(
                color = Color.Red,
                radius = expand * r,
                center = Offset(cx, cy - r),
                blendMode = BlendMode.Plus
            )
            drawCircle(
                color = Color.Green,
                radius = expand * r,
                center = Offset(cx - tx, cy + ty),
                blendMode = BlendMode.Plus
            )
            drawCircle(
                color = Color.Blue,
                radius = expand * r,
                center = Offset(cx + tx, cy + ty),
                blendMode = BlendMode.Plus
            )

            restoreToCount(checkPoint)
        }
    }
}

private val canvasModifier = Modifier
    .padding(8.dp)
    .shadow(1.dp)
    .background(Color.White)
    .fillMaxSize()
    .height(200.dp)


@Preview
@Composable
private fun PreviewBlendMode() {
    BlendMode_Screen()
}