package ru.easycode.zerotoheroandroidtdd

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

@Composable
fun FilterSettingsScreen(
    viewModel: ProductsViewModel,
    onNavigateBack: () -> Unit
) {
    val filters by viewModel.filtersUiListStateFlow.collectAsState()
    FilterSettingsContent(
        filters = filters,
        onNavigateBack = onNavigateBack,
        chooseFilter = viewModel::chooseFilter,
        unchooseFilter = viewModel::unchooseFilter
    )
}

@Composable
private fun FilterSettingsContent(
    filters: List<FilterUi>,
    onNavigateBack: () -> Unit,
    chooseFilter: (Int) -> Unit,
    unchooseFilter: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = "Filter By",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )
            Button(
                onClick = onNavigateBack,
                modifier = Modifier.testTag("save button")
            ) {
                Text(text = "save")
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(filters) { filter ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(selected = filter.chosen, onClick = {
                            if (filter.chosen) {
                                unchooseFilter(filter.id)
                            } else {
                                chooseFilter(filter.id)
                            }
                        })
                        .testTag("filter ${filter.category} ${filter.value}")
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = filter.chosen, onCheckedChange = null)
                    Text(text = "${filter.category}: ${filter.value}", modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun FilterSettingsScreenPreview() {
    ZeroToHeroAndroidTDDTheme {
        FilterSettingsContent(
            filters = listOf(
                FilterUi(id = 1, category = "os", value = "Android", chosen = true),
                FilterUi(id = 2, category = "os", value = "iOS", chosen = false),
            ), onNavigateBack = {}, chooseFilter = {}, unchooseFilter = {})
    }
}
