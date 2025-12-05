package com.tecsup.productmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tecsup.productmanager.ui.viewmodel.ProductViewModel

@Composable
fun ProductFormScreen(
    nav: NavController,
    id: String,
    vm: ProductViewModel
) {

    val products by vm.products.collectAsState()
    val editing = id != "new"

    val existing = products.find { it.id == id }

    var name by remember { mutableStateOf(existing?.name ?: "") }
    var price by remember { mutableStateOf(existing?.price?.toString() ?: "") }
    var stock by remember { mutableStateOf(existing?.stock?.toString() ?: "") }
    var category by remember { mutableStateOf(existing?.category ?: "") }

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(20.dp)
        ) {

            Text(
                if (editing) "Editar producto" else "Nuevo producto",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
            OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Precio") })
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") })
            OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Categoría") })

            Spacer(Modifier.height(20.dp))

            Button(onClick = {

                if (editing) {
                    vm.update(
                        id = id,
                        name = name,
                        price = price.toDouble(),
                        stock = stock.toInt(),
                        category = category
                    ) {
                        nav.popBackStack()
                    }
                } else {
                    vm.create(
                        name = name,
                        price = price.toDouble(),
                        stock = stock.toInt(),
                        category = category
                    ) {
                        nav.popBackStack()
                    }
                }

            }) {
                Text("Guardar")
            }
        }
    }
}
