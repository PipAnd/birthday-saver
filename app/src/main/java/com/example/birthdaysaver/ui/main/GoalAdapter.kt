package com.example.birthdaysaver.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaysaver.R
import com.example.birthdaysaver.data.model.GiftGoal
import com.example.birthdaysaver.utils.StringUtils
import java.text.DecimalFormat

class GoalAdapter(private val goals: List<GiftGoal>) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    class GoalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvBirthday: TextView = view.findViewById(R.id.tvBirthday)
        val tvTargetAmount: TextView = view.findViewById(R.id.tvTargetAmount)
        val tvMonthlyAmount: TextView = view.findViewById(R.id.tvMonthlyAmount)
        val tvProgress: TextView = view.findViewById(R.id.tvProgress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.tvName.text = goal.name
        holder.tvBirthday.text = "${goal.birthDay} ${StringUtils.getMonthName(goal.birthMonth)}"
        holder.tvTargetAmount.text = StringUtils.formatCurrency(goal.targetAmount)
        holder.tvMonthlyAmount.text = StringUtils.formatCurrency(goal.monthlyAmount)
        
        val progressPercent = if (goal.targetAmount > 0) {
            (goal.progress.toDouble() / goal.targetAmount * 100).toInt()
        } else 0
        
        holder.tvProgress.text = "Прогресс: $progressPercent%"
    }

    override fun getItemCount() = goals.size
}
