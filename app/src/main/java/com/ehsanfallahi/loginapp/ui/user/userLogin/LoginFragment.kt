package com.ehsanfallahi.loginapp.ui.user.userLogin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.data.UserPreferences
import com.ehsanfallahi.loginapp.databinding.LoginFragmentBinding
import com.ehsanfallahi.loginapp.util.Constant.Companion.MY_TAG
import com.ehsanfallahi.loginapp.util.ScopedFragment
import com.ehsanfallahi.loginapp.util.snackBar
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
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
        binding.progressLogin.visibility=View.GONE

         userPreferences=UserPreferences(requireContext())
         userPreferences.fetchToke.asLiveData().observe(viewLifecycleOwner, Observer {
            if(it==null)return@Observer
            val directions =
                LoginFragmentDirections.actionLoginFragmentToUserListFragment(it)
            findNavController().navigate(directions)

        })


        binding.emailLogin.addTextChangedListener(afterTextChangedListener)
        binding.passwordLogin.addTextChangedListener(afterTextChangedListener)


        viewModel._errorMsg.observe(viewLifecycleOwner, Observer { error->
            if (error.isNullOrEmpty()) {return@Observer}
            else{requireView().snackBar(error)
            binding.progressLogin.visibility=View.GONE}
        })

        binding.loginButton.setOnClickListener{
            binding.progressLogin.visibility=View.VISIBLE
            loginUser(
                binding.emailLogin.text.toString().trim(),
                binding.passwordLogin.text.toString().trim()
            )
        }


        //for FCM Notification
        createChannel(getString(R.string.fcm_notification_channel_id),
            getString(R.string.fcm_notification_channel_name))

        subscribeTopic()

        return binding.root
    }

    private fun loginUser(email: String, password: String) {
        if (viewModel.isValidForLogin(email,password)){
            viewModel.login(email,password)
            viewModel.isValid.observe(viewLifecycleOwner, Observer { isValid->
                if (isValid) {
                    val directions =
                        LoginFragmentDirections.actionLoginFragmentToUserListFragment(binding.emailLogin.text.toString())
                    binding.progressLogin.visibility=View.GONE
                    findNavController().navigate(directions)
                    viewModel.saveEmailUser(binding.emailLogin.text.toString().trim())
                    requireView().snackBar(resources.getString(R.string.title_login_success))
                    viewModel.isValid.value=false
                }
            })

        }else{
            Log.i(MY_TAG," not  if (viewModel.isValidForLogin(email,password))")
        }
    }

    private val afterTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val emailInput: String = binding.emailLogin.text.toString().trim()
            val passwordInput: String = binding.passwordLogin.text.toString().trim()

            binding.loginButton.isEnabled = (emailInput.isNotBlank()
                    && emailInput.isNotEmpty()
                    && passwordInput.isNotBlank()
                    && passwordInput.isNotEmpty())
        }

        override fun afterTextChanged(s: Editable) {
            // ignore
        }
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

    override fun onDestroy() {
        super.onDestroy()
        binding.emailLogin.setText("")
        binding.passwordLogin.setText("")
    }

    override fun onStart() {
        super.onStart()
        viewModel._errorMsg.value=""

        binding.emailLogin.setText("")
        binding.passwordLogin.setText("")
    }
}