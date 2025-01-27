package dev.yado.lib.core.composable.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.yado.lib.core.ext.captureYadoLocation
import dev.yado.lib.core.model.YadoPosition
import dev.yado.lib.source.state.YadoState

/**
 * A composable function that represents a block in the YADO (Yet Another Dynamic Onboarding) system.
 * This function tracks its position and integrates it into the [YadoState], enabling precise positioning
 * and state-aware behavior for dynamic onboarding components.
 *
 * @param modifier A [Modifier] applied to the root `Box` composable, allowing customization of its appearance and behavior.
 * @param position A [YadoPosition] instance representing the location or role of this block within the onboarding flow.
 * @param state The current [YadoState] of the onboarding system, used to track and manage this block's location.
 * @param block A composable lambda that defines the UI content of the block.
 */
@Composable
fun YadoBlock(
    modifier: Modifier = Modifier,
    position: YadoPosition,
    state: YadoState,
    block: @Composable () -> Unit,
) {
    Box(
        modifier =
        modifier
            .captureYadoLocation {
                // Registers the block's position in the state when its location is determined
                state.addItem(position, it)
            },
    ) {
        block()
    }
}
