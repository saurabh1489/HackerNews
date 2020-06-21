package com.sample.userstory.domain.usecase

import javax.inject.Inject

class LoginUseCase @Inject constructor() {
    operator fun invoke(userName: String, password: String): Boolean {
        return true
    }
}