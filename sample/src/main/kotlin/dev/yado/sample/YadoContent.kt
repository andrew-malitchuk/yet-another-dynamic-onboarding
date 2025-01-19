package dev.yado.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.yado.lib.core.composable.layout.YadoScreen
import dev.yado.lib.core.composable.widget.YadoBlock
import dev.yado.lib.core.composable.widget.YadoPage
import dev.yado.lib.core.model.YadoPosition
import dev.yado.lib.core.model.background
import dev.yado.lib.core.model.blindSpot
import dev.yado.lib.source.state.YadoState
import dev.yado.lib.source.state.rememberYadoState
import kotlinx.coroutines.launch

@Composable
fun YadoContent(
    modifier: Modifier=Modifier,
    yadoState: YadoState = rememberYadoState()
) {

    val coroutineScope = rememberCoroutineScope()

    YadoScreen(
        modifier = modifier,
        state = yadoState,
        loading = {
            Text(text = "loading")
        },
        page = {
            YadoPage(
                modifier = Modifier
                    .fillMaxSize(),
                yadoState = yadoState,
                pageState = it,
                promptBlock = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "^",
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        x = it,
                                        y = 0
                                    )
                                }
                        )
                        Text(
                            text = yadoState.currentPosition.toString()
                        )
                    }
                },
                actionBlock = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                yadoState.next()
                            }
                        }
                    ) {
                        Text("foobar")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .statusBarsPadding()
            ) {
                YadoBlock(
                    position = YadoPosition(0),
                    state = yadoState
                ) {
                    Text(
                        modifier = Modifier,
                        text = "foobar3"
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(64.dp)
                )
                Box(
                    modifier = Modifier
                ) {
                    YadoBlock(
                        position = YadoPosition(1),
                        state = yadoState
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            onClick = {

                            }
                        ) {
                            Text("Hello world")
                        }
                    }
                }
                YadoBlock(
                    position = YadoPosition(3),
                    state = yadoState
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Hello world"
                    )
                }
                Text(
                    modifier = Modifier,
                    text = "Hello world"
                )
                YadoBlock(
                    position = YadoPosition(2),
                    state = yadoState
                ) {
                    Text(
                        modifier = Modifier,
                        text = "foobar2"
                    )
                }
                Text(
                    modifier = Modifier,
                    text = "Hello world"
                )
                Text(
                    modifier = Modifier,
                    text = "Hello world"
                )
            }
        }
    )

}