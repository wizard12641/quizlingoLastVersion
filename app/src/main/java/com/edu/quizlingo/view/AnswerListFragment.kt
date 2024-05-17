package com.edu.quizlingo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.edu.quizlingo.R
import com.edu.quizlingo.adapter.AnswerListAdapter
import com.edu.quizlingo.databinding.FragmentAnswerListBinding
import com.edu.quizlingo.util.QuizLingoSingleton
import com.edu.quizlingo.viewmodel.AnswerListViewModel
import dagger.hilt.android.AndroidEntryPoint

//this is the answer list fragment
@AndroidEntryPoint
class AnswerListFragment : Fragment() {

    private lateinit var binding: FragmentAnswerListBinding

    private val vm: AnswerListViewModel by viewModels()
    private var answerListAdapter = AnswerListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnswerListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //this is the toolbar navigation click listener
            tbExam.setNavigationOnClickListener(View.OnClickListener {
                Navigation.findNavController(view).popBackStack(R.id.answerListFragment, true)
                Navigation.findNavController(view).navigate(R.id.mainFragment)
            })

            //this is the get answer list function
            vm.getAnswerList(QuizLingoSingleton.categoryId)

            rvAnswers.adapter = answerListAdapter

            //this is the answer live data observer
            vm.answerLiveData.observe(viewLifecycleOwner){
                it?.let {
                    answerListAdapter.submitList(it)
                }
            }
        }
    }

}