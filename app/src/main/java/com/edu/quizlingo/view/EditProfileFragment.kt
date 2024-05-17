package com.edu.quizlingo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.edu.quizlingo.R
import com.edu.quizlingo.databinding.FragmentEditProfileBinding
import com.edu.quizlingo.model.request.LoginData
import com.edu.quizlingo.model.request.UserData
import com.edu.quizlingo.util.QuizLingoSingleton
import com.edu.quizlingo.viewmodel.LoginViewModel
import com.edu.quizlingo.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

//this is the edit profile fragment
@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding

    private val vm: ProfileViewModel by viewModels()

    var isAdmin = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            //this is the toolbar navigation click listener
            tbExam.setNavigationOnClickListener(View.OnClickListener {
                Navigation.findNavController(view).popBackStack(R.id.editProfileFragment, true)
            })

            //this is the user data
            QuizLingoSingleton.user?.let {
                edtUsername.setText(it.userName)
                edtSurname.setText(it.userSurname)
                edtEmail.setText(it.userEmail)
                edtPassword.setText(it.userPassword)
            }

            chkAdmin.isChecked = QuizLingoSingleton.user?.userAdmin == 1

            isAdmin = if (chkAdmin.isChecked) {
                1
            } else {
                0
            }

            chkAdmin.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    isAdmin = 1
                } else {
                    isAdmin = 0
                }
            }

            //this is the update button click listener
            btnUpdate.setOnClickListener {
                val userData = UserData().apply {
                    userName = edtUsername.text.toString()
                    userSurname = edtSurname.text.toString()
                    userEmail = edtEmail.text.toString()
                    userPassword = edtPassword.text.toString()
                    uid = QuizLingoSingleton.user?.uid!!
                    userAdmin = isAdmin
                }
                vm.editProfile(userData)
            }

            //this is the edit profile live data observer
            vm.editProfileLiveData.observe(viewLifecycleOwner){
                it?.let {
                    vm.loginCheck(LoginData().apply {
                        userEmail = edtEmail.text.toString()
                        userPassword = edtPassword.text.toString()
                    })
                }
            }

            //this is the login live data observer
            vm.loginLiveData.observe(viewLifecycleOwner){
                it?.let {
                    QuizLingoSingleton.user = it
                    Snackbar.make(view, "Profile updated successfully", Snackbar.LENGTH_SHORT).show()
                    Navigation.findNavController(view).popBackStack(R.id.editProfileFragment, true)
                    Navigation.findNavController(view).navigate(R.id.profileFragment)
                }
            }
        }
    }

}