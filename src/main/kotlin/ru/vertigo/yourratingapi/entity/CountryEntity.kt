package ru.vertigo.yourratingapi.entity

import jakarta.persistence.*


@Entity
@Table(name = "countries")
class CountryEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,
        var name: String,
        @Column(name = "ru_name")
        var ruName: String,
        @Column(name = "short_name")
        var shortName: String,
        var priority: Int,
)