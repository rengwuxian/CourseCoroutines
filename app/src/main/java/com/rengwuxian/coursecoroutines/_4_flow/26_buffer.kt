package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val start = System.currentTimeMillis()
  val flow1 = flow {
    for (i in 1..5) {
      emit(i)
      println("Emitted: $i - ${System.currentTimeMillis() - start}ms")
    }
  }.buffer(1)
    .flowOn(Dispatchers.IO)
    .buffer(2)
//    .conflate()
    .map { it + 1 }
    .map { it * 2 }
  scope.launch {
    flow1.mapLatest { it }.buffer(0).collect {
      delay(1000)
      println("Data: $it")
    }
  }
  delay(10000)
}