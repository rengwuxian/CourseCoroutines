package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.measureTime

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val parentJob = scope.launch {
    val childJob = launch {
      println("Child job started")
      delay(3000)
      println("Child job finished")
    }
  }
  delay(1000)
  parentJob.cancel()
  measureTime { parentJob.join() }.also { println("Duration: $it") }
  delay(10000)
}