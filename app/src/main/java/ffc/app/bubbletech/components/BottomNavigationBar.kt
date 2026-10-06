package ffc.app.bubbletech.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ffc.app.bubbletech.ui.theme.BubbleTechTheme
import ffc.app.bubbletech.ui.theme.NavBlue
import ffc.app.bubbletech.ui.theme.NavHighlightBlue

data class BottomNavItem(val label: String, val icon: ImageVector)

private val bottomNavItems = listOf(
    BottomNavItem("Últimas notícias", Icons.Outlined.Lightbulb),
    BottomNavItem("Buscar", Icons.Outlined.Search),
    BottomNavItem("Home", Icons.Outlined.Home),
    BottomNavItem("Recentes", Icons.Outlined.History),
    BottomNavItem("Perfil", Icons.Outlined.Person)
)

@Composable
fun BottomNavigationBar(
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavBlue)
            .navigationBarsPadding()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = item.label == selected
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelect(item.label) }
            ) {
                if (isSelected) {
                    // A caixa ocupa o tamanho de um ícone normal (24dp), então a barra não cresce.
                    // O círculo dentro dela é maior (requiredSize) e "vaza" pra cima com o offset.
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .offset(y = (-18).dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .requiredSize(56.dp)
                                .clip(CircleShape)
                                .background(NavHighlightBlue)
                        ) {
                            Icon(item.icon, contentDescription = item.label, tint = Color.White)
                        }
                    }
                } else {
                    Icon(item.icon, contentDescription = item.label, tint = Color.White)
                }
                Text(
                    text = item.label,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00174E)
@Composable
fun BottomNavigationBarPreview() {
    BubbleTechTheme {
        BottomNavigationBar(selected = "Home", onSelect = {})
    }
}