package com.mrtnmrls.gitcommandscompose.ui

import com.mrtnmrls.gitcommandscompose.domain.GitCommand
import com.mrtnmrls.gitcommandscompose.domain.GitSubCommand

sealed class MainAction {
    data class OnCommandSelected(val command: GitCommand): MainAction()
    data class OnSubCommandSelected(val subCommand: GitSubCommand): MainAction()
}