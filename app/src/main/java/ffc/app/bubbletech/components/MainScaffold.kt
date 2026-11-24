package ffc.app.bubbletech.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ffc.app.bubbletech.ui.theme.Background

@Composable
fun MainScaffold(
    selectedTab: String,
    onTabSelect: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    userInitial: String,
    onAvatarClick: () -> Unit = {},
    topBarExtra: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        containerColor = Background,
        topBar = {
            Column(modifier = Modifier.background(Background)) {
                TopSearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    userInitial = userInitial,
                    onAvatarClick = onAvatarClick
                )
                topBarExtra()
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selected = selectedTab,
                onSelect = onTabSelect
            )
        },
        content = content
    )
}
