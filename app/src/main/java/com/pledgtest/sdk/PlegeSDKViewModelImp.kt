package com.pledgtest.sdk

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.pledgtest.model.Response
import com.pledgtest.model.Transaction
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PlegeSDKViewModelImp : PlegeSDKViewModel, BaseObservable() {


    private val URL = "https://ecard-front-mobile.herokuapp.com/purchase"
    private val embeded = "1"
    private val sdkMobile = 1

    override val onActiveTransaction = MutableLiveData<Boolean>()
    override val onPurchaseSuccess = MutableLiveData<Response>()
    override val onPurchaseError = MutableLiveData<Boolean>()
    override val onScrollTopEvent = MutableLiveData<Boolean>()
    override val onCloseEvent = MutableLiveData<Boolean>()
    lateinit var transaction: Transaction
    fun generateUrl() =
        "$URL?" +
                "embeded=$embeded" +
                "&merchant_uid=${transaction.merchant_uid}" +
                "&amount_cents=${transaction.priceInCent}" +
                "&title=${transaction.title}" +
                "&subtitle=${transaction.subtitle}" +
                "&currency=${transaction.currency}" +
                "&customer-email=${parseToEmail(transaction.customerEmail)}" +
                "&email=${parseToEmail(transaction.emal)}" +
                "&lang=${transaction.lang}" +
                "&sdk_mobile=$sdkMobile" +
                "&reference=${transaction.reference}"

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

    override fun startTransaction(transaction: Transaction) {
        this.transaction = transaction
        onActiveTransaction.value = true
    }

    private fun scrollToTop() {
        onScrollTopEvent.value = true
    }

    private fun nothing() {

    }

    private fun close() {

    }

    private fun sendSuccess(response: Response) {
        onPurchaseSuccess.value = response
    }

    private fun sendError() {
        onPurchaseError.value = true
    }

}