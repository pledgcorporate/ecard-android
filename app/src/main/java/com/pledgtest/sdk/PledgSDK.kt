package com.pledgtest.sdk

import com.pledgtest.model.Transaction

interface PledgSDK{
    val viewModel: PledgSDKViewModel
    fun startTransaction(transaction: Transaction)
}