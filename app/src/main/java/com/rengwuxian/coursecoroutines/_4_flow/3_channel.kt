package com.rengwuxian.coursecoroutines._4_flow

import com.rengwuxian.coursecoroutines.common.Contributor
import com.rengwuxian.coursecoroutines.common.gitHub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val channel = Channel<List<Contributor>>()
  scope.launch {
    channel.send(gitHub.contributors("square", "retrofit"))
  }
  scope.launch {
    while (isActive) {
      channel.receive()
    }
  }
  scope.launch {
    while (isActive) {
      channel.receive()
    }
  }
  val receiver = scope.produce {
    while (isActive) {
      val data = gitHub.contributors("square", "retrofit")
      send(data)
    }
  }
  launch {
    delay(5000)
    while (isActive) {
      println("Contributors: ${receiver.receive()}")
    }
  }
  delay(10000)
}