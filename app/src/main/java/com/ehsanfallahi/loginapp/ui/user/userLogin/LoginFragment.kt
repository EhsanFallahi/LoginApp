package com.ehsanfallahi.loginapp.ui.user.userLogin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.data.service.ApiService
import com.ehsanfallahi.loginapp.databinding.LoginFragmentBinding
import com.ehsanfallahi.loginapp.util.Constant.Companion.MY_TAG
import com.ehsanfallahi.loginapp.util.ScopedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : ScopedFragment() {
    private lateinit var binding:LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

       bindUI()


//        GlobalScope.launch(Dispatchers.Main) {
//            val api=ApiService()
//            var response=api.getAllUsers().await()
//            binding.loginText.text=response.data.toString()
//        }

        return binding.root
    }

    private fun bindUI()=launch {
        val users=viewModel.getAllUsers.await()
        users.observe(viewLifecycleOwner, Observer {
            Log.i(MY_TAG,"it=$it")
            if (it==null)return@Observer
            binding.loginText.text=it.toString()
        })
    }


}