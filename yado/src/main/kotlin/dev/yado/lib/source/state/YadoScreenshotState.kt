package dev.yado.lib.source.state

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf

/**
 * A state holder class for managing screenshots in the YADO (Yet Another Dynamic Onboarding) system.
 * This class encapsulates the screenshot state, provides a mechanism to capture screenshots,
 * and stores the resulting bitmap in a [mutableStateOf] property.
 */
class YadoScreenshotState {

    /**
     * A mutable state containing the result of the most recent screenshot operation.
     * The result is represented as a [Result] wrapping a nullable [Bitmap].
     * - A successful screenshot operation contains the [Bitmap].
     * - A failure contains an exception.
     * - A `null` value indicates no screenshot has been captured yet.
     */
    val bitmapState = mutableStateOf<Result<Bitmap?>?>(null)

    /**
     * An internal callback invoked when a screenshot capture is requested.
     * This should be assigned by the component responsible for providing screenshots.
     */
    internal var callback: (() -> Unit)? = null

    /**
     * Requests a screenshot capture by invoking the assigned callback.
     * If no callback is assigned, this function does nothing.
     */
    fun capture() = callback?.invoke()
}