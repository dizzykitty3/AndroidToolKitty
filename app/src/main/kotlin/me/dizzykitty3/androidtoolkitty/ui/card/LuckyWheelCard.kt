package me.dizzykitty3.androidtoolkitty.ui.card

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Casino
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref.getLuckySpinningWheelItems
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref.setLuckySpinningWheelItems
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.util.ToastUtil
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun LuckyWheelCard() {
    // 使用CustomCard布局展示幸运轮盘
    CustomCard(
        icon = Icons.Outlined.Casino,
        title = R.string.lucky_wheel
    ) {
        val baseItem = stringResource(R.string.item)
        // 初始化轮盘项目列表
        var items by remember {
            mutableStateOf(
                getLuckySpinningWheelItems() ?: List(4) { index -> "$baseItem${index + 1}" }
            )
        }

        // 记住画笔设置，避免每次绘制时重新创建
        val paint = remember {
            android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = 40f
            }
        }
        var hasRotated by remember { mutableStateOf(false) }

        // Material Design颜色主题
        val colors = List(2) { index ->
            when (index) {
                0 -> MaterialTheme.colorScheme.primaryContainer
                else -> MaterialTheme.colorScheme.secondaryContainer
            }
        }

        // 记忆旋转度数，用于控制动画
        var rotationDegrees by remember { mutableFloatStateOf(0f) }
        var targetRotationDegrees by remember { mutableFloatStateOf(0f) }
        // 动画状态管理
        val currentRotationDegrees by animateFloatAsState(
            targetValue = targetRotationDegrees,
            animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing), label = "",
        )

        val baseSelected = stringResource(R.string.selected)
        // 当动画结束时，计算并显示选中的项目
        LaunchedEffect(currentRotationDegrees) {
            if (currentRotationDegrees == targetRotationDegrees && hasRotated) {
                val normalizedRotationDegrees = targetRotationDegrees % 360
                val itemsCount = items.size
                val anglePerItem = 360f / itemsCount
                val selectedIndex =
                    (((360 - normalizedRotationDegrees + 270) % 360) / anglePerItem).toInt() % itemsCount
                val selected = items[selectedIndex]

                ToastUtil.toast("$baseSelected: $selected")
                rotationDegrees = targetRotationDegrees % 360
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val primary = MaterialTheme.colorScheme.primary

            // 绘制转盘
            Canvas(modifier = Modifier.size(250.dp)) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2
                items.indices.forEach { index ->
                    val startAngle = (360f / items.size * index + currentRotationDegrees) % 360
                    val sweepAngle = 360f / items.size
                    val color = colors[index % colors.size]
                    drawArc(
                        color = color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        size = size,
                        topLeft = Offset(center.x - radius, center.y - radius)
                    )

                    // 绘制分隔线
                    val endAngleRad = Math.toRadians((startAngle + sweepAngle).toDouble()).toFloat()
                    val lineEnd = Offset(
                        center.x + radius * cos(endAngleRad),
                        center.y + radius * sin(endAngleRad)
                    )
                    drawLine(
                        color = Color.Black,
                        start = center,
                        end = lineEnd,
                        strokeWidth = 2f
                    )
                }

                // 绘制项目文本
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

                // 绘制指示箭头
                val arrowPath = Path().apply {
                    moveTo(center.x, center.y - radius - 15)
                    lineTo(center.x - 10, center.y - radius - 30)
                    lineTo(center.x + 10, center.y - radius - 30)
                    close()
                }
                drawPath(arrowPath, primary)

                // 绘制圆盘外边缘
                drawCircle(
                    color = Color.Black,
                    radius = radius,
                    center = center,
                    style = Stroke(width = 4f)
                )
            }

            // 旋转按钮
            Button(onClick = {
                if (items.isNotEmpty()) {
                    hasRotated = true
                    val randomBaseCircles = 3
                    val fineTunedAngle = Random.nextInt(360)
                    targetRotationDegrees += (360 * randomBaseCircles) + fineTunedAngle
                }
            }) {
                Text(text = stringResource(R.string.start_spinning))
            }
            // 可扩展列表，用于显示和修改项目列表
            ExpandableList(
                items = items,
                onItemsChange = { updatedItems ->
                    items = updatedItems
                    setLuckySpinningWheelItems(updatedItems)
                }
            )
        }
    }
}

@Composable
fun ExpandableList(items: List<String>, onItemsChange: (List<String>) -> Unit) {
    // 标记列表是否展开
    var expanded by remember { mutableStateOf(false) }
    // 正在编辑的元素索引，-1表示没有元素处于编辑状态
    var editingIndex by remember { mutableIntStateOf(-1) }
    // 编辑中的文本列表，用于暂存编辑时的文本更改
    val editingText =
        remember { mutableStateListOf<String>().also { list -> items.forEach { list.add(it) } } }
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // 当items更新时，同时更新编辑中的文本列表
    LaunchedEffect(items) {
        editingText.clear()
        editingText.addAll(items)
    }

    // 当editingIndex或items的大小改变时，触发滚动到最新条目的逻辑
    LaunchedEffect(editingIndex, items.size) {
        if (editingIndex >= 0) {
            listState.animateScrollToItem(index = editingIndex)
            keyboardController?.show()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // 折叠/展开的头部，点击切换展开状态
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
        ) {
            Text(
                text = items.joinToString(", "),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = "Expand",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // 展开状态下显示的可编辑列表
        AnimatedVisibility(visible = expanded) {
            val baseItem = stringResource(R.string.item)
            Column {
                LazyColumn(state = listState, modifier = Modifier.heightIn(max = 200.dp)) {
                    itemsIndexed(items) { index, item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(8.dp)
                        ) {
                            // 编辑状态显示输入框，否则显示文本
                            if (editingIndex == index) {
                                TextField(
                                    value = editingText[index],
                                    onValueChange = { editingText[index] = it },
                                    modifier = Modifier.weight(1f),
                                    singleLine = true
                                )
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Done",
                                    modifier = Modifier.clickable {
                                        val updatedList = items.toMutableList()
                                            .also { it[index] = editingText[index] }
                                        onItemsChange(updatedList)
                                        editingIndex = -1
                                    }
                                )
                            } else {
                                Text(
                                    text = item,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            editingIndex = index
                                        }
                                )
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Remove",
                                    modifier = Modifier.clickable {
                                        val newList =
                                            items.toMutableList().apply { removeAt(index) }
                                        onItemsChange(newList)
                                        // 重置或调整编辑索引
                                        if (index == editingIndex) {
                                            editingIndex = -1
                                        } else if (index < editingIndex) {
                                            editingIndex -= 1
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                // 添加新元素的按钮
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            val newItem = "$baseItem${items.size + 1}"
                            val updatedItems = items + newItem
                            onItemsChange(updatedItems)
                            editingText.add(newItem)
                            editingIndex = items.size // 设置新添加的条目为编辑状态
                        }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}