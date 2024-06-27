package com.rengwuxian.coursecoroutines._1_basics

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.rengwuxian.coursecoroutines.common.Contributor
import com.rengwuxian.coursecoroutines.common.gitHub
import com.rengwuxian.coursecoroutines.ui.theme.CourseCoroutinesTheme
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

class PreviewActivity : ComponentActivity() {
  private val handler = Handler(Looper.getMainLooper())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      CourseCoroutinesTheme {}
    }

    thread {

    }

    GlobalScope.launch {

    }
  }

  private fun callbackStyle() {
    gitHub.contributorsCall("square", "retrofit").enqueue(object : Callback<List<Contributor>> {
      override fun onResponse(
        call: Call<List<Contributor>>, response: Response<List<Contributor>>,
      ) {
        showContributors(response.body()!!)
      }

      override fun onFailure(call: Call<List<Contributor>>, t: Throwable) {
        t.printStackTrace()
      }
    })
  }

  private fun coroutinesStyle() = lifecycleScope.launch {
    val contributors = gitHub.contributors("square", "retrofit")
    showContributors(contributors)
  }

  private fun completableFutureStyleMerge() {
    val future1 = gitHub.contributorsFuture("square", "retrofit")
    val future2 = gitHub.contributorsFuture("square", "okhttp")
    future1.thenCombine(future2) { contributors1, contributors2 ->
      contributors1 + contributors2
    }.thenAccept { mergedContributors ->
      handler.post {
        showContributors(mergedContributors)
      }
    }
  }

  private fun rxStyleMerge(): Disposable {
    val single1 = gitHub.contributorsRx("square", "retrofit")
    val single2 = gitHub.contributorsRx("square", "okhttp")
    return Single.zip(single1, single2) { contributors1, contributors2 ->
      contributors1 + contributors2
    }.observeOn(AndroidSchedulers.mainThread())
      .subscribe(::showContributors)
  }

  private fun coroutinesStyleMerge() = lifecycleScope.launch {
    val contributors1 = async { gitHub.contributors("square", "retrofit") }
    val contributors2 = async { gitHub.contributors("square", "okhttp") }
    showContributors(contributors1.await() + contributors2.await())
  }

  private fun showContributors(contributors: List<Contributor>) = contributors.forEach {
    println("${it.login} has made ${it.contributions} contributions")
  }
}