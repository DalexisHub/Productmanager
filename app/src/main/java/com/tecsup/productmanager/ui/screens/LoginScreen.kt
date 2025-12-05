package com.tecsup.productmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tecsup.productmanager.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(nav: NavController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Inicio de Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(error, color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            authViewModel.login(email, pass) { ok, msg ->
                if (ok) nav.navigate("products")
                else error = msg ?: "Error"
            }
        }) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = {
            nav.navigate("register")
        }) {
            Text("Registrarme")
        }
    }
}
