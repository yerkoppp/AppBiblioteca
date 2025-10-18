package dev.ycosorio.biblioteca.data.model

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val disponible: Boolean,
    val descripcion: String,
    val portadaResId: Int
)