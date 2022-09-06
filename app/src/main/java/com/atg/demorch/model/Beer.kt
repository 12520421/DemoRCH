package com.atg.demorch.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Beer(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("tagline")
    val tagline: String,
    @Expose
    @SerializedName("first_brewed")
    val first_brewed: String,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("image_url")
    val image_url: String,
    @Expose
    @SerializedName("abv")
    val abv: Double,
    @Expose
    @SerializedName("ibu")
    val ibu: Double,
    @Expose
    @SerializedName("target_fg")
    val target_fg: Double,
    @Expose
    @SerializedName("target_og")
    val target_og: Double,
    @Expose
    @SerializedName("ebc")
    val ebc: Double,
    @Expose
    @SerializedName("srm")
    val srm: Double,
    @Expose
    @SerializedName("ph")
    val ph: Double,
    @Expose
    @SerializedName("attenuation_level")
    val attenuation_level: Double,
    @Expose
    @SerializedName("volume")
    val volume: Volume,
    @Expose
    @SerializedName("boil_volume")
    val boil_volume: Boil_Volume,
)

data class Boil_Volume(
    @Expose
    @SerializedName("value")
    val value: Int,
    @Expose
    @SerializedName("unit")
    val unit: String
)

data class Temp(
    @Expose
    @SerializedName("value")
    val value: Int,
    @Expose
    @SerializedName("unit")
    val unit: String
)

data class Volume(
    @Expose
    @SerializedName("value")
    val value: Int,
    @Expose
    @SerializedName("unit")
    val unit: String
)
