package dev.yado.lib.source.state

import android.graphics.Bitmap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.yado.lib.core.model.AnimationSpec
import dev.yado.lib.core.model.Background
import dev.yado.lib.core.model.BlindSpot
import dev.yado.lib.core.model.YadoLocation
import dev.yado.lib.core.model.YadoPosition
import dev.yado.lib.core.util.cropRoundedComposableFromBitmap

@Composable
fun rememberYadoState() =
    remember {
        YadoState()
    }

class YadoState {

    //region config
    var blindSpot: BlindSpot = BlindSpot()
    var background: Background = Background()
    var animationSpec: AnimationSpec = AnimationSpec()
    //endregion config

    val internalState = mutableStateOf<YadoInternalState>(YadoInternalState.Init)

    var yadoScreenshotState: YadoScreenshotState = YadoScreenshotState()

    var pagerState: PagerState? = null

    var allItems = mutableStateMapOf<YadoPosition, YadoLocation>()

    private val sortedItems: List<YadoPosition>
        get() =
            allItems.keys.sortedBy(YadoPosition::position)

    val currentPosition: YadoPosition?
        get() = pagerState?.let {
            sortedItems.getOrNull(it.currentPage)
        }

    var allItemsCaptures = mutableStateMapOf<YadoPosition, Result<Bitmap>>()

    var screenCapture = mutableStateOf<Result<Bitmap?>?>(null)

    fun addItem(position: YadoPosition, location: YadoLocation) {
        allItems[position] = location
    }

    private fun capture() = yadoScreenshotState.capture()

    private fun captureAll() {
        screenCapture.value = runCatching {
            yadoScreenshotState.bitmapState.value?.getOrNull()?.let {
                it.copy(
                    it.config!!,
                    false
                )
            }
        }

        allItems.keys.forEach {
            capture(it)
        }
        pagerState = PagerState {
            allItems.keys.size
        }
    }

    private fun capture(position: YadoPosition) {
        val location = allItems[position]
        if (yadoScreenshotState.bitmapState.value?.getOrNull() != null && location?.offset != null) {
            yadoScreenshotState.bitmapState.value?.getOrNull()?.let {
                allItemsCaptures[position] = cropRoundedComposableFromBitmap(
                    bitmap = it,
                    location.offset,
                    location.size,
                    blindSpot.padding,
                    blindSpot.corner
                )
            }
        }
    }

    fun init() {
        internalState.value = YadoInternalState.InProgress
        capture()
        internalState.value = YadoInternalState.Idle
    }

    suspend fun next() {
        pagerState?.let {
            val nextPage = it.currentPage + 1
            if (nextPage == it.pageCount) {
                finish()
            } else {
                it.animateScrollToPage(
                    nextPage,
                    animationSpec = tween(durationMillis = animationSpec.duration)
                )
            }
        }
    }


    fun finish() {
        allItemsCaptures = mutableStateMapOf()
        internalState.value = YadoInternalState.Idle
    }

    fun start() {
        captureAll()
        internalState.value = YadoInternalState.Next
    }

}
