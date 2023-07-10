package powerrangers.eivom.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import powerrangers.eivom.R

val VintageKing = FontFamily(
    Font(R.font.vintageking)
)

val UlagadiRegular = FontFamily(
    Font(R.font.ulagadiregular)
)
val UlagadiBold = FontFamily(
    Font(R.font.ulagadibold)
)
// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = VintageKing,
        fontWeight = FontWeight.Normal,
        letterSpacing = 1.sp,
    ),
    body1 = TextStyle(
        fontFamily = UlagadiRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    h2 = TextStyle (
        fontFamily =  UlagadiBold,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)