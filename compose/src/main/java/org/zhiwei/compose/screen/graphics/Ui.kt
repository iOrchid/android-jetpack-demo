package org.zhiwei.compose.screen.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.zhiwei.compose.R
import kotlin.math.roundToInt

/**
 * Expandable selection menu
 * @param title of the displayed item on top
 * @param index index of selected item
 * @param options list of [String] options
 * @param onSelected lambda to be invoked when an item is selected that returns
 * its index.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ExposedSelectionMenu(
    title: String,
    index: Int,
    options: List<String>,
    onSelected: (Int) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[index]) }
    //目前material3的ExposedDropdownMenuBox使用有Bug无法弹窗，所以暂时用material 1.6.7的
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = expanded.not() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        TextField(
            value = selectedOptionText,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(),
        ) {
            options.forEachIndexed { index: Int, selectionOption: String ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onSelected(index)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun DrawingControl(
    modifier: Modifier = Modifier,
    pathOption: PathOption,
    eraseModeOn: Boolean,
    onEraseModeChange: (Boolean) -> Unit,
) {

    var showColorDialog by remember { mutableStateOf(false) }
    var showPropertiesDialog by remember { mutableStateOf(false) }
    var eraseMode = eraseModeOn

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {
                eraseMode = !eraseMode
                onEraseModeChange(eraseMode)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_car_light),
                contentDescription = null,
                tint = if (eraseMode) Color.Black else Color.LightGray
            )
        }
        IconButton(onClick = { showColorDialog = !showColorDialog }) {
            Icon(Icons.Filled.ColorLens, contentDescription = null, tint = Color.LightGray)
        }

        IconButton(onClick = { showPropertiesDialog = !showPropertiesDialog }) {
            Icon(Icons.Filled.BorderColor, contentDescription = null, tint = Color.LightGray)
        }

        Canvas(
            modifier = Modifier
                .height(10.dp)
                .width(100.dp)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            val path = Path()
            path.moveTo(0f, size.height / 2)
            path.lineTo(size.width, size.height / 2)

            drawPath(
                color = pathOption.color,
                path = path,
                style = Stroke(
                    width = pathOption.strokeWidth,
                    cap = pathOption.strokeCap,
                    join = pathOption.strokeJoin
                )
            )
        }
    }

    if (showColorDialog) {
        ColorSelectionDialog(
            pathOption.color,
            onDismiss = { showColorDialog = !showColorDialog },
            onNegativeClick = { showColorDialog = !showColorDialog },
            onPositiveClick = { color: Color ->
                showColorDialog = !showColorDialog
                pathOption.color = color
            }
        )
    }

    if (showPropertiesDialog) {
        DrawingMenuDialog(pathOption) {
            showPropertiesDialog = !showPropertiesDialog
        }
    }
}

@Composable
fun DrawingControlExtended(
    modifier: Modifier = Modifier,
    pathOption: PathOption,
    drawMode: DrawMode,
    onDrawModeChanged: (DrawMode) -> Unit,
) {

    var showColorDialog by remember { mutableStateOf(false) }
    var showPropertiesDialog by remember { mutableStateOf(false) }
    var currentDrawMode = drawMode

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {
                currentDrawMode = if (currentDrawMode == DrawMode.Touch) {
                    DrawMode.Draw
                } else {
                    DrawMode.Touch
                }
                onDrawModeChanged(currentDrawMode)
            }
        ) {
            Icon(
                Icons.Filled.TouchApp,
                contentDescription = null,
                tint = if (currentDrawMode == DrawMode.Touch) Color.Black else Color.LightGray
            )
        }
        IconButton(
            onClick = {
                currentDrawMode = if (currentDrawMode == DrawMode.Erase) {
                    DrawMode.Draw
                } else {
                    DrawMode.Erase
                }
                onDrawModeChanged(currentDrawMode)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_car_light),
                contentDescription = null,
                tint = if (currentDrawMode == DrawMode.Erase) Color.Black else Color.LightGray
            )
        }
        IconButton(onClick = { showColorDialog = !showColorDialog }) {
            Icon(Icons.Filled.ColorLens, contentDescription = null, tint = Color.LightGray)
        }

        IconButton(onClick = { showPropertiesDialog = !showPropertiesDialog }) {
            Icon(Icons.Filled.BorderColor, contentDescription = null, tint = Color.LightGray)
        }

        Canvas(
            modifier = Modifier
                .height(10.dp)
                .width(100.dp)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            val path = Path()
            path.moveTo(0f, size.height / 2)
            path.lineTo(size.width, size.height / 2)

            drawPath(
                color = pathOption.color,
                path = path,
                style = Stroke(
                    width = pathOption.strokeWidth,
                    cap = pathOption.strokeCap,
                    join = pathOption.strokeJoin
                )
            )
        }
    }

    if (showColorDialog) {
        ColorSelectionDialog(
            pathOption.color,
            onDismiss = { showColorDialog = !showColorDialog },
            onNegativeClick = { showColorDialog = !showColorDialog },
            onPositiveClick = { color: Color ->
                showColorDialog = !showColorDialog
                pathOption.color = color
            }
        )
    }

    if (showPropertiesDialog) {
        DrawingMenuDialog(pathOption) {
            showPropertiesDialog = !showPropertiesDialog
        }
    }
}


@Composable
fun ColorSelectionDialog(
    initialColor: Color,
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Color) -> Unit,
) {
    var red by remember { mutableStateOf(initialColor.red * 255) }
    var green by remember { mutableStateOf(initialColor.green * 255) }
    var blue by remember { mutableStateOf(initialColor.blue * 255) }
    var alpha by remember { mutableStateOf(initialColor.alpha * 255) }

    val color = Color(
        red = red.roundToInt(),
        green = green.roundToInt(),
        blue = blue.roundToInt(),
        alpha = alpha.roundToInt()
    )

    Dialog(onDismissRequest = onDismiss) {

        BoxWithConstraints(
            Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {

            val widthInDp = LocalDensity.current.run { maxWidth }


            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Color",
                    color = Color.Cyan,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp)
                )

                // Initial and Current Colors
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp, vertical = 20.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                initialColor,
                                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                color,
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                            )
                    )
                }

                ColorWheel(
                    modifier = Modifier
                        .width(widthInDp * .8f)
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sliders
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Red",
                    titleColor = Color.Red,
                    rgb = red,
                    onColorChanged = {
                        red = it
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Green",
                    titleColor = Color.Green,
                    rgb = green,
                    onColorChanged = {
                        green = it
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))

                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Blue",
                    titleColor = Color.Blue,
                    rgb = blue,
                    onColorChanged = {
                        blue = it
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Alpha",
                    titleColor = Color.Black,
                    rgb = alpha,
                    onColorChanged = {
                        alpha = it
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Buttons

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xffF3E5F5)),
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    TextButton(
                        onClick = onNegativeClick,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Text(text = "CANCEL")
                    }
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = {
                            onPositiveClick(color)
                        },
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}


@Composable
fun DrawingMenuDialog(pathOption: PathOption, onDismiss: () -> Unit) {

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = CardDefaults.cardElevation(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = "Stroke Width ${pathOption.strokeWidth.toInt()}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Slider(
                    value = pathOption.strokeWidth,
                    onValueChange = { pathOption.strokeWidth = it },
                    valueRange = 1f..100f,
                    onValueChangeFinished = {}
                )


                ExposedSelectionMenu(title = "Stroke Cap",
                    index = when (pathOption.strokeCap) {
                        StrokeCap.Butt -> 0
                        StrokeCap.Round -> 1
                        else -> 2
                    },
                    options = listOf("Butt", "Round", "Square"),
                    onSelected = {
                        println("STOKE CAP $it")
                        pathOption.strokeCap = when (it) {
                            0 -> StrokeCap.Butt
                            1 -> StrokeCap.Round
                            else -> StrokeCap.Square
                        }
                    }
                )

                ExposedSelectionMenu(title = "Stroke Join",
                    index = when (pathOption.strokeJoin) {
                        StrokeJoin.Miter -> 0
                        StrokeJoin.Round -> 1
                        else -> 2
                    },
                    options = listOf("Miter", "Round", "Bevel"),
                    onSelected = {
                        println("STOKE JOIN $it")

                        pathOption.strokeJoin = when (it) {
                            0 -> StrokeJoin.Miter
                            1 -> StrokeJoin.Round
                            else -> StrokeJoin.Bevel
                        }
                    }
                )
            }
        }
    }
}

/**
 * Simple circle with stroke to show rainbow colors as [Brush.sweepGradient]
 */
