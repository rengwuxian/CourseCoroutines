package com.rengwuxian.coursecoroutines._4_flow

import com.rengwuxian.coursecoroutines.common.Contributor
import com.rengwuxian.coursecoroutines.common.gitHub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

fun main() = runBlocking<Unit> {
  val flow1 = flowOf(1, 2, 3)
  val flow2 = listOf(1, 2, 3).asFlow()
  val flow3 = setOf(1, 2, 3).asFlow()
  val flow4 = sequenceOf(1, 2, 3).asFlow()
  val channel = Channel<Int>()
  val flow5 = channel.consumeAsFlow()
  val flow6 = channel.receiveAsFlow()
  val flow7 = channelFlow {
    launch {
      delay(2000)
      send(2)
    }
    delay(1000)
    send(1)
  }
  val flow8 = flow {
    launch {
      delay(2000)
      emit(2)
    }
    delay(1000)
    emit(1)
  }
  val flow9 = callbackFlow {
    gitHub.contributorsCall("square", "retrofit")
      .enqueue(object : Callback<List<Contributor>> {
        override fun onResponse(call: Call<List<Contributor>>, response: Response<List<Contributor>>) {
          trySend(response.body()!!)
          close()
        }

        override fun onFailure(call: Call<List<Contributor>>, error: Throwable) {
          cancel(CancellationException(error))
        }
      })
    awaitClose()
  }
  val scope = CoroutineScope(EmptyCoroutineContext)
  scope.launch {
    flow9.collect {
      println("channelFlow with callback: $it")
    }
    /*flow8.collect {
      println("channelFlow: $it")
    }*/
    /*flow5.collect {
      println("Flow6 - 1: $it")
    }*/
  }
  scope.launch {
    /*flow5.collect {
      println("Flow6 - 2: $it")
    }*/
  }
  /*channel.send(1)
  channel.send(2)
  channel.send(3)
  channel.send(4)*/
  delay(10000)
}