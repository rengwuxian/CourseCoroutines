package com.rengwuxian.coursecoroutines._2_structured_concurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val initJob = scope.launch {
    launch {  }
    launch {  }
  }
  scope.launch {
    initJob.join()
    // ???
  }
  var innerJob: Job? = null
  val job = scope.launch {
    launch(Job()) {
      delay(100)
    }
//    val customJob = Job()
//    innerJob = launch(customJob) {
//      delay(100)
//    }
  }
  val startTime = System.currentTimeMillis()
  job.join()
  val duration = System.currentTimeMillis() - startTime
  println("duration: $duration")
//  val children = job.children
//  println("children count: ${children.count()}")
//  println("innerJob === children.first(): ${innerJob === children.first()}")
//  println("innerJob.parent === job: ${innerJob?.parent === job}")
}