@Composable
fun ColorWheel(modifier: Modifier = Modifier) {

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        require(canvasWidth == canvasHeight,
            lazyMessage = {
                print("Canvas dimensions should be equal to each other")
            }
        )
        val cX = canvasWidth / 2
        val cY = canvasHeight / 2
        val canvasRadius = canvasWidth.coerceAtMost(canvasHeight) / 2f
        val center = Offset(cX, cY)
        val strokeWidth = canvasRadius * .3f
        // Stroke is drawn out of the radius, so it's required to subtract stroke width from radius
        val radius = canvasRadius - strokeWidth

        drawCircle(
            brush = Brush.sweepGradient(
                colors = listOf(
                    Color.Green,
                    Color.Cyan,
                    Color.Red,
                    Color.Blue,
                    Color.Yellow,
                    Color.Magenta,
                ), center = center
            ),
            radius = radius,
            center = center,
            style = Stroke(
                width = strokeWidth
            )
        )
    }
}

class PathOption(
    strokeWidth: Float = 10f,
    color: Color = Color.Black,
    strokeCap: StrokeCap = StrokeCap.Round,
    strokeJoin: StrokeJoin = StrokeJoin.Round,
) {
    var strokeWidth by mutableFloatStateOf(strokeWidth)
    var color by mutableStateOf(color)
    var strokeCap by mutableStateOf(strokeCap)
    var strokeJoin by mutableStateOf(strokeJoin)
    var eraseMode = false
}

@Composable
fun ColorSlider(
    modifier: Modifier,
    title: String,
    titleColor: Color,
    valueRange: ClosedFloatingPointRange<Float> = 0f..255f,
    rgb: Float,
    onColorChanged: (Float) -> Unit,
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {

        androidx.compose.material.Text(
            text = title.substring(0, 1),
            color = titleColor,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        androidx.compose.material.Slider(
            modifier = Modifier.weight(1f),
            value = rgb,
            onValueChange = { onColorChanged(it) },
            valueRange = valueRange,
            onValueChangeFinished = {}
        )

        Spacer(modifier = Modifier.width(8.dp))
        androidx.compose.material.Text(
            text = rgb.toInt().toString(),
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.width(30.dp)
        )

    }
}

enum class DrawMode {
    Draw, Touch, Erase
}


@Composable
fun CustomDialogWithResultExample(
    initialColor: Color,
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Color) -> Unit,
) {
    var red by remember { mutableFloatStateOf(initialColor.red * 255) }
    var green by remember { mutableFloatStateOf(initialColor.green * 255) }
    var blue by remember { mutableFloatStateOf(initialColor.blue * 255) }

    val color = Color(
        red = red.toInt(),
        green = green.toInt(),
        blue = blue.toInt(),
        alpha = 255
    )

    Dialog(onDismissRequest = onDismiss) {

        BoxWithConstraints(
            Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {

            val widthInDp = LocalDensity.current.run { maxWidth }


            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                androidx.compose.material.Text(
                    text = "Color",
                    color = Color.Cyan,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp)
                )

                // Initial and Current Colors
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp, vertical = 20.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                initialColor,
                                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                            )
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                color,
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                            )
                    )
                }

                ColorWheel(
                    modifier = Modifier
                        .width(widthInDp * .8f)
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sliders
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Red",
                    titleColor = Color.Red,
                    rgb = red,
                    onColorChanged = {
                        red = it
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Green",
                    titleColor = Color.Green,
                    rgb = green,
                    onColorChanged = {
                        green = it
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))

                ColorSlider(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth(),
                    title = "Blue",
                    titleColor = Color.Blue,
                    rgb = blue,
                    onColorChanged = {
                        blue = it
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Buttons

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xffF3E5F5)),
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    androidx.compose.material.TextButton(
                        onClick = onNegativeClick,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        androidx.compose.material.Text(text = "CANCEL")
                    }
                    androidx.compose.material.TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = {
                            onPositiveClick(color)
                        },
                    ) {
                        androidx.compose.material.Text(text = "OK")
                    }
                }
            }
        }
    }
}

val gradientColors = listOf(Color.Red, Color.Green, Color.Magenta, Color.Cyan, Color.Yellow)