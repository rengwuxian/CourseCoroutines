package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flow {
    try {
      for (i in 1..5) {
        emit(i)
      }
    } catch (e: Exception) {
      println("try / catch: $e")
    }
  }.onStart {
    println("onStart 1")
    throw RuntimeException("onStart error")
  }
    .onStart { println("onStart 2") }
    .onCompletion {
      println("onCompletion: $it")
    }.onEmpty { println("onEmpty") }
    .catch { println("catch: $it") }
  scope.launch {
    flow1.collect {
      println("Data: $it")
    }
  }
  delay(10000)
}