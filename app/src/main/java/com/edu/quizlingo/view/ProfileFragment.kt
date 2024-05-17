package com.edu.quizlingo.view

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.edu.quizlingo.R
import com.edu.quizlingo.databinding.FragmentProfileBinding
import com.edu.quizlingo.util.QuizLingoSingleton
import com.edu.quizlingo.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

//this is the profile fragment
@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val vm: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            //this is the edit profile button click listener
            btnEditProfile.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editProfileFragment)
            }

            //this is the question add button click listener
            btnQuestionAdd.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_quizQuestionAddFragment)
            }

            //this is the user list button click listener
            if (QuizLingoSingleton.user?.userAdmin == 1) {
                btnGetUsers.visibility = View.VISIBLE
            } else {
                btnGetUsers.visibility = View.GONE
            }

            //this is the get users button click listener
            btnGetUsers.setOnClickListener {
                vm.getUsers()
            }

            //this is the logout button click listener
            btnLogout.setOnClickListener {
                QuizLingoSingleton.user = null
                QuizLingoSingleton.categoryResponse = null
                Navigation.findNavController(view).popBackStack(R.id.profileFragment, true)
                Navigation.findNavController(view).popBackStack(R.id.mainFragment, true)
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment)
            }

            //this is the user live data observer
            vm.getUserLiveData.observe(viewLifecycleOwner){
                it?.let {
                    val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    dialog.setTitle("Users")
                    val arrayAdapter = android.widget.ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1)
                    for (user in it){
                        arrayAdapter.add(user.userName + " " + user.userSurname)
                    }
                    dialog.setAdapter(arrayAdapter){ dialog, which ->
                        val user = it.getOrNull(which)
                        val dialogDetail = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        dialogDetail.setTitle("User Detail")
                        dialogDetail.setMessage("Name: " + user?.userName + "\nSurname: " + user?.userSurname + "\nEmail: " + user?.userEmail)
                        dialogDetail.setPositiveButton("OK"){ dialog, which -> dialog.cancel() }
                        dialogDetail.show()
                    }
                    dialog.show()
                }
            }
        }
    }

    //this is the on resume function
    override fun onResume() {
        super.onResume()
        binding.apply {
            QuizLingoSingleton.user?.let {
                tvUsername.text = it.userName
                tvSurname.text = it.userSurname
                tvMail.text = it.userEmail
            }
        }
    }
}