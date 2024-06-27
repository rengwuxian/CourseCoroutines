package com.rengwuxian.coursecoroutines._1_basics

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rengwuxian.coursecoroutines.ui.theme.CourseCoroutinesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.newSingleThreadContext
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext

class LaunchCoroutineActivity : ComponentActivity() {

  @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      CourseCoroutinesTheme {}
    }

    thread {

    }

    println("Main thread: ${Thread.currentThread().name}")
    val executor = Executors.newCachedThreadPool()
    executor.execute {
      println("Executor thread: ${Thread.currentThread().name}")
    }

    val context = newFixedThreadPoolContext(20, "MyThread")
    val context1 = newSingleThreadContext("MyThread")
    val scope = CoroutineScope(context)
    context.close()
    scope.launch {
      println("Coroutine thread: ${Thread.currentThread().name}")
    }
    scope.launch {
      println("Coroutine thread: ${Thread.currentThread().name}")
    }

    // I/O: input / output 100k

  }
}