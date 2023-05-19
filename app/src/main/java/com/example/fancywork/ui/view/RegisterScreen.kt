package com.example.fancywork.ui.view


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.fancywork.ui.components.RoundedButton
import com.example.fancywork.ui.components.ErrorDialog
import com.example.fancywork.ui.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateLogin: () -> Unit,
    registerViewModel: RegisterViewModel,
    navigateMain: () -> Unit
) {
    var isEmptyName by remember { mutableStateOf(false) }
    var isEmptyEmail by remember { mutableStateOf(false) }
    var isEmptyPhone by remember { mutableStateOf(false) }
    var isEmptyPassword by remember { mutableStateOf(false) }
    var isEmptyConfirmPass by remember { mutableStateOf(false) }
    val darkTheme = isSystemInDarkTheme()
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var isPhoneError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var errorPhoneMessage = ""
    val charLimit = 10
    val state = registerViewModel.state

    fun validate(text: String) {
        isPhoneError = text.length > charLimit
    }

    fun validateName(text: String) {
        isEmptyName = text.isBlank()
    }

    fun validateEmail(text: String) {
        isEmptyEmail = text.isBlank()
    }

    fun validatePhone(text: String) {
        isEmptyPhone = text.isBlank()
    }

    fun validatePass(text: String) {
        isEmptyPassword = text.isBlank()
    }

    fun validateConfirmPass(text: String) {
        isEmptyConfirmPass = text.isBlank()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (darkTheme) Color.Black else Color.White)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        navigateLogin.invoke()
                    }
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back Icon",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = "Crea una cuenta",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            validateName(state.value.userName)
                        },
                    value = state.value.userName,
                    onValueChange = {
                        registerViewModel.onUserNameChange(it)
                        validateName(state.value.userName)
                    },
                    label = { Text("Nombre Completo") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    visualTransformation = VisualTransformation.None
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            validateEmail(state.value.userEmail)
                        },
                    value = state.value.userEmail,
                    onValueChange = {
                        registerViewModel.onUserEmailChange(it)
                        validateEmail(state.value.userEmail)
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
                    value = state.value.userPhone,
                    onValueChange = {
                        registerViewModel.onUserPhoneChange(it)
                        validate(state.value.userPhone)
                        validatePhone(state.value.userPhone)
                    },
                    label = { Text(if (isPhoneError) "Telefono*" else "Telefono") },
                    supportingText = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Limite: ${state.value.userPhone.length}/$charLimit",
                            textAlign = TextAlign.End
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorPhoneMessage,
                            textAlign = TextAlign.Start
                        )
                    },
                    isError = isPhoneError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ) {
                        validate(state.value.userPhone)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "Icon Phone"
                        )
                    },
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            if (isPhoneError) errorPhoneMessage =
                                "El campo no debe ser mayor a 10 digitos"
                            validatePhone(state.value.userPhone)
                        }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            validatePass(state.value.userPassword)
                        },
                    value = state.value.userPassword,
                    onValueChange = {
                        registerViewModel.onUserPasswordChange(it)
                        validatePass(state.value.userPassword)
                    },
                    label = {
                        Text(
                            text = "contraseña"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.None,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisibility = !passwordVisibility }
                        ) {
                            Icon(
                                imageVector = if (passwordVisibility) {
                                    Icons.Default.Lock
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

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics {
                            validateConfirmPass(state.value.userConfirmPassword)
                        },
                    value = state.value.userConfirmPassword,
                    onValueChange = {
                        registerViewModel.onUserConfirmPasswordChange(it)
                        validateConfirmPass(state.value.userConfirmPassword)
                    },
                    label = { Text("Confirmar contraseña") },
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
                            onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }
                        ) {
                            Icon(
                                imageVector = if (confirmPasswordVisibility) {
                                    Icons.Filled.Lock
                                } else {
                                    Icons.Outlined.Lock
                                },
                                contentDescription = if (confirmPasswordVisibility)
                                    "Show password" else "Hide password"
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisibility)
                        VisualTransformation.None
                    else PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                RoundedButton(
                    text = "Registrar",
                    displayProgressBar = state.value.displayProgressBar,
                    enabled = !isEmptyName && !isEmptyEmail && !isEmptyPhone
                            && !isEmptyPassword && !isEmptyConfirmPass && !isPhoneError,
                    onClick = {
                        registerViewModel.register()
                    }
                )

                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            append("Ya tienes una cuenta?")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(" Ingresar")
                        }
                    },
                    onClick = {
                        navigateLogin.invoke()
                    }
                )
            }
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
    if (state.value.errorMessage != null) {
        ErrorDialog(
            errorMessage = state.value.errorMessage.toString(),
            onDismiss = registerViewModel::hideErrorDialog
        )
    }

    if (state.value.successRegister) {
        registerViewModel.registerDb()
    }

    if (state.value.successRegisterDB) {
        navigateMain.invoke()
    }


}


