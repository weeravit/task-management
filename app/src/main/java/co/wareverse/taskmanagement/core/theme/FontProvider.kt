package co.wareverse.taskmanagement.core.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import co.wareverse.taskmanagement.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val openSans = GoogleFont("Open Sans")
private val kanitFont = GoogleFont("Kanit")

val OpenSansFontFamily = FontFamily(
    Font(googleFont = openSans, fontProvider = provider)
)
val KanitFontFamily = FontFamily(
    Font(googleFont = kanitFont, fontProvider = provider)
)