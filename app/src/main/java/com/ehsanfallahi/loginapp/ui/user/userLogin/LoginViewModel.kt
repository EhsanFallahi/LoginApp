package com.ehsanfallahi.loginapp.ui.user.userLogin

import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.data.remoteData.request.AuthLoginRequest
import com.ehsanfallahi.loginapp.data.remoteData.request.AuthToken
import com.ehsanfallahi.loginapp.data.repository.Repository
import com.ehsanfallahi.loginapp.util.Constant.Companion.MY_TAG
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

     val _errorMsg = MutableLiveData<String>()

     val isValid = MutableLiveData<Boolean>()

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
            try {
                _loginResult.value= repository.login(AuthLoginRequest(email = email,password=password))
                isValid.value=true
            }catch (e:Exception){
                Log.i(MY_TAG,"input is wrong")
                postErrorValue("متاسفانه چنین داده ای موجود نمی\u200Cباشد!")
                isValid.value=false
            }

        }

    fun saveEmailUser(email: String)=viewModelScope.launch {
        repository.saveEmailUser(email)
    }


    override fun onCleared() {
        postErrorValue("")
    }


}

