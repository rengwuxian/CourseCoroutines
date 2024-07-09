package com.rengwuxian.coursecoroutines._4_flow

import com.rengwuxian.coursecoroutines.common.Contributor
import com.rengwuxian.coursecoroutines.common.gitHub
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.FileWriter
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val fileChannel = Channel<FileWriter>() { it.close() }
  fileChannel.send(FileWriter("test.txt"))
//  val channel = Channel<List<Contributor>>(8, BufferOverflow.DROP_OLDEST)
//  val channel = Channel<List<Contributor>>(1, BufferOverflow.DROP_OLDEST)
  val channel = Channel<List<Contributor>>(CONFLATED)
  scope.launch {
    channel.send(gitHub.contributors("square", "retrofit"))
    channel.close()
    channel.close(IllegalStateException("Data error!"))
    channel.receive()
    channel.receive()
    channel.send(gitHub.contributors("square", "retrofit"))
    channel.trySend(gitHub.contributors("square", "retrofit"))
    channel.tryReceive()
  }
  launch {
    for (data in channel) {
      println("Contributors: $data")
    }
    /*while (isActive) {
      val contributors = channel.receive()
      println("Contributors: $contributors")
    }*/
  }
  delay(1000)
  channel.cancel()
  delay(10000)
}