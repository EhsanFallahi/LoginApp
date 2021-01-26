package com.ehsanfallahi.loginapp.ui.user.userLogin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.SharedPreferences
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.data.UserPreferences
import com.ehsanfallahi.loginapp.data.service.ApiService
import com.ehsanfallahi.loginapp.databinding.LoginFragmentBinding
import com.ehsanfallahi.loginapp.util.Constant.Companion.MY_TAG
import com.ehsanfallahi.loginapp.util.ScopedFragment
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : ScopedFragment() {
    private lateinit var binding:LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        userPreferences= UserPreferences(requireContext())

        viewModel.loginResult.observe(viewLifecycleOwner, Observer {result->
            if (result.error!=null){
//                Toast.makeText(requireContext(),"result is null",Toast.LENGTH_SHORT).show()
                Log.i(MY_TAG," result have error:${result.error}")
                return@Observer
            }else{
                Log.i(MY_TAG," result is:$result")

                lifecycleScope.launch {
                    if(!result.token.isNullOrEmpty()){
                        userPreferences.saveAuthToken(binding.emailLogin.text.toString().trim())
                        Toast.makeText(requireContext(),R.string.title_login_success,Toast.LENGTH_SHORT).show()
                    }

                }

            }
        })

        viewModel.errorMsg.observe(viewLifecycleOwner, Observer { error->
            if (error==null){
//                Toast.makeText(requireContext(),"error = null",Toast.LENGTH_SHORT).show()
                Log.i(MY_TAG," error = null")
                return@Observer
            }else{
//                Toast.makeText(requireContext(),"error is :$error",Toast.LENGTH_SHORT).show()
                Log.i(MY_TAG," error is :$error")

            }
        })

        binding.loginButton.setOnClickListener{
            loginUser(
                binding.emailLogin.text.toString().trim(),
                binding.passwordLogin.text.toString().trim()
            )
        }

       bindUI()

        createChannel(getString(R.string.fcm_notification_channel_id),
            getString(R.string.fcm_notification_channel_name))
        subscribeTopic()

        return binding.root
    }

    private fun loginUser(email: String, password: String) {
        Log.i(MY_TAG," fun loginUser")
        if (viewModel.isValidForLogin(email,password)){
            Log.i(MY_TAG,"  if (viewModel.isValidForLogin(email,password))")
            viewModel.login(email,password)

        }else{
            Log.i(MY_TAG," not  if (viewModel.isValidForLogin(email,password))")
        }
    }

    private fun bindUI()=launch {
        val users=viewModel.getAllUsers.await()
        users.observe(viewLifecycleOwner, Observer {
            Log.i(MY_TAG,"it=$it")
            if (it==null)return@Observer
            binding.loginText.text=it.toString()
        })
    }

    private fun createChannel(channelId:String,channelName:String){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel= NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor= Color.BLUE
            notificationChannel.enableVibration(true)
            notificationChannel.description="notification title for loginApp"

            val notificationManager=requireActivity().getSystemService(NotificationManager::class.java)
                    as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("topic")
            .addOnCompleteListener { task ->
                var msg = "Subscribed to topic"
                if (!task.isSuccessful) {
                    msg = "Failed to subscribe to topic"
                }
                Log.d(MY_TAG,"topic  notification:$msg")
            }
    }



}