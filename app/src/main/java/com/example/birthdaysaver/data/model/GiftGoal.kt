package com.example.birthdaysaver.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gift_goals")
data class GiftGoal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val birthDay: Int,
    val birthMonth: Int,
    val targetAmount: Double,
    val monthlyAmount: Double,
    val startDate: Long,
    val paymentDay: Int,
    var progress: Int = 0
)
