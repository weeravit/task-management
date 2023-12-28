package co.wareverse.taskmanagement.presentation.passcode.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.wareverse.taskmanagement.core.theme.BackgroundColor
import co.wareverse.taskmanagement.core.theme.KanitFontFamily
import co.wareverse.taskmanagement.core.theme.PinkColor
import co.wareverse.taskmanagement.core.theme.PlaceHolderColor
import co.wareverse.taskmanagement.core.theme.Red500
import co.wareverse.taskmanagement.core.theme.TextColor

@Composable
fun PasscodeContent(
    modifier: Modifier,
    state: PasscodeState = rememberPasscodeState(),
    title: String,
    subtitle: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Header(
            title = title,
            subtitle = subtitle,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CodeLabel(
                horizontalSpacedBy = 16.dp,
                codeLength = state.passcode.length,
                maxLength = 6,
            )

            AnimatedVisibility(
                visible = state.errorMessage.isNotEmpty(),
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                Text(
                    fontFamily = KanitFontFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    color = Red500,
                    textAlign = TextAlign.Center,
                    text = state.errorMessage,
                )
            }
        }

        NumPad(
            modifier = Modifier.padding(horizontal = 40.dp),
            buttonSize = 90.dp,
            verticalSpacedBy = 16.dp,
            onClick = { text ->
                val newPasscode = when {
                    text == "DEL" -> state.passcode.dropLast(1)
                    state.passcode.length < 6 -> state.passcode.plus(text)
                    else -> state.passcode
                }

                state.passcode = newPasscode
            },
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            fontFamily = KanitFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = TextColor,
            textAlign = TextAlign.Center,
            text = title,
        )

        Text(
            fontFamily = KanitFontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            color = PlaceHolderColor,
            textAlign = TextAlign.Center,
            text = subtitle,
        )
    }
}

@Composable
private fun CodeLabel(
    modifier: Modifier = Modifier,
    horizontalSpacedBy: Dp = 8.dp,
    codeLength: Int = 0,
    maxLength: Int = 6,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacedBy),
    ) {
        (1..maxLength).forEach { count ->
            val alpha = when {
                count <= codeLength -> 1f
                else -> 0.3f
            }

            Spacer(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(20.dp)
                    .background(
                        color = PinkColor.copy(
                            alpha = alpha
                        )
                    )
            )
        }
    }
}

@Composable
private fun NumPad(
    modifier: Modifier = Modifier,
    buttonSize: Dp = 80.dp,
    verticalSpacedBy: Dp = 8.dp,
    onClick: (String) -> Unit,
) {
    val numpad = arrayOf(
        arrayOf("1", "2", "3"),
        arrayOf("4", "5", "6"),
        arrayOf("7", "8", "9"),
        arrayOf("", "0", "DEL"),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(verticalSpacedBy),
    ) {
        numpad.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                row.forEach { text ->
                    when {
                        text.isEmpty() -> Spacer(modifier = Modifier.size(buttonSize))
                        else -> NumberButton(
                            modifier = Modifier.size(buttonSize),
                            text = text,
                            onClick = { onClick(text) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NumberButton(
    modifier: Modifier = Modifier,
    bgColor: Color = BackgroundColor,
    textColor: Color = TextColor,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = textColor,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
        ),
        onClick = onClick,
    ) {
        Text(
            fontFamily = KanitFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            text = text,
        )
    }
}

@Composable
fun rememberPasscodeState(
    initialPasscode: String = "",
    initialErrorMessage: String = "",
): PasscodeState {
    return remember(
        initialPasscode,
        initialErrorMessage,
    ) {
        PasscodeState(
            initialPasscode = initialPasscode,
            initialErrorMessage = initialErrorMessage,
        )
    }
}

data class PasscodeState(
    private val initialPasscode: String,
    private val initialErrorMessage: String,
) {
    var passcode by mutableStateOf(initialPasscode)
    var errorMessage by mutableStateOf(initialErrorMessage)
}