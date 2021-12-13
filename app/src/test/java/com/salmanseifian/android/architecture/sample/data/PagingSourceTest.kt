package com.salmanseifian.android.architecture.sample.data

import androidx.paging.PagingSource
import com.salmanseifian.android.architecture.sample.data.model.Item
import com.salmanseifian.android.architecture.sample.data.model.Owner
import com.salmanseifian.android.architecture.sample.data.model.SearchRepositoriesResponse
import com.salmanseifian.android.architecture.sample.data.remote.ApiService
import com.salmanseifian.android.architecture.sample.data.remote.GithubRepositoryDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PagingSourceTest {

    @Mock
    lateinit var apiService: ApiService

    lateinit var githubRepositoryDataSource: GithubRepositoryDataSource

    val q = "tetris"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        githubRepositoryDataSource = GithubRepositoryDataSource(apiService, q)
    }


    @Test
    fun `repositories paging source load - failure - http error`() = runBlockingTest {
        val error = RuntimeException("404", Throwable())
        given(apiService.searchRepositories(any(), any())).willThrow(error)

        val expectedResult = PagingSource.LoadResult.Error<Int, Item>(error)

        assertEquals(
            expectedResult,
            githubRepositoryDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }


    @Test
    fun `repositories paging source load - failure - received null`() = runBlockingTest {
        given(apiService.searchRepositories(any(), any())).willReturn(null)

        val expectedResult = PagingSource.LoadResult.Error<Int, Item>(NullPointerException())

        assertEquals(
            expectedResult.toString(),
            githubRepositoryDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
    fun `repositories paging source refresh - success`() = runBlockingTest {
        given(apiService.searchRepositories(any(), any())).willReturn(searchRepositoryResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = searchRepositoryResponse.items,
            prevKey = null,
            nextKey = 2
        )

        assertEquals(
            expectedResult,
            githubRepositoryDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )

    }


    @Test
    fun `repositories paging source append - success`() = runBlockingTest {
        given(apiService.searchRepositories(any(), any())).willReturn(nextsSearchRepositoryResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = nextsSearchRepositoryResponse.items,
            prevKey = null,
            nextKey = 2
        )
        assertEquals(
            expectedResult, githubRepositoryDataSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    companion object {
        val searchRepositoryResponse = SearchRepositoriesResponse(
            false,
            listOf(
                Item(
                    id = 76954504,
                    nodeId = "MDEwOlJlcG9zaXRvcnk3Njk1NDUwNA==",
                    name = "react-tetris",
                    fullName = "chvin/react-tetris",
                    private = false,
                    owner = Owner(
                        login = "chvin",
                        id = 5383506,
                        nodeId = "MDQ6VXNlcjUzODM1MDY=",
                        avatarUrl = "https://avatars.githubusercontent.com/u/5383506?v=4",
                        gravatarId = "",
                        url = "https://api.github.com/users/chvin",
                        htmlUrl = "https://github.com/chvin",
                        followersUrl = "https://api.github.com/users/chvin/followers",
                        followingUrl = "https://api.github.com/users/chvin/following{/other_user}",
                        gistsUrl = "https://api.github.com/users/chvin/gists{/gist_id}",
                        starredUrl = "https://api.github.com/users/chvin/starred{/owner}{/repo}",
                        subscriptionsUrl = "https://api.github.com/users/chvin/subscriptions",
                        organizationsUrl = "https://api.github.com/users/chvin/orgs",
                        reposUrl = "https://api.github.com/users/chvin/repos",
                        eventsUrl = "https://api.github.com/users/chvin/events{/privacy}",
                        receivedEventsUrl = "https://api.github.com/users/chvin/received_events",
                        type = "User",
                        siteAdmin = false
                    ),
                    htmlUrl = "https://github.com/chvin/react-tetris",
                    description = "Use React, Redux, Immutable to code Tetris.",
                    fork = false,
                    url = "",
                    forksUrl = "https://api.github.com/repos/chvin/react-tetris/forks",
                    keysUrl = "https://api.github.com/repos/chvin/react-tetris/keys{/key_id}",
                    collaboratorsUrl = "https://api.github.com/repos/chvin/react-tetris/collaborators{/collaborator}",
                    teamsUrl = "https://api.github.com/repos/chvin/react-tetris/teams",
                    hooksUrl = "https://api.github.com/repos/chvin/react-tetris/hooks",
                    issueEventsUrl = "https://api.github.com/repos/chvin/react-tetris/issues/events{/number}",
                    eventsUrl = "https://api.github.com/repos/chvin/react-tetris/events",
                    assigneesUrl = "https://api.github.com/repos/chvin/react-tetris/assignees{/user}",
                    branchesUrl = "https://api.github.com/repos/chvin/react-tetris/branches{/branch}",
                    tagsUrl = "https://api.github.com/repos/chvin/react-tetris/tags",
                    blobsUrl = "https://api.github.com/repos/chvin/react-tetris/git/blobs{/sha}",
                    gitTagsUrl = "https://api.github.com/repos/chvin/react-tetris/git/tags{/sha}",
                    gitRefsUrl = "https://api.github.com/repos/chvin/react-tetris/git/refs{/sha}",
                    treesUrl = "https://api.github.com/repos/chvin/react-tetris/git/trees{/sha}",
                    statusesUrl = "https://api.github.com/repos/chvin/react-tetris/statuses/{sha}",
                    languagesUrl = "https://api.github.com/repos/chvin/react-tetris/languages",
                    stargazersUrl = "https://api.github.com/repos/chvin/react-tetris/stargazers",
                    contributorsUrl = "https://api.github.com/repos/chvin/react-tetris/contributors",
                    subscribersUrl = "https://api.github.com/repos/chvin/react-tetris/subscribers",
                    subscriptionUrl = "https://api.github.com/repos/chvin/react-tetris/subscription",
                    commitsUrl = "https://api.github.com/repos/chvin/react-tetris/commits{/sha}",
                    gitCommitsUrl = "https://api.github.com/repos/chvin/react-tetris/git/commits{/sha}",
                    commentsUrl = "https://api.github.com/repos/chvin/react-tetris/comments{/number}",
                    issueCommentUrl = "https://api.github.com/repos/chvin/react-tetris/issues/comments{/number}",
                    contentsUrl = "https://api.github.com/repos/chvin/react-tetris/contents/{+path}",
                    compareUrl = "https://api.github.com/repos/chvin/react-tetris/compare/{base}...{head}",
                    mergesUrl = "https://api.github.com/repos/chvin/react-tetris/merges",
                    archiveUrl = "https://api.github.com/repos/chvin/react-tetris/{archive_format}{/ref}",
                    downloadsUrl = "https://api.github.com/repos/chvin/react-tetris/downloads",
                    issuesUrl = "https://api.github.com/repos/chvin/react-tetris/issues{/number}",
                    pullsUrl = "https://api.github.com/repos/chvin/react-tetris/pulls{/number}",
                    milestonesUrl = "https://api.github.com/repos/chvin/react-tetris/milestones{/number}",
                    notificationsUrl = "https://api.github.com/repos/chvin/react-tetris/notifications{?since,all,participating}",
                    labelsUrl = "https://api.github.com/repos/chvin/react-tetris/labels{/name}",
                    releasesUrl = "https://api.github.com/repos/chvin/react-tetris/releases{/id}",
                    deploymentsUrl = "https://api.github.com/repos/chvin/react-tetris/deployments",
                    createdAt = "2016-12-20T12:26:11Z",
                    updatedAt = "2021-12-13T08:21:40Z",
                    pushedAt = "2021-05-10T05:56:48Z",
                    gitUrl = "git://github.com/chvin/react-tetris.git",
                    sshUrl = "git@github.com:chvin/react-tetris.git",
                    cloneUrl = "https://github.com/chvin/react-tetris.git",
                    svnUrl = "https://github.com/chvin/react-tetris",
                    homepage = "https://chvin.github.io/react-tetris/?lan=en",
                    size = 4319,
                    stargazersCount = 6766,
                    watchersCount = 6766,
                    language = "JavaScript",
                    hasIssues = true,
                    hasProjects = true,
                    hasDownloads = true,
                    hasWiki = true,
                    hasPages = true,
                    forksCount = 1441,
                    mirrorUrl = null,
                    archived = false,
                    disabled = false,
                    openIssuesCount = 5,
                    license = null,
                    allowForking = true,
                    isTemplate = false,
                    topics = listOf(
                        "immutable",
                        "react",
                        "redux",
                        "tetris"
                    ),
                    visibility = "public",
                    forks = 1441,
                    openIssues = 5,
                    watchers = 6766,
                    defaultBranch = "master",
                    score = 1
                )
            ),
            46558
        )


        val nextsSearchRepositoryResponse = SearchRepositoriesResponse(
            false,
            listOf(
                Item(
                    id = 76954504,
                    nodeId = "MDEwOlJlcG9zaXRvcnk3Njk1NDUwNA==",
                    name = "react-tetris",
                    fullName = "chvin/react-tetris",
                    private = false,
                    owner = Owner(
                        login = "chvin",
                        id = 5383506,
                        nodeId = "MDQ6VXNlcjUzODM1MDY=",
                        avatarUrl = "https://avatars.githubusercontent.com/u/5383506?v=4",
                        gravatarId = "",
                        url = "https://api.github.com/users/chvin",
                        htmlUrl = "https://github.com/chvin",
                        followersUrl = "https://api.github.com/users/chvin/followers",
                        followingUrl = "https://api.github.com/users/chvin/following{/other_user}",
                        gistsUrl = "https://api.github.com/users/chvin/gists{/gist_id}",
                        starredUrl = "https://api.github.com/users/chvin/starred{/owner}{/repo}",
                        subscriptionsUrl = "https://api.github.com/users/chvin/subscriptions",
                        organizationsUrl = "https://api.github.com/users/chvin/orgs",
                        reposUrl = "https://api.github.com/users/chvin/repos",
                        eventsUrl = "https://api.github.com/users/chvin/events{/privacy}",
                        receivedEventsUrl = "https://api.github.com/users/chvin/received_events",
                        type = "User",
                        siteAdmin = false
                    ),
                    htmlUrl = "https://github.com/chvin/react-tetris",
                    description = "Use React, Redux, Immutable to code Tetris.",
                    fork = false,
                    url = "",
                    forksUrl = "https://api.github.com/repos/chvin/react-tetris/forks",
                    keysUrl = "https://api.github.com/repos/chvin/react-tetris/keys{/key_id}",
                    collaboratorsUrl = "https://api.github.com/repos/chvin/react-tetris/collaborators{/collaborator}",
                    teamsUrl = "https://api.github.com/repos/chvin/react-tetris/teams",
                    hooksUrl = "https://api.github.com/repos/chvin/react-tetris/hooks",
                    issueEventsUrl = "https://api.github.com/repos/chvin/react-tetris/issues/events{/number}",
                    eventsUrl = "https://api.github.com/repos/chvin/react-tetris/events",
                    assigneesUrl = "https://api.github.com/repos/chvin/react-tetris/assignees{/user}",
                    branchesUrl = "https://api.github.com/repos/chvin/react-tetris/branches{/branch}",
                    tagsUrl = "https://api.github.com/repos/chvin/react-tetris/tags",
                    blobsUrl = "https://api.github.com/repos/chvin/react-tetris/git/blobs{/sha}",
                    gitTagsUrl = "https://api.github.com/repos/chvin/react-tetris/git/tags{/sha}",
                    gitRefsUrl = "https://api.github.com/repos/chvin/react-tetris/git/refs{/sha}",
                    treesUrl = "https://api.github.com/repos/chvin/react-tetris/git/trees{/sha}",
                    statusesUrl = "https://api.github.com/repos/chvin/react-tetris/statuses/{sha}",
                    languagesUrl = "https://api.github.com/repos/chvin/react-tetris/languages",
                    stargazersUrl = "https://api.github.com/repos/chvin/react-tetris/stargazers",
                    contributorsUrl = "https://api.github.com/repos/chvin/react-tetris/contributors",
                    subscribersUrl = "https://api.github.com/repos/chvin/react-tetris/subscribers",
                    subscriptionUrl = "https://api.github.com/repos/chvin/react-tetris/subscription",
                    commitsUrl = "https://api.github.com/repos/chvin/react-tetris/commits{/sha}",
                    gitCommitsUrl = "https://api.github.com/repos/chvin/react-tetris/git/commits{/sha}",
                    commentsUrl = "https://api.github.com/repos/chvin/react-tetris/comments{/number}",
                    issueCommentUrl = "https://api.github.com/repos/chvin/react-tetris/issues/comments{/number}",
                    contentsUrl = "https://api.github.com/repos/chvin/react-tetris/contents/{+path}",
                    compareUrl = "https://api.github.com/repos/chvin/react-tetris/compare/{base}...{head}",
                    mergesUrl = "https://api.github.com/repos/chvin/react-tetris/merges",
                    archiveUrl = "https://api.github.com/repos/chvin/react-tetris/{archive_format}{/ref}",
                    downloadsUrl = "https://api.github.com/repos/chvin/react-tetris/downloads",
                    issuesUrl = "https://api.github.com/repos/chvin/react-tetris/issues{/number}",
                    pullsUrl = "https://api.github.com/repos/chvin/react-tetris/pulls{/number}",
                    milestonesUrl = "https://api.github.com/repos/chvin/react-tetris/milestones{/number}",
                    notificationsUrl = "https://api.github.com/repos/chvin/react-tetris/notifications{?since,all,participating}",
                    labelsUrl = "https://api.github.com/repos/chvin/react-tetris/labels{/name}",
                    releasesUrl = "https://api.github.com/repos/chvin/react-tetris/releases{/id}",
                    deploymentsUrl = "https://api.github.com/repos/chvin/react-tetris/deployments",
                    createdAt = "2016-12-20T12:26:11Z",
                    updatedAt = "2021-12-13T08:21:40Z",
                    pushedAt = "2021-05-10T05:56:48Z",
                    gitUrl = "git://github.com/chvin/react-tetris.git",
                    sshUrl = "git@github.com:chvin/react-tetris.git",
                    cloneUrl = "https://github.com/chvin/react-tetris.git",
                    svnUrl = "https://github.com/chvin/react-tetris",
                    homepage = "https://chvin.github.io/react-tetris/?lan=en",
                    size = 4319,
                    stargazersCount = 6766,
                    watchersCount = 6766,
                    language = "JavaScript",
                    hasIssues = true,
                    hasProjects = true,
                    hasDownloads = true,
                    hasWiki = true,
                    hasPages = true,
                    forksCount = 1441,
                    mirrorUrl = null,
                    archived = false,
                    disabled = false,
                    openIssuesCount = 5,
                    license = null,
                    allowForking = true,
                    isTemplate = false,
                    topics = listOf(
                        "immutable",
                        "react",
                        "redux",
                        "tetris"
                    ),
                    visibility = "public",
                    forks = 1441,
                    openIssues = 5,
                    watchers = 6766,
                    defaultBranch = "master",
                    score = 1
                ),
                Item(
                    id = 76954504,
                    nodeId = "MDEwOlJlcG9zaXRvcnk3Njk1NDUwNA==",
                    name = "react-tetris",
                    fullName = "chvin/react-tetris",
                    private = false,
                    owner = Owner(
                        login = "chvin",
                        id = 5383506,
                        nodeId = "MDQ6VXNlcjUzODM1MDY=",
                        avatarUrl = "https://avatars.githubusercontent.com/u/5383506?v=4",
                        gravatarId = "",
                        url = "https://api.github.com/users/chvin",
                        htmlUrl = "https://github.com/chvin",
                        followersUrl = "https://api.github.com/users/chvin/followers",
                        followingUrl = "https://api.github.com/users/chvin/following{/other_user}",
                        gistsUrl = "https://api.github.com/users/chvin/gists{/gist_id}",
                        starredUrl = "https://api.github.com/users/chvin/starred{/owner}{/repo}",
                        subscriptionsUrl = "https://api.github.com/users/chvin/subscriptions",
                        organizationsUrl = "https://api.github.com/users/chvin/orgs",
                        reposUrl = "https://api.github.com/users/chvin/repos",
                        eventsUrl = "https://api.github.com/users/chvin/events{/privacy}",
                        receivedEventsUrl = "https://api.github.com/users/chvin/received_events",
                        type = "User",
                        siteAdmin = false
                    ),
                    htmlUrl = "https://github.com/chvin/react-tetris",
                    description = "Use React, Redux, Immutable to code Tetris.",
                    fork = false,
                    url = "",
                    forksUrl = "https://api.github.com/repos/chvin/react-tetris/forks",
                    keysUrl = "https://api.github.com/repos/chvin/react-tetris/keys{/key_id}",
                    collaboratorsUrl = "https://api.github.com/repos/chvin/react-tetris/collaborators{/collaborator}",
                    teamsUrl = "https://api.github.com/repos/chvin/react-tetris/teams",
                    hooksUrl = "https://api.github.com/repos/chvin/react-tetris/hooks",
                    issueEventsUrl = "https://api.github.com/repos/chvin/react-tetris/issues/events{/number}",
                    eventsUrl = "https://api.github.com/repos/chvin/react-tetris/events",
                    assigneesUrl = "https://api.github.com/repos/chvin/react-tetris/assignees{/user}",
                    branchesUrl = "https://api.github.com/repos/chvin/react-tetris/branches{/branch}",
                    tagsUrl = "https://api.github.com/repos/chvin/react-tetris/tags",
                    blobsUrl = "https://api.github.com/repos/chvin/react-tetris/git/blobs{/sha}",
                    gitTagsUrl = "https://api.github.com/repos/chvin/react-tetris/git/tags{/sha}",
                    gitRefsUrl = "https://api.github.com/repos/chvin/react-tetris/git/refs{/sha}",
                    treesUrl = "https://api.github.com/repos/chvin/react-tetris/git/trees{/sha}",
                    statusesUrl = "https://api.github.com/repos/chvin/react-tetris/statuses/{sha}",
                    languagesUrl = "https://api.github.com/repos/chvin/react-tetris/languages",
                    stargazersUrl = "https://api.github.com/repos/chvin/react-tetris/stargazers",
                    contributorsUrl = "https://api.github.com/repos/chvin/react-tetris/contributors",
                    subscribersUrl = "https://api.github.com/repos/chvin/react-tetris/subscribers",
                    subscriptionUrl = "https://api.github.com/repos/chvin/react-tetris/subscription",
                    commitsUrl = "https://api.github.com/repos/chvin/react-tetris/commits{/sha}",
                    gitCommitsUrl = "https://api.github.com/repos/chvin/react-tetris/git/commits{/sha}",
                    commentsUrl = "https://api.github.com/repos/chvin/react-tetris/comments{/number}",
                    issueCommentUrl = "https://api.github.com/repos/chvin/react-tetris/issues/comments{/number}",
                    contentsUrl = "https://api.github.com/repos/chvin/react-tetris/contents/{+path}",
                    compareUrl = "https://api.github.com/repos/chvin/react-tetris/compare/{base}...{head}",
                    mergesUrl = "https://api.github.com/repos/chvin/react-tetris/merges",
                    archiveUrl = "https://api.github.com/repos/chvin/react-tetris/{archive_format}{/ref}",
                    downloadsUrl = "https://api.github.com/repos/chvin/react-tetris/downloads",
                    issuesUrl = "https://api.github.com/repos/chvin/react-tetris/issues{/number}",
                    pullsUrl = "https://api.github.com/repos/chvin/react-tetris/pulls{/number}",
                    milestonesUrl = "https://api.github.com/repos/chvin/react-tetris/milestones{/number}",
                    notificationsUrl = "https://api.github.com/repos/chvin/react-tetris/notifications{?since,all,participating}",
                    labelsUrl = "https://api.github.com/repos/chvin/react-tetris/labels{/name}",
                    releasesUrl = "https://api.github.com/repos/chvin/react-tetris/releases{/id}",
                    deploymentsUrl = "https://api.github.com/repos/chvin/react-tetris/deployments",
                    createdAt = "2016-12-20T12:26:11Z",
                    updatedAt = "2021-12-13T08:21:40Z",
                    pushedAt = "2021-05-10T05:56:48Z",
                    gitUrl = "git://github.com/chvin/react-tetris.git",
                    sshUrl = "git@github.com:chvin/react-tetris.git",
                    cloneUrl = "https://github.com/chvin/react-tetris.git",
                    svnUrl = "https://github.com/chvin/react-tetris",
                    homepage = "https://chvin.github.io/react-tetris/?lan=en",
                    size = 4319,
                    stargazersCount = 6766,
                    watchersCount = 6766,
                    language = "JavaScript",
                    hasIssues = true,
                    hasProjects = true,
                    hasDownloads = true,
                    hasWiki = true,
                    hasPages = true,
                    forksCount = 1441,
                    mirrorUrl = null,
                    archived = false,
                    disabled = false,
                    openIssuesCount = 5,
                    license = null,
                    allowForking = true,
                    isTemplate = false,
                    topics = listOf(
                        "immutable",
                        "react",
                        "redux",
                        "tetris"
                    ),
                    visibility = "public",
                    forks = 1441,
                    openIssues = 5,
                    watchers = 6766,
                    defaultBranch = "master",
                    score = 1
                )
            ),
            46558
        )
    }

}