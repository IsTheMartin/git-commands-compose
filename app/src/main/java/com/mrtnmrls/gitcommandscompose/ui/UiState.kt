package com.mrtnmrls.gitcommandscompose.ui

import com.mrtnmrls.gitcommandscompose.domain.GitCommand
import com.mrtnmrls.gitcommandscompose.domain.GitSubCommand

data class UiState(
    val commandSelected: GitCommand? = null,
    val subCommandSelected: GitSubCommand? = null,
    val commandsList: List<GitCommand> = emptyList(),
    val subCommandsList: List<GitSubCommand> = emptyList()
)