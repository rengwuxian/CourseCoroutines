package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  var childJob: Job? = null
  val parentJob = scope.launch {
    childJob = launch {
      launch {
        println("Grand child started")
        delay(3000)
        println("Grand child done")
      }
      delay(1000)
      throw IllegalStateException("User invalid!")
    }
    println("Parent started")
    delay(3000)
    println("Parent done")
  }
  delay(500)
//  CoroutineExceptionHandler
//  parentJob.cancel()
//  println("isActive: parent - ${parentJob.isActive}, child - ${childJob?.isActive}")
//  println("isCancelled: parent - ${parentJob.isCancelled}, child - ${childJob?.isCancelled}")
//  delay(1000)
//  println("isActive: parent - ${parentJob.isActive}, child - ${childJob?.isActive}")
//  println("isCancelled: parent - ${parentJob.isCancelled}, child - ${childJob?.isCancelled}")
  delay(10000)
}