package dev.yado.lib.core.composable.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.yado.lib.source.state.YadoState

/**
 * A composable function that represents a horizontally scrollable pager in the YADO (Yet Another Dynamic Onboarding) system.
 * This pager handles navigation between multiple pages based on the current [YadoState].
 *
 * @param modifier A [Modifier] applied to the root container, allowing customization of the pager's appearance and layout.
 * @param yadoState The current [YadoState], providing access to the [PagerState] for managing the pager's behavior.
 * @param page A composable lambda that defines the UI for each page in the pager, with access to the [PagerState].
 */
@Composable
fun YadoPager(
    modifier: Modifier = Modifier,
    yadoState: YadoState,
    page: @Composable (PagerState) -> Unit,
) {
    yadoState.pagerState?.let { pager ->
        HorizontalPager(
            modifier = modifier,
            state = pager,
            userScrollEnabled = false, // Disables manual scrolling for controlled transitions
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                // Render the content for the current page
                page(pager)
            }
        }
    }
}
