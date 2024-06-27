package com.rengwuxian.coursecoroutines._1_basics

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.rengwuxian.coursecoroutines.R
import com.rengwuxian.coursecoroutines.common.Contributor
import com.rengwuxian.coursecoroutines.common.gitHub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WithContext2Activity : ComponentActivity() {
  private lateinit var infoTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.layout_1)
    infoTextView = findViewById(R.id.infoTextView)

    CoroutineScope(Dispatchers.Main).launch {
      val data = getData()
      val processedData = processData(data)
      println("Processed data: $processedData")
    }
  }

  private suspend fun getData() = withContext(Dispatchers.IO) {
    // 网络代码
    "data"
  }

  private suspend fun processData(data: String) = withContext(Dispatchers.Default) {
    // 处理数据
    "processed $data"
  }

  private fun coroutinesStyle() = lifecycleScope.launch {
    val contributors = contributorsOfRetrofit()
    showContributors(contributors)
  }

  private suspend fun contributorsOfRetrofit() = gitHub.contributors("square", "retrofit")

  private fun showContributors(contributors: List<Contributor>) = contributors
    .map { "${it.login} (${it.contributions})" }
    .reduce { acc, s -> "$acc\n$s" }
    .let { infoTextView.text = it }
}