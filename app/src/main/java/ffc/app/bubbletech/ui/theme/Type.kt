package ffc.app.bubbletech.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ffc.app.bubbletech.R

val Poppins = FontFamily(
    Font(R.font.poppins_extra_light, FontWeight.ExtraLight),
    Font(R.font.poppinsregular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

private val defaultTypography = Typography()

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = Poppins),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = Poppins),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = Poppins),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = Poppins),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = Poppins),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = Poppins),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = Poppins),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = Poppins),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = Poppins),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = Poppins),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = Poppins),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = Poppins),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = Poppins),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = Poppins),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = Poppins)
)
