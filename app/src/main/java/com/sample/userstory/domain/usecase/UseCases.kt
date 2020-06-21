package com.sample.userstory.domain.usecase

data class UseCases(
    val addNote: GetStoryUseCase,
    val getAllNotes: LoginUseCase,
    val setTheme: SetThemeUseCase,
    val getTheme: GetThemeUseCase
)