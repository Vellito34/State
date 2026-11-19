package com.example.state.Screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun InicioScreen(modifier: Modifier,viewModel: loginViewModel = viewModel() ) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (!state.correcto) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(Modifier.padding(30.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.Blue)
                    )
                    Spacer(Modifier.padding(10.dp))
                    Text(text = "Clinica Vida", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Spacer(Modifier.padding(5.dp))
                    Text(text = "Consultar tus citas medicas", fontWeight = FontWeight.Light)
                }
            }
            item {
                OutlinedTextField(
                    value = state.correo,
                    onValueChange = viewModel::correoChange,
                    label = { Text("Correo") },
                    placeholder = { Text("Correo") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.padding(5.dp))
                OutlinedTextField(
                    value = state.contra,
                    onValueChange = viewModel::contraChange,
                    label = { Text("Contraseña") },
                    visualTransformation = if (state.verContra) {
                        VisualTransformation.None}else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = viewModel::verContraChange){
                            if (state.verContra){ Text("-") } else {Text("0")}
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = state.recordar, onCheckedChange = viewModel::recordarChange)
                    Text("Recordarme")
                }
            }
            item {
                Spacer(Modifier.padding(10.dp))
                if (state.intentos >= 1) {
                    Card(
                        modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.Red),
                    ) {
                        Text(text = "Correo o contraseña incorrectos", fontWeight = FontWeight.Bold)
                        Text("(intento ${state.intentos} de 3)")
                    }
                }
                Spacer(Modifier.padding(10.dp))
                Button(
                    onClick = viewModel::correcto,
                    enabled = state.intentos < 3, modifier = Modifier.fillMaxWidth()
                ) {Text("Entrar") }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Bienvenido")
            Button(
                onClick = viewModel::correctoChange,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Cerrar Secion") }
        }
    }
}

