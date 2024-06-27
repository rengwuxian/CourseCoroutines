package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(SupervisorJob())
  val supervisorJob = SupervisorJob()
  val job = Job()
  scope.launch {
    val handler = CoroutineExceptionHandler { _, exception ->
      println("Caught in handler: $exception")
    }
    launch(SupervisorJob(coroutineContext.job) + handler) {
      launch {
        throw RuntimeException("Error!")
      }
    }
  }
  delay(1000)
  println("Parent Job cancelled: ${job.isCancelled}")
  delay(10000)
}