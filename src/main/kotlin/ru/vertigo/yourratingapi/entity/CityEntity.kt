package ru.vertigo.yourratingapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "cities")
class CityEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    var name: String,
    @Column(name = "ru_name")
    var ruName: String,
    @Column(name = "short_name")
    var shortName: String,
    var priority: Int,
    @ManyToOne
    @JoinColumn(name = "country_id")
    var country: CountryEntity,
)