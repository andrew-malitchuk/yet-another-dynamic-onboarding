package dev.yado.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.yado.lib.source.state.YadoState
import dev.yado.lib.source.state.rememberYadoState
import kotlinx.coroutines.launch

@Composable
fun DemoContent(
    yadoState: YadoState = rememberYadoState(), block: @Composable (Modifier) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        block(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(modifier = Modifier.weight(1f), onClick = {
                yadoState.apply {
                    init()
                }
            }) {
                Text(text = "Init")
            }
            Button(modifier = Modifier.weight(1f), onClick = {
                yadoState.apply {
                    start()
                }
            }) {
                Text(text = "Start")
            }
            Button(modifier = Modifier.weight(1f), onClick = {
                coroutineScope.launch {
                    yadoState.next()
                }
            }) {
                Text(text = "Next")
            }
            Button(modifier = Modifier.weight(1f), onClick = {
                yadoState.finish()
            }) {
                Text(text = "Stop")
            }
        }
    }
}