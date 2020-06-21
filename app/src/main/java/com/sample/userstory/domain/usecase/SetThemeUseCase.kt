package com.sample.userstory.domain.usecase

import com.sample.userstory.data.repository.ThemeRepository
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(private val repository: ThemeRepository) {
    operator fun invoke(theme: Int) = repository.saveTheme(theme)
}