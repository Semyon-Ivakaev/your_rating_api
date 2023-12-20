package ru.vertigo.yourratingapi.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.vertigo.yourratingapi.dto.CountryDto
import ru.vertigo.yourratingapi.service.CountryService

@RestController
@RequestMapping("/countries")
class CountryController (
        private val countryService: CountryService
) {
    @GetMapping
    fun getAll(): List<CountryDto> = countryService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Int): CountryDto = countryService.getById(id = id)

    @GetMapping("/search")
    fun searchCountries(@RequestParam("text") prefix: String): List<CountryDto> =
            countryService.search(prefix = prefix)

    @PostMapping
    fun create(@RequestBody dto: CountryDto): Int =
            countryService.create(dto = dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody dto: CountryDto) {
        countryService.update(id = id, dto = dto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        countryService.delete(id = id)
    }
}