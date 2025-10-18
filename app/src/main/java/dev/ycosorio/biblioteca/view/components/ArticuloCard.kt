package dev.ycosorio.biblioteca.view.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.ycosorio.biblioteca.data.repository.model.Articulo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticuloCard(articulo: Articulo, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.Article, contentDescription = "Artículo", modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = articulo.titulo, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        }
    }
}