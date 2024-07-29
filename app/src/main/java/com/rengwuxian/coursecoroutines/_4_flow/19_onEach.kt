package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flowOf(1, 2, 3, 4, 5)
  scope.launch {
    flow1.onEach {
      println("onEach 1: $it")
    }.onEach {
      println("onEach 2: $it")
    }.filter {
      it % 2 == 0
    }.onEach {
      println("onEach 3: $it")
    }.collect {
      println("collect: $it")
    }
  }
  delay(10000)
}