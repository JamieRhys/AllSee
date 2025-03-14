package uk.co.jaffakree.allsee.feature_accountdetails.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import uk.co.jaffakree.allsee.feature_accountdetails.components.cards.accountdetailscard.AccountDetailsCard
import uk.co.jaffakree.allsee.feature_accountdetails.components.cards.accountdetailscard.AccountDetailsType
import uk.co.jaffakree.allsee.feature_accountdetails.components.cards.accountdetailscard.CountryType
import uk.co.jaffakree.allsee.core.ui.theme.AllSeeTheme
import uk.co.jaffakree.allsee.feature_accountdetails.R

internal object AccountDetailsPageScreenTestTags {
    const val SCREEN = "screen_account_details_page"

    const val ACCOUNT_UK_DETAILS_CARD = "account_uk_details_card"
    const val ACCOUNT_INTERNATIONAL_DETAILS_CARD = "account_international_details_card"

    const val BUTTON_BACK = "button_back"

    const val TEXT_ACCOUNT_NUMBER = "account_number"
    const val TEXT_ACCOUNT_TYPE = "account_type"
    const val TEXT_BIC = "bic"
    const val TEXT_IBAN = "iban"
    const val TEXT_PERSON_NAME = "person_name"
    const val TEXT_SORT_CODE = "sort_code"
    const val TEXT_SCREEN_DESCRIPTION = "screen_description"
    const val TEXT_SCREEN_TITLE = "screen_title"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailsPageScreen(
    topSectionColor: Color = MaterialTheme.colorScheme.surface,
    bottomSectionColor: Color = MaterialTheme.colorScheme.inverseSurface,
    type: String,
    accountDetailsType: AccountDetailsType,
    onBackButtonClick: () -> Unit,
) {
    val topSectionHeight = 0.50f
    val topSectionWidth = 0.50f
    val bottomSectionHeight = 0.50f
    val bottomSectionWidth = 0.50f
    val roundedCornerSize = 90.dp

    Scaffold(
        modifier = Modifier.testTag(AccountDetailsPageScreenTestTags.SCREEN),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .testTag(AccountDetailsPageScreenTestTags.TEXT_SCREEN_TITLE),
                        text = stringResource(id = R.string.account_details_page_title), // TODO: remove
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag(AccountDetailsPageScreenTestTags.BUTTON_BACK),
                        onClick = { onBackButtonClick() },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            val (
                backgroundTop,
                backgroundTopRight,
                backgroundBottomLeft,
                backgroundBottom,
                foregroundContent,
            ) = createRefs()

            // Background/TopRight
            Box(
                modifier = Modifier
                    .fillMaxWidth(topSectionWidth)
                    .fillMaxHeight(topSectionHeight)
                    .background(color = bottomSectionColor)
                    .constrainAs(backgroundTopRight) {
                        top.linkTo(parent.top)
                        bottom.linkTo(backgroundBottom.top)
                        end.linkTo(parent.end)
                    }
            )
            
            // Background/Top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(topSectionHeight)
                    .background(
                        color = topSectionColor,
                        shape = RoundedCornerShape(bottomEnd = roundedCornerSize),
                    )
                    .constrainAs(backgroundTop) {
                        top.linkTo(parent.top)
                        bottom.linkTo(backgroundBottom.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // Background/BottomLeft
            Box(
                modifier = Modifier
                    .fillMaxWidth(bottomSectionWidth)
                    .fillMaxHeight(bottomSectionHeight)
                    .background(color = topSectionColor)
                    .constrainAs(backgroundBottom) {
                        top.linkTo(backgroundTop.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            )

            // Background/Bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(bottomSectionHeight)
                    .background(
                        color = bottomSectionColor,
                        shape = RoundedCornerShape(topStart = roundedCornerSize)
                    )
                    .constrainAs(backgroundBottom) {
                        top.linkTo(parent.top)
                        bottom.linkTo(backgroundBottom.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // Foreground/Content
            Box(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .constrainAs(foregroundContent) {
                        top.linkTo(backgroundTop.top)
                        bottom.linkTo(backgroundBottom.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Content(
                    type = type,
                    accountDetailsType = accountDetailsType,
                )
            }
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    type: String,
    accountDetailsType: AccountDetailsType,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            modifier = Modifier.testTag(AccountDetailsPageScreenTestTags.TEXT_SCREEN_DESCRIPTION),
            text = stringResource(id = R.string.account_details_page_description), // TODO: remove
        )

        Text(
            modifier = Modifier
                .padding(top = 16.dp)
                .testTag(AccountDetailsPageScreenTestTags.TEXT_ACCOUNT_TYPE),
            text = type,
            style = MaterialTheme.typography.bodyLarge,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp)
        ) {
            AccountDetailsCard(
                testTag = AccountDetailsPageScreenTestTags.ACCOUNT_UK_DETAILS_CARD,
                countryType = CountryType.UK,
                value = accountDetailsType,
            )
            AccountDetailsCard(
                testTag = AccountDetailsPageScreenTestTags.ACCOUNT_INTERNATIONAL_DETAILS_CARD,
                countryType = CountryType.INTERNATIONAL,
                value = accountDetailsType,
            )
        }
    }
}

@Preview(name = "Light Mode", showSystemUi = true, showBackground = true)
@Composable
private fun LM_AccountDetailsPageScreen() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            AccountDetailsPageScreen(
                type = "Individual",
                accountDetailsType = AccountDetailsType.Value(
                    accountHolderName = "Joe Bloggs",
                    accountNumber = "12345678",
                    sortCode = "123456",
                    iban = "GB12345678123456",
                    bic = "1324567",
                ),
                onBackButtonClick = {},
            )
        }
    }
}

@Preview(name = "Dark Mode", showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DM_AccountDetailsPageScreen() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            AccountDetailsPageScreen(
                type = "Individual",
                accountDetailsType = AccountDetailsType.Value(
                    accountHolderName = "Joe Bloggs",
                    accountNumber = "12345678",
                    sortCode = "123456",
                    iban = "GB12345678123456",
                    bic = "1324567",
                ),
                onBackButtonClick = {},
            )
        }
    }
}