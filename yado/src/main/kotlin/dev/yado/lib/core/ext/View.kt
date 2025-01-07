package dev.yado.lib.core.ext

import android.app.Activity
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.View
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.toAndroidRect

fun View.screenshot(
    bounds: Rect,
    bitmapCallback: (Result<Bitmap>) -> Unit,
) {
    try {
        val bitmap =
            Bitmap.createBitmap(
                bounds.width.toInt(),
                bounds.height.toInt(),
                Bitmap.Config.ARGB_8888,
            )

        // Above Android O not using PixelCopy throws exception
        // https://stackoverflow.com/questions/58314397/java-lang-illegalstateexception-software-rendering-doesnt-support-hardware-bit
        PixelCopy.request(
            (this.context as Activity).window,
            bounds.toAndroidRect(),
            bitmap,
            {
                when (it) {
                    PixelCopy.SUCCESS ->
                        bitmapCallback(Result.success(bitmap))

                    PixelCopy.ERROR_DESTINATION_INVALID ->
                        bitmapCallback(
                            Result.failure(
                                Exception(
                                    "The destination isn't a valid copy target. " +
                                        "If the destination is a bitmap this can occur " +
                                        "if the bitmap is too large for the hardware to " +
                                        "copy to. " +
                                        "It can also occur if the destination " +
                                        "has been destroyed",
                                ),
                            ),
                        )

                    PixelCopy.ERROR_SOURCE_INVALID ->
                        bitmapCallback(
                            Result.failure(
                                Exception(
                                    "It is not possible to copy from the source. " +
                                        "This can happen if the source is " +
                                        "hardware-protected or destroyed.",
                                ),
                            ),
                        )

                    PixelCopy.ERROR_TIMEOUT ->
                        bitmapCallback(
                            Result.failure(
                                Exception(
                                    "A timeout occurred while trying to acquire a buffer " +
                                        "from the source to copy from.",
                                ),
                            ),
                        )

                    PixelCopy.ERROR_SOURCE_NO_DATA ->
                        bitmapCallback(
                            Result.failure(
                                Exception(
                                    "The source has nothing to copy from. " +
                                        "When the source is a Surface this means that " +
                                        "no buffers have been queued yet. " +
                                        "Wait for the source to produce " +
                                        "a frame and try again.",
                                ),
                            ),
                        )

                    else ->
                        bitmapCallback(
                            Result.failure(
                                Exception(
                                    "The pixel copy request failed with an unknown error.",
                                ),
                            ),
                        )
                }
            },
            Handler(Looper.getMainLooper()),
        )
    } catch (e: Exception) {
        bitmapCallback(Result.failure(e))
    }
}
