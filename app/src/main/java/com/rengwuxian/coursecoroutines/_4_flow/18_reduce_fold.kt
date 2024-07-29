package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flowOf(1, 2, 3, 4, 5)
  val list = listOf(1, 2, 3, 4, 5)
  list.reduce { acc, i -> acc + i }.let { println("List reduced to $it") }
  list.runningReduce { acc, i -> acc + i }.let { println("New List: $it") }
  list.fold(10) { acc, i -> acc + i }.let { println("List folded to $it") }
  list.fold("ha") { acc, i -> "$acc - $i" }.let { println("List folded to string: $it") }
  list.runningFold("ha") { acc, i -> "$acc - $i" }.let { println("New String List: $it") }
  scope.launch {
    val sum = flow1.reduce { accumulator, value -> accumulator + value }
    println("Sum is $sum")
    flow1.runningReduce { accumulator, value -> accumulator + value }
      .collect { println("runningReduce: $it") }
    flow1.fold("ha") { acc, i -> "$acc - $i" }.let { println("Flow folded to string: $it") }
    flow1.runningFold("ha") { acc, i -> "$acc - $i" }
      .collect { println("Flow.runningFold(): $it") }
    flow1.scan("ha") { acc, i -> "$acc - $i" }
      .collect { println("Flow.scan(): $it") }
  }
  delay(10000)
}