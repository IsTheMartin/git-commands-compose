package com.mrtnmrls.gitcommandscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mrtnmrls.gitcommandscompose.data.local.LocalDataSource
import com.mrtnmrls.gitcommandscompose.ui.theme.GitCommandsComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitCommandsComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val commands = LocalDataSource(context).getGitCommands()
                    val subCommands = LocalDataSource(context).getGitSubCommands("show")
                    Column {
                        Text(modifier = Modifier.fillMaxWidth(), text = commands.toString())
                        Divider()
                        Text(modifier = Modifier.fillMaxWidth(), text = subCommands.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitCommandsComposeTheme {
        Greeting("Android")
    }
}