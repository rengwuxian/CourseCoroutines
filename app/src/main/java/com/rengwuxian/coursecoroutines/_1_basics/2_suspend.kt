package com.rengwuxian.coursecoroutines._1_basics

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rengwuxian.coursecoroutines.R
import com.rengwuxian.coursecoroutines.common.Contributor
import com.rengwuxian.coursecoroutines.common.gitHub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SuspendActivity : ComponentActivity() {
  private lateinit var infoTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.layout_1)
    infoTextView = findViewById(R.id.infoTextView)


//    callbackStyle()
    coroutinesStyle()
  }

  private fun callbackStyle() {
    gitHub.contributorsCall("square", "retrofit")
      .enqueue(object : Callback<List<Contributor>> {
        override fun onResponse(
          call: Call<List<Contributor>>, response: Response<List<Contributor>>,
        ) {
          showContributors(response.body()!!)
          gitHub.contributorsCall("square", "okhttp")
            .enqueue(object : Callback<List<Contributor>> {
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

        override fun onFailure(call: Call<List<Contributor>>, t: Throwable) {
          t.printStackTrace()
        }
      })
  }

  private fun coroutinesStyle() = CoroutineScope(Dispatchers.Main).launch {
    val contributors = gitHub.contributors("square", "retrofit")
    showContributors(contributors)
    val contributors2 = gitHub.contributors("square", "okhttp")
    showContributors(contributors2)
  }

  private fun showContributors(contributors: List<Contributor>) = contributors
    .map { "${it.login} (${it.contributions})" }
    .reduce { acc, s -> "$acc\n$s" }
    .let { infoTextView.text = it }
}