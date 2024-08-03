package com.rengwuxian.coursecoroutines._4_flow

import com.rengwuxian.coursecoroutines.common.unstableGitHub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeoutException
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flow {
    for (i in 1..5) {
      // 数据库读数据
      // 网络请求
      if (i == 3) {
        throw RuntimeException("flow() error")
      } else {
        emit(i)
      }
    }
  }.catch {
    println("catch(): $it")
    emit(100)
    emit(200)
    emit(300)
//    throw RuntimeException("Exception from catch()")
  }/*.onEach { throw RuntimeException("Exception from onEach()") }
    .catch { println("catch() 2: $it") }*/
  scope.launch {
    try {
      flow1.collect {
        /*val contributors = unstableGitHub.contributors("square", "retrofit")
        println("Contributors: $contributors")*/
        println("Data: $it")
      }
    } catch (e: TimeoutException) {
      println("Network error: $e")
    }
  }
  delay(10000)
}