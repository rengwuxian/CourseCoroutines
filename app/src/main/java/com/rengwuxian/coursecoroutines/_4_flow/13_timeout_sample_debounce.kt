package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class)
fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flow {
    emit(0)
    delay(500)
    emit(1)
    delay(800)
    emit(2)
    delay(900)
    emit(3)
    delay(1000)
  }
  scope.launch {
//    flow1.timeout(1.seconds).collect { println("1: $it") }
//    flow1.sample(1.seconds).collect { println("2: $it") }
    flow1.debounce(1.seconds).collect { println("3: $it") }
  }
  delay(10000)
}

fun <T> Flow<T>.throttle(timeWindow: Duration): Flow<T> = flow {
  var lastTime = 0L
  collect {
    if (System.currentTimeMillis() - lastTime > timeWindow.inWholeMilliseconds) {
      emit(it)
      lastTime = System.currentTimeMillis()
    }
  }
}