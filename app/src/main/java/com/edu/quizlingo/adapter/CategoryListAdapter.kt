package com.edu.quizlingo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edu.quizlingo.databinding.CoursesListItemLayoutBinding
import com.edu.quizlingo.model.request.CategoryData

//this is the category list adapter
class CategoryListAdapter : ListAdapter<CategoryData, CategoryListAdapter.ViewHolder>(
    CourseDiffCallback()
    ) {

    var courseClickListener: CourseClickListener? = null

    interface CourseClickListener {
        fun examItem(item: CategoryData)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, courseClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: CoursesListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryData, courseClickListener: CourseClickListener?) {
            with(binding) {
                tvCourseName.text = item.categoryName

                binding.root.setOnClickListener {

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


class CourseDiffCallback :
    DiffUtil.ItemCallback<CategoryData>() {

    override fun areItemsTheSame(
        oldItem: CategoryData,
        newItem: CategoryData
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CategoryData,
        newItem: CategoryData
    ): Boolean {
        return oldItem == newItem
    }
}