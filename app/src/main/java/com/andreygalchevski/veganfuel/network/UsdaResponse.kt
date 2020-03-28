package com.andreygalchevski.veganfuel.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class UsdaResponse(
    val report: Report
)

data class Report(
    val sr: String,
    val groups: List<Group>,
    val subset: String,
    val end: Int,
    val start: Int,
    val total: Int,
    val foods: List<Food>
)

data class Group(
    val id: String,
    val description: String
)

data class Food(
    val ndbno: String,
    val name: String,
    val weight: Double,
    val measure: String,
    val nutrients: List<Nutrient>
)

@Parcelize
data class Nutrient(
    @Json(name = "nutrient_id") val id: String,
    @Json(name = "nutrient") val name: String,
    val unit: String? = "",
    val value: String? = "",
    val gm: Double? = 0.0
) : Parcelable
