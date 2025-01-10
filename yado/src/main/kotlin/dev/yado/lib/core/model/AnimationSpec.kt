package dev.yado.lib.core.model

/**
 * A data class representing the animation specification for the YADO (Yet Another Dynamic Onboarding) system.
 * The animation specification defines the duration for an animation.
 *
 * @param duration The duration of the animation, specified in milliseconds. Defaults to 1000 ms (1 second).
 */
data class AnimationSpec(
    val duration: Int = 1_000,
)

/**
 * A marker annotation used for denoting DSL functions related to the construction of [AnimationSpec] objects.
 * This helps in identifying blocks of code that are part of the animation specification builder DSL.
 */
@DslMarker
annotation class AnimationSpecDsl

/**
 * A builder class for constructing [AnimationSpec] instances using a DSL-style syntax.
 * Allows configuration of the [duration] property of the animation.
 */
@AnimationSpecDsl
class AnimationSpecBuilder {
    /**
     * The duration of the animation, specified in milliseconds.
     * Defaults to 0 ms if not specified.
     */
    var duration: Int = 0

    /**
     * Builds and returns an [AnimationSpec] object using the configured values.
     */
    fun build(): AnimationSpec =
        AnimationSpec(
            duration = duration,
        )
}

/**
 * A DSL function that constructs an [AnimationSpec] instance using the provided block of code.
 * This allows configuration of the [duration] property in a natural, readable syntax.
 *
 * @param block A lambda block where [AnimationSpecBuilder] properties are configured.
 * @return An [AnimationSpec] object constructed with the provided configuration.
 */
fun animationSpec(block: AnimationSpecBuilder.() -> Unit): AnimationSpec {
    return AnimationSpecBuilder().apply(block).build()
}
