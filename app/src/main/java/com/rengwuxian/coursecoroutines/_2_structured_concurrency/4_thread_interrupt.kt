package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

fun main() = runBlocking<Unit> {
  val thread = object : Thread() {
    override fun run() {
      println("Thread: I'm running!")
      try {
        Thread.sleep(200)
      } catch (e: InterruptedException) {
        println("isInterrupted: $isInterrupted")
        println("Clearing ...")
        return
      }
      val lock = Object()
      try {
        lock.wait()
      } catch (e: InterruptedException) {
        println("isInterrupted: $isInterrupted")
        println("Clearing ...")
        return
      }
      val newThread = thread {

      }
      newThread.join()
      val latch = CountDownLatch(3)
      latch.await()
      /*var count = 0
      while (true) {
        if (isInterrupted) {
          // clear
          return
        }
        count++
        if (count % 100_000_000 == 0) {
          println(count)
        }
        if (count % 1_000_000_000 == 0) {
          break
        }
      }*/
      println("Thread: I'm done!")
    }
  }.apply { start() }
  Thread.sleep(100)
  thread.interrupt()
}