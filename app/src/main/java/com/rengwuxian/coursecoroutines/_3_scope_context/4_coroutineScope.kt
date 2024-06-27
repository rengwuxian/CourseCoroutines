package com.rengwuxian.coursecoroutines._3_scope_context

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  scope.launch {
    supervisorScope {

    }
    val name = try {
      coroutineScope {
        val deferred1 = async { "rengwuxian" }
        val deferred2: Deferred<String> = async { throw RuntimeException("Error!") }
        "${deferred1.await()} ${deferred2.await()}"
      }
    } catch (e: Exception) {
      e.message
    }
    println("Full name: $name")
    val startTime1 = System.currentTimeMillis()
    coroutineScope {
      launch {
        delay(2000)
      }
      delay(1000)
      println("Duration within coroutineScope: ${System.currentTimeMillis() - startTime1}")
    }
    println("Duration of coroutineScope: ${System.currentTimeMillis() - startTime1}")
    val startTime = System.currentTimeMillis()
    launch {
      delay(1000)
      println("Duration within launch: ${System.currentTimeMillis() - startTime}")
    }
    println("Duration of launch: ${System.currentTimeMillis() - startTime}")
  }
  delay(10000)
}

private suspend fun someFun() = coroutineScope {
  launch {

  }
}