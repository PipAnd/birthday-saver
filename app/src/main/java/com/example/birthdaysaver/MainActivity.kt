package com.example.birthdaysaver

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaysaver.ui.add.AddGoalActivity
import com.example.birthdaysaver.ui.main.GoalAdapter
import com.example.birthdaysaver.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {
    
    private lateinit var viewModel: MainViewModel
    private lateinit var rvGoals: RecyclerView
    private lateinit var tvNoGoals: TextView
    private lateinit var adapter: GoalAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        setupViewModel()
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }
    
    private fun initViews() {
        rvGoals = findViewById(R.id.rvGoals)
        tvNoGoals = findViewById(R.id.tvNoGoals)
    }
    
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }
    
    private fun setupRecyclerView() {
        rvGoals.layoutManager = LinearLayoutManager(this)
    }
    
    private fun setupObservers() {
        viewModel.allGoals.observe(this) { goals ->
            if (goals.isEmpty()) {
                tvNoGoals.visibility = View.VISIBLE
                rvGoals.visibility = View.GONE
            } else {
                tvNoGoals.visibility = View.GONE
                rvGoals.visibility = View.VISIBLE
                adapter = GoalAdapter(goals)
                rvGoals.adapter = adapter
            }
        }
    }
    
    private fun setupClickListeners() {
        val btnAddGoal = findViewById<Button>(R.id.btnAddGoal)
        btnAddGoal.setOnClickListener {
            val intent = Intent(this, AddGoalActivity::class.java)
            startActivity(intent)
        }
    }
}
