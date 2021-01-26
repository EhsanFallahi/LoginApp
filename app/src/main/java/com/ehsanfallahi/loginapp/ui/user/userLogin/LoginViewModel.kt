package com.ehsanfallahi.loginapp.ui.user.userLogin

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.data.remoteData.request.AuthLoginRequest
import com.ehsanfallahi.loginapp.data.remoteData.request.AuthToken
import com.ehsanfallahi.loginapp.data.repository.Repository
import com.ehsanfallahi.loginapp.util.lazyDeferred
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

class LoginViewModel@ViewModelInject
constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _loginResult = MutableLiveData<AuthToken>()
    val loginResult: LiveData<AuthToken>
        get() = _loginResult

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String>
        get() = _errorMsg



    val getAllUsers by lazyDeferred {
        repository.getUsers()
    }

    fun isValidForLogin(loginEmail: String?, loginPassword: String?): Boolean {
        if (loginEmail.isNullOrEmpty()
            || loginEmail.isNullOrBlank()
            || loginPassword.isNullOrEmpty()
            || loginPassword.isNullOrBlank()
        ) {
            postErrorValue(context.getString(R.string.empty_email_or_password))
            return false
        }

        if(loginPassword.length < 6) {
            postErrorValue(context.getString(R.string.invalid_password))
            return false
        }

        return true
    }

    private fun postErrorValue(errorMessage: String) {
        _errorMsg.postValue(errorMessage)
    }

    fun login(email: String, password: String)=
        viewModelScope.launch {
            _loginResult.value= repository.login(AuthLoginRequest(email = email,password=password))
        }



}

