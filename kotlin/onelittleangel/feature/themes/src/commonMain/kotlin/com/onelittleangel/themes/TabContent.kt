package com.onelittleangel.themes

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesTabContent(
    state: LazyListState,
    quotes: List<UserQuote>,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize().testTag("interests:topics"),
        contentPadding = PaddingValues(
            top = (SearchBarDefaults.InputFieldHeight.value.dp * 2 +
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp + 16.dp),
            start = 16.dp,
            end = 16.dp,
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            quotes,
            key =  { it.hashCode() + it.isSaved.hashCode() }
        ) {quote ->
            QuoteCard(quote = quote,
                onTopicClick = onTopicClick,
                navigateTo = navigateTo,
                onToggleBookmark = onToggleBookmark)
        }
    }
}

*//*
@Composable
fun BiographyTabContent(
    state: LazyListState,
    biography: UserBiography,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            mutableListOf(biography),
            key = { it.hashCode() + it.isSaved.hashCode() }
        ) {
            BiographyCard(
                biography = it,
                onAuthorClick = {},
                modifier = modifier,
                onToggleBookmark = onToggleBookmark
            )
        }

        item {
            Spacer(Modifier.height(8.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.dp))
        }
    }
}
*//*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PicturesTabContent(
    state: LazyListState,
    pictures: List<UserPicture>,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize().testTag("interests:topics"),
        contentPadding = PaddingValues(
            top = (SearchBarDefaults.InputFieldHeight.value.dp * 2 +
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp + 16.dp),
            start = 16.dp,
            end = 16.dp,
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            pictures,
            key = { it.hashCode() + it.isSaved.hashCode() }
        ) {picture ->
            PictureCard(picture = picture, onToggleBookmark = onToggleBookmark)
        }
    }
}*/
