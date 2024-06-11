package com.mrtnmrls.gitcommandscompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrtnmrls.gitcommandscompose.data.local.LocalDataSource
import com.mrtnmrls.gitcommandscompose.domain.GitCommand
import com.mrtnmrls.gitcommandscompose.domain.GitSubCommand
import com.mrtnmrls.gitcommandscompose.ui.MainAction
import com.mrtnmrls.gitcommandscompose.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localDataSource: LocalDataSource
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCommands()
    }

    fun dispatchActions(action: MainAction) {
        when(action) {
            is MainAction.OnCommandSelected -> {
                updateCommandSelected(action.command)
                getSubCommand(action.command.value)
            }
            is MainAction.OnSubCommandSelected -> updateSubCommandSelected(action.subCommand)
        }
    }

    private fun getCommands() = viewModelScope.launch{
        val commands = localDataSource.getGitCommands()
        _uiState.update { it.copy(commandsList = commands) }
    }

    private fun updateCommandSelected(command: GitCommand) = viewModelScope.launch {
        _uiState.update { it.copy(commandSelected = command, subCommandSelected = null) }
    }

    private fun getSubCommand(key: String) = viewModelScope.launch {
        val subCommands = localDataSource.getGitSubCommands(key)
        _uiState.update { it.copy(subCommandsList = subCommands) }
    }

    private fun updateSubCommandSelected(subCommand: GitSubCommand) = viewModelScope.launch {
        _uiState.update { it.copy(subCommandSelected = subCommand) }
    }

}