package com.ehsanfallahi.loginapp.ui.user.userList

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ehsanfallahi.loginapp.data.dataModel.Data
import com.ehsanfallahi.loginapp.data.repository.Repository
import com.ehsanfallahi.loginapp.util.Constant
import com.ehsanfallahi.loginapp.util.RecyclerViewAdapter
import com.ehsanfallahi.loginapp.util.lazyDeferred
import kotlinx.coroutines.launch

class UserListViewModel @ViewModelInject
constructor(
    private val repository: Repository
): ViewModel() {

    lateinit var userData: MutableLiveData<Data>
    lateinit var newsRecyclerViewAdapter: RecyclerViewAdapter


    fun getUserRecyclerAdapter(): RecyclerViewAdapter {
        return newsRecyclerViewAdapter
    }

    fun getUser(id:Int)=viewModelScope.launch {
        repository.getUser(id)
    }

    fun getItemDataObserver(): MutableLiveData<Data> {
        return userData
    }

    val getAllNews by lazyDeferred {
        repository.getAllUsers()
    }

    fun clearPreferences()=viewModelScope.launch{
        repository.clearPrefereces()
    }
}