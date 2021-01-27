package com.ehsanfallahi.loginapp.util


import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

fun<T>lazyDeferred(block:suspend CoroutineScope.()->T):Lazy<Deferred<T>>{
    return lazy{
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

fun View.snackBar(message:String){
    val snackBar=Snackbar.make(this,message,Snackbar.LENGTH_LONG).show()
}



