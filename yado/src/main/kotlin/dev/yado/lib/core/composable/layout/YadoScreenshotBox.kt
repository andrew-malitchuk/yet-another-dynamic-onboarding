package dev.yado.lib.core.composable.layout

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import dev.yado.lib.core.ext.screenshot
import dev.yado.lib.source.state.YadoScreenshotState

/**
 * A composable function that provides a box with screenshot capabilities, allowing you to capture
 * a bitmap of its content. It integrates with a [YadoScreenshotState] to manage the screenshot lifecycle
 * and the resulting bitmap.
 *
 * @param modifier A [Modifier] applied to the `Box` composable, allowing customization of its appearance and behavior.
 * @param yadoScreenshotState An instance of [YadoScreenshotState] that manages the screenshot capturing logic
 * and stores the resulting bitmap.
 * @param content A composable lambda representing the content to be displayed inside the `Box`.
 */
@Composable
fun YadoScreenshotBox(
    modifier: Modifier = Modifier,
    yadoScreenshotState: YadoScreenshotState,
    content: @Composable () -> Unit,
) {
    val view: View = LocalView.current

    var composableBounds by remember {
        mutableStateOf<Rect?>(null)
    }

    DisposableEffect(Unit) {
        with(yadoScreenshotState) {
            // Set the callback to capture the screenshot when invoked
            callback = {
                composableBounds?.let { bounds ->
                    if (bounds.width == 0f || bounds.height == 0f) return@let
                    view.screenshot(bounds) {
                        bitmapState.value = it
                    }
                }
            }

            // Cleanup logic for the bitmap and callback
            onDispose {
                val bmp = bitmapState.value?.getOrNull()
                bmp?.apply {
                    if (!isRecycled) {
                        recycle()
                    }
                }
                bitmapState.value = null
                callback = null
            }
        }
    }

    Box(
        modifier =
            modifier
                .onGloballyPositioned {
                    // Update composable bounds whenever the position changes
                    composableBounds = it.boundsInWindow()
                },
    ) {
        content()
    }
}
