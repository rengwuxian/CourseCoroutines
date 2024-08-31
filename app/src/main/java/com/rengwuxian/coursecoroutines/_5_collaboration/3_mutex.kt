package com.rengwuxian.coursecoroutines._5_collaboration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext

//@Synchronized
fun main() = runBlocking {
  var number = 0
  val lock = Any()
  /*val thread1 = thread {
    repeat(100_0000) {
      synchronized(lock) {
        number++
      }
    }
  }
  val thread2 = thread {
    repeat(100_0000) {
      synchronized(lock) {
        number--
      }
    }
  }
  thread1.join()
  thread2.join()
  println("number: $number")*/
  val scope = CoroutineScope(EmptyCoroutineContext)
  val mutex = Mutex() // mutual exclusion
  val semaphore = Semaphore(3)
  AtomicInteger()
  CopyOnWriteArrayList<String>()
  val job1 = scope.launch {
    semaphore.acquire()
    semaphore.release()
    repeat(100_0000) {
      mutex.withLock {
        number++
      }
    }
  }
  val job2 = scope.launch {
    repeat(100_0000) {
      mutex.withLock {
        number--
      }
    }
  }
  job1.join()
  job2.join()
  println("number: $number")
  delay(10000)
}

@Volatile var v = 0
@Transient var t = 0