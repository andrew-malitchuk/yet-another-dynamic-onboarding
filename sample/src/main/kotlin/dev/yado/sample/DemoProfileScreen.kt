package dev.yado.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import dev.yado.lib.core.composable.widget.YadoBlock
import dev.yado.lib.core.model.YadoPosition
import dev.yado.lib.source.state.YadoState

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    yadoState:YadoState,
    user: User,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Header Section with Profile Picture
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.Gray)
            ) {
                Text(
                    text = "Welcome, ${user.name}",
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User Bio Section
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Bio",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                YadoBlock(
                    position = YadoPosition(0),
                    state = yadoState
                ) {
                    Text(
                        text = user.bio,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User Details Section
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
//                YadoBlock(
//                    position = YadoPosition(4),
//                    state = yadoState
//                ) {
//                    UserDetailRow(label = "Email", value = user.email)
//                }
//                YadoBlock(
//                    position = YadoPosition(5),
//                    state = yadoState
//                ) {
//                    UserDetailRow(label = "Phone", value = user.phoneNumber)
//                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Actions Section with Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
//                YadoBlock(
//                    position = YadoPosition(1),
//                    state = yadoState
//                ) {
//                    Button(
//                        onClick = {},
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(text = "Edit Profile")
//                    }
//                }
//                YadoBlock(
//                    position = YadoPosition(2),
//                    state = yadoState
//                ) {
//                    Button(
//                        onClick = {},
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(text = "Logout")
//                    }
//                }
            }
        }

        // Floating Action Button for quick actions
        FloatingActionButton(
            onClick = { /* Handle fab click */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
        }
    }
}

@Composable
fun UserDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

data class User(
    val name: String,
    val bio: String,
    val email: String,
    val phoneNumber: String
)