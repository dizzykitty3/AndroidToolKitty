package me.dizzykitty3.androidtoolkitty.view.card

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
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
        // 定义项目列表
        var items by remember { mutableStateOf(listOf("条目1", "条目2", "条目3", "条目4", "条目5", "条目6")) }

        val context = LocalContext.current
        val paint = remember {
            android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = 40f
            }
        }
        var hasRotated by remember { mutableStateOf(false) }

        // 颜色设置
        val primaryColor = MaterialTheme.colorScheme.primary
        val secondaryColor = MaterialTheme.colorScheme.secondary
        val tertiaryColor = MaterialTheme.colorScheme.tertiary
        val colors = listOf(primaryColor, secondaryColor, tertiaryColor)

        var rotationDegrees by remember { mutableFloatStateOf(0f) }
        var targetRotationDegrees by remember { mutableFloatStateOf(0f) }
        val currentRotationDegrees by animateFloatAsState(
            targetValue = targetRotationDegrees,
            animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing), label = ""

        )

        // 计算指向元素
        LaunchedEffect(currentRotationDegrees) {
            if (currentRotationDegrees == targetRotationDegrees && hasRotated) {
                val normalizedRotationDegrees = targetRotationDegrees % 360
                val arrowAngle = 270
                val itemsCount = items.size
                val anglePerItem = 360f / itemsCount
                val selectedIndex =
                    (((360 - normalizedRotationDegrees + arrowAngle) % 360) / anglePerItem).toInt() % itemsCount
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
                    val colorIndex = index % 3
                    val color = colors[colorIndex]
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

                // 绘制文字
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

                // 绘制箭头
                val path = Path().apply {
                    moveTo(center.x, center.y - radius - 15) // 此处假设“radius + 15”足以将箭头放在转盘外侧
                    lineTo(center.x - 10, center.y - radius - 30) // 箭头底部的一个角，稍微更远离圆心
                    lineTo(center.x + 10, center.y - radius - 30) // 箭头底部的另一个角
                    close()
                }
                drawPath(path, primaryColor)
            }

            // 底部按钮
            Button(onClick = {
                hasRotated = true
                val randomBaseCircles = 3
                val fineTunedAngle = Random.nextInt(360)
                targetRotationDegrees += (360 * randomBaseCircles) + fineTunedAngle
            }) {
                Text(text = "开始旋转")
            }
            ExpandableList(
                items = items,
                onItemsChange = { updatedItems -> items = updatedItems }
            )
        }
    }
}

@Composable
fun ExpandableList(items: List<String>, onItemsChange: (List<String>) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    // 用于追踪哪个元素正在被编辑
    var editingIndex by remember { mutableIntStateOf(-1) }
    // 编辑中的文本
    val editingText = remember { mutableStateListOf<String>().also { list -> items.forEach { list.add(it) } } }

    Column(modifier = Modifier.padding(16.dp)) {
        // 折叠/展开的横条
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFEEEEEE))
                .padding(8.dp)
        ) {
            Text(
                text = items.joinToString(", "),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f) // 让文本组件使用可用空间，但留下足够的空间给箭头图标
            )
            Icon(
                imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowRight,
                contentDescription = "Expand",
                modifier = Modifier.padding(start = 8.dp) // 为箭头和文本之间提供一些空间
            )
        }

        // 当折叠状态为展开时显示的列表
        AnimatedVisibility(visible = expanded) {
            Column {
                LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                    itemsIndexed(items) { index, item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFEEEEEE))
                                .padding(8.dp)
                        ) {
                            if (editingIndex == index) {
                                TextField(
                                    value = editingText[index],
                                    onValueChange = { editingText[index] = it },
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Done",
                                    modifier = Modifier.clickable {
                                        val updatedList = items.toMutableList().also { it[index] = editingText[index] }
                                        onItemsChange(updatedList)
                                        editingIndex = -1 // 结束编辑状态
                                    }
                                )
                            } else {
                                Text(
                                    text = item,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            editingIndex = index // 进入编辑状态
                                        }
                                )
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Remove",
                                    modifier = Modifier.clickable {
                                        val newList = items.toMutableList().apply { removeAt(index) }
                                        onItemsChange(newList)
                                        if (index == editingIndex) editingIndex = -1 // 如果删除的是正在编辑的元素
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp)) // 添加一点间距
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)) // 应用圆角矩形
                        .background(Color(0xFFEEEEEE)) // 设置背景色
                        .clickable {
                            val newItem = "新条目${items.size + 1}" // 创建新项目的文本
                            val updatedItems = items + newItem // 将新项目添加到当前项目列表中
                            onItemsChange(updatedItems) // 通过回调更新上层组件的items列表
                        }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center // 图标居中
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(24.dp) // 设置图标大小
                    )
                }
            }
        }
    }
}


