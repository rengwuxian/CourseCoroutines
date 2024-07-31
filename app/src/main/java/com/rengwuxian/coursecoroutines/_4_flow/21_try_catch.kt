package com.rengwuxian.coursecoroutines._4_flow

import com.rengwuxian.coursecoroutines.common.unstableGitHub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeoutException
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flow {
    try {
      for (i in 1..5) {
        // 数据库读数据
        // 网络请求
        emit(i)
      }
    } catch (e: Exception) {
      println("Error in flow(): $e")
      throw e
    }
  }.map { throw NullPointerException() }
    .onEach { throw NullPointerException() }
    .transform<Int, Int> {
      val data = it * 2
      emit(data)
      emit(data)
    }
  // Exception Transparency
  scope.launch {
    try {
      flow1.collect {
        val contributors = unstableGitHub.contributors("square", "retrofit")
        println("Contributors: $contributors")
      }
    } catch (e: TimeoutException) {
      println("Network error: $e")
    } catch (e: NullPointerException) {
      println("Null data: $e")
    }
  }
  delay(10000)
}

private fun fun1() {
  fun2()
}

private fun fun2() {
  fun3()
}

private fun fun3() {
  throw NullPointerException("User null")
}
