package com.sycosoft.allsee

import android.content.res.Resources
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.models.types.AccountHolderType
import com.sycosoft.allsee.domain.repository.AppRepository
import com.sycosoft.allsee.presentation.MainActivity
import com.sycosoft.allsee.presentation.MockAllSeeApplication
import com.sycosoft.allsee.presentation.components.dialogs.UserConfirmationDialogTestTags
import com.sycosoft.allsee.presentation.components.screens.accountaccesspage.AccessTokenRequestScreenTestTags
import io.mockk.coEvery
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    internal lateinit var appRepository: AppRepository

    private lateinit var app: MockAllSeeApplication

    @Before
    fun setup() {
        app = ApplicationProvider.getApplicationContext<MockAllSeeApplication>() as MockAllSeeApplication
        app.component.inject(this)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun happyPathTest() {

        // Setup
        coEvery { appRepository.saveToken(any()) } returns Unit
        coEvery { appRepository.getPerson() } returns Person(
            uid = UUID.randomUUID(),
            type = AccountHolderType.INDIVIDUAL,
            title = "vocibus",
            firstName = "Olive Huff",
            lastName = "Jerry Torres",
            dob = LocalDate.now(),
            email = "frieda.rosario@example.com",
            phone = "(929) 435-2759"

        )
        val robot = MainActivityLoginRobot(rule = composeTestRule, resources = app.resources)
        activityScenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)

        // Assertions
        robot.seesLoginTitle() // Assertions probably not required by now - we just want to test user journeys

        // Actions
        robot.insertsTokenText()
        robot.clicksButton()
        robot.waitsForDialog()
        robot.clicksDialogConfirm()

        // Test next "screen" etc ...
    }
}

/**
 * Testing robot to abstract boilerplate rule interactions.
 *
 * seesXxx
 * clicksXxx
 * waitsXxx
 *
 */
class MainActivityLoginRobot(
    private val rule: ComposeTestRule,
    private val resources: Resources
) {

    fun seesLoginTitle() {
        with(rule) {
            onNodeWithTag(testTag = AccessTokenRequestScreenTestTags.TITLE)
                .assertTextEquals(resources.getString(R.string.aap_title))
        }
    }

    fun insertsTokenText() {
        with(rule) {
            onNodeWithTag(AccessTokenRequestScreenTestTags.ACCESS_TOKEN_INPUT)
                .performTextInput("12345")
        }
    }

    fun clicksButton() {
        rule.onNodeWithTag(AccessTokenRequestScreenTestTags.BUTTON_GET_STARTED)  // find node by text / tag
            .performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun waitsForDialog() {
        with(rule) {
            waitUntilExactlyOneExists(hasTestTag(UserConfirmationDialogTestTags.CONFIRM_BUTTON))
        }
    }

    fun clicksDialogConfirm() {
        rule.onNodeWithTag(UserConfirmationDialogTestTags.CONFIRM_BUTTON)
            .performClick()
    }
}