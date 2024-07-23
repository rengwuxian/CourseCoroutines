package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val numsFlow = flow {
    emit(1)
    delay(100)
    emit(2)
  }
  val scope = CoroutineScope(EmptyCoroutineContext)
  scope.launch {
//    showWeather(weatherFlow)
    weatherFlow.collect {
      println("Weather: $it")
    }
    // log("done")
    /*numsFlow.collect {
      println("A: $it")
    }*/
  }
  scope.launch {
    delay(50)
    numsFlow.collect {
      println("B: $it")
    }
  }
  delay(10000)
}

val weatherFlow = flow {
  while (true) {
    emit(getWeather())
    delay(60000)
  }
}

suspend fun showWeather(flow: Flow<String>) {
  flow.collect {
    println("Weather: $it")
  }
}

suspend fun getWeather() = withContext(Dispatchers.IO) {
  "Sunny"
}