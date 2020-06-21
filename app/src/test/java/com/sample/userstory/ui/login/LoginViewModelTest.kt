package com.sample.userstory.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sample.userstory.domain.usecase.LoginUseCase
import com.sample.userstory.util.mock
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class LoginViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var loginViewModel: LoginViewModel

    private val loginUseCase = mock(LoginUseCase::class.java)

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun isValidUserName_validEmailPassword_Success() {
        val userName = "Test@123453.com"
        val res = loginViewModel.isValidUserName(userName)
        assertTrue(res)
    }

    @Test
    fun isValidUserName_noDomain_Failure() {
        val userName = "Test@123453"
        val res = loginViewModel.isValidUserName(userName)
        assertFalse(res)
    }

    @Test
    fun isValidUserName_noSeparator_Failure() {
        val userName = "Test.com"
        val res = loginViewModel.isValidUserName(userName)
        assertFalse(res)
    }

    @Test
    fun isValidUserName_noSeparatorNoDomain_Failure() {
        val userName = "Test"
        val res = loginViewModel.isValidUserName(userName)
        assertFalse(res)
    }

    @Test
    fun isValidUserName_emptyEmail_Failure() {
        val userName = ""
        val res = loginViewModel.isValidUserName(userName)
        assertFalse(res)
    }

    @Test
    fun isValidPassword_validPassword_Success() {
        val password = "Testing@123"
        val res = loginViewModel.isValidPassword(password)
        assertTrue(res)
    }

    @Test
    fun isValidPassword_emptyString_Failure() {
        val password = ""
        val res = loginViewModel.isValidPassword(password)
        assertFalse(res)
    }

    @Test
    fun isValidPassword_noCapitalLetter_Failure() {
        val password = "testing@123"
        val res = loginViewModel.isValidPassword(password)
        assertFalse(res)
    }

    @Test
    fun isValidPassword_noSmallLetter_Failure() {
        val password = "TESTING@123"
        val res = loginViewModel.isValidPassword(password)
        assertFalse(res)
    }

    @Test
    fun isValidPassword_noSpecialLetter_Failure() {
        val password = "Testing123"
        val res = loginViewModel.isValidPassword(password)
        assertFalse(res)
    }

    @Test
    fun isValidPassword_noDigit_Failure() {
        val password = "TestingWithNoDigit"
        val res = loginViewModel.isValidPassword(password)
        assertFalse(res)
    }

    @Test
    fun isValidPassword_lessThan8Chars_Failure() {
        val password = "testing"
        val res = loginViewModel.isValidPassword(password)
        assertFalse(res)
    }

    @Test
    fun isValidPassword_greaterThan15Chars_Failure() {
        val password = "Testing@12345678"
        val res = loginViewModel.isValidPassword(password)
        assertFalse(res)
    }

    @Test
    fun isValidPassword_15Chars_Success() {
        val password = "Testing@1234567"
        val res = loginViewModel.isValidPassword(password)
        assertTrue(res)
    }

    @Test
    fun isValidPassword_8Chars_Success() {
        val password = "Test@123"
        val res = loginViewModel.isValidPassword(password)
        assertTrue(res)
    }

    @Test
    fun validateForm_validEmailPassword_notifySuccess() {
        loginViewModel.mUsernameLiveData.value = "test@xyz.com"
        loginViewModel.mPasswordLiveData.value = "Testing@123"

        val observer = mock<Observer<Boolean>>()
        loginViewModel.mUsernamePasswordMediator.observeForever(observer)

        verify(observer, times(2)).onChanged(true)
    }

    @Test
    fun validateForm_validEmailInvalidPassword_notifyFailure() {
        loginViewModel.mUsernameLiveData.value = "test@xyz.com"
        loginViewModel.mPasswordLiveData.value = ""

        val observer = mock<Observer<Boolean>>()
        loginViewModel.mUsernamePasswordMediator.observeForever(observer)

        verify(observer, times(2)).onChanged(false)
    }

    @Test
    fun login_loginPerformed_notifyObserver() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(true)

        val observer = mock<Observer<Boolean>>()
        loginViewModel.loginStatus.observeForever(observer)

        loginViewModel.login("user@xyz.com","Testing@123")

        verify(observer, times(1)).onChanged(true)
    }

}