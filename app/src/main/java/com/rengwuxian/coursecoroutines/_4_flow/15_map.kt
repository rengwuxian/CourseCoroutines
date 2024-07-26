package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flowOf(1, 2, 3, 4, 5)
  val flow2 = flow {
    delay(100)
    emit(1)
    delay(100)
    emit(2)
    delay(100)
    emit(3)
  }
  scope.launch {
    flow1.map { if (it == 3) null else it + 1 }.collect { println("1: $it") }
    flow1.mapNotNull { if (it == 3) null else it + 1 }.collect { println("2: $it") }
    flow1.map { if (it == 3) null else it + 1 }.filterNotNull()
    flow1.filter { it != 3 }.map { it + 1 }
    flow2.mapLatest { delay(120); it + 1}.collect { println("3: $it") }
  }
  delay(10000)
}