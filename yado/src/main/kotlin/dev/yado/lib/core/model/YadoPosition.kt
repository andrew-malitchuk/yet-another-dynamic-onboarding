package dev.yado.lib.core.model

/**
 * A value class that encapsulates a position value as an integer.
 * This class is used to represent the position of an item or component in the YADO (Yet Another Dynamic Onboarding) system.
 * It is a wrapper around an integer, providing a type-safe way to handle positions.
 *
 * @param position The integer value representing the position.
 */
@JvmInline
value class YadoPosition(
    val position: Int,
)
