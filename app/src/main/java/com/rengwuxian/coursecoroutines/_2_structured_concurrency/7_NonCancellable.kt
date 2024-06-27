package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  var childJob: Job? = null
  var childJob2: Job? = null
  val newParent = Job()
  val parentJob = scope.launch {
    childJob = launch(NonCancellable) {
      println("Child started")
      delay(1000)
      println("Child stopped")
    }
    println("childJob parent: ${childJob?.parent}")
    childJob2 = launch(newParent) {
      println("Child started")
      writeInfo()
      launch(NonCancellable) {
        // Log
      }
      if (!isActive) {
        withContext(NonCancellable) {
          // Write to database (Room)
          delay(1000)
        }
        throw CancellationException()
      }
      try {
        delay(3000)
      } catch (e: CancellationException) {

        throw e
      }
      println("Child 2 started")
      delay(3000)
      println("Child 2 stopped")
    }
    println("Parent started")
    delay(3000)
    println("Parent stopped")
  }
  delay(1500)
  newParent.cancel()
  delay(10000)
}

suspend fun writeInfo() = withContext(Dispatchers.IO + NonCancellable) {
  // write to file
  // read from database (Room)
  // write data to file
}

suspend fun uselessSuspendFun() {
  Thread.sleep(1000)
}