package org.com.onelittleangel.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.com.onelittleangel.PACKAGE_NAME
import org.com.onelittleangel.authors.authorsScrollFeedDownUp
import org.com.onelittleangel.authors.authorsWaitForContent
import org.com.onelittleangel.books.booksScrollFeedDownUp
import org.com.onelittleangel.books.booksWaitForContent
import org.com.onelittleangel.faiths.faithsScrollFeedDownUp
import org.com.onelittleangel.faiths.faithsWaitForContent
import org.com.onelittleangel.foryou.forYouScrollFeedDownUp
import org.com.onelittleangel.foryou.forYouWaitForContent
import org.com.onelittleangel.themes.themesScrollFeedDownUp
import org.com.onelittleangel.themes.themesWaitForContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class generates a basic startup baseline profile for the target package.
 *
 * We recommend you start with this but add important user flows to the profile to improve their performance.
 * Refer to the [baseline profile documentation](https://d.android.com/topic/performance/baselineprofiles)
 * for more information.
 *
 * You can run the generator with the "Generate Baseline Profile" run configuration in Android Studio or
 * the equivalent `generateBaselineProfile` gradle task:
 * ```
 * ./gradlew :androidApp:generateReleaseBaselineProfile
 * ```
 * The run configuration runs the Gradle task and applies filtering to run only the generators.
 *
 * Check [documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args)
 * for more information about available instrumentation arguments.
 *
 * After you run the generator, you can verify the improvements running the [StartupBenchmarks] benchmark.
 *
 * When using this class to generate a baseline profile, only API 33+ or rooted API 28+ are supported.
 *
 * The minimum required version of androidx.benchmark to generate a baseline profile is 1.2.0.
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() =
        baselineProfileRule.collect(
            packageName = PACKAGE_NAME,
            includeInStartupProfile = true
        ) {
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll
            // through your most important UI.
            
            pressHome()
            startActivityAndWait()

            val searchBar = device.findObject(By.res("searchBar:placeholder"))

            forYouWaitForContent()
            //forYouSelectAuthors(true)
            forYouScrollFeedDownUp()

            searchBar.click()
            device.waitForIdle()

            device.wait(Until.findObject(By.res("authors")), 30_000)

            device.findObject(By.res("authors")).click()
            device.waitForIdle()

            authorsWaitForContent()
            authorsScrollFeedDownUp()

            searchBar.clickAndWait(Until.newWindow(), 1_000)
            device.waitForIdle()

            val books = device.findObject(By.res("books"))
            books.clickAndWait(Until.newWindow(), 1_000)

            device.waitForIdle()

            booksWaitForContent()
            booksScrollFeedDownUp()

            searchBar.clickAndWait(Until.newWindow(), 1_000)
            device.waitForIdle()

            val faiths = device.findObject(By.res("faiths"))
            faiths.clickAndWait(Until.newWindow(), 1_000)

            device.waitForIdle()

            faithsWaitForContent()
            faithsScrollFeedDownUp()

            searchBar.clickAndWait(Until.newWindow(), 1_000)
            device.waitForIdle()

            val themes = device.findObject(By.res("themes"))
            themes.clickAndWait(Until.newWindow(), 1_000)

            themesWaitForContent()
            themesScrollFeedDownUp()
        }
}
