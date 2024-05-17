package com.edu.quizlingo

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.edu.quizlingo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

//this is the main activity
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            //check internet connection
            /*if (isNetworkAvailable(this@MainActivity).not()){
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Internet Connection Error")
                    .setMessage("Check Your Internet Connection")
                    .setCancelable(false)
                    .setPositiveButton("OK", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            p0?.cancel()
                            finish()
                        }
                    }).create().show()
            }*/
            val navController = findNavController(R.id.fragment_btm_nav)
            NavigationUI.setupWithNavController(btmNav,navController)

            //hide bottom navigation on certain fragments
            navController.addOnDestinationChangedListener { _, destination, arguments ->
                btmNav.apply {
                    when (destination.id) {
                        R.id.loginFragment -> visibility = View.GONE
                        R.id.registerFragment -> visibility = View.GONE
                        R.id.quizFragment -> visibility = View.GONE
                        R.id.answerListFragment -> visibility = View.GONE
                        R.id.editProfileFragment -> visibility = View.GONE
                        R.id.quizQuestionAddFragment -> visibility = View.GONE
                        else -> visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    //check internet connection
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}