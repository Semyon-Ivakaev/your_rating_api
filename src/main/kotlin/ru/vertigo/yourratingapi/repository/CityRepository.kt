package ru.vertigo.yourratingapi.repository

import org.springframework.data.repository.CrudRepository
import ru.vertigo.yourratingapi.entity.CityEntity
import ru.vertigo.yourratingapi.entity.CountryEntity

interface CityRepository: CrudRepository<CityEntity, Int> {

    fun deleteAllByCountry(country: CountryEntity)
}