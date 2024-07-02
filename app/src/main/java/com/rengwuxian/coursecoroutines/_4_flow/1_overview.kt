package com.rengwuxian.coursecoroutines._4_cooperation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlin.concurrent.thread
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

fun main() = runBlocking<Unit> {
  flow { emit(1) }.map {  }.buffer().onCompletion {  }.catch {  }.collect {}

  produce<String> {
    send("")
    select {
      onSend("") {  }
    }
  }
    .consumeEach {  }
  val context = Dispatchers.IO + Job() + Job()
  val scope = CoroutineScope(Dispatchers.IO)
  val channel = Channel<String>()
  val job = scope.launch {
    this.coroutineContext[Job]
    coroutineContext.job
    coroutineContext[ContinuationInterceptor]
  }
  delay(10000)
}

fun <T> Flow<T>.buffer(size: Int = 0): Flow<T> = flow {
  coroutineScope {
    val channel = produce(capacity = size) {
      collect { send(it) }
    }
    channel.consumeEach { emit(it) }
  }
}