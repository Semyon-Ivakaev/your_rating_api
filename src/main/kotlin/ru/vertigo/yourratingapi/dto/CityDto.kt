package ru.vertigo.yourratingapi.dto

data class CityDto(
    val id: Int? = null,
    var name: String,
    var ruName: String,
    var shortName: String,
    var priority: Int,
)
