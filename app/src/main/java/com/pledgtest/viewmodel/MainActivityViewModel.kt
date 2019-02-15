package com.pledgtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pledgtest.model.Response
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivityViewModel : ViewModel() {


    private val URL = "https://ecard-front-mobile.herokuapp.com/purchase"
    private val embeded = "1"
    private val merchant_uid = "mer_8ec99f9a-f650-4893-a4a3-16f20e16bb66"
    private val priceInCent = "30550"
    private val currency: String = "EUR"
    private val customerEmail = "sales@pledge.co"
    private val emal: String = "lukasz@lukas.com"
    private val lang: String = "en_GB"
    private val sdkMobile = 1
    private val reference: String = "order_123"
    val title = "STAY IN LONDON"
    val subtitle = "Fly + Hotel 2 nights (3 rooms)"
    val transaction = MutableLiveData<Boolean>()
    val onPurchaseSuccess = MutableLiveData<Response>()
    val onPurchaseError = MutableLiveData<Boolean>()
    val onScrollTopEvent = MutableLiveData<Boolean>()
    val onCloseEvent = MutableLiveData<Boolean>()
    fun generateUrl() =
        "$URL?" +
                "embeded=$embeded" +
                "&merchant_uid=$merchant_uid" +
                "&amount_cents=$priceInCent" +
                "&title=$title" +
                "&subtitle=$subtitle" +
                "&currency=$currency" +
                "&customer-email=${parseToEmail(customerEmail)}" +
                "&email=${parseToEmail(emal)}" +
                "&lang=$lang" +
                "&sdk_mobile=$sdkMobile" +
                "&reference=$reference"


    private fun parseToEmail(email: String) =
        email.replace("@", "%40")

    fun onMessage(value: String) {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adpater = moshi.adapter(Response::class.java)
        val response = adpater.fromJson(value)
        response?.let {
            when (it.name) {
                "SCROLL_TOP" -> scrollToTop()
                "PLEDG_SCROLL_TO_TOP" -> scrollToTop()
                "VIRTUAL_CARD_SUCCESS" -> sendSuccess(response)
                "PLEDG_SUCCESS" -> sendSuccess(response)
                "CANCEL_CLOSE" -> close()
                "PLEDG_CANCEL_CLOSE" -> close()
                "PLEDG_SHARE_PAYMENT" -> nothing()
                "PLEDG_CONFIRMED" -> sendSuccess(response)
            }

        }
    }

    private fun scrollToTop() {
        onScrollTopEvent.value = true
    }

    private fun nothing() {

    }
    private fun close(){

    }

    fun onUrlClick() {
        transaction.value = true
    }

    private fun sendSuccess(response: Response) {
        onPurchaseSuccess.value = response
    }

    private fun sendError() {
        onPurchaseError.value = true
    }

}