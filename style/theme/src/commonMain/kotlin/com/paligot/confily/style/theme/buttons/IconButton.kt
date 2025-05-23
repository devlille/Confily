package com.paligot.confily.style.theme.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.paligot.confily.style.theme.ConfilyTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IconButton(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondaryContainer,
    shape: Shape = MaterialTheme.shapes.small,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        shape = shape,
        containerColor = color,
        modifier = modifier,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
private fun IconButtonPreview() {
    ConfilyTheme {
        IconButton(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            onClick = {}
        )
    }
}
