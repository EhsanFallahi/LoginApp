package com.ehsanfallahi.loginapp.ui.user.userLogin

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ehsanfallahi.loginapp.data.repository.Repository
import com.ehsanfallahi.loginapp.util.Constant
import com.ehsanfallahi.loginapp.util.lazyDeferred

class LoginViewModel@ViewModelInject
constructor(
    private val repository: Repository
): ViewModel() {

    val getAllUsers by lazyDeferred {
        repository.getUsers()
    }

}

