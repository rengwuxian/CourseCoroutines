package com.rengwuxian.coursecoroutines._1_basics

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.rengwuxian.coursecoroutines.R
import com.rengwuxian.coursecoroutines.common.Contributor
import com.rengwuxian.coursecoroutines.common.gitHub
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class ParallelActivity : ComponentActivity() {
  private val handler = Handler(Looper.getMainLooper())
  private lateinit var infoTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.layout_1)
    infoTextView = findViewById(R.id.infoTextView)

    lifecycleScope.launch {
      coroutineScope {
        val deferred1 = async { gitHub.contributors("square", "retrofit") }
        val deferred2 = async { gitHub.contributors("square", "okhttp") }
        showContributors(deferred1.await() + deferred2.await())
      }
    }
    lifecycleScope.launch {
      val initJob = launch {
//        init()
      }
      val contributors1 = gitHub.contributors("square", "retrofit")
      initJob.join()
//      processData()
    }
  }

  private fun coroutinesStyle() = lifecycleScope.launch {
    val contributors1 = gitHub.contributors("square", "retrofit")
    val contributors2 = gitHub.contributors("square", "okhttp")
    showContributors(contributors1 + contributors2)
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

  private fun showContributors(contributors: List<Contributor>) = contributors
    .map { "${it.login} (${it.contributions})" }
    .reduce { acc, s -> "$acc\n$s" }
    .let { infoTextView.text = it }
}