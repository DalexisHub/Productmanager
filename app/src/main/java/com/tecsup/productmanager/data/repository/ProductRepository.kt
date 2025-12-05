package com.tecsup.productmanager.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tecsup.productmanager.data.model.Product
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProductRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val productsRef = db.collection("products")

    fun getProducts() = callbackFlow {
        val uid = auth.currentUser?.uid ?: ""

        val listener = productsRef
            .whereEqualTo("userId", uid)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val list = snapshot?.documents?.map { doc ->
                    doc.toObject(Product::class.java)!!.copy(id = doc.id)
                } ?: emptyList()

                trySend(list)
            }

        awaitClose { listener.remove() }
    }

    suspend fun create(product: Product) {
        val doc = productsRef.document()
        product.id = doc.id
        doc.set(product).await()
    }

    suspend fun update(product: Product) {
        productsRef.document(product.id).set(product).await()
    }

    suspend fun delete(id: String) {
        productsRef.document(id).delete().await()
    }
}
