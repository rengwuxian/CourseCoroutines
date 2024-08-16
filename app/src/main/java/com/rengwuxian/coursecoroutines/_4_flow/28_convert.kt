package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flowOf(1, 2, 3, 4, 5)
  val channel = flow1.produceIn(scope) // Channel produce()
  scope.launch {
    println("First: ${flow1.first()}")
    println("First with condition: ${flow1.first { it > 2 }}")
    try {
      flowOf<Int>().first()
    } catch (e: NoSuchElementException) {
      println("No element")
    }
    println("firstOrNull(): ${flow1.firstOrNull { it > 5 }}")
    // terminal operator
    flow1.last()
    flow1.lastOrNull()
    try {
      flow1.single()
    } catch (e: Exception) {

    }
    flow1.singleOrNull()
    println("count(): ${flow1.count { it > 2 }}")
    val list = mutableListOf<Int>()
    println("List: ${flow1.toList(list)}")
    flow1.toSet() // Set
    flow1.toCollection(list)
  }
  delay(10000)
}