package com.edu.quizlingo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.edu.quizlingo.R
import com.edu.quizlingo.databinding.FragmentLoginBinding
import com.edu.quizlingo.model.request.LoginData
import com.edu.quizlingo.util.QuizLingoSingleton
import com.edu.quizlingo.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint

//this is the login fragment
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            //this is the hawk init
            Hawk.init(requireContext()).build()

            //this is the register textview click listener
            tvRegister.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
            }

            Hawk.get<Boolean>("rememberMe")?.let {
                cbRememberMe.isChecked = it
            }
            Hawk.get<String>("email")?.let {
                edtEmail.setText(it)
            }
            Hawk.get<String>("password")?.let {
                edtPassword.setText(it)
            }

            //this is the remember me checkbox listener
            cbRememberMe.setOnCheckedChangeListener(){ _, isChecked ->
                if (isChecked){
                    Hawk.put("rememberMe", isChecked)
                    Hawk.put("email", edtEmail.text.toString())
                    Hawk.put("password", edtPassword.text.toString())
                }else{
                    Hawk.deleteAll()
                }

            }

            //this is the login button click listener
            btnLogin.setOnClickListener {
                if (!formValidation()){
                    return@setOnClickListener
                }
                var loginData = LoginData().apply {
                    userEmail = edtEmail.text.toString()
                    userPassword = edtPassword.text.toString()
                }
                loginViewModel.loginCheck(loginData.userEmail.toString(), loginData.userPassword.toString())
            }

            //this is the observer for the login live data
            loginViewModel.loginLiveData.observe(viewLifecycleOwner){
                it.let {
                    if (it != null){
                        QuizLingoSingleton.user = it
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment)
                    }else{
                        Snackbar.make(view, "User Not Found", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    //this function is used to validate the form
    fun formValidation() : Boolean{
        var isValid = true
        binding.apply {
            if (edtEmail.text.toString().isEmpty()){
                edtEmail.error = "Please enter email"
                isValid = false
            }
            if (edtPassword.text.toString().isEmpty()){
                edtPassword.error = "Please enter password"
                isValid = false
            }
            return isValid
        }
    }
}