package com.example.kotlin_practice_8_10.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_practice_8_10.retrofit.User

class MyViewModel : ViewModel() {
    private val login = MutableLiveData<String>()
    private val password = MutableLiveData<String>()
    private val email = MutableLiveData<String>()
    fun onResult(user: User) {
        login.value = user.username
        password.value = user.password
        email.value = user.email
    }
    fun setLogin(text: String?) {
        login.value = text
    }

    fun getLogin(): LiveData<String> {
        return login
    }

    fun setPassword(text: String?) {
        password.value = text
    }

    fun getPassword(): LiveData<String> {
        return password
    }

    fun setEmail(text: String?) {
        email.value = text
    }

    fun getEmail(): LiveData<String> {
        return email
    }
}