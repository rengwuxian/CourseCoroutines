package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flow {
    emit(1)
    delay(1000)
    emit(2)
    delay(1000)
    emit(3)
  }
  val sharedFlow = flow1.shareIn(scope, SharingStarted.WhileSubscribed(), 2)
  scope.launch {
    val parent = this
    launch {
      delay(4000)
      parent.cancel()
    }
    delay(1500)
    sharedFlow.collect {
      println("SharedFlow in Coroutine 1: $it")
    }
  }
  scope.launch {
    delay(5000)
    sharedFlow.collect {
      println("SharedFlow in Coroutine 2: $it")
    }
  }
  delay(10000)
}