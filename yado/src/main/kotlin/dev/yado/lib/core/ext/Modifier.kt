package dev.yado.lib.core.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import dev.yado.lib.core.model.YadoLocation

/**
 * An extension function for [Modifier] that determines whether a composable is positioned
 * on the top half of the screen and invokes a callback with the result.
 *
 * @param screenHeight The height of the screen in density-independent pixels (dp).
 *                     Defaults to the current screen height from [LocalConfiguration].
 * @param isOnTopCallback A lambda that receives `true` if the composable is in the top half
 *                        of the screen, or `false` otherwise.
 * @return A [Modifier] that observes the composable's position and invokes the callback.
 */
@Composable
fun Modifier.isOnTop(
    screenHeight: Int = LocalConfiguration.current.screenHeightDp,
    isOnTopCallback: (Boolean) -> Unit,
): Modifier {
    return this.onGloballyPositioned { coordinates: LayoutCoordinates ->
        val yPos = coordinates.boundsInWindow().top
        val isOnTop = yPos < screenHeight / 2
        isOnTopCallback(isOnTop)
    }
}

/**
 * An extension function for [Modifier] that captures the position and size of a composable
 * in the layout hierarchy and provides it through a callback.
 *
 * @param onPositioned A lambda that receives a [YadoLocation], containing the composable's
 *                     position in the root layout and its size.
 * @return A [Modifier] that observes the composable's global position and size.
 */
fun Modifier.captureYadoLocation(onPositioned: (YadoLocation) -> Unit): Modifier = this.then(
    Modifier.onGloballyPositioned { coordinates: LayoutCoordinates ->
        val position = coordinates.positionInRoot()
        val size = coordinates.size
        onPositioned(YadoLocation(position, size))
    },
)
