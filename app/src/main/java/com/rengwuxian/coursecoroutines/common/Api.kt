package com.rengwuxian.coursecoroutines.common

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.mock.BehaviorDelegate
import java.util.concurrent.CompletableFuture

const val GITHUB_API: String = "https://api.github.com"

data class Contributor(val login: String, val contributions: Int)

interface GitHub {
  // https://api.github.com/repos/{owner}/{repo}/contributors
  @GET("/repos/{owner}/{repo}/contributors")
  fun contributorsCall(
    @Path("owner") owner: String, // square
    @Path("repo") repo: String, // retrofit
  ): Call<List<Contributor>>

  @GET("/repos/{owner}/{repo}/contributors")
  suspend fun contributors(
    @Path("owner") owner: String,
    @Path("repo") repo: String,
  ): List<Contributor>

  @GET("/repos/{owner}/{repo}/contributors")
  fun contributorsFuture(
    @Path("owner") owner: String,
    @Path("repo") repo: String,
  ): CompletableFuture<List<Contributor>>

  @GET("/repos/{owner}/{repo}/contributors")
  fun contributorsRx(
    @Path("owner") owner: String,
    @Path("repo") repo: String,
  ): Single<List<Contributor>>
}

class MockGitHub(private val delegate: BehaviorDelegate<GitHub>) : GitHub {
  private val ownerRepoContributors: MutableMap<String, MutableMap<String, MutableList<Contributor>>> =
    LinkedHashMap()

  init {
    // Seed some mock data.
    addContributor("square", "retrofit", "John Doe", 12)
    addContributor("square", "retrofit", "Bob Smith", 2)
    addContributor("square", "retrofit", "Big Bird", 40)
    addContributor("square", "okhttp", "Proposition Joe", 39)
    addContributor("square", "okhttp", "Keiser Soze", 152)
  }

  override fun contributorsCall(owner: String, repo: String): Call<List<Contributor>> {
    var response: List<Contributor>? = emptyList()
    val repoContributors: Map<String, MutableList<Contributor>>? =
      ownerRepoContributors[owner]
    if (repoContributors != null) {
      val contributors: List<Contributor>? =
        repoContributors[repo]
      if (contributors != null) {
        response = contributors
      }
    }
    return delegate.returningResponse(response).contributorsCall(owner, repo)
  }

  override fun contributorsFuture(owner: String, repo: String): CompletableFuture<List<Contributor>> {
    var response: List<Contributor>? = emptyList()
    val repoContributors: Map<String, MutableList<Contributor>>? =
      ownerRepoContributors[owner]
    if (repoContributors != null) {
      val contributors: List<Contributor>? =
        repoContributors[repo]
      if (contributors != null) {
        response = contributors
      }
    }
    return delegate.returningResponse(response).contributorsFuture(owner, repo)
  }

  override suspend fun contributors(owner: String, repo: String): List<Contributor> {
    var response: List<Contributor>? = emptyList()
    val repoContributors: Map<String, MutableList<Contributor>>? =
      ownerRepoContributors[owner]
    if (repoContributors != null) {
      val contributors: List<Contributor>? =
        repoContributors[repo]
      if (contributors != null) {
        response = contributors
      }
    }
    return delegate.returningResponse(response).contributors(owner, repo)
  }

  override fun contributorsRx(owner: String, repo: String): Single<List<Contributor>> {
    var response: List<Contributor>? = emptyList()
    val repoContributors: Map<String, MutableList<Contributor>>? =
      ownerRepoContributors[owner]
    if (repoContributors != null) {
      val contributors: List<Contributor>? =
        repoContributors[repo]
      if (contributors != null) {
        response = contributors
      }
    }
    return delegate.returningResponse(response).contributorsRx(owner, repo)
  }

  private fun addContributor(owner: String, repo: String, name: String?, contributions: Int) {
    var repoContributors = ownerRepoContributors[owner]
    if (repoContributors == null) {
      repoContributors = LinkedHashMap()
      ownerRepoContributors[owner] = repoContributors
    }
    var contributors = repoContributors[repo]
    if (contributors == null) {
      contributors = ArrayList()
      repoContributors[repo] = contributors
    }
    contributors.add(Contributor(name!!, contributions))
  }
}