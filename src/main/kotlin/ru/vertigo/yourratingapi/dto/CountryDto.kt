package ru.vertigo.yourratingapi.dto

data class CountryDto (
        val id: Int? = null,
        var name: String,
        var ruName: String,
        var shortName: String,
        val priority: Int,
        val cities: List<CityDto>,
)