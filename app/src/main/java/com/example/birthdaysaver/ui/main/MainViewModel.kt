package com.example.birthdaysaver.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.birthdaysaver.data.db.AppDatabase
import com.example.birthdaysaver.data.db.GiftGoalDao
import com.example.birthdaysaver.data.model.GiftGoal
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: GiftGoalDao
    val allGoals: LiveData<List<GiftGoal>>
    
    init {
        val dao = AppDatabase.getDatabase(application).giftGoalDao()
        repository = dao
        allGoals = repository.getAllGoals()
    }
    
    fun insert(goal: GiftGoal) = viewModelScope.launch {
        repository.insert(goal)
    }
    
    fun update(goal: GiftGoal) = viewModelScope.launch {
        repository.update(goal)
    }
    
    fun delete(goal: GiftGoal) = viewModelScope.launch {
        repository.delete(goal)
    }
}
