package com.mrtnmrls.gitcommandscompose.data.local

import android.content.Context
import com.mrtnmrls.gitcommandscompose.domain.GitCommand
import com.mrtnmrls.gitcommandscompose.domain.GitSubCommand
import org.json.JSONObject
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val context: Context) {

    fun loadFromJson(): String {
        return context.assets.open("git-commands.json").bufferedReader().use {
            it.readText()
        }
    }

    fun getGitCommands(): List<GitCommand> {
        val gitCommandsArray = JSONObject(loadFromJson()).getJSONArray(PRIMARY_OPTIONS)
        val gitCommandsList = mutableListOf<GitCommand>()
        for (i in 0 until gitCommandsArray.length()) {
            val commandObject = gitCommandsArray.getJSONObject(i)
                gitCommandsList.add(
                    GitCommand(
                        commandObject.getString(LABEL),
                        commandObject.getString(VALUE)
                    )
                )
        }
        return gitCommandsList
    }

    fun getGitSubCommands(gitCommand: String): List<GitSubCommand> {
        val gitSubCommandsArray = JSONObject(loadFromJson())
            .getJSONObject(SECONDARY_OPTIONS)
            .getJSONArray(gitCommand)
        val gitSubCommandsList = mutableListOf<GitSubCommand>()
        for(i in 0 until gitSubCommandsArray.length()) {
            val subCommandObject = gitSubCommandsArray.getJSONObject(i)
            val usage = if (subCommandObject.has(USAGE)) subCommandObject.getString(USAGE) else null
            val nb = if (subCommandObject.has(NB)) subCommandObject.getString(NB) else null
            gitSubCommandsList.add(
                GitSubCommand(
                    subCommandObject.getString(LABEL),
                    subCommandObject.getString(VALUE),
                    usage,
                    nb
                )
            )
        }
        return gitSubCommandsList
    }

    companion object {
        private const val PRIMARY_OPTIONS = "primary_options"
        private const val SECONDARY_OPTIONS = "secondary_options"
        private const val LABEL = "label"
        private const val VALUE = "value"
        private const val USAGE = "usage"
        private const val NB = "nb"
    }
}