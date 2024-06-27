package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val handler = CoroutineExceptionHandler { _, exception ->
    println("Caught $exception")
  }
  scope.launch(handler) {
    launch {
      throw RuntimeException("Error!")
    }
    launch {

    }
  }
  delay(10000)
}