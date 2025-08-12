package com.example.unitconverterproject

object Converter {

    fun convert(category: String, input: Double, from: String, to: String): Double {
        return when (category) {
            "Length" -> input * lengthUnits[from]!! / lengthUnits[to]!!
            "Weight" -> input * weightUnits[from]!! / weightUnits[to]!!
            "Time" -> input * timeUnits[from]!! / timeUnits[to]!!
            "Speed" -> input * speedUnits[from]!! / speedUnits[to]!!
            "Temperature" -> convertTemperature(input, from, to)
            "Currency" -> input * currencyUnits[from]!! / currencyUnits[to]!!
            else -> 0.0
        }
    }

    private fun convertTemperature(value: Double, from: String, to: String): Double {
        val celsius = when (from) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32) * 5 / 9
            "Kelvin" -> value - 273.15
            else -> 0.0
        }

        return when (to) {
            "Celsius" -> celsius
            "Fahrenheit" -> (celsius * 9 / 5) + 32
            "Kelvin" -> celsius + 273.15
            else -> 0.0
        }
    }

    val unitCategories = listOf("Length", "Weight", "Time", "Speed", "Temperature", "Currency")

    val lengthUnits = mapOf(
        "Kilometer" to 1000.0,
        "Meter" to 1.0,
        "Centimeter" to 0.01,
        "Millimeter" to 0.001,
        "Feet" to 0.3048,
        "Inches" to 0.0254
    )

    val weightUnits = mapOf(
        "Ton" to 1000.0,
        "Kilogram" to 1.0,
        "Gram" to 0.001,
        "Milligram" to 0.000001,
        "Pound" to 0.453592
    )

    val timeUnits = mapOf(
        "Day" to 86400.0,
        "Hour" to 3600.0,
        "Minute" to 60.0,
        "Second" to 1.0
    )

    val speedUnits = mapOf(
        "Meters/Second" to 1.0,
        "Kilometers/Hour" to 0.277778,
        "Miles/Hour" to 0.44704
    )

    val temperatureUnits = listOf("Celsius", "Fahrenheit", "Kelvin")

    val currencyUnits = mapOf(
        "INR" to 1.0,
        "USD" to 0.012,
        "EUR" to 0.011,
        "JPY" to 1.80,
        "GBP" to 0.0095
    )
}
