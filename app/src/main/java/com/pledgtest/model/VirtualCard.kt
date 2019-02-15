package com.pledgtest.model

import com.squareup.moshi.Json

class VirtualCard {
    @Json(name = "account")
    lateinit var account: Account
    @Json(name = "amount")
    var amount: Float? = null
    @Json(name = "amount_cents")
    var amountCents: Int? = null
    @Json(name = "card_number")
    var cardNumber: String? = null
    @Json(name = "created")
    var created: String? = null
    @Json(name = "currency")
    var currency: String? = null
    @Json(name = "currency_symbol")
    var currencySymbol: String? = null
    @Json(name = "cvc")
    var cvc: String? = null
    @Json(name = "expiration_date")
    var expirationDate: String? = null
    @Json(name = "purchase")
    lateinit var purchase: Purchase
    @Json(name = "state")
    lateinit var state: String
    @Json(name = "uid")
    var uid: String? = null
    @Json(name = "updated")
    var updated: String? = null
    @Json(name = "vcp_reference")
    var vcpReference: String? = null
    @Json(name = "expiry_month")
    var expiryMonth: String? = null
    @Json(name = "expiry_year")
    var expiryYear: String? = null

    override fun toString(): String {
        return "VirtualCard(account=$account, amount=$amount, amountCents=$amountCents, cardNumber=$cardNumber, created=$created, currency=$currency, currencySymbol=$currencySymbol, cvc=$cvc, expirationDate=$expirationDate, purchase=$purchase, state=$state, uid=$uid, updated=$updated, vcpReference=$vcpReference, expiryMonth=$expiryMonth, expiryYear=$expiryYear)"
    }

}
