package me.dizzykitty3.androidtoolkitty.view.card

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomCard
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun LuckyWheelCard() {
    CustomCard(
        icon = Icons.AutoMirrored.Outlined.MenuBook,
        title = R.string.lucky_spinning_wheel
    ) {
        val context = LocalContext.current
        val paint = remember {
            android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = 40f
            }
        }
        var hasRotated by remember { mutableStateOf(false) }

        val primaryColor = MaterialTheme.colorScheme.primary
        val secondaryColor = MaterialTheme.colorScheme.secondary

        // 定义项目列表
        val items = listOf("条目1", "条目2", "条目3", "条目4", "条目5", "条目6")
        var rotationDegrees by remember { mutableFloatStateOf(0f) }
        var targetRotationDegrees by remember { mutableFloatStateOf(0f) }

        val currentRotationDegrees by animateFloatAsState(
            targetValue = targetRotationDegrees,
            animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing), label = ""

        )

        LaunchedEffect(currentRotationDegrees) {
            if (currentRotationDegrees == targetRotationDegrees && hasRotated) {
                val normalizedRotationDegrees = targetRotationDegrees % 360
                val arrowAngle = 270
                val itemsCount = items.size
                val anglePerItem = 360f / itemsCount
                val selectedIndex = (((360 - normalizedRotationDegrees + arrowAngle) % 360) / anglePerItem).toInt() % itemsCount
                val selected = items[selectedIndex]

                Toast.makeText(context, "Selected: $selected", Toast.LENGTH_SHORT).show()
                rotationDegrees = targetRotationDegrees % 360
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.size(300.dp)) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2
                items.indices.forEach { index ->
                    val startAngle = (360f / items.size * index + currentRotationDegrees) % 360
                    val sweepAngle = 360f / items.size
                    val color =
                        if (index % 2 == 0) secondaryColor else primaryColor
                    drawArc(
                        color = color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        size = size,
                        topLeft = Offset(center.x - radius, center.y - radius)
                    )
                    // 计算并绘制分隔线
                    val endAngleRad = Math.toRadians((startAngle + sweepAngle).toDouble()).toFloat()
                    val lineStart = center
                    val lineEnd = Offset(
                        center.x + radius * cos(endAngleRad),
                        center.y + radius * sin(endAngleRad)
                    )
                    drawLine(
                        color = androidx.compose.ui.graphics.Color.Black,
                        start = lineStart,
                        end = lineEnd,
                        strokeWidth = 2f // 根据需要调整分隔线的宽度
                    )
                }

                items.forEachIndexed { index, item ->
                    val textAngleRad =
                        Math.toRadians((360f / items.size * index + currentRotationDegrees + 360f / items.size / 2) % 360.toDouble())
                            .toFloat()
                    val textRadius = radius * 0.7f
                    drawContext.canvas.nativeCanvas.drawText(
                        item,
                        center.x + textRadius * cos(textAngleRad),
                        center.y + textRadius * sin(textAngleRad),
                        paint
                    )
                }

                val path = Path().apply {
                    moveTo(center.x, center.y - radius - 15)
                    lineTo(center.x - 10, center.y - radius)
                    lineTo(center.x + 10, center.y - radius)
                    close()
                }
                drawPath(path, androidx.compose.ui.graphics.Color.Red)
            }

            Button(onClick = {
                hasRotated = true
                val randomBaseCircles = 3
                val fineTunedAngle = Random.nextInt(360)
                targetRotationDegrees += (360 * randomBaseCircles) + fineTunedAngle
            }) {
                Text(text = "开始旋转")
            }

        }
    }
}
