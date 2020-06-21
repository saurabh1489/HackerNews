package com.sample.userstory.ui.login

import android.util.Patterns
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.userstory.data.repository.Theme
import com.sample.userstory.domain.usecase.GetThemeUseCase
import com.sample.userstory.domain.usecase.LoginUseCase
import com.sample.userstory.domain.usecase.SetThemeUseCase
import javax.inject.Inject

const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=]).{8,15}\$"

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean>
        get() = _loginStatus

    val mUsernameLiveData = MutableLiveData<String>()
    val mPasswordLiveData = MutableLiveData<String>()
    val mUsernamePasswordMediator = MediatorLiveData<Boolean>()

    init {
        mUsernamePasswordMediator.addSource(mUsernameLiveData) { validateForm() }
        mUsernamePasswordMediator.addSource(mPasswordLiveData) { validateForm() }
    }

    private fun validateForm() {
        val userName = mUsernameLiveData.value ?: ""
        val password = mPasswordLiveData.value ?: ""
        val isUserNameValid = isValidUserName(userName)
        val isPasswordValid = isValidPassword(password)
        mUsernamePasswordMediator.value = isUserNameValid && isPasswordValid
    }

    fun isValidUserName(userName: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(userName).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.matches(Regex(PASSWORD_REGEX))
    }

    fun login(userName: String, password: String) {
        val status = loginUseCase(userName, password)
        _loginStatus.postValue(status)
    }

    override fun onCleared() {
        mUsernamePasswordMediator.removeSource(mUsernameLiveData)
        mUsernamePasswordMediator.removeSource(mPasswordLiveData)
    }

}