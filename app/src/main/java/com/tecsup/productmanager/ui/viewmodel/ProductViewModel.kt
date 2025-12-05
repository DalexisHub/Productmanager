package com.tecsup.productmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.productmanager.data.model.Product
import com.tecsup.productmanager.data.repository.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repo = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        viewModelScope.launch {
            repo.getProducts().collectLatest {
                _products.value = it
            }
        }
    }

    fun create(name: String, price: Double, stock: Int, category: String, onDone: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val product = Product(
            userId = uid,
            name = name,
            price = price,
            stock = stock,
            category = category
        )

        viewModelScope.launch {
            repo.create(product)
            onDone(true)
        }
    }

    fun update(id: String, name: String, price: Double, stock: Int, category: String, onDone: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val product = Product(
            id = id,
            userId = uid,
            name = name,
            price = price,
            stock = stock,
            category = category
        )

        viewModelScope.launch {
            repo.update(product)
            onDone(true)
        }
    }

    fun delete(id: String) {
        viewModelScope.launch {
            repo.delete(id)
        }
    }
}
