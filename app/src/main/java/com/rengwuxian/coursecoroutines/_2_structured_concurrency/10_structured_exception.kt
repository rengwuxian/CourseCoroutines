package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.system.exitProcess

fun main() = runBlocking<Unit> {
  Thread.setDefaultUncaughtExceptionHandler { t, e ->
    println("Caught default: $e")
    exitProcess(1)
  }
  /*val thread = Thread {
    try {

    } catch (e: NullPointerException) {

    }
    throw RuntimeException("Thread error!")
  }*/
  /*thread.setUncaughtExceptionHandler { t, e ->
    println("Caught $e")
  }*/
//  thread.start()
  val scope = CoroutineScope(EmptyCoroutineContext)
  val handler = CoroutineExceptionHandler { _, exception ->
    println("Caught in Coroutine: $exception")
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