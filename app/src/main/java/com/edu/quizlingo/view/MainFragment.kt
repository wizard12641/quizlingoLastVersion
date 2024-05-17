package com.edu.quizlingo.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.edu.quizlingo.R
import com.edu.quizlingo.adapter.CategoryListAdapter
import com.edu.quizlingo.databinding.FragmentMainBinding
import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.util.QuizLingoSingleton
import com.edu.quizlingo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


//this is the main fragment
@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val categoryAdapter = CategoryListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            requireActivity()
                .onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        requireActivity().finish()
                    }
                    }
                )

            QuizLingoSingleton.user?.let {
                tvWhoamiPersonName.text = "Welcome, " + it.userName
            }
            rvCourseList.adapter = categoryAdapter

            mainViewModel.getCategory()

            //this is the category live data observer
            mainViewModel.mainCategoryLiveData.observe(viewLifecycleOwner){
                it?.let {
                    QuizLingoSingleton.categoryResponse = it
                    categoryAdapter.submitList(it)
                }
            }

            //this is the course click listener
            categoryAdapter.courseClickListener = object : CategoryListAdapter.CourseClickListener {
                override fun examItem(item: CategoryData) {
                    QuizLingoSingleton.categoryId = item.uid
                    mainViewModel.checkQuizCompleted(item.uid)
                }
            }



            mainViewModel.checkCompletedLiveData.observe(viewLifecycleOwner){
                it?.let {
                    if(it.status){
                        showRestartQuizDialog()
                    }else{
                        Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_quizFragment, bundleOf("categoryId" to QuizLingoSingleton.categoryId))
                    }
                }
            }
            mainViewModel.removeAnswersLiveData.observe(viewLifecycleOwner){
                it?.let {
                    if(it.status){
                        Navigation.findNavController(view).popBackStack(R.id.mainFragment, true)
                        Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_quizFragment, bundleOf("categoryId" to QuizLingoSingleton.categoryId))
                    }
                }
            }
        }
    }

    private fun showRestartQuizDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Restart Quiz?")
        builder.setMessage("Are you sure you want to restart the quiz?")

         builder.setPositiveButton("Restart Quiz",
            DialogInterface.OnClickListener { dialog, which ->
                // Again Quiz Restart
                mainViewModel.removeAllAnswers()
            })

        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}