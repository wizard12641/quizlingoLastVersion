package com.edu.quizlingo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.edu.quizlingo.R
import com.edu.quizlingo.databinding.FragmentRegisterBinding
import com.edu.quizlingo.model.request.UserData
import com.edu.quizlingo.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

//this is the register fragment
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {


            //this is the register button click listener
            btnRegister.setOnClickListener {
                if (formValidation().not()){
                    return@setOnClickListener
                }
                var userData = UserData().apply {
                    userName = edtUsername.text.toString()
                    userEmail = edtEmail.text.toString()
                    userPassword = edtPassword.text.toString()
                    userSurname = edtUsersurname.text.toString()
                }
                registerViewModel.registerUser(userData)
            }
            //this is the back button click listener
            btnBack.setOnClickListener {
               findNavController().popBackStack(R.id.registerFragment , true)
            }

            //this is the register live data observer
            registerViewModel.registerLiveData.observe(viewLifecycleOwner){
                it?.let {
                    Snackbar.make(view, it.message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    //this is the form validation
    fun formValidation() : Boolean{
        var isValid = true
        binding.apply {
            if (edtUsername.text.toString().isEmpty()){
                edtUsername.error = "Please enter username"
                isValid = false
            }
            if (edtUsersurname.text.toString().isEmpty()){
                edtUsersurname.error = "Please enter surname"
                isValid = false
            }
            if (edtEmail.text.toString().isEmpty()){
                edtEmail.error = "Please enter email"
                isValid = false
            }else if (!edtEmail.text.toString().matches(Regex("^[\\w.+\\-]+@(gmail|hotmail|outlook)\\.com$"))) {
                edtEmail.error = "Email must be a valid Gmail, Hotmail, or Outlook address"
                isValid = false
            }
            if (!edtPassword.text.toString().any { it.isDigit() }) {
                edtPassword.error = "Password must include numbers"
                isValid = false
            }
            if (edtPassword.text.toString() == edtUsername.text.toString()) {
                edtPassword.error = "Password cannot be the same as username"
                isValid = false
            }
            if (edtPassword.text.toString().isEmpty()){
                edtPassword.error = "Please enter password"
                isValid = false
            }
            if (!edtPassword.text.toString().any { it.isLetter() }) {
                edtPassword.error = "Password must include letters"
                isValid = false
            }else {
                // Additional password validations:
                if (edtPassword.text.toString().length < 6) {
                    edtPassword.error = "Password must be at least 6 characters"
                    isValid = false
                }
            }

            return isValid
        }
    }

}