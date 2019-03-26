package com.pledg.sdk

import com.pledg.sdk.model.Transaction


interface PledgSDK{
    val viewModel: PledgSDKViewModel
    fun startTransaction(transaction: Transaction)
}