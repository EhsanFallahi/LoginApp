package com.ehsanfallahi.loginapp.ui.user.userList

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ehsanfallahi.loginapp.data.dataModel.Data
import com.ehsanfallahi.loginapp.data.repository.Repository
import com.ehsanfallahi.loginapp.util.Constant
import com.ehsanfallahi.loginapp.util.RecyclerViewAdapter
import com.ehsanfallahi.loginapp.util.lazyDeferred

class UserListViewModel @ViewModelInject
constructor(
    private val repository: Repository
): ViewModel() {

    lateinit var userData: MutableLiveData<Data>
    lateinit var newsRecyclerViewAdapter: RecyclerViewAdapter


    fun getUserRecyclerAdapter(): RecyclerViewAdapter {
        Log.i(Constant.MY_TAG,"getNewsRecyclerAdapter")
        return newsRecyclerViewAdapter
    }


    fun getItemDataObserver(): MutableLiveData<Data> {
        return userData
    }

    val getAllNews by lazyDeferred {
        repository.getUsers()
    }
}