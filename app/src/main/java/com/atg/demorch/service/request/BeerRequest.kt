package com.atg.demorch.service.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BeerRequest(
    @Expose
    @SerializedName("brewed_before")
    val brewedBefore: String? = null,
    @Expose
    @SerializedName("per")
    val per: Int? = null,
    @Expose
    @SerializedName("per_page")
    val perPage: Int? = null,
)