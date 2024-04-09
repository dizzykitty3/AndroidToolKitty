package me.dizzykitty3.androidtoolkitty.foundation.ui_component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider

@Composable
fun CustomIconPopup(
    text: String
) {
    var showPopup by remember { mutableStateOf(false) }
    val buttonRef = remember { mutableStateOf<Size?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { showPopup = true },
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    buttonRef.value = coordinates.size.toSize()
                }
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                modifier = Modifier.size(16.dp)
            )
        }

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
                        text = text,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}