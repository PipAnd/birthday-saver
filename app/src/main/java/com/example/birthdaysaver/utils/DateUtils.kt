package com.example.birthdaysaver.utils

import java.util.*

object DateUtils {
    
    fun monthsUntil(birthDay: Int, birthMonth: Int): Int {
        val now = Calendar.getInstance()
        val currentYear = now.get(Calendar.YEAR)
        val currentMonth = now.get(Calendar.MONTH)
        val currentDay = now.get(Calendar.DAY_OF_MONTH)
        
        var targetYear = currentYear
        // Если день рождения уже был в этом году, считаем до следующего
        if (currentMonth + 1 > birthMonth || (currentMonth + 1 == birthMonth && currentDay > birthDay)) {
            targetYear++
        }
        
        val birthDate = Calendar.getInstance().apply {
            set(targetYear, birthMonth - 1, birthDay)
        }
        
        val diffInMillis = birthDate.timeInMillis - now.timeInMillis
        val daysDiff = diffInMillis / (1000 * 60 * 60 * 24)
        
        // Приблизительное количество месяцев
        return (daysDiff / 30.44).toInt().coerceAtLeast(1)
    }
    
    fun calculateMonthlyAmount(target: Double, months: Int): Double {
        return if (months > 0) target / months else target
    }
    
    fun getNextPaymentDate(paymentDay: Int): Date {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        
        if (currentDay >= paymentDay) {
            calendar.add(Calendar.MONTH, 1)
        }
        calendar.set(Calendar.DAY_OF_MONTH, paymentDay)
        
        return calendar.time
    }
}
