package com.ehsanfallahi.loginapp.ui.user.userList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.databinding.UserListFragmentBinding
import com.ehsanfallahi.loginapp.util.RecyclerViewAdapter
import com.ehsanfallahi.loginapp.util.ScopedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class UserListFragment : ScopedFragment() {


    private lateinit var viewModel: UserListViewModel
    private lateinit var binding:UserListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        binding= DataBindingUtil.inflate(inflater,R.layout.user_list_fragment,container,false)

        setUpNewsRecyclerView()

        return binding.root
    }

    private fun setUpNewsRecyclerView() =launch {
        binding.rvListUser.apply {
            layoutManager= LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
            val users= viewModel.getAllNews.await()
            users.observe(viewLifecycleOwner, Observer {
                binding.progress.visibility= View.GONE
                if(it==null ) return@Observer
                binding.rvListUser.adapter= RecyclerViewAdapter(it.data)

            })
        }
    }


}