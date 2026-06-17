package org.com.onelittleangel.books

/*@RunWith(AndroidJUnit4ClassRunner::class)
class ScrollBooksFeedBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollFeedCompilationNone() = scrollFeed(CompilationMode.None())

    @Test
    fun scrollFeedCompilationBaselineProfile() = scrollFeed(CompilationMode.Partial())

    private fun scrollFeed(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        compilationMode = compilationMode,
        iterations = 10,
        startupMode = StartupMode.COLD,
        setupBlock = {
            // Start the app
            pressHome()
            startActivityAndWait()
        }
    ) {
        booksWaitForContent()
        //forYouSelectAuthors()
        booksScrollFeedDownUp()
    }
}*/
