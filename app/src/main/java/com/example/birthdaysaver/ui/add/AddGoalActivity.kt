package com.example.birthdaysaver.ui.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.birthdaysaver.R
import com.example.birthdaysaver.data.model.GiftGoal
import com.example.birthdaysaver.data.db.AppDatabase
import com.example.birthdaysaver.utils.DateUtils
import kotlinx.coroutines.*
import java.util.*

class AddGoalActivity : AppCompatActivity() {
    
    private lateinit var etName: EditText
    private lateinit var btnBirthDate: Button
    private lateinit var etTargetAmount: EditText
    private lateinit var btnStartDate: Button
    private lateinit var etPaymentDay: EditText
    private lateinit var btnCalculate: Button
    
    private var selectedBirthDay = 1
    private var selectedBirthMonth = 1
    private var selectedStartDate = System.currentTimeMillis()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_goal)
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        etName = findViewById(R.id.etName)
        btnBirthDate = findViewById(R.id.btnBirthDate)
        etTargetAmount = findViewById(R.id.etTargetAmount)
        btnStartDate = findViewById(R.id.btnStartDate)
        etPaymentDay = findViewById(R.id.etPaymentDay)
        btnCalculate = findViewById(R.id.btnCalculate)
        
        // Установка значений по умолчанию
        btnBirthDate.text = "Выберите дату"
        btnStartDate.text = java.text.SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            .format(Date())
        etPaymentDay.setText("1")
    }
    
    private fun setupClickListeners() {
        btnBirthDate.setOnClickListener {
            showBirthDatePicker()
        }
        
        btnStartDate.setOnClickListener {
            showStartDatePicker()
        }
        
        btnCalculate.setOnClickListener {
            calculateAndSaveGoal()
        }
    }
    
    private fun showBirthDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, _, month, dayOfMonth ->
                selectedBirthMonth = month + 1
                selectedBirthDay = dayOfMonth
                btnBirthDate.text = "$dayOfMonth.${month + 1}"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    
    private fun showStartDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                selectedStartDate = selectedDate.timeInMillis
                btnStartDate.text = java.text.SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    .format(selectedDate.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    
    private fun calculateAndSaveGoal() {
        val name = etName.text.toString().trim()
        val targetAmountStr = etTargetAmount.text.toString().trim()
        val paymentDayStr = etPaymentDay.text.toString().trim()
        
        if (name.isEmpty()) {
            Toast.makeText(this, "Введите имя именинника", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (targetAmountStr.isEmpty()) {
            Toast.makeText(this, "Введите целевую сумму", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (paymentDayStr.isEmpty()) {
            Toast.makeText(this, "Введите день внесения", Toast.LENGTH_SHORT).show()
            return
        }
        
        val targetAmount = targetAmountStr.toDoubleOrNull()
        val paymentDay = paymentDayStr.toIntOrNull()
        
        if (targetAmount == null || targetAmount <= 0) {
            Toast.makeText(this, "Введите корректную сумму", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (paymentDay == null || paymentDay !in 1..31) {
            Toast.makeText(this, "День внесения должен быть от 1 до 31", Toast.LENGTH_SHORT).show()
            return
        }
        
        val months = DateUtils.monthsUntil(selectedBirthDay, selectedBirthMonth)
        val monthlyAmount = DateUtils.calculateMonthlyAmount(targetAmount, months)
        
        val giftGoal = GiftGoal(
            name = name,
            birthDay = selectedBirthDay,
            birthMonth = selectedBirthMonth,
            targetAmount = targetAmount,
            monthlyAmount = monthlyAmount,
            startDate = selectedStartDate,
            paymentDay = paymentDay
        )
        
        saveGoalToDatabase(giftGoal)
    }
    
    private fun saveGoalToDatabase(goal: GiftGoal) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(this@AddGoalActivity)
            database.giftGoalDao().insert(goal)
            
            withContext(Dispatchers.Main) {
                Toast.makeText(this@AddGoalActivity, "Цель сохранена!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
