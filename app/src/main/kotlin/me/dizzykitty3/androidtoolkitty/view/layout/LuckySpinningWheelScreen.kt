package me.dizzykitty3.androidtoolkitty.view.layout

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCardNoIcon
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomScreen
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun LuckySpinningWheelScreen() {
    CustomScreen {
        CustomCardNoIcon(title = "Lucky spinning wheel") {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                var selectedItem by remember { mutableStateOf("") }
                var isSpinning by remember { mutableStateOf(false) }
                val items = listOf("项目1", "项目2", "项目3", "项目4", "项目5")
                SpinningWheel(
                    items = items,
                    isSpinning = isSpinning,
                    onItemSelected = { item ->
                        selectedItem = item
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { isSpinning = !isSpinning }) {
                    Text(if (isSpinning) "停止" else "旋转")
                }
                if (selectedItem.isNotEmpty()) {
                    Text(
                        "选中的项目是：$selectedItem",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun SpinningWheel(
    items: List<String>,
    isSpinning: Boolean,
    onItemSelected: (String) -> Unit
) {
    val angle = remember { Animatable(0f) }

    LaunchedEffect(isSpinning) {
        if (isSpinning) {
            angle.animateTo(
                targetValue = angle.value + 360f * (5..10).random(),
                animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
            )
        } else {
            onItemSelected(items[((angle.value / 360f * items.size).toInt() + items.size) % items.size])
        }
    }

    Canvas(modifier = Modifier.size(300.dp)) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)
        val anglePerItem = 360f / items.size
        val textRadius = radius * 0.7f // 文本离中心点的距离

        for (i in items.indices) {
            val startAngle = anglePerItem * i + angle.value
            drawArc(
                color = if (i % 2 == 0) Color.Green else Color.Blue,
                startAngle = startAngle,
                sweepAngle = anglePerItem,
                useCenter = true,
                size = size
            )

            // 旋转文本绘制角度以使文本水平显示
            val textAngleRadians =
                Math.toRadians((startAngle + anglePerItem / 2).toDouble()).toFloat()
            val textX = center.x + textRadius * cos(textAngleRadians)
            val textY = center.y + textRadius * sin(textAngleRadians)
            drawContext.canvas.nativeCanvas.drawText(
                items[i],
                textX,
                textY,
                Paint().apply {
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 36f
                    color = android.graphics.Color.WHITE
                }
            )
        }
    }
}