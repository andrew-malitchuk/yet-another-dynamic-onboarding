package dev.yado.lib.core.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A data class representing a blind spot configuration in the YADO (Yet Another Dynamic Onboarding) system.
 * The blind spot defines a region around a component that is visually ignored or masked during onboarding.
 *
 * @param padding The padding around the blind spot, specified in density-independent pixels (dp).
 * @param corner The corner radius for rounding the edges of the blind spot, specified in dp.
 */
data class BlindSpot(
    val padding: Dp = 0.dp,
    val corner: Dp = 0.dp,
)

/**
 * A marker annotation used for denoting DSL functions related to the construction of [BlindSpot] objects.
 * This helps in identifying blocks of code that are part of the blind spot builder DSL.
 */
@DslMarker
annotation class BlindSpotDsl

/**
 * A builder class for constructing [BlindSpot] instances using a DSL-style syntax.
 * Allows configuration of the [padding] and [corner] properties of the [BlindSpot].
 */
@BlindSpotDsl
class BlindSpotBuilder {
    /**
     * The padding around the blind spot, specified in density-independent pixels (dp).
     * Defaults to 0.dp if not specified.
     */
    var padding: Dp = 0.dp

    /**
     * The corner radius for rounding the edges of the blind spot, specified in dp.
     * Defaults to 0.dp if not specified.
     */
    var corner: Dp = 0.dp

    /**
     * Builds and returns a [BlindSpot] object using the configured values.
     */
    fun build(): BlindSpot =
        BlindSpot(
            padding = padding,
            corner = corner,
        )
}

/**
 * A DSL function that constructs a [BlindSpot] instance using the provided block of code.
 * This allows configuration of the [padding] and [corner] properties in a natural, readable syntax.
 *
 * @param block A lambda block where [BlindSpotBuilder] properties are configured.
 * @return A [BlindSpot] object constructed with the provided configuration.
 */
fun blindSpot(block: BlindSpotBuilder.() -> Unit): BlindSpot {
    return BlindSpotBuilder().apply(block).build()
}
