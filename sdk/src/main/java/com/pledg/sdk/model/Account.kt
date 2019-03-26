package com.pledg.sdk.model

import com.squareup.moshi.Json

class Account {
    @Json(name = "uid")
    var uid: String? = null

    override fun toString(): String {
        return "Account(uid=$uid)"
    }

}
