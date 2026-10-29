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
    if (t.equals("Taller")) {
        return true
    }
    return false
}

@Composable
fun InscripcionScreen(modifier: Modifier = Modifier) {

    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }

    var taller by rememberSaveable { mutableStateOf("Taller") }

    var aceptaAviso by rememberSaveable { mutableStateOf(false) }

    var inscrito by rememberSaveable { mutableStateOf(false) }

    var validaciones by rememberSaveable { mutableStateOf((false)) }
    var nuevo by rememberSaveable { mutableStateOf((false)) }
    if (nuevo) {
        Box(contentAlignment = Alignment.Center) {

            nuevo = confirmacion(
                nombre = nombre,
                correo = correo,
                edad = edad,
                taller = taller,
                Nueva = {
                    nombre = ""
                    correo = ""
                    edad = ""
                    taller = "Taller"
                    aceptaAviso = false
                    inscrito = false
                })

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
                    value = nombre,
                    onValueChange = { nuevo -> nombre = nuevo },
                    label = { Text("Nombre completo") },
                    placeholder = { Text("Nombre Completo") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !validarNombre(nombre),
                    supportingText = {
                        if (!validarNombre(nombre)) {
                            Text("Minimo 3 caracteres")
                        }
                    }
                )
                TextField(
                    value = correo,
                    onValueChange = { nuevo -> correo = nuevo },
                    label = { Text("Correo") },
                    placeholder = { Text("Correo") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    isError = !validarCorreo(correo),
                    supportingText = {
                        if (!validarCorreo(correo)) {
                            Text("Ingresa un correo valido")
                        }
                    }
                )
                TextField(
                    value = edad,
                    onValueChange = { nuevo -> edad = nuevo.filter { it.isDigit() } },
                    label = { Text("Edad") },
                    placeholder = { Text("Edad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    isError = !validarEdad(edad),
                    supportingText = {
                        if (!validarEdad(edad)) {
                            Text("Edad no valida")
                        }
                    }
                )

                SeleccionarTaller(taller, { taller = it })

            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = aceptaAviso, onCheckedChange = { aceptaAviso = it })
                    Spacer(Modifier.padding(7.dp))
                    Column() {
                        Spacer(Modifier.padding(6.dp))
                        Text(text = "Acepto el aviso de privacidad", textAlign = TextAlign.Center)
                    }
                }
            }
            if (validarNombre(nombre) && aceptaAviso && !correo.equals("") && validarEdad(edad) && !validarTaller(
                    taller
                )
            ) {
                validaciones = true
            } else {
                validaciones = false
            }
            item {
                Button(
                    onClick = {
                        if (validaciones) {

                            inscrito = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = validaciones
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
    Nueva: () -> Unit
): Boolean {
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
    return confirmar;
}

@Composable
fun SeleccionarTaller(
    taller: String,
    cambioTaller: (String) -> Unit,
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

        // Capa invisible encima del campo que recibe el toque
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
                        cambioTaller(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}