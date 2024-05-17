package com.edu.quizlingo.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.edu.quizlingo.R
import com.edu.quizlingo.databinding.FragmentQuizQuestionAddBinding
import com.edu.quizlingo.model.request.CategoryData
import com.edu.quizlingo.model.request.QuestionData
import com.edu.quizlingo.util.QuizLingoSingleton
import com.edu.quizlingo.viewmodel.QuizQuestionAddViewModel
import com.edu.quizlingo.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

//this is the quiz question add fragment
@AndroidEntryPoint
class QuizQuestionAddFragment : Fragment() {

    private lateinit var binding: FragmentQuizQuestionAddBinding
    private val vm: QuizQuestionAddViewModel by viewModels()

    val spinnerList: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizQuestionAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            tbExam.setNavigationOnClickListener(View.OnClickListener {
                Navigation.findNavController(view).popBackStack(R.id.quizQuestionAddFragment    , true)
            })

            getCategoryForSpinner()

            //this is the spinner on item selected listener
            spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position == spinnerList.size - 1){
                        //I need category add for input alert dialog show
                        var alertDialog = AlertDialog.Builder(context)
                        with(alertDialog){
                            setTitle("Category Add")
                            setMessage("Please enter category name")
                            val input = android.widget.EditText(context)
                            setView(input)
                            setPositiveButton("Add") { dialog, which ->
                                vm.saveCategory(CategoryData(input.text.toString()))
                            }
                            setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                            show()
                        }
                    }else{
                        QuizLingoSingleton.categoryResponse?.get(position)?.uid?.let {
                            vm.selectedCategoryId = it
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            //this is the add quiz button click listener
            btnAddQuiz.setOnClickListener {
                val questionData = QuestionData().apply {
                    quizTitle = etQuizTitle.text.toString()
                    quizQuestion = etQuizQuestion.text.toString()
                    quizOptions = etQuizOption.text.toString()
                    quizTrueOptions = etQuizTrueOption.text.toString()
                    categoryId = vm.selectedCategoryId
                }
                vm.registerQuiz(questionData)
            }

            //this is the register quiz live data observer
            vm.registerQuizLiveData.observe(viewLifecycleOwner){
                it?.let {
                    Navigation.findNavController(view).popBackStack(R.id.quizQuestionAddFragment, true)
                    Snackbar.make(view, it.message, Snackbar.LENGTH_LONG).show()
                }
            }
            //this is the save category live data observer
            vm.saveCategoryLiveData.observe(viewLifecycleOwner){
                it?.let {
                    vm.getCategory()
                }
            }
            //this is the main category live data observer
            vm.mainCategoryLiveData.observe(viewLifecycleOwner){
                it?.let {
                    QuizLingoSingleton.categoryResponse = it
                    getCategoryForSpinner()
                }
            }
        }
    }

    //this is the get category for spinner function
    fun getCategoryForSpinner(){
        spinnerList.clear()
        QuizLingoSingleton.categoryResponse?.forEach {
            spinnerList.add(it.categoryName)

        }
        spinnerList.add("Category Add")
        binding.spCategory.adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, spinnerList) }

    }
}