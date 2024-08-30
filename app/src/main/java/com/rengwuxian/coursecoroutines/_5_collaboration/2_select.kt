package com.rengwuxian.coursecoroutines._5_collaboration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val job1 = launch {
    delay(1000)
    println("job1 done")
  }
  val job2 = launch {
    delay(2000)
    println("job2 done")
  }
  val deferred = async {
    delay(500)
    "rengwuxian"
  }
  val channel = Channel<String>()
  scope.launch {
    val result = select {
      job1.onJoin {
        delay(2000)
        1
      }
      job2.onJoin {
        2
      }
      deferred.onAwait {
        it
      }
      channel.onSend("haha") {

      }
      /* channel 的 onSend()、onReceive()、onReceiveCatching() 只能选其一，不能同时应用
      channel.onReceive {

      }
      channel.onReceiveCatching {

      }
      */
      onTimeout(1.seconds) {

      }
    }
    println("Result: $result")
  }
  delay(10000)
}