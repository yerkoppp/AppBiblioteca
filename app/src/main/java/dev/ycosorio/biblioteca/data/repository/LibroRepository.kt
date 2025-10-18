package dev.ycosorio.biblioteca.data.repository

import dev.ycosorio.biblioteca.R
import dev.ycosorio.biblioteca.data.model.Libro
import kotlinx.coroutines.delay

class LibroRepository {

    // Lista de libros "hardcodeada" para simular una base de datos.
    private val catalogoDeLibros = listOf(
        Libro(
            1,
            "El Aleph",
            "Jorge Luis Borges",
            true,
            "Una colección de cuentos que exploran conceptos como el infinito, la identidad y la metafísica. Considerada una obra cumbre de la literatura.",
            portadaResId = R.drawable.el_aleph
        ),
        Libro(
            2,
            "Cien Años de Soledad",
            "Gabriel García Márquez",
            false,
            "La historia de la familia Buendía en el pueblo ficticio de Macondo. Una novela icónica del realismo mágico.",
            portadaResId = R.drawable.cien_anios_soledad
        ),
        Libro(
            3,
            "La Casa de los Espíritus",
            "Isabel Allende",
            true,
            "Saga familiar que narra la vida de la familia Trueba a lo largo de varias generaciones en un país latinoamericano con marcados cambios sociales y políticos.",
            portadaResId = R.drawable.la_casa_de_los_espiritus
        ),
        Libro(
            4,
            "Ficciones",
            "Jorge Luis Borges",
            true,
            "Otra magistral colección de cuentos de Borges, que juega con los límites entre la realidad y la fantasía, la filosofía y el relato policial.",
            portadaResId = R.drawable.ficciones
        ),
        Libro(
            5,
            "Rayuela",
            "Julio Cortázar",
            false,
            "Una 'contranovela' que puede leerse de múltiples maneras, rompiendo con la estructura lineal tradicional y explorando temas de amor, arte y existencialismo en París y Buenos Aires.",
            portadaResId = R.drawable.rayuela
        )
    )

    suspend fun buscarLibros(query: String): List<Libro> {
        // Simulamos una llamada a la red que tarda 2 segundos.
        delay(2000)

        // Si la búsqueda está vacía, devolvemos todo el catálogo.
        if (query.isBlank()) return catalogoDeLibros
        return catalogoDeLibros.filter {
            it.titulo.contains(query, ignoreCase = true) ||
                    it.autor.contains(query, ignoreCase = true)
        }
    }

    /**
     * Nueva función para buscar un libro por su ID.
     * Simula una búsqueda rápida en la base de datos.
     */
    suspend fun buscarLibroPorId(id: Int): Libro? {
        delay(500) // Simulamos una pequeña latencia
        return catalogoDeLibros.find { it.id == id }
    }
}