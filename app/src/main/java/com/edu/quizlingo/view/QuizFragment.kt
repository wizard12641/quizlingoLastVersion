package com.edu.quizlingo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.edu.quizlingo.R
import com.edu.quizlingo.databinding.FragmentQuizBinding
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.util.AppEvent
import com.edu.quizlingo.util.EventBusShared
import com.edu.quizlingo.view.adapter.QuizPagerAdapter
import com.edu.quizlingo.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//this is the quiz fragment
@AndroidEntryPoint
class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val vm: QuizViewModel by viewModels()

    @Inject
    lateinit var eventBusShared: EventBusShared

    private var quizList: List<QuestionData> = listOf()

    private var categoryId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get the category id from the bundle
        val bundle = this.arguments
        bundle?.let {
            with(it){
                categoryId = getInt("categoryId")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity()
            .onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                       Navigation.findNavController(view).popBackStack(R.id.quizFragment, true)
                       Navigation.findNavController(view).navigate(R.id.mainFragment)
                    }
                }
            )

        binding.apply {
            //this is the eventBusShared subscribe
            eventBusShared.run {
                subscribe(lifecycleScope){ event->
                    when(event){
                        is AppEvent.NextQuestionId -> {
                            binding.vpSurvey.setCurrentItem(event.position + 1, true)
                            tvStep.text = "${event.position + 2}/${quizList.size}"
                        }
                    }
                }
            }

            //this is the quiz list function
            vm.quizList(categoryId)

            //this is the quiz live data observer
            vm.quizLiveData.observe(viewLifecycleOwner){
                it?.let {
                    tvStep.text = "1/${it.size}"
                    quizList = it
                    val tabAdapter = QuizPagerAdapter(this@QuizFragment, it as ArrayList<QuestionData>, it.last().questionId)
                    with(vpSurvey){
                        adapter = tabAdapter
                        isUserInputEnabled = false
                    }
                }
            }
        }
    }
}