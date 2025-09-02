package com.example.birthdaysaver.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.birthdaysaver.data.model.GiftGoal

@Dao
interface GiftGoalDao {
    @Query("SELECT * FROM gift_goals")
    fun getAllGoals(): LiveData<List<GiftGoal>>

    @Insert
    suspend fun insert(goal: GiftGoal)

    @Update
    suspend fun update(goal: GiftGoal)

    @Delete
    suspend fun delete(goal: GiftGoal)
}
