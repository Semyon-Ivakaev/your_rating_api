package ru.vertigo.yourratingapi.repository

import org.springframework.data.repository.CrudRepository
import ru.vertigo.yourratingapi.entity.CountryEntity

interface CountryRepository: CrudRepository<CountryEntity, Int> {

    fun findByOrderByRuName(): List<CountryEntity>

    fun findByRuNameStartsWithIgnoreCaseOrderByRuName(prefix: String): List<CountryEntity>
}