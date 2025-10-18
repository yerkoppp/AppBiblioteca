package dev.ycosorio.biblioteca.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.ycosorio.biblioteca.view.components.ArticuloCard
import dev.ycosorio.biblioteca.view.components.LibroCard
import dev.ycosorio.biblioteca.viewmodel.BusquedaUiState
import dev.ycosorio.biblioteca.viewmodel.BusquedaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaScreen(
    viewModel: BusquedaViewModel = viewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Biblioteca Municipal") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // --- 1. CAMPO DE BÚSQUEDA MEJORADO ---
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.busquedaActual,
                onValueChange = { viewModel.onQueryChange(it) },
                label = { Text("Buscar en el catálogo...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                trailingIcon = {
                    AnimatedVisibility(visible = uiState.busquedaActual.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onQueryChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.buscar() },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buscar")
            }
            Spacer(modifier = Modifier.height(24.dp))

            // --- 2. GESTIÓN DE ESTADOS ---
            when {
                uiState.isLoading -> LoadingState()
                uiState.error != null -> ErrorState(error = uiState.error!!)
                uiState.resultados.isEmpty() && uiState.resultadosArticulos.isEmpty() -> {
                    if (uiState.busquedaActual.isBlank()) InitialState()
                    else NoResultsState(query = uiState.busquedaActual)
                }
                else -> ResultsList(uiState = uiState, navController = navController)
            }
        }
    }
}

// --- COMPOSABLES PARA LOS ESTADOS DE LA UI ---

@Composable
fun InitialState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Empieza a buscar", style = MaterialTheme.typography.titleLarge)
        Text(
            "Encuentra libros y artículos de nuestro catálogo.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun LoadingState() {
    // Skeletons/Placeholders
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        repeat(4) {
            Card(modifier = Modifier.fillMaxWidth().height(100.dp)) {
                // Puedes añadir una animación de shimmer aquí para un efecto más avanzado
            }
        }
    }
}

@Composable
fun NoResultsState(query: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sin resultados", style = MaterialTheme.typography.titleLarge)
        Text(
            "No hemos encontrado nada para \"$query\".",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            "Prueba con otras palabras.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ErrorState(error: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(error, color = MaterialTheme.colorScheme.error)
    }
}


@Composable
fun ResultsList(uiState: BusquedaUiState, navController: NavController) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (uiState.resultadosArticulos.isNotEmpty()) {
            item {
                Text("Artículos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            items(uiState.resultadosArticulos, key = { it.id }) { articulo ->
                ArticuloCard(articulo = articulo, onClick = {
                    navController.navigate("articulo/${articulo.id}")
                })
            }
        }

        if (uiState.resultados.isNotEmpty()) {
            item {
                Text("Libros", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            items(uiState.resultados, key = { it.id }) { libro ->
                LibroCard(libro = libro, onClick = {
                    navController.navigate("detalle/${libro.id}")
                })
            }
        }
    }
}



