package dev.ycosorio.biblioteca.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ycosorio.biblioteca.data.model.Articulo
import dev.ycosorio.biblioteca.data.repository.ArticuloRepository
import dev.ycosorio.biblioteca.data.model.Libro
import dev.ycosorio.biblioteca.data.repository.LibroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BusquedaViewModel : ViewModel() {

    private val libroRepository = LibroRepository()
    private val articuloRepository = ArticuloRepository()

    private val _uiState = MutableStateFlow(BusquedaUiState())
    val uiState: StateFlow<BusquedaUiState> = _uiState.asStateFlow()

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(busquedaActual = query) }
    }

    /**
     * Función de búsqueda que utiliza launch, withContext, async y await
     * para cumplir con todos los requisitos del ejercicio.
     */
    fun buscar() {
        val query = _uiState.value.busquedaActual

        // [launch]: Inicia la corrutina en el scope del ViewModel sin bloquear el hilo principal.
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // [withContext(Dispatchers.IO)]: Cambia explícitamente el contexto a un hilo de fondo
                // para todas las operaciones dentro de este bloque. Esto cumple el requisito.
                val (librosResult, articulosResult) = withContext(Dispatchers.IO) {
                    // [async]: Inicia ambas búsquedas en paralelo dentro del contexto Dispatchers.IO.
                    // Heredan el contexto del withContext padre.
                    val librosDeferred = async { libroRepository.buscarLibros(query) }
                    val articulosDeferred = async { articuloRepository.buscarArticulos(query) }

                    // [await]: Espera a que ambas operaciones 'async' terminen y devuelve los resultados.
                    // El tiempo total de espera será el de la operación más larga, no la suma de ambas.
                    librosDeferred.await() to articulosDeferred.await()
                }

                // Al salir del bloque withContext, la corrutina vuelve automáticamente al hilo
                // principal (Dispatchers.Main), por lo que es seguro actualizar el StateFlow de la UI aquí.
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        resultados = librosResult,
                        resultadosArticulos = articulosResult
                    )
                }
            } catch (e: Exception) {
                // También es seguro actualizar la UI en caso de error.
                _uiState.update { it.copy(isLoading = false, error = "Error en la búsqueda.") }
            }
        }
    }
}

data class BusquedaUiState(
    val isLoading: Boolean = false,
    val resultados: List<Libro> = emptyList(),
    val resultadosArticulos: List<Articulo> = emptyList(),
    val error: String? = null,
    val busquedaActual: String = ""
)