package com.edu.quizlingo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edu.quizlingo.R
import com.edu.quizlingo.databinding.AnswerListItemLayoutBinding
import com.edu.quizlingo.model.QuestionAndAnswer

//this is the answer list adapter
class AnswerListAdapter : ListAdapter<QuestionAndAnswer, AnswerListAdapter.ViewHolder>(
    AnswerListDiffCallback()
    ) {

    var courseClickListener: CourseClickListener? = null

    interface CourseClickListener {
        fun examItem(item: QuestionAndAnswer)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, courseClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: AnswerListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: QuestionAndAnswer, courseClickListener: CourseClickListener?) {
            with(binding) {
                tvQuestion.text = item.quizQuestion
                tvYourAnswer.text = "Your Answer : " + item.answerText
                tvTrueAnswer.text = "True Answer : " + item.quizTrueOptions

                if (item.answerText == item.quizTrueOptions) {
                    ivStatus.background = ContextCompat.getDrawable(binding.root.context, R.drawable.checked)
                } else {
                    ivStatus.background = ContextCompat.getDrawable(binding.root.context, R.drawable.cancel)
                }

                binding.root.setOnClickListener {
                    courseClickListener?.examItem(item)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AnswerListItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class AnswerListDiffCallback :
    DiffUtil.ItemCallback<QuestionAndAnswer>() {

    override fun areItemsTheSame(
        oldItem: QuestionAndAnswer,
        newItem: QuestionAndAnswer
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: QuestionAndAnswer,
        newItem: QuestionAndAnswer
    ): Boolean {
        return oldItem == newItem
    }
}