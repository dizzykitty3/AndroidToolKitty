package me.dizzykitty3.androidtoolkitty.foundation.ui_component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider

@Composable
fun CustomIconPopup(
    deviceType: String,
    deviceAddress: String
) {
    var showPopup by remember { mutableStateOf(false) }

    Row {
        Box {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                modifier = Modifier
                    .size(16.dp)
                    .clickable { showPopup = true },
                tint = Color.DarkGray
            )

            if (showPopup) {
                Popup(
                    onDismissRequest = { showPopup = false },
                    popupPositionProvider = object : PopupPositionProvider {
                        override fun calculatePosition(
                            anchorBounds: IntRect,
                            windowSize: IntSize,
                            layoutDirection: LayoutDirection,
                            popupContentSize: IntSize
                        ): IntOffset {
                            val positionAbove = anchorBounds.top - popupContentSize.height

                            return if (positionAbove >= 0) {
                                IntOffset(anchorBounds.left, positionAbove)
                            } else {
                                IntOffset(anchorBounds.left, anchorBounds.bottom)
                            }
                        }
                    }
                ) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            text = ("$deviceType, $deviceAddress"),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}