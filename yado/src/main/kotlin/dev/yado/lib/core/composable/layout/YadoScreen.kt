package dev.yado.lib.core.composable.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.yado.lib.core.composable.widget.YadoPager
import dev.yado.lib.source.state.YadoInternalState
import dev.yado.lib.source.state.YadoState

/**
 * A composable function that represents the main screen of the YADO (Yet Another Dynamic Onboarding) system.
 * It dynamically adjusts its content based on the current state of the onboarding process, enabling flexible
 * and state-driven UI transitions.
 *
 * @param modifier A [Modifier] applied to the root `Box` composable, allowing customization of its appearance and behavior.
 * @param state The current [YadoState] of the onboarding process, containing the state and screenshot logic.
 * @param loading A composable lambda to display the loading UI when the onboarding is in progress.
 * @param page A composable lambda that defines the UI for the paging state, with access to the [PagerState].
 * @param content A composable lambda to display the main content of the onboarding when in `Init` or `Idle` states.
 */
@Composable
fun YadoScreen(
    modifier: Modifier = Modifier,
    state: YadoState,
    loading: @Composable () -> Unit,
    page: @Composable (PagerState) -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        when (state.internalState.value) {
            // Render the content wrapped in a screenshot-enabled YadoBox during initialization
            YadoInternalState.Init ->
                YadoScreenshotBox(
                    modifier = Modifier,
                    yadoScreenshotState = state.yadoScreenshotState,
                ) {
                    content()
                }

            // Render the loading composable during the "In Progress" state
            YadoInternalState.InProgress -> loading()

            // Render the YadoPager composable to handle page navigation during the "Next" state
            YadoInternalState.Next ->
                YadoPager(
                    yadoState = state,
                    page = page,
                )

            // Render the content
            YadoInternalState.Idle -> content()
        }
    }
}
