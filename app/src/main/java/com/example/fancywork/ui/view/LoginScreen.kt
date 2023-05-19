package com.example.fancywork.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.fancywork.R
import com.example.fancywork.ui.components.ErrorDialog
import com.example.fancywork.ui.components.RoundedButton
import com.example.fancywork.ui.viewmodel.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navigateWelcome: () -> Unit,
    navigateRegister: () -> Unit,
    navigateMain: () -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    var isEmptyEmail by remember { mutableStateOf(false) }
    var isEmptyPass by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val darkTheme = isSystemInDarkTheme()
    val state = loginViewModel.state

    fun validateEmail(text: String) {
        isEmptyEmail = text.isBlank()
    }

    fun validatePassword(text: String) {
        isEmptyPass = text.isBlank()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (darkTheme) Color.Black else Color.White)
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { navigateWelcome.invoke() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Icon",
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.logo),
            modifier = Modifier.height(450.dp),
            contentDescription = "Login Image",
            contentScale = ContentScale.Inside
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
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
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "¡Hola de nuevo!",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Ingresa a tu cuenta",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics {
                                    validateEmail(state.value.email)
                                },
                            value = state.value.email,
                            onValueChange = {
                                loginViewModel.onUserEmailChange(it)
                                validateEmail(state.value.email)
                            },
                            label = { Text("Email") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                capitalization = KeyboardCapitalization.None,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Email,
                                    contentDescription = "Icon Email"
                                )
                            },

                            visualTransformation = VisualTransformation.None,
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics {
                                    validatePassword(state.value.password)
                                },
                            value = state.value.password,
                            onValueChange = {
                                loginViewModel.onUserPasswordChange(it)
                                validatePassword(state.value.password)
                            },
                            label = { Text("Contraseña") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                capitalization = KeyboardCapitalization.None,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.clearFocus()
                                }
                            ),
                            trailingIcon = {
                                IconButton(
                                    onClick = { passwordVisibility = !passwordVisibility }
                                ) {
                                    Icon(
                                        imageVector = if (passwordVisibility) {
                                            Icons.Filled.Lock
                                        } else {
                                            Icons.Outlined.Lock
                                        },
                                        contentDescription = if (passwordVisibility)
                                            "Show password" else "Hide password"
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisibility)
                                VisualTransformation.None
                            else PasswordVisualTransformation()
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "¿Olvidaste tu contraseña?",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RoundedButton(
                            text = "Ingresar",
                            displayProgressBar = state.value.displayProgressBar,
                            enabled = !isEmptyEmail && !isEmptyPass,
                            onClick = {
                                loginViewModel.login()
                            }
                        )

                        ClickableText(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                ) {
                                    append("¿Aun no tienes cuenta? ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(" Registrar")
                                }
                            }
                        ) {
                            navigateRegister.invoke()
                        }
                    }
                }
            }

        }
    }
    if (state.value.errorMessage != null) {
        ErrorDialog(
            errorMessage = state.value.errorMessage.toString(),
            onDismiss = loginViewModel::hideErrorDialog
        )
    }

    if(state.value.successLogin){
        navigateMain.invoke()
    }
}