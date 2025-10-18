package dev.ycosorio.biblioteca.data.repository

import dev.ycosorio.biblioteca.data.repository.model.Articulo
import kotlinx.coroutines.delay

class ArticuloRepository {

    // Lista para simular otra fuente de datos.
    private val catalogoDeArticulos = listOf(
        Articulo(
            101,
            "Borges y la Metafísica",
            "Un análisis profundo sobre cómo las ideas filosóficas del infinito y la identidad se reflejan en la obra de Jorge Luis Borges, con especial atención a 'El Aleph' y 'Ficciones'."
        ),
        Articulo(
            102,
            "Análisis de 'Cien Años de Soledad'",
            "Este artículo desglosa los elementos clave del realismo mágico en la novela de Gabriel García Márquez, explorando la estructura cíclica del tiempo y el destino de la familia Buendía."
        ),
        Articulo(
            103,
            "El Realismo Mágico de Allende",
            "Exploración de la influencia del realismo mágico en 'La Casa de los Espíritus' de Isabel Allende, comparando su estilo con el de otros grandes autores del boom latinoamericano."
        )
    )

    // Devuelve una lista de Articulos.
    suspend fun buscarArticulos(query: String): List<Articulo> {
        delay(1000)
        if (query.isBlank()) return catalogoDeArticulos
        return catalogoDeArticulos.filter {
            it.titulo.contains(query, ignoreCase = true) ||
                    it.contenido.contains(query, ignoreCase = true)
        }
    }

    // Función para encontrar un artículo por su ID.
    suspend fun buscarArticuloPorId(id: Int): Articulo? {
        delay(300)
        return catalogoDeArticulos.find { it.id == id }
    }


}