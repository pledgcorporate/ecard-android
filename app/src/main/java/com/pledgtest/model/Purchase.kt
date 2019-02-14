package com.pledgtest.model

import com.squareup.moshi.Json

class Purchase {
    @Json(name = "uid")
    var uid: String? = null

    override fun toString(): String {
        return "Purchase(uid=$uid)"
    }

}
