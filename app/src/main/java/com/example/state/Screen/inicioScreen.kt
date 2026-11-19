package com.example.state.Screen

import android.service.autofill.OnClickAction
import android.view.Menu
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow

fun validarCorreo(c: String): Boolean {
    var con = 0
    c.forEach { char ->
        if (char == ('@') || char == '.') {
            con++
            if (con == 2) {
                return true
            }
        }
    }
    return false
}

fun validarNombre(n: String): Boolean {
    if (n.count() > 2 && !n.equals("")) {
        return true
    }
    return false
}

fun validarEdad(e: String): Boolean {
    var numero: Int? = e.toIntOrNull()
    if (numero != null) {
        var num = numero

        if (num >= 12 && num <= 99) {
            return true
        }
    }
    return false
}

fun validarTaller(t: String): Boolean {
    return t.isNotEmpty()
}

@Composable
fun InscripcionScreen(
    modifier: Modifier = Modifier,
    viewModel: InscripcionViewModel = viewModel()
) {

    val state by viewModel.uiState.collectAsState()

    val formularioValido = validarNombre(state.nombre) &&
            validarCorreo(state.correo) &&
            validarEdad(state.edad) &&
            validarTaller(state.taller) &&
            state.aceptaAviso

    if (state.inscrito) {
        Box(contentAlignment = Alignment.Center) {

            confirmacion(
                nombre = state.nombre,
                correo = state.correo,
                edad = state.edad,
                taller = state.taller,

            )

        }

    } else {
        LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(24.dp)) {
            item {
                Box(contentAlignment = Alignment.Center) {
                    Text("Inscripcion a talleres")
                    Spacer(Modifier.padding(10.dp))
                }
            }

            item {
                Text(text = "Casa del Lago ~ cupo limitado")

                OutlinedTextField(
                    value = state.nombre,
                    onValueChange = viewModel::nombreChange,
                    label = { Text("Nombre completo") },
                    placeholder = { Text("Nombre Completo") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !validarNombre(state.nombre),
                    supportingText = {
                        if (!validarNombre(state.nombre)) {
                            Text("Minimo 3 caracteres")
                        }
                    }
                )
                TextField(
                    value = state.correo,
                    onValueChange = viewModel::correoChange,
                    label = { Text("Correo") },
                    placeholder = { Text("Correo") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    isError = !validarCorreo(state.correo),
                    supportingText = {
                        if (!validarCorreo(state.correo)) {
                            Text("Ingresa un correo valido")
                        }
                    }
                )
                TextField(
                    value = state.edad,
                    onValueChange = viewModel::edadChange,
                    label = { Text("Edad") },
                    placeholder = { Text("Edad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    isError = !validarEdad(state.edad),
                    supportingText = {
                        if (!validarEdad(state.edad)) {
                            Text("Edad no valida")
                        }
                    }
                )

                SeleccionarTaller(state.taller, onTallerChange = viewModel::tallerChange)

            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = state.aceptaAviso, onCheckedChange = viewModel::avisoChange)
                    Spacer(Modifier.padding(7.dp))
                    Column() {
                        Spacer(Modifier.padding(6.dp))
                        Text(text = "Acepto el aviso de privacidad", textAlign = TextAlign.Center)
                    }
                }
            }

            item {
                Button(
                    onClick = viewModel::inscribir,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = formularioValido
                ) { Text(text = "Inscribirme") }
                Text(
                    text = "Quedan 6 lugares en este taller",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
}

@Composable
fun confirmacion(
    modifier: Modifier = Modifier.fillMaxSize(),
    nombre: String,
    edad: String,
    correo: String,
    taller: String,
){
    var confirmar by rememberSaveable { mutableStateOf(true) };
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()

        ) {

            Column() {
                Text(
                    text = "Inscripcion realizada",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
                Spacer(Modifier.padding(10.dp))
                Text(
                    text = "Nombre: $nombre",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "Correo: $correo",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "Edad: $edad",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "Taller: $taller",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
                Button(onClick = {
                    confirmar = false
                }, modifier = Modifier.fillMaxWidth()) { Text("Aceptar") }
            }
        }

    }
}

@Composable
fun SeleccionarTaller(
    taller: String,
    onTallerChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val talleres = listOf("Pintura", "Fotografía", "Guitarra")
    var expandido by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = taller,
            onValueChange = {},
            readOnly = true,
            label = { Text("Taller") },
            trailingIcon = { Text("V") },
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expandido = true }
        )

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            talleres.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onTallerChange(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}