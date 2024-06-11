package com.mrtnmrls.gitcommandscompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mrtnmrls.gitcommandscompose.R
import com.mrtnmrls.gitcommandscompose.domain.GitCommand
import com.mrtnmrls.gitcommandscompose.domain.GitSubCommand
import com.mrtnmrls.gitcommandscompose.ui.MainAction
import com.mrtnmrls.gitcommandscompose.ui.UiState
import com.mrtnmrls.gitcommandscompose.ui.theme.Aquamarine
import com.mrtnmrls.gitcommandscompose.ui.theme.Bone
import com.mrtnmrls.gitcommandscompose.ui.theme.GitCommandsComposeTheme
import com.mrtnmrls.gitcommandscompose.ui.views.CommandsDialog
import com.mrtnmrls.gitcommandscompose.ui.views.SubCommandsDialog

@Composable
internal fun CommandsScreen(uiState: UiState, onMainAction: (MainAction) -> Unit) {
    val commands = uiState.commandsList
    val subCommands = uiState.subCommandsList
    var showGitCommandDialog by remember { mutableStateOf(false) }
    var showGitSubCommandDialog by remember { mutableStateOf(false) }
    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(modifier = Modifier.padding(vertical = 20.dp)) {
                item {
                    TitleView()
                }
                item {
                    CommandsSelectionView(
                        modifier = Modifier,
                        uiState.commandSelected,
                        uiState.subCommandSelected,
                        { showGitCommandDialog = true }
                    ) { showGitSubCommandDialog = true }
                }
                item {
                    CommandDescriptionView(gitSubCommand = uiState.subCommandSelected)
                }
            }
        }

        if (showGitCommandDialog) {
            CommandsDialog(
                commandsList = commands,
                onDismiss = { showGitCommandDialog = false }
            ) {
                onMainAction(MainAction.OnCommandSelected(it))
                showGitCommandDialog = false
            }
        }

        if (showGitSubCommandDialog) {
            SubCommandsDialog(
                subCommandsList = subCommands,
                onDismiss = { showGitSubCommandDialog = false }
            ) {
                onMainAction(MainAction.OnSubCommandSelected(it))
                showGitSubCommandDialog = false
            }
        }
    }
}

@Composable
fun TitleView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Aquamarine)) {
                    append(stringResource(id = R.string.title_first))
                }
                append(" ")
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(stringResource(id = R.string.title_second))
                }
            },
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.subtitle),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CommandsSelectionView(
    modifier: Modifier = Modifier,
    commandSelected: GitCommand?,
    subCommandSelected: GitSubCommand?,
    onShowCommandDialog: () -> Unit,
    onShowSubCommandDialog: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.command_selection_title),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        ElevatedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onShowCommandDialog() },
            shape = RectangleShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Bone,
                contentColor = Color.Black
            )
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commandSelected?.label ?: "...",
                textAlign = TextAlign.Start
            )
        }
        Spacer(Modifier.height(12.dp))
        ElevatedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onShowSubCommandDialog() },
            shape = RectangleShape,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Bone,
                contentColor = Color.Black
            )
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subCommandSelected?.label ?: "...",
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun CommandDescriptionView(modifier: Modifier = Modifier, gitSubCommand: GitSubCommand?) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.command_usage),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .clip(RoundedCornerShape(4.dp))
        ) {
            Divider(
                Modifier
                    .fillMaxHeight()
                    .padding(end = 8.dp)
                    .width(8.dp),
                color = Aquamarine
            )
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = gitSubCommand?.usage.orEmpty()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (gitSubCommand?.nb != null) {
            Text(
                text = stringResource(id = R.string.command_notes),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                Divider(
                    Modifier
                        .fillMaxHeight()
                        .padding(end = 8.dp)
                        .width(8.dp),
                    color = Aquamarine
                )
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = gitSubCommand.nb.orEmpty()
                )
            }
        }
    }
}

@Preview
@Composable
fun TitlePreview() {
    GitCommandsComposeTheme {
        Surface {
            TitleView()
        }
    }
}

@Preview
@Composable
fun CommandSelectionPreview() {
    GitCommandsComposeTheme {
        Surface {
            CommandsSelectionView(
                commandSelected = GitCommand("add", "add"),
                subCommandSelected = GitSubCommand("new changes", "new-changes", null, null),
                onShowCommandDialog = { }) {

            }
        }
    }
}

@Preview
@Composable
fun CommandDescriptionPreview() {
    GitCommandsComposeTheme {
        Surface {
            CommandDescriptionView(
                gitSubCommand = GitSubCommand(
                    label = "add",
                    value = "add",
                    usage = "git add <file.ext>",
                    nb = "To add all the files in the current directory, use “git add .”\n\nTo add a directory use “git add <directory>”"
                )
            )
        }
    }
}
