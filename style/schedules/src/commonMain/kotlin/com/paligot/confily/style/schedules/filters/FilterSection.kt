package com.paligot.confily.style.schedules.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.paligot.confily.style.theme.ConfilyTheme
import com.paligot.confily.style.theme.toDp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FilterSection(
    title: String,
    modifier: Modifier = Modifier,
    style: TextStyle = FilterSectionDefaults.style,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(FilterSectionTokens.BetweenSpacing.toDp())
    ) {
        Text(text = title, style = style)
        this.content()
    }
}

@Preview
@Composable
private fun FilterSectionPreview() {
    ConfilyTheme {
        FilterSection(title = "Categories") {
            Text(text = "Filter content")
        }
    }
}
