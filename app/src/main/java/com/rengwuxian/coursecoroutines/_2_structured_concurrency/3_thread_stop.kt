package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main() = runBlocking<Unit> {
  val thread = thread {
    println("Thread: I'm running!")
    Thread.sleep(200)
    println("Thread: I'm done!")
  }
  Thread.sleep(100)
  thread.stop()
}