package com.ehsanfallahi.loginapp.ui.user.userDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.data.dataModel.Data
import com.ehsanfallahi.loginapp.databinding.UserDetailFragmentBinding

class UserDetailFragment : Fragment() {


    private lateinit var viewModel: UserDetailViewModel
    private lateinit var binding: UserDetailFragmentBinding
    private val args:UserDetailFragmentArgs by navArgs()
    private lateinit var data: Data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        data=args.data
        viewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
        binding= DataBindingUtil.inflate(inflater,R.layout.user_detail_fragment,container,false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        Glide.with(this)
                .load(data.avatar)
                .into(binding.imgUserDetail)
        binding.apply {
            tvFirstName.text=data.firstName
            tvLastName.text=data.lastName
            tvDetailEmail.text=data.email
        }
    }

}