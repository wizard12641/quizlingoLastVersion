package com.edu.quizlingo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.room.Room
import com.edu.quizlingo.R
import com.edu.quizlingo.adapter.QuizOptionsListAdapter
import com.edu.quizlingo.databinding.FragmentQuizItemBinding
import com.edu.quizlingo.model.QuestionOption
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.model.request.SaveAnswer
import com.edu.quizlingo.room.QuizLingoAppDataBase
import com.edu.quizlingo.util.AppEvent
import com.edu.quizlingo.util.EventBusShared
import com.edu.quizlingo.util.QuizLingoSingleton
import com.edu.quizlingo.viewmodel.SaveAnswerViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

//this is the quiz item fragment
@AndroidEntryPoint
class QuizItemFragment : Fragment() {

    private lateinit var binding: FragmentQuizItemBinding
    private var position = -1
    private var question: QuestionData = QuestionData()

    private val quizOptionsListAdapter = QuizOptionsListAdapter()

    private var questionOption: ArrayList<QuestionOption> = arrayListOf()

    private val vm: SaveAnswerViewModel by viewModels()

    var saveAnswer: SaveAnswer = SaveAnswer()

    var lastItemPosition = 0

    @Inject
    lateinit var eventBusShared: EventBusShared

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get the position, last item position, and question from the bundle
        val bundle = this.arguments
        bundle?.let {
            with(it){
                position = getInt("position")
                lastItemPosition = getInt("lastItem")
                question = getParcelable("question")!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            //set the question data
            tvQuizTitle.text = question.quizTitle
            tvQuizDesc.text = question.quizQuestion
            rvAnswerList.adapter = quizOptionsListAdapter


            question.quizOptions?.split(",")?.forEach {
                questionOption.add(QuestionOption(it))
            }

            quizOptionsListAdapter.submitList(questionOption)

            //this is the answer click listener
            quizOptionsListAdapter.answerClickListener = object : QuizOptionsListAdapter.CourseClickListener {
                override fun examItem(item: QuestionOption) {
                    quizOptionsListAdapter.setSelectedPosition(questionOption.indexOf(item))
                    fabNext.visibility = View.VISIBLE
                    saveAnswer = SaveAnswer().apply {
                        answerText = item.option
                        userId = QuizLingoSingleton.user?.uid
                        quizId = question.questionId
                    }
                }
            }

            tvStep.setOnClickListener {
                Navigation.findNavController(view).popBackStack(R.id.quizFragment, true)
                Navigation.findNavController(view).navigate(R.id.action_quizItemFragment_to_answerListFragment)
            }

            //this is the next button click listener
            fabNext.setOnClickListener {
                binding.progressBar.visibility = View.VISIBLE
                vm.saveAnswer(saveAnswer)
            }

            //this is the save answer live data observer
            vm.saveAnswerLiveData.observe(viewLifecycleOwner){
                it?.let {
                    if (it.status){
                        binding.progressBar.visibility = View.GONE
                        if (lastItemPosition == question.questionId){
                            tvStep.visibility = View.VISIBLE
                        }else{
                            tvStep.visibility = View.GONE
                        }
                        lifecycleScope.launch {
                            eventBusShared.emit(AppEvent.NextQuestionId(position))
                        }
                    }else{
                        Snackbar.make(view, it.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}