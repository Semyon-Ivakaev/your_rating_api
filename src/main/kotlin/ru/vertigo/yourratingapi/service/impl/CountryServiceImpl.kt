package ru.vertigo.yourratingapi.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vertigo.yourratingapi.dto.CityDto
import ru.vertigo.yourratingapi.dto.CountryDto
import ru.vertigo.yourratingapi.entity.CityEntity
import ru.vertigo.yourratingapi.entity.CountryEntity
import ru.vertigo.yourratingapi.exception.CountryNotFoundException
import ru.vertigo.yourratingapi.repository.CityRepository
import ru.vertigo.yourratingapi.repository.CountryRepository
import ru.vertigo.yourratingapi.service.CountryService

@Service
class CountryServiceImpl(
        private val countryRepository: CountryRepository,
    private val cityRepository: CityRepository,
): CountryService {
    override fun getAll(): List<CountryDto> = countryRepository.findByOrderByRuName().map { it.toDto() }

    override fun getById(id: Int): CountryDto = countryRepository.findByIdOrNull(id = id)
            ?.toDto() ?: throw CountryNotFoundException(id)

    override fun search(prefix: String): List<CountryDto> =
            countryRepository.findByRuNameStartsWithIgnoreCaseOrderByRuName(prefix = prefix)
                    .map { it.toDto() }

    @Transactional
    override fun create(dto: CountryDto): Int {
        val countryEntity = countryRepository.save(dto.toEntity())

        val cities = dto.cities.map { it.toEntity(country = countryEntity) }
        cityRepository.saveAll(cities)
        return countryEntity.id
    }

    @Transactional
    override fun update(id: Int, dto: CountryDto) {
        var existingCountry = countryRepository.findByIdOrNull(id = id) ?: throw CountryNotFoundException(id)

        existingCountry.apply {
            name = dto.name
            ruName = dto.ruName
            shortName = dto.shortName
            priority = dto.priority
        }
        existingCountry = countryRepository.save(existingCountry)

        val cities = dto.cities.map { it.toEntity(country = existingCountry) }
        cityRepository.deleteAllByCountry(existingCountry)
        cityRepository.saveAll(cities)
    }

    @Transactional
    override fun delete(id: Int) {
        val existingCountry = countryRepository.findByIdOrNull(id = id) ?: throw CountryNotFoundException(id)

        cityRepository.deleteAllByCountry(existingCountry)

        countryRepository.deleteById(existingCountry.id)
    }

    private fun CountryEntity.toDto(): CountryDto =
            CountryDto(
                    id = this.id,
                    name = this.name,
                    ruName = this.ruName,
                    shortName = this.shortName,
                    priority = this.priority,
                    cities = this.cities.map { it.toDto() }
            )

    private fun CityEntity.toDto(): CityDto =
        CityDto(
            id = this.id,
            name = this.name,
            ruName = this.ruName,
            shortName = this.shortName,
            priority = this.priority,
        )

    private fun CountryDto.toEntity(): CountryEntity =
            CountryEntity(
                    id = 0,
                    name = this.name,
                    ruName = this.ruName,
                    shortName = this.shortName,
                    priority = this.priority,
            )

    private fun CityDto.toEntity(country: CountryEntity): CityEntity =
        CityEntity(
            id = 0,
            name = this.name,
            ruName = this.ruName,
            shortName = this.shortName,
            country = country,
            priority = this.priority,
        )
}