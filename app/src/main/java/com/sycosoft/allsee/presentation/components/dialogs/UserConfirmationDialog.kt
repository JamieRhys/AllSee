package com.sycosoft.allsee.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.sycosoft.allsee.R

@Composable
fun UserConfirmationDialog(
    modifier: Modifier = Modifier,
    name: String,
    accountType: String,
    onDismissButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                modifier = Modifier.testTag(UserConfirmationDialogTestTags.TITLE),
                text = stringResource(R.string.user_confirmation_dialog_title)
            )
        },
        text = {
            Text(
                modifier = Modifier.testTag(UserConfirmationDialogTestTags.TEXT),
                text = stringResource(R.string.user_confirmation_dialog_text, name, accountType)
            )
        },
        confirmButton = {
            Button(
                modifier = Modifier.testTag(UserConfirmationDialogTestTags.CONFIRM_BUTTON),
                onClick = onConfirmButtonClick,
            ) {
                Text(text = stringResource(R.string.button_yes))
            }
        },
        dismissButton = {
            Button(
                modifier = Modifier.testTag(UserConfirmationDialogTestTags.DISMISS_BUTTON),
                onClick = onDismissButtonClick,
            ) {
                Text(text = stringResource(R.string.button_no))
            }
        }
    )
}