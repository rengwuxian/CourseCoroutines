package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val name = MutableStateFlow("rengwuxian")
  val flow1 = flow {
    emit(1)
    delay(1000)
    emit(2)
    delay(1000)
    emit(3)
  }
  name.asStateFlow()
  val state = flow1.stateIn(scope)
  scope.launch {
    name.collect {
      println("State: $it")
    }
  }
  scope.launch {
    delay(2000)
    name.emit("扔物线")
  }
  delay(10000)
}