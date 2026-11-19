package ffc.app.bubbletech.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ffc.app.bubbletech.ui.theme.BubbleTechTheme
import ffc.app.bubbletech.ui.theme.SecondaryBlue

fun Modifier.dropShadow(
    color: Color = Color.Black.copy(alpha = 0.25f),
    offsetX: Dp = 0.dp,
    offsetY: Dp = (-5).dp,
    blur: Dp = 20.dp,
    spread: Dp = 4.dp,
    shape: Shape = RectangleShape
): Modifier = this.drawBehind {
    val shadowColor = color.toArgb()
    val spreadPx = spread.toPx()
    val left = -spreadPx + offsetX.toPx()
    val top = -spreadPx + offsetY.toPx()
    val right = size.width + spreadPx + offsetX.toPx()
    val bottom = size.height + spreadPx + offsetY.toPx()

    if (right <= left || bottom <= top) return@drawBehind

    val outline = shape.createOutline(
        size = Size(right - left, bottom - top),
        layoutDirection = layoutDirection,
        density = this
    )

    drawIntoCanvas { canvas ->
        val paint = Paint()
        @Suppress("DEPRECATION")
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = shadowColor
        if (blur.toPx() > 0) {
            frameworkPaint.maskFilter = android.graphics.BlurMaskFilter(
                blur.toPx(),
                android.graphics.BlurMaskFilter.Blur.NORMAL
            )
        }
        canvas.save()
        canvas.translate(left, top)
        when (outline) {
            is Outline.Rectangle -> canvas.drawRect(outline.rect, paint)
            is Outline.Rounded -> canvas.drawRoundRect(
                left = 0f,
                top = 0f,
                right = outline.roundRect.width,
                bottom = outline.roundRect.height,
                radiusX = outline.roundRect.topLeftCornerRadius.x,
                radiusY = outline.roundRect.topLeftCornerRadius.y,
                paint = paint
            )
            is Outline.Generic -> canvas.drawPath(outline.path, paint)
        }
        canvas.restore()
    }
}

data class BottomNavItem(val label: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector)

private val bottomNavItems = listOf(
    BottomNavItem("Feed", Icons.Filled.Explore, Icons.Outlined.Explore),
    BottomNavItem("Hot", Icons.Filled.LocalFireDepartment, Icons.Outlined.LocalFireDepartment),
    BottomNavItem("Saved", Icons.Filled.Bookmarks, Icons.Outlined.Bookmarks),
    BottomNavItem("Recents", Icons.Filled.History, Icons.Outlined.History)
)

@Composable
fun BottomNavigationBar(
    selected: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                color = Color.Black.copy(alpha = 0.25f),
                offsetX = 0.dp,
                offsetY = (-5).dp,
                blur = 20.dp,
                spread = 4.dp
            )
            .background(Color.White)
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
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onSelect(item.label) }
            ) {
                Icon(
                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                    contentDescription = item.label,
                    modifier = Modifier.size(30.dp),
                    tint = if (isSelected) SecondaryBlue else Color.Black
                )
                Text(
                    text = item.label,
                    color = if (isSelected) SecondaryBlue else Color.Black,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 11.sp,
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
        BottomNavigationBar(selected = "Feed", onSelect = {})
    }
}