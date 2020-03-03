package com.amrabed.skeleton

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.*
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.cleanstatusbar.CleanStatusBar
import tools.fastlane.screengrab.locale.LocaleTestRule


@RunWith(AndroidJUnit4::class)
class ScreenshotTaker {

    @get:ClassRule
    val localeTestRule = LocaleTestRule()

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @BeforeClass
    fun cleanStatusBar() {
        CleanStatusBar()
            .setShowNotifications(false)
            .enable()
    }

    @Test
    fun takeScreenshots() {
        Screengrab.screenshot("main_activity")
    }

    @AfterClass
    fun done() {
        CleanStatusBar.disable()
    }
}