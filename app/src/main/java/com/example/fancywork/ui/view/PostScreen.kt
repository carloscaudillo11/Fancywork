package com.example.fancywork.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.fancywork.ui.components.ErrorDialog
import com.example.fancywork.ui.components.RoundedButton
import com.example.fancywork.ui.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    postViewModel: MainViewModel,
    backStack: () -> Unit,
    navigate: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults
    val state = postViewModel.state
    var isEmptyTitle by remember { mutableStateOf(false) }
    var isEmptyPrice by remember { mutableStateOf(false) }
    var isEmptyDescription by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    fun validateTitle(text: String) {
        isEmptyTitle = text.isBlank()
    }

    fun validateDescription(text: String) {
        isEmptyDescription = text.isBlank()
    }

    fun validatePrice(text: String) {
        isEmptyPrice = text.isBlank()
    }

    if (!state.value.isLoading) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Publicar un servicio",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    scrollBehavior = scrollBehavior.enterAlwaysScrollBehavior(),
                    navigationIcon = {
                        IconButton(onClick = { backStack.invoke() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back",
                            )
                        }
                    }
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
                    .background(MaterialTheme.colorScheme.background)
            ) {
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
                                validateTitle(state.value.title)
                            },
                        value = state.value.title,
                        onValueChange = {
                            postViewModel.onTitleChange(it)
                            validateTitle(state.value.title)
                        },
                        label = { Text("Titulo") },
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
                                validatePrice(state.value.price)
                            },
                        value = state.value.price,
                        onValueChange = {
                            postViewModel.onPriceChange(it)
                            validatePrice(state.value.price)
                        },
                        label = { Text("Precio") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        prefix = {
                            Text(text = "$ ")
                        }
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        shape = RoundedCornerShape(5.dp),
                        value = state.value.description,
                        onValueChange = {
                            postViewModel.onDescriptionChange(it)
                            validateDescription(state.value.description)
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        placeholder = {
                            Text(text = "Descripción")
                        }
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    RoundedButton(
                        text = "Publicar",
                        displayProgressBar = state.value.displayProgressBar,
                        enabled = !isEmptyTitle && !isEmptyPrice && !isEmptyDescription,
                        onClick = {
                            postViewModel.registerDb()
                        }
                    )
                }
            }
        }
    }
    if (state.value.errorMessage != null) {
        ErrorDialog(
            errorMessage = state.value.errorMessage.toString(),
            onDismiss = postViewModel::hideErrorDialog
        )
    }

    if (state.value.successRegisterDB) {
        navigate.invoke()
    }
}
