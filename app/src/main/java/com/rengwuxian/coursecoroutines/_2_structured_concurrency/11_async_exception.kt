package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val handler = CoroutineExceptionHandler { _, exception ->
    println("Caught in Coroutine: $exception")
  }
  scope.async/*(handler)*/ {
    val deferred = async {
      delay(1000)
      throw RuntimeException("Error!")
    }
    launch(Job()) {
      try {
        deferred.await()
      } catch (e: Exception) {
        println("Caught in await: $e")
      }
      try {
        delay(1000)
      } catch (e: Exception) {
        println("Caught in delay: $e")
      }
    }
//    delay(100)
//    cancel()
  }
  delay(10000)
}