package com.sample.userstory.domain.usecase

import com.sample.userstory.data.repository.ThemeRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(private val repository: ThemeRepository) {
    operator fun invoke() = repository.getTheme()
}