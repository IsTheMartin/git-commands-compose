package com.mrtnmrls.gitcommandscompose.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mrtnmrls.gitcommandscompose.R
import com.mrtnmrls.gitcommandscompose.domain.GitCommand
import com.mrtnmrls.gitcommandscompose.domain.GitSubCommand
import com.mrtnmrls.gitcommandscompose.ui.theme.GitCommandsComposeTheme

@Composable
fun CommandsDialog(
    commandsList: List<GitCommand>,
    onDismiss: () -> Unit,
    onClick: (GitCommand) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = stringResource(id = R.string.command_dialog_title),
                    fontWeight = FontWeight.SemiBold
                )
                LazyColumn {
                    items(commandsList) { command ->
                        DialogItem(command.label) {
                            onClick(command)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SubCommandsDialog(
    subCommandsList: List<GitSubCommand>,
    onDismiss: () -> Unit,
    onClick: (GitSubCommand) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = Color.White
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = stringResource(id = R.string.subcommand_dialog_title),
                    fontWeight = FontWeight.SemiBold
                )
                LazyColumn {
                    items(subCommandsList) { subCommand ->
                        DialogItem(subCommand.label) {
                            onClick(subCommand)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DialogItem(label: String, onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Column {
            Text(text = label)
        }
    }
}

@Preview
@Composable
fun CommandDialogPreview() {
    val commandsList = listOf(
        GitCommand("add", "add"),
        GitCommand("modify", "modify"),
        GitCommand("squash", "squash"),
        GitCommand("rebase", "rebase"),
    )
    GitCommandsComposeTheme {
        Surface {
            CommandsDialog(
                commandsList = commandsList,
                onDismiss = { /*TODO*/ }
            ) {}
        }
    }
}