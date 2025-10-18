package dev.ycosorio.biblioteca.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Nueva paleta de colores para el tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = BibliotecaPrimaryDark,
    secondary = BibliotecaSecondaryDark,
    tertiary = BibliotecaTertiaryDark,
    error = BibliotecaErrorDark,
    background = Color(0xFF191C1D),
    surface = Color(0xFF191C1D),
    onPrimary = Color(0xFF00373F),
    onSecondary = Color(0xFF4D2411),
    onTertiary = Color(0xFF2C342C),
    onError = Color(0xFF690005),
    onBackground = Color(0xFFE1E3E3),
    onSurface = Color(0xFFE1E3E3),
)

// Nueva paleta de colores para el tema claro
private val LightColorScheme = lightColorScheme(
    primary = BibliotecaPrimary,
    secondary = BibliotecaSecondary,
    tertiary = BibliotecaTertiary,
    error = BibliotecaError,
    background = Color(0xFFFBFDFD),
    surface = Color(0xFFFBFDFD),
    onPrimary = Color.White,
    onSecondary = Color(0xFF2D1500),
    onTertiary = Color.White,
    onError = Color.White,
    onBackground = Color(0xFF191C1D),
    onSurface = Color(0xFF191C1D),
)

@Composable
fun BibliotecaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color está deshabilitado para que nuestra paleta personalizada siempre se use.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
