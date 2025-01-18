package dev.yado.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.yado.lib.core.composable.layout.YadoScreen
import dev.yado.lib.core.composable.widget.YadoPage
import dev.yado.lib.core.model.background
import dev.yado.lib.core.model.blindSpot
import dev.yado.lib.source.state.rememberYadoState
import kotlinx.coroutines.launch

class SampleActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val coroutineScope = rememberCoroutineScope()

            val yadoState = rememberYadoState()
            yadoState.blindSpot = blindSpot {
                padding = 8.dp
            }

            yadoState.background = background {
                color = android.graphics.Color.parseColor("#B3dbdbdb")
            }

            DemoContent(yadoState) {
                YadoScreen(
                    modifier = it,
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
                        UserProfileScreen(
                            yadoState = yadoState,
                            user = User(
                                name = "John Doe",
                                bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                                email = "john.doe@example.com",
                                phoneNumber = "+1234567890"
                            ),
                        )
                    }
                )
            }


        }
    }
}