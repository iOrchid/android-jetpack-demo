package org.zhiwei.compose.screen.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.zhiwei.compose.ui.widget.Title_Desc_Text
import org.zhiwei.compose.ui.widget.Title_Sub_Text
import org.zhiwei.compose.ui.widget.Title_Text
import kotlin.math.roundToInt

@Composable
internal fun CanvasPathOperations_Screen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Title_Text(title = "Clip Path/Rect")
        Title_Sub_Text(title = "示例演示path的操作符，DrawScope的clipPath/clipRect使用不同的交互模式而形成不同的UI显示效果")
        Title_Desc_Text(desc = "path.op Stroke")
        PathOpStroke()
    }

}

@Composable
private fun PathOpStroke() {

    var sides1 by remember { mutableFloatStateOf(5f) }
    var radius1 by remember { mutableFloatStateOf(300f) }

    var sides2 by remember { mutableFloatStateOf(7f) }
    var radius2 by remember { mutableFloatStateOf(300f) }

    var operation by remember { mutableStateOf(PathOperation.Difference) }

    val newPath = remember { Path() }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val cx1 = canvasWidth / 3
        val cx2 = canvasWidth * 2 / 3
        val cy = canvasHeight / 2


        val path1 = createPolygonPath(cx1, cy, sides1.roundToInt(), radius1)
        val path2 = createPolygonPath(cx2, cy, sides2.roundToInt(), radius2)

        drawPath(
            color = Color.Red,
            path = path1,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )

        drawPath(
            color = Color.Blue,
            path = path2,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )

        // We apply operation to path1 and path2 and setting this new path to our newPath
        /*
            Set this path to the result of applying the Op to the two specified paths.
            The resulting path will be constructed from non-overlapping contours.
            The curve order is reduced where possible so that cubics may be turned into quadratics,
            and quadratics maybe turned into lines.
         */
        newPath.op(path1, path2, operation = operation)

        drawPath(
            color = Color.Green,
            path = newPath,
            style = Stroke(
                width = 4.dp.toPx(),
            )
        )
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {

        ExposedSelectionMenu(title = "Path Operation",
            index = when (operation) {
                PathOperation.Difference -> 0
                PathOperation.Intersect -> 1
                PathOperation.Union -> 2
                PathOperation.Xor -> 3
                else -> 4
            },
            options = listOf("Difference", "Intersect", "Union", "Xor", "ReverseDifference"),
            onSelected = {
                operation = when (it) {
                    0 -> PathOperation.Difference
                    1 -> PathOperation.Intersect
                    2 -> PathOperation.Union
                    3 -> PathOperation.Xor
                    else -> PathOperation.ReverseDifference
                }
            }
        )

        Text(text = "Sides left: ${sides1.roundToInt()}")
        Slider(
            value = sides1,
            onValueChange = { sides1 = it },
            valueRange = 3f..12f,
            steps = 10
        )
        Text(text = "radius left: ${radius1.roundToInt()}")
        Slider(
            value = radius1,
            onValueChange = { radius1 = it },
            valueRange = 100f..500f
        )

        Text(text = "Sides right: ${sides2.roundToInt()}")
        Slider(
            value = sides2,
            onValueChange = { sides2 = it },
            valueRange = 3f..12f,
            steps = 10
        )
        Text(text = "radius right: ${radius2.roundToInt()}")
        Slider(
            value = radius2,
            onValueChange = { radius2 = it },
            valueRange = 100f..500f
        )
    }
}


private val canvasModifier = Modifier
    .padding(8.dp)
    .shadow(1.dp)
    .background(Color.White)
    .fillMaxSize()
    .clipToBounds()
    .height(300.dp)

@Preview
@Composable
private fun PreviewCanvasPathOps() {
    CanvasPathOperations_Screen()
}