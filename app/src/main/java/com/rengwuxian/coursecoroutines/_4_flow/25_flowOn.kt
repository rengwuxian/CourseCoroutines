package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flow {
    println("CoroutineContext in flow(): ${currentCoroutineContext()}")
    for (i in 1..5) {
      emit(i)
    }
  }.map {
    println("CoroutineContext in map() 1: ${currentCoroutineContext()}")
    it * 2
  }.flowOn(Dispatchers.IO).flowOn(Dispatchers.Default)
    .map {
      println("CoroutineContext in map() 2: ${currentCoroutineContext()}")
      it * 2
    }.flowOn(newFixedThreadPoolContext(2, "TestPool"))
  val flow2 = channelFlow {
    println("CoroutineContext in channelFlow(): ${currentCoroutineContext()}")
    for (i in 1..5) {
      send(i)
    }
  }.map { it }.flowOn(Dispatchers.IO)
  scope.launch {
    /*flow1.map {
      it + 1
    }.onEach {
      println("Data: $it - ${currentCoroutineContext()}")
    }.flowOn(Dispatchers.IO)
      .collect {}*/
    flow2.collect()
  }
  /*flow1.map {
    it + 1
  }.onEach {
    println("Data: $it - ${currentCoroutineContext()}")
  }.launchIn(scope + Dispatchers.IO)*/
  delay(10000)
}