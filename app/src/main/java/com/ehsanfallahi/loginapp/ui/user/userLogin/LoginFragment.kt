package com.ehsanfallahi.loginapp.ui.user.userLogin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.data.service.ApiService
import com.ehsanfallahi.loginapp.databinding.LoginFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding:LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)

        GlobalScope.launch(Dispatchers.Main) {
            val api=ApiService()
            var response=api.getAllUsers().await()
            binding.loginText.text=response.data.toString()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

    }

}