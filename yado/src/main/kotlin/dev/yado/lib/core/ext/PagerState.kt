package dev.yado.lib.core.ext

import androidx.compose.foundation.pager.PagerState

fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}
