package ru.vertigo.yourratingapi.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vertigo.yourratingapi.dto.CountryDto
import ru.vertigo.yourratingapi.entity.CountryEntity
import ru.vertigo.yourratingapi.exception.CountryNotFoundException
import ru.vertigo.yourratingapi.repository.CountryRepository
import ru.vertigo.yourratingapi.service.CountryService

@Service
class CountryServiceImpl(
        private val countryRepository: CountryRepository
): CountryService {
    override fun getAll(): List<CountryDto> = countryRepository.findByOrderByRuName().map { it.toDto() }

    override fun getById(id: Int): CountryDto = countryRepository.findByIdOrNull(id = id)
            ?.toDto() ?: throw CountryNotFoundException(id)

    override fun search(prefix: String): List<CountryDto> =
            countryRepository.findByRuNameStartsWithIgnoreCaseOrderByRuName(prefix = prefix)
                    .map { it.toDto() }

    override fun create(dto: CountryDto): Int = countryRepository.save(dto.toEntity()).id

    @Transactional
    override fun update(id: Int, dto: CountryDto) {
        var existingCountry = countryRepository.findByIdOrNull(id = id) ?: throw CountryNotFoundException(id)

        existingCountry.apply {
            name = dto.name
            ruName = dto.ruName
            shortName = dto.shortName
            priority = dto.priority
        }
        countryRepository.save(existingCountry)
    }

    @Transactional
    override fun delete(id: Int) {
        val existingCountry = countryRepository.findByIdOrNull(id = id) ?: throw CountryNotFoundException(id)
        countryRepository.deleteById(existingCountry.id)
    }

    private fun CountryEntity.toDto(): CountryDto =
            CountryDto(
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
}