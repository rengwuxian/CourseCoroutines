package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.suspendCoroutine

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext) // Default
  val job = launch(Dispatchers.Default) {
    suspendCoroutine<String> {

    }
    suspendCancellableCoroutine<String> {

    }
    /*var count = 0
    while (true) {
//      ensureActive()
      if (!isActive) {
        // clear
        throw CancellationException()
      }
      count++
      if (count % 100_000_000 == 0) {
        println(count)
      }
      if (count % 1_000_000_000 == 0) {
        break
      }
    }*/
    // InterruptedException
    var count = 0
    while (true) {
      /*if (!isActive) {
        // Clear
        return@launch
      }*/
      println("count: ${count++}")
      try {
        delay(500)
      } /*catch (e: CancellationException) {
        println("Cancelled")
        // Clear
        throw e
      }*/ finally {
        // Clear
      }
    }
  }
  delay(1000)
  job.cancel()
  // Thread.interrupt()
}