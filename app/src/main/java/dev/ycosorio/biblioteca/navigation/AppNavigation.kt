package dev.ycosorio.biblioteca.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.ycosorio.biblioteca.view.screens.ArticuloScreen
import dev.ycosorio.biblioteca.view.screens.BusquedaScreen
import dev.ycosorio.biblioteca.view.screens.LibroScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "busqueda") {
        composable("busqueda") {
            // Pasamos el navController a la pantalla de búsqueda
            BusquedaScreen( navController = navController)
        }

        // Acepta un argumento 'libroId' de tipo Entero.
        composable(
            route = "detalle/{libroId}",
            arguments = listOf(navArgument("libroId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extraemos el argumento y lo pasamos a la pantalla de detalle.
            val libroId = backStackEntry.arguments?.getInt("libroId")
            requireNotNull(libroId) { "El ID del libro no puede ser nulo" }
            LibroScreen(libroId = libroId, navController = navController)
        }

        composable(
            route = "articulo/{articuloId}",
            arguments = listOf(navArgument("articuloId") { type = NavType.IntType })
        ) { backStackEntry ->
            val articuloId = backStackEntry.arguments?.getInt("articuloId")
            requireNotNull(articuloId) { "El ID del artículo no puede ser nulo" }
            ArticuloScreen(articuloId = articuloId, navController = navController)
        }
    }
}