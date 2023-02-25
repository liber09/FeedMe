package com.example.feedme.data

import com.google.firebase.firestore.DocumentId

data class OrderItem(
    var title: String? = null,
    @DocumentId var documentId : String? = null
)