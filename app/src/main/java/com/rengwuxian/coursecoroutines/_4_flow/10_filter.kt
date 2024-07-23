package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flowOf(1, 2, null, 3, null, 4, 5) // Int?
  val flow2 = flowOf(1, 2, 3, "rengwuxian.com", "扔物线",
    listOf("A", "B"), listOf(1, 2))
  scope.launch {
    flow1.filterNotNull().filter { it % 2 == 0 }.collect { println("1: $it") }
    flow1.filterNotNull().filterNot { it % 2 == 0 }.collect { println("2: $it") }
    flow2.filterIsInstance<List<String>>().collect { println("3: $it") }
    flow2.filterIsInstance(List::class).collect { println("4: $it") }
    flow2.filter { it is List<*> && it.firstOrNull()?.let { item -> item is String } == true }
      .collect { println("5: $it") }
  }
  delay(10000)
}