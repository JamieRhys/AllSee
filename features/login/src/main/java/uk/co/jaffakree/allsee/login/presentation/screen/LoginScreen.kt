package uk.co.jaffakree.allsee.login.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import uk.co.jaffakree.allsee.core.presentation.theme.AllSeeTheme
import uk.co.jaffakree.allsee.login.R

internal object LoginScreenTestTags {
    const val COMPONENT = "login_screen"

    const val BUTTON_LOGIN = "${COMPONENT}_button_login"
    const val BUTTON_LOGIN_TEXT = "${BUTTON_LOGIN}_text"

    const val PROGRESS_BAR = "${COMPONENT}_progress_bar"

    const val TEXT_FIELD_ACCESS_TOKEN = "${COMPONENT}_text_field_access_token"

    const val TEXT_GET_STARTED = "${COMPONENT}_text_get_started"
    const val TEXT_LOGGING_IN = "${COMPONENT}_text_logging_in"
    const val TEXT_TITLE = "${COMPONENT}_text_title"
}

@Composable
internal fun LoginScreen(
    accessToken: String,
    onAccessTokenChange: (String) -> Unit,
    showProgressBar: Boolean,
    onLoginButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .testTag(LoginScreenTestTags.COMPONENT)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (showProgressBar) {
            ProgressBarContent()
        } else {
            QuestionContent(
                accessToken = accessToken,
                onAccessTokenChange = { onAccessTokenChange(it) },
                onLoginButtonClick = { onLoginButtonClick() },
            )
        }
    }
}

@Composable
private fun QuestionContent(
    accessToken: String,
    onAccessTokenChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
) {
    Text(
        modifier = Modifier
            .testTag(LoginScreenTestTags.TEXT_TITLE)
            .padding(32.dp),
        text = stringResource(R.string.title),
        style = MaterialTheme.typography.titleLarge,
    )
    Text(
        modifier = Modifier
            .testTag(LoginScreenTestTags.TEXT_GET_STARTED),
        text = stringResource(R.string.get_started)
    )
    OutlinedTextField(
        modifier = Modifier
            .testTag(LoginScreenTestTags.TEXT_FIELD_ACCESS_TOKEN)
            .padding(top = 64.dp, bottom = 16.dp),
        value = accessToken,
        singleLine = true,
        onValueChange = { onAccessTokenChange(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.access_token)
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
        )
    )
    Button(
        modifier = Modifier
            .testTag(LoginScreenTestTags.BUTTON_LOGIN),
        onClick = { onLoginButtonClick() },
    ) {
        Text(
            modifier = Modifier
                .testTag(LoginScreenTestTags.BUTTON_LOGIN_TEXT),
            text = stringResource(R.string.button_login),
        )
    }
}

@Composable
private fun ProgressBarContent() {
    CircularProgressIndicator(
        modifier = Modifier
            .testTag(LoginScreenTestTags.PROGRESS_BAR)
    )
    Text(
        modifier = Modifier
            .testTag(LoginScreenTestTags.TEXT_LOGGING_IN),
        text = stringResource(R.string.logging_in)
    )
}

// region Previews

@PreviewLightDark
@Composable
fun LoginScreenPreview() {
    AllSeeTheme {
        Surface {
            LoginScreen(
                accessToken = "",
                onAccessTokenChange = {},
                showProgressBar = false,
                onLoginButtonClick = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
fun LoginScreenWithProgressBarPreview() {
    AllSeeTheme {
        Surface {
            LoginScreen(
                accessToken = "",
                onAccessTokenChange = {},
                showProgressBar = true,
                onLoginButtonClick = {}
            )
        }
    }
}

// endregion