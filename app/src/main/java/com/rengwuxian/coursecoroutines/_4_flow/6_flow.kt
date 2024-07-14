package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val list = buildList {
//    while (true) {
      add(getData())
//    }
  }
  for (num in list) {
    println("List item: $num")
  }
  val nums = sequence {
    while (true) {
      yield(1)
    }
  }.map { "number $it" }
  for (num in nums) {
    println(num)
  }

  val numsFlow = flow {
    while (true) {
      emit(getData())
    }
  }.map { "number $it" }
  val scope = CoroutineScope(EmptyCoroutineContext)
  scope.launch {
    numsFlow.collect {
      println(it)
    }
  }
  delay(10000)
}

suspend fun getData(): Int {
  return 1
}
