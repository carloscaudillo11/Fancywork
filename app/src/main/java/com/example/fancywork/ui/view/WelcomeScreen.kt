package com.example.fancywork.ui.view


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fancywork.R
import com.example.fancywork.ui.components.ErrorDialog
import com.example.fancywork.ui.components.SocialButton
import com.example.fancywork.ui.viewmodel.WelcomeViewModel
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun WelcomeScreen(
    navigateLogin: () -> Unit,
    welcomeViewModel: WelcomeViewModel,
    navigate: () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()

    val state = welcomeViewModel.state
    val googleAuthState = rememberOneTapSignInState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) Color.Black else Color.White)
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.icono),
                modifier = Modifier
                    .height(100.dp),
                contentDescription = "Welcome Image",
                contentScale = ContentScale.Inside
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.hello),
                modifier = Modifier
                    .height(550.dp),
                contentDescription = "Login Image",
                contentScale = ContentScale.Inside
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(370.dp),
                shape = RoundedCornerShape(
                    topStartPercent = 8,
                    topEndPercent = 8
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "¡Bienvenido a Fancywork!",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 15.sp
                                    )
                                ) {
                                    append("Si accedes, aceptas los")
                                }
                            }
                        )

                        ClickableText(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                ) {
                                    append("términos de servicio")
                                }
                            },
                            onClick = {}
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontSize = 14.sp
                                    )
                                ) {
                                    append("y la ")
                                }
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                    ) {

                        ClickableText(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                    )
                                ) {
                                    append("polítíca de privacidad de Fancywork company.")
                                }
                            },
                            onClick = {}
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Spacer(modifier = Modifier.height(20.dp))

                        SocialButton(
                            modifier = Modifier
                                .width(250.dp)
                                .height(50.dp),
                            text = "Ingresar con Facebook",
                            icon = R.drawable.ic_facebook_logo,
                            shape = RoundedCornerShape(50),
                            borderColor = Color(0xFF1778F2),
                            backgroundColor = Color(0xFF1778F2),
                            color = Color.White,
                            onClicked = {

                            }
                        )

                        SocialButton(
                            modifier = Modifier
                                .width(250.dp)
                                .height(50.dp),
                            text = "Ingresar con Google",
                            icon = R.drawable.ic_google_logo,
                            shape = RoundedCornerShape(50),
                            borderColor = MaterialTheme.colorScheme.outline,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            color = MaterialTheme.colorScheme.onSurface,
                            onClicked = {
                                googleAuthState.open()
                            }
                        )

                        SocialButton(
                            modifier = Modifier
                                .width(250.dp)
                                .height(50.dp),
                            text = "Ingresar con el Correo",
                            icon = R.drawable.outline_email_24,
                            shape = RoundedCornerShape(50),
                            borderColor = MaterialTheme.colorScheme.outline,
                            backgroundColor = Color.Gray,
                            color = Color.White,
                            onClicked = {
                                navigateLogin.invoke()
                            }
                        )
                    }
                }
            }
        }
    }

    OneTapSignInWithGoogle(
        state = googleAuthState,
        clientId = "953963664108-7cb87pn8g2orbi07kagsiuginipbaknh.apps.googleusercontent.com",
        onTokenIdReceived = { tokenId ->
            welcomeViewModel.googleSignIn(
                token = tokenId
            )
        },
        onDialogDismissed = { message ->
            Log.d("googleAuthError", message)
        }
    )

    if (state.value.errorMessage != null) {
        ErrorDialog(
            errorMessage = state.value.errorMessage.toString(),
            onDismiss = welcomeViewModel::hideErrorDialog
        )
    }

    if (state.value.successRegister) {
        welcomeViewModel.registerDb()
    }

    if (state.value.successRegisterDB) {
        navigate.invoke()
    }
}


