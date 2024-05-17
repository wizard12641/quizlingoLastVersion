package com.edu.quizlingo.view.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.view.QuizItemFragment

//this is the quiz pager adapter
class QuizPagerAdapter(fm: Fragment, var questionList: ArrayList<QuestionData>?, var lastItem: Int) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = questionList?.size ?: 0

    override fun createFragment(position: Int): Fragment {
        val fragment = QuizItemFragment().apply {
            arguments = bundleOf("position" to position, "question" to questionList?.getOrNull(position), "lastItem" to lastItem)
        }
        return fragment
    }
}