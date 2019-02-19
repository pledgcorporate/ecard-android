package com.pledgtest.model

data class Transaction(
    val merchant_uid: String,
    val priceInCent: Int,
    val currency: String,
    val customerEmail: String,
    val emal: String,
    val lang: String,
    val reference: String,
    val title: String,
    val subtitle: String
)