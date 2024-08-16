package com.rengwuxian.coursecoroutines._4_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val flow1 = flow {
    delay(500)
    emit(1)
    delay(500)
    emit(2)
    delay(500)
    emit(3)
  }
  val flow2 = flow {
    delay(250)
    emit(4)
    delay(500)
    emit(5)
    delay(500)
    emit(6)
  }
  val mergedFlow = merge(flow1, flow2)
  val flowList = listOf(flow1, flow2)
  val mergedFlowFromList = flowList.merge()
  val flowFlow = flowOf(flow1, flow2) // flatten
  val concattedFlowFlow = flowFlow.flattenConcat() // concatenate
  val mergedFlowFlow = flowFlow.flattenMerge()
  val concattedMappedFlow = flow1.flatMapConcat { from -> (1..from).asFlow().map { "$from - $it" } }
  val mergedMappedFlow = flow1.flatMapMerge { from -> (1..from).asFlow().map { "$from - $it" } }
  val latestMappedFlow = flow1.flatMapLatest { from -> (1..from).asFlow().map { "$from - $it" } }
  val combinedFlow = flow1.combine(flow2) { a, b -> "$a - $b" }
  val combinedFlow2 = combine(flow1, flow2, flow1) { a, b, c -> "$a - $b - $c" }
  flow1.combineTransform(flow2) { a, b -> emit("$a - $b") }
  val zippedFlow = flow1.zip(flow2) { a, b -> "$a - $b" }
  scope.launch {
    zippedFlow.collect { println(it) }
  }
  delay(10000)
}