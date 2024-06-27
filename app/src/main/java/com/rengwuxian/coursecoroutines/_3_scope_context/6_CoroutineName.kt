package com.rengwuxian.coursecoroutines._3_scope_context

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  thread {  }.name = "MyThread"
  val name = CoroutineName("MyCoroutine")
  val scope = CoroutineScope(Dispatchers.IO + name)
  withContext(name) {

  }
  scope.launch {
    println("CoroutineName: ${coroutineContext[CoroutineName]?.name}")
  }
  delay(10000)
}