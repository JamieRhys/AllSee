package uk.co.jaffakree.allsee.login.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import uk.co.jaffakree.allsee.core.presentation.theme.AllSeeTheme
import uk.co.jaffakree.allsee.login.R

internal object UserConfirmationDialogTestTags {
    const val COMPONENT = "user_confirmation_dialog"

    const val BUTTON_CONFIRM = "${COMPONENT}_button_confirm"
    const val BUTTON_CONFIRM_TEXT = "${BUTTON_CONFIRM}_text"

    const val BUTTON_DISMISS = "${COMPONENT}_button_dismiss"
    const val BUTTON_DISMISS_TEXT = "${BUTTON_DISMISS}_text"

    const val TEXT_TITLE = "${COMPONENT}_text_title"
    const val TEXT_BODY = "${COMPONENT}_text_body"
}

/** Used to confirm if the user details match what has been pulled from the API. This is
 * important because we do not want to show an incorrect user any details that do not belong
 * to them within the app.
 *
 * @property name The users name pulled from the API
 * @property accountType The account type which is pulled from the API
 * @property onDismissButtonClick Callback when the dismiss button is clicked by the user
 * @property onConfirmButtonClick Callback when the confirm button is clicked by the user
 * @property onDismissRequest Callback when the user clicks outside of the dialog (or back button)
 */
@Composable
internal fun UserConfirmationDialog(
    name: String,
    accountType: String,
    onDismissButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier
            .testTag(UserConfirmationDialogTestTags.COMPONENT),
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                modifier = Modifier
                    .testTag(UserConfirmationDialogTestTags.TEXT_TITLE),
                text = stringResource(R.string.user_confirmation_dialog_title)
            )
        },
        text = {
            Text(
                modifier = Modifier
                    .testTag(UserConfirmationDialogTestTags.TEXT_BODY),
                text = stringResource(
                    R.string.user_confirmation_dialog_text,
                    name,
                    accountType
                )
            )
        },
        confirmButton = {
            Button(
                modifier = Modifier
                    .testTag(UserConfirmationDialogTestTags.BUTTON_CONFIRM),
                onClick = { onConfirmButtonClick() },
            ) {
                Text(
                    modifier = Modifier
                        .testTag(UserConfirmationDialogTestTags.BUTTON_CONFIRM_TEXT),
                    text = stringResource(uk.co.jaffakree.allsee.core.R.string.button_yes)
                )
            }
        },
        dismissButton = {
            TextButton(
                modifier = Modifier
                    .testTag(UserConfirmationDialogTestTags.BUTTON_DISMISS),
                onClick = { onDismissButtonClick() },
            ) {
                Text(
                    modifier = Modifier
                        .testTag(UserConfirmationDialogTestTags.BUTTON_DISMISS_TEXT),
                    text = stringResource(uk.co.jaffakree.allsee.core.R.string.button_no),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
    )
}

// region Previews

@PreviewLightDark
@Composable
private fun UserConfirmationDialogPreview() {
    AllSeeTheme {
        Surface {
            UserConfirmationDialog(
                name = "Joe Bloggs",
                accountType = "Individual",
                onDismissButtonClick = {},
                onConfirmButtonClick = {},
                onDismissRequest = {},
            )
        }
    }
}

// endregion