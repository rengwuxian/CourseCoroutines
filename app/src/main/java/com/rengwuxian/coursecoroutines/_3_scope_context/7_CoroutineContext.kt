package com.rengwuxian.coursecoroutines._3_scope_context

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalStdlibApi::class)
fun main() = runBlocking<Unit> {
  val job1 = Job()
  val job2 = Job()
  val scope = CoroutineScope(Dispatchers.IO + job1
      + CoroutineName("MyCoroutine") + job2)
  // [[CoroutineName(MyCoroutine), job2], Dispatchers.IO]
  println("job1: $job1, job2: $job2")
  println("CoroutineContext: ${scope.coroutineContext}")
  scope.launch {
    val job: Job? = coroutineContext[Job]
    val interceptor: CoroutineDispatcher? = coroutineContext[CoroutineDispatcher]
    println("coroutineContext: $coroutineContext")
    println("coroutineContext after minusKey() ${coroutineContext.minusKey(Job)}")
  }
  delay(10000)
}