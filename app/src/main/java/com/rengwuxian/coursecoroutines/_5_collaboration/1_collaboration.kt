package com.rengwuxian.coursecoroutines._5_collaboration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val latch = CountDownLatch(2)
  thread {
    latch.await()
    //
  }
  thread {
    Thread.sleep(1000)
    latch.countDown()
  }
  thread {
    Thread.sleep(2000)
    latch.countDown()
  }
  val preJob1 = launch {
    delay(1000)
  }
  val preJob2 = launch {
    delay(2000)
  }
  launch {
    preJob1.join()
    preJob2.join()
  }
  val channel = Channel<Unit>(2)
  launch {
    repeat(2) {
      channel.receive()
    }
  }
  launch {
    delay(1000)
    channel.send(Unit)
  }
  launch {
    delay(2000)
    channel.send(Unit)
  }
  val job1 = scope.launch {
    println("Job 1 started")
    delay(2000)
    println("Job 1 done")
  }
  val deferred = scope.async {
    println("Deferred started")
    delay(3000)
    println("Deferred done")
    "rengwuxian"
  }
  scope.launch {
    println("Job 2 started")
    delay(1000)
    job1.join()
    println("Deferred result: ${deferred.await()}")
    println("Job 2 done")
  }
  delay(10000)
}