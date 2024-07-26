package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.transformWhile
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
    flow1.transform {
      if (it > 0) {
        repeat(it) { _ ->
          emit("$it - hahaha")
        }
      }
    }.collect { println("1: $it") }
    flow1.transformWhile {
      if (it > 3) return@transformWhile false
      if (it > 0) {
        repeat(it) { _ ->
          emit("$it - hahaha")
        }
      }
      true
    }.collect { println("2: $it") }
    flow2.transformLatest {
      delay(50)
      emit("$it - start")
      delay(100)
      emit("$it - end")
    }.collect { println("3: $it") }
  }
  delay(10000)
}