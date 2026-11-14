package ffc.app.bubbletech.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ffc.app.bubbletech.ui.theme.BubbleTechTheme
import ffc.app.bubbletech.ui.theme.SecondaryBlue

@Composable
fun CategoryChips(
    categories: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories){ category ->
            val isSelected = category == selected
            Text(
                text = category.uppercase(),
                color = if (isSelected) Color.White else Color.Gray,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) SecondaryBlue else Color.Transparent)
                    .border(1.dp, if (isSelected) Color.Transparent else Color.Gray, RoundedCornerShape(50))
                    .clickable { onSelect(category) }
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryChipsPreview(){
    BubbleTechTheme {
        CategoryChips(
            categories = listOf("All", "IA", "Cibersegurança", "Carreira", "Startups"),
            selected = "All",
            onSelect = {}
        )
    }
}