package ffc.app.bubbletech.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import ffc.app.bubbletech.components.BackButton
import ffc.app.bubbletech.ui.theme.Background
import ffc.app.bubbletech.ui.theme.DarkText
import ffc.app.bubbletech.ui.theme.GrayText
import ffc.app.bubbletech.ui.theme.SecondaryBlue

private val LogoutRed = Color(0xFFDC2626)

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val name = user?.displayName?.takeIf { it.isNotBlank() } ?: "Usuário"
    val email = user?.email.orEmpty()
    val initial = (user?.displayName?.takeIf { it.isNotBlank() } ?: email).take(1).uppercase().ifBlank { "E" }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        BackButton(
            onClick = onBack,
            modifier = Modifier.padding(16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 80.dp, bottom = 32.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(SecondaryBlue)
            ) {
                Text(
                    text = initial,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = name,
                color = DarkText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            if (email.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = email,
                    color = GrayText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onLogout,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, LogoutRed),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = LogoutRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sair da conta",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
