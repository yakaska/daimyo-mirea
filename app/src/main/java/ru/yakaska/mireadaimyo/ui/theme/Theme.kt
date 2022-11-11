package ru.yakaska.mireadaimyo.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val daimyoDarkColorScheme = darkColorScheme(
    primary = daimyoDarkPrimary,
    onPrimary = daimyoDarkOnPrimary,
    primaryContainer = daimyoDarkPrimaryContainer,
    onPrimaryContainer = daimyoDarkOnPrimaryContainer,
    inversePrimary = daimyoDarkPrimaryInverse,
    secondary = daimyoDarkSecondary,
    onSecondary = daimyoDarkOnSecondary,
    secondaryContainer = daimyoDarkSecondaryContainer,
    onSecondaryContainer = daimyoDarkOnSecondaryContainer,
    tertiary = daimyoDarkTertiary,
    onTertiary = daimyoDarkOnTertiary,
    tertiaryContainer = daimyoDarkTertiaryContainer,
    onTertiaryContainer = daimyoDarkOnTertiaryContainer,
    error = daimyoDarkError,
    onError = daimyoDarkOnError,
    errorContainer = daimyoDarkErrorContainer,
    onErrorContainer = daimyoDarkOnErrorContainer,
    background = daimyoDarkBackground,
    onBackground = daimyoDarkOnBackground,
    surface = daimyoDarkSurface,
    onSurface = daimyoDarkOnSurface,
    inverseSurface = daimyoDarkInverseSurface,
    inverseOnSurface = daimyoDarkInverseOnSurface,
    surfaceVariant = daimyoDarkSurfaceVariant,
    onSurfaceVariant = daimyoDarkOnSurfaceVariant,
    outline = daimyoDarkOutline
)

private val daimyoLightColorScheme = lightColorScheme(
    primary = daimyoLightPrimary,
    onPrimary = daimyoLightOnPrimary,
    primaryContainer = daimyoLightPrimaryContainer,
    onPrimaryContainer = daimyoLightOnPrimaryContainer,
    inversePrimary = daimyoLightPrimaryInverse,
    secondary = daimyoLightSecondary,
    onSecondary = daimyoLightOnSecondary,
    secondaryContainer = daimyoLightSecondaryContainer,
    onSecondaryContainer = daimyoLightOnSecondaryContainer,
    tertiary = daimyoLightTertiary,
    onTertiary = daimyoLightOnTertiary,
    tertiaryContainer = daimyoLightTertiaryContainer,
    onTertiaryContainer = daimyoLightOnTertiaryContainer,
    error = daimyoLightError,
    onError = daimyoLightOnError,
    errorContainer = daimyoLightErrorContainer,
    onErrorContainer = daimyoLightOnErrorContainer,
    background = daimyoLightBackground,
    onBackground = daimyoLightOnBackground,
    surface = daimyoLightSurface,
    onSurface = daimyoLightOnSurface,
    inverseSurface = daimyoLightInverseSurface,
    inverseOnSurface = daimyoLightInverseOnSurface,
    surfaceVariant = daimyoLightSurfaceVariant,
    onSurfaceVariant = daimyoLightOnSurfaceVariant,
    outline = daimyoLightOutline
)

@Composable
fun MireaDaimyoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val mireaDaimyoColorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
        darkTheme -> daimyoDarkColorScheme
        else -> daimyoLightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = mireaDaimyoColorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = mireaDaimyoColorScheme,
        typography = mireaDaimyoType,
        shapes = mireaDaimyoShapes,
        content = content
    )
}