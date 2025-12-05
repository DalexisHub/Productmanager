package com.tecsup.productmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tecsup.productmanager.ui.viewmodel.AuthViewModel
import com.tecsup.productmanager.ui.viewmodel.ProductViewModel

@Composable
fun ProductListScreen(
    nav: NavController,
    vm: ProductViewModel,
    auth: AuthViewModel
) {

    val products by vm.products.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                nav.navigate("productForm/new")
            }) {
                Text("+")
            }
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            Text("Mis Productos", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(20.dp))

            Button(onClick = {
                auth.logout()
                nav.navigate("login") {
                    popUpTo("products") { inclusive = true }
                }
            }) {
                Text("Cerrar sesión")
            }

            LazyColumn {
                items(products.size) { i ->
                    val product = products[i]
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Nombre: ${product.name}")
                            Text("Precio: S/ ${product.price}")
                            Text("Stock: ${product.stock}")
                            Text("Categoría: ${product.category}")

                            Row {
                                TextButton(onClick = {
                                    nav.navigate("productForm/${product.id}")
                                }) { Text("Editar") }

                                TextButton(onClick = { vm.delete(product.id) }) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
