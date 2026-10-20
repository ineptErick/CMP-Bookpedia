package com.plcoding.bookpedia.core.presentation

import com.plcoding.bookpedia.core.domain.Language
import kotlinx.datetime.LocalDate

/**
 * Utility for formatting numbers and dates based on the active [Language].
 */
object Formatter {
    
    fun formatRating(rating: Double?, language: Language): String {
        if (rating == null) return "-"
        return if (language == Language.RUSSIAN) {
            // Russian uses comma as decimal separator
            rating.toString().replace(".", ",")
        } else {
            rating.toString()
        }
    }

    fun formatNumber(number: Int?, language: Language): String {
        if (number == null) return "-"
        val str = number.toString()
        return if (language == Language.RUSSIAN) {
            // Russian often uses spaces as thousands separators
            str.reversed().chunked(3).joinToString(" ").reversed()
        } else {
            // English uses commas as thousands separators
            str.reversed().chunked(3).joinToString(",").reversed()
        }
    }

    fun formatDate(date: LocalDate?, language: Language): String {
        if (date == null) return "-"
        return if (language == Language.RUSSIAN) {
            "${date.dayOfMonth.toString().padStart(2, '0')}.${date.monthNumber.toString().padStart(2, '0')}.${date.year}"
        } else {
            "${date.monthNumber.toString().padStart(2, '0')}/${date.dayOfMonth.toString().padStart(2, '0')}/${date.year}"
        }
    }
}
