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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
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
        Title_Desc_Text(desc = "path.op Stroke Fill，就是演示数学集合中交集、并集、差集、补集等")
        PathOpStrokeFill()
        PathOpStrokeFill(true)
        Title_Desc_Text(desc = "clipPath 根据路径path裁剪")
        ClipPath()
        Title_Desc_Text(desc = "clipRect 根据rect矩形裁剪")
        ClipRect()
    }

}

@Composable
private fun PathOpStrokeFill(fill: Boolean = false) {

    var sides1 by remember { mutableFloatStateOf(5f) }
    var radius1 by remember { mutableFloatStateOf(300f) }

    var sides2 by remember { mutableFloatStateOf(7f) }
    var radius2 by remember { mutableFloatStateOf(300f) }
    //两图形的交互层叠方式
    var operation by remember { mutableStateOf(PathOperation.Difference) }

    val newPath = remember { Path() }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val cx1 = canvasWidth / 3
        val cx2 = canvasWidth * 2 / 3
        val cy = canvasHeight / 2


        val pathA = createPolygonPath(cx1, cy, sides1.roundToInt(), radius1)
        val pathB = createPolygonPath(cx2, cy, sides2.roundToInt(), radius2)
        //绘制A图形
        drawPath(
            color = Color.Red,
            path = pathA,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )
        //绘制B图形
        drawPath(
            color = Color.Blue,
            path = pathB,
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
        newPath.op(pathA, pathB, operation = operation)
        //触发层叠方式计算后的部分，对应的路径path的绘制
        drawPath(
            color = Color.Green,
            path = newPath,
            //style可以是描边，可以是填充
            style = if (fill) Fill else Stroke(
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
            options = listOf(
                "Difference A与B的差集",
                "Intersect B与A的交集",
                "Union 并集",
                "Xor 交集在并集的补集",
                "ReverseDifference B与A的差集"
            ),
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

@Composable
private fun ClipPath() {

    var sides1 by remember { mutableFloatStateOf(5f) }
    var radius1 by remember { mutableFloatStateOf(400f) }

    var sides2 by remember { mutableFloatStateOf(7f) }
    var radius2 by remember { mutableFloatStateOf(300f) }

    var clipOp by remember { mutableStateOf(ClipOp.Difference) }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val cx1 = canvasWidth / 3
        val cx2 = canvasWidth * 2 / 3
        val cy = canvasHeight / 2

        val path1 = createPolygonPath(cx1, cy, sides1.roundToInt(), radius1)
        val path2 = createPolygonPath(cx2, cy, sides2.roundToInt(), radius2)


        // Draw path1 to display it as reference, it's for demonstration
        drawPath(
            color = Color.Red,
            path = path1,
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f))
            )
        )

        // We apply clipPath operation to pah1 and draw after this operation
        /*
            Reduces the clip region to the intersection of the current clip and the given path.
            This method provides a callback to issue drawing commands within the region defined
            by the clipped path. After this method is invoked, this clip is no longer applied
         */
        clipPath(path = path1, clipOp = clipOp) {

            // Draw path1 to display it as reference, it's for demonstration
            drawPath(
                color = Color.Green,
                path = path1,
                style = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 40f))
                )
            )


            // Anything inside this scope will be clipped according to path1 shape
            drawRect(
                color = Color.Yellow,
                topLeft = Offset(100f, 100f),
                size = Size(canvasWidth - 300f, canvasHeight - 300f)
            )

            drawPath(
                color = Color.Blue,
                path = path2
            )

            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(Color.Red, Color.Green, Color.Magenta, Color.Cyan, Color.Yellow)
                ),
                radius = 200f
            )

            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(canvasWidth, canvasHeight),
                strokeWidth = 10f
            )
        }
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {

        ExposedSelectionMenu(title = "Clip Operation",
            index = when (clipOp) {
                ClipOp.Difference -> 0

                else -> 1
            },
            options = listOf("Difference", "Intersect"),
            onSelected = {
                clipOp = when (it) {
                    0 -> ClipOp.Difference
                    else -> ClipOp.Intersect
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

@Composable
private fun ClipRect() {

    var clipOp by remember { mutableStateOf(ClipOp.Difference) }

    Canvas(modifier = canvasModifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height


        drawRect(
            color = Color.Red,
            topLeft = Offset(100f, 80f),
            size = Size(600f, 320f),
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
        )

        /*
            Reduces the clip region to the intersection of the current clip and the
            given rectangle indicated by the given left, top, right and bottom bounds.
            This provides a callback to issue drawing commands within the clipped region.
            After this method is invoked, this clip is no longer applied.
         */
        clipRect(left = 100f, top = 80f, right = 700f, bottom = 400f, clipOp = clipOp) {

            drawCircle(
                center = Offset(canvasWidth / 2 + 100, +canvasHeight / 2 + 50),
                brush = Brush.sweepGradient(
                    center = Offset(canvasWidth / 2 + 100, +canvasHeight / 2 + 50),
                    colors = listOf(Color.Red, Color.Green, Color.Magenta, Color.Cyan, Color.Yellow)
                ),
                radius = 300f
            )

            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(canvasWidth, canvasHeight),
                strokeWidth = 10f
            )
        }
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {

        ExposedSelectionMenu(title = "Clip Operation",
            index = when (clipOp) {
                ClipOp.Difference -> 0

                else -> 1
            },
            options = listOf("Difference", "Intersect"),
            onSelected = {
                clipOp = when (it) {
                    0 -> ClipOp.Difference
                    else -> ClipOp.Intersect
                }
            }
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