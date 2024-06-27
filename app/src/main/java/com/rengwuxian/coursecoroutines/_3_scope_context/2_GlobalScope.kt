package com.rengwuxian.coursecoroutines._3_scope_context

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking<Unit> {
  CoroutineScope(EmptyCoroutineContext).launch {

  }
  GlobalScope.launch {

  }
  GlobalScope.cancel()
  val job = GlobalScope.async {
    delay(1000)
  }
  println("job parent: ${job.parent}")
  delay(10000)
}