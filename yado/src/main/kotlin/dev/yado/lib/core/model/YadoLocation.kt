package dev.yado.lib.core.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

/**
 * A data class that represents the location and size of an item or component within the YADO (Yet Another Dynamic Onboarding) system.
 * This class encapsulates the offset (position) and size (width and height) of a component, useful for managing layout positions.
 *
 * @param offset The offset (position) of the component, represented as an [Offset] containing x and y coordinates.
 * @param size The size of the component, represented as an [IntSize] containing width and height.
 */
data class YadoLocation(
    val offset: Offset,
    val size: IntSize,
)
