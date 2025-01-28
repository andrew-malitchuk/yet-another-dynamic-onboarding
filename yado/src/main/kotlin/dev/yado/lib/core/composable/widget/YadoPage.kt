package dev.yado.lib.core.composable.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import dev.yado.lib.core.ext.calculateCurrentOffsetForPage
import dev.yado.lib.core.ext.isOnTop
import dev.yado.lib.core.model.YadoLocation
import dev.yado.lib.core.util.applyTintToBitmap
import dev.yado.lib.source.state.YadoState
import kotlin.math.absoluteValue

/**
 * A composable function that represents a single page in the YADO (Yet Another Dynamic Onboarding) system.
 * This function handles rendering the page's content, applying dynamic transformations for transitions,
 * and displaying prompts or actions based on the current state.
 *
 * @param modifier A [Modifier] applied to the root container, allowing customization of the page's appearance and layout.
 * @param yadoState The current [YadoState], providing access to stateful data such as captures, offsets, and configurations.
 * @param pageState A [PagerState] instance representing the current state of the pager, used for managing transitions.
 * @param promptBlock A composable lambda for rendering prompts, with access to the pointer position.
 * @param actionBlock A composable lambda for rendering the action block, typically aligned to the top or bottom of the screen.
 */
@Composable
fun YadoPage(
    modifier: Modifier = Modifier,
    yadoState: YadoState,
    pageState: PagerState,
    promptBlock: @Composable (Int) -> Unit,
    actionBlock: @Composable () -> Unit,
) {
    val isOnTop = remember { mutableStateOf(false) }

    Box(
        modifier =
        modifier
            .fillMaxSize()
            .graphicsLayer {
                yadoState.pagerState?.let {
                    val pageOffset =
                        it.calculateCurrentOffsetForPage(pageState.currentPage)
                    translationX = pageOffset * size.width
                    alpha = 1 - pageOffset.absoluteValue
                }
            },
    ) {
        // Display a blurred, tinted screenshot as the background
        yadoState.screenCapture.value?.getOrNull()?.let {
            applyTintToBitmap(
                it,
                yadoState.background.color,
            ).getOrNull()?.asImageBitmap()?.let { bitmap ->
                Image(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .blur(yadoState.background.blur),
                    bitmap = bitmap,
                    contentDescription = null,
                )
            }
        }

        // Render the prompt and its associated UI elements if the current position is valid
        yadoState.currentPosition?.let { currentPosition ->
            val bitmap = yadoState.allItemsCaptures[currentPosition]?.getOrNull()?.asImageBitmap()
            val position = yadoState.allItems[currentPosition]?.offset
            if (bitmap != null && position != null) {
                Column(
                    modifier =
                    Modifier
                        .offset {
                            IntOffset(
                                (position.x.toInt() - yadoState.blindSpot.padding.toPx()).toInt(),
                                ((position.y.toInt() - yadoState.blindSpot.padding.toPx()).toInt()),
                            )
                        }
                        .isOnTop {
                            isOnTop.value = it
                        },
                ) {
                    Image(
                        modifier = Modifier,
                        bitmap = bitmap,
                        contentDescription = null,
                    )
                    promptBlock(
                        pointerPosition(
                            yadoState.allItems[currentPosition],
                            yadoState.blindSpot.padding.value.toInt(),
                        ),
                    )
                }
            }
        }

        // Render the action block aligned based on the prompt's position
        Box(
            modifier =
            Modifier
                .align(
                    if (isOnTop.value) Alignment.BottomStart else Alignment.TopStart,
                )
                .then(
                    Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ),
        ) {
            actionBlock()
        }
    }
}

/**
 * Calculates the pointer's horizontal position based on the location and padding.
 *
 * @param yadoLocation The [YadoLocation] containing the offset and size of the target element.
 * @param padding The padding value applied to adjust the pointer position.
 * @return The calculated horizontal position as an [Int].
 */
fun pointerPosition(
    yadoLocation: YadoLocation?,
    padding: Int,
): Int {
    if (yadoLocation == null) return 0
    return (yadoLocation.offset.x + (yadoLocation.size.width / 2) + (padding / 2)).toInt()
}
