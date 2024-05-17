package com.edu.quizlingo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edu.quizlingo.R
import com.edu.quizlingo.databinding.CoursesListItemLayoutBinding
import com.edu.quizlingo.model.QuestionOption

//this is the quiz options list adapter
class QuizOptionsListAdapter : ListAdapter<QuestionOption, QuizOptionsListAdapter.ViewHolder>(
    AnswerDiffCallback()
    ) {

    var answerClickListener: CourseClickListener? = null
    private val selectedPosition = intArrayOf(-1)

    interface CourseClickListener {
        fun examItem(item: QuestionOption)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, answerClickListener, selectedPosition)
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition[0] = position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CoursesListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: QuestionOption,
            courseClickListener: CourseClickListener?,
            selectedPosition: IntArray
        ) {
            with(binding) {
                tvCourseName.text = item.option

                if (selectedPosition[0] == adapterPosition) {
                    clCourseItem.background = ContextCompat.getDrawable(clCourseItem.context, R.drawable.background_brandly_item_selector)
                } else {
                    clCourseItem.background = ContextCompat.getDrawable(clCourseItem.context, R.drawable.background_brandly_item)
                }

                binding.root.setOnClickListener {
                    selectedPosition[0] = adapterPosition
                    courseClickListener?.examItem(item)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CoursesListItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class AnswerDiffCallback :
    DiffUtil.ItemCallback<QuestionOption>() {

    override fun areItemsTheSame(
        oldItem: QuestionOption,
        newItem: QuestionOption
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: QuestionOption,
        newItem: QuestionOption
    ): Boolean {
        return oldItem == newItem
    }
}