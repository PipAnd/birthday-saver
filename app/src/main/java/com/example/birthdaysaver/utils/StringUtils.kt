package com.example.birthdaysaver.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object StringUtils {
    private val decimalFormat = DecimalFormat("#,##0.00")
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    
    fun formatCurrency(amount: Double): String {
        return "${decimalFormat.format(amount)} ₽"
    }
    
    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }
    
    fun getMonthName(month: Int): String {
        val months = arrayOf(
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
        )
        return if (month in 1..12) months[month - 1] else ""
    }
}
