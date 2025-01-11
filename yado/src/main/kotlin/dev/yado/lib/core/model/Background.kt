package dev.yado.lib.core.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A data class representing a background configuration for the YADO (Yet Another Dynamic Onboarding) system.
 * The background defines the color and blur effect applied to an element in the onboarding process.
 *
 * @param color The background color, specified as an integer (e.g., `android.graphics.Color.WHITE`).
 * @param blur The blur effect applied to the background, specified in density-independent pixels (dp).
 */
data class Background(
    val color: Int = android.graphics.Color.WHITE,
    val blur: Dp = 0.dp,
)

/**
 * A marker annotation used for denoting DSL functions related to the construction of [Background] objects.
 * This helps in identifying blocks of code that are part of the background builder DSL.
 */
@DslMarker
annotation class BackgroundDsl

/**
 * A builder class for constructing [Background] instances using a DSL-style syntax.
 * Allows configuration of the [color] and [blur] properties of the [Background].
 */
@BackgroundDsl
class BackgroundBuilder {
    /**
     * The background color, specified as an integer (e.g., `android.graphics.Color.WHITE`).
     * Defaults to `android.graphics.Color.WHITE` if not specified.
     */
    var color = android.graphics.Color.WHITE

    /**
     * The blur effect applied to the background, specified in density-independent pixels (dp).
     * Defaults to 0.dp if not specified.
     */
    var blur = 0.dp

    /**
     * Builds and returns a [Background] object using the configured values.
     */
    fun build(): Background =
        Background(
            color = color,
            blur = blur,
        )
}

/**
 * A DSL function that constructs a [Background] instance using the provided block of code.
 * This allows configuration of the [color] and [blur] properties in a natural, readable syntax.
 *
 * @param block A lambda block where [BackgroundBuilder] properties are configured.
 * @return A [Background] object constructed with the provided configuration.
 */
fun background(block: BackgroundBuilder.() -> Unit): Background {
    return BackgroundBuilder().apply(block).build()
}
