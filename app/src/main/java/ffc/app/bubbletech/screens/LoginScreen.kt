package ffc.app.bubbletech.screens

// Pqp quanto import *_*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import ffc.app.bubbletech.R
import ffc.app.bubbletech.ui.theme.GrayBackground
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.TextField
import ffc.app.bubbletech.ui.theme.LightGrayText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import ffc.app.bubbletech.ui.theme.ExtraLightGray
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import ffc.app.bubbletech.ui.theme.LightBlue
import ffc.app.bubbletech.ui.theme.SecondaryBlue

// Depois tem quebrar essa tela em pequenos modulos!
@Composable
fun LoginScreen(
    onLogin: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val isVisible = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    val isHelloVisible = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize().zIndex(1f)
    ) {
        AnimatedVisibility(
            visibleState = isHelloVisible,
            exit = ExitTransition.None,
            enter = slideInVertically(
                initialOffsetY = { height -> -height },
                animationSpec = tween(
                    durationMillis = 1200,
                    easing = FastOutSlowInEasing
                )
            )
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(815.dp)
                    .padding(25.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy((-20).dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Olá!",
                        color = Color.White,
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "Bem-vindo ao TechBubble.",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraLight
                    )
                }
            }
        }

        AnimatedVisibility(
            visibleState = isVisible,
            exit = ExitTransition.None,
            enter = slideInVertically(
                initialOffsetY = { height -> height },
                animationSpec = tween(
                    durationMillis = 1200,
                    easing = FastOutSlowInEasing
                )
            )
        ) {
            Box(
                modifier = Modifier
                    .height(650.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            40.dp,
                            40.dp,
                            0.dp,
                            0.dp
                        )
                    )
                    .padding(30.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 112.dp)
                    ) {
                        Text(
                            "Entrar",
                            color = Color.Black,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            "Seu portal de notícias tech.",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = {
                            Text(
                                "E-mail",
                                color = LightGrayText
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Email,
                                contentDescription = null,
                                tint = LightGrayText,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = GrayBackground,
                            unfocusedContainerColor = GrayBackground,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = ExtraLightGray,
                                shape = RoundedCornerShape(12.dp)
                            )
                    )

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = {
                                Text(
                                    "Senha",
                                    color = LightGrayText
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Lock,
                                    contentDescription = null,
                                    tint = LightGrayText,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        isPasswordVisible = !isPasswordVisible
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) {
                                            Icons.Rounded.VisibilityOff
                                        } else {
                                            Icons.Rounded.Visibility
                                        },
                                        contentDescription = if (isPasswordVisible) {
                                            "Ocultar senha"
                                        } else {
                                            "Mostrar senha"
                                        },
                                        tint = LightGrayText,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            visualTransformation = if (isPasswordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = GrayBackground,
                                unfocusedContainerColor = GrayBackground,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = ExtraLightGray,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )

                        Text(
                            "Esqueci minha senha",
                            color = LightBlue,
                            textAlign = TextAlign.End,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth()
                        ) // Mudar para TextButton depois, deixei assim pq n sei fazer ainda.
                    }

                    Button(
                        onClick = {print("Hey")},
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryBlue,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            "Entrar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            thickness = 1.dp,
                            color = ExtraLightGray
                        )

                        Text(
                            "ou",
                            color = ExtraLightGray,
                            fontSize = 14.sp
                        )

                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            thickness = 1.dp,
                            color = ExtraLightGray
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            print("Hello")
                        },
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, ExtraLightGray),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF182030)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_google),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Text(
                            "Continuar com o Google",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            4.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Não tem conta?",
                            color = LightGrayText,
                            fontSize = 14.sp
                        )

                        Text(
                            "Criar conta",
                            color = LightBlue,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        ) // Mudar para TextButton depois, deixei assim pq n sei fazer ainda.
                    }
                }

                Image(
                    painter = painterResource(R.drawable.login_character),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .scale(2.5f)
                        .width(100.dp)
                        .height(260.dp)
                        .offset(y = (-153).dp)
                )
            }
        }
    }
}