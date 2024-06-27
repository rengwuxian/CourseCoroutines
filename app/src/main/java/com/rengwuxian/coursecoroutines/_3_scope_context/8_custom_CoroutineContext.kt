package com.rengwuxian.coursecoroutines._3_scope_context

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

fun main() = runBlocking<Unit> {
  val scope = CoroutineScope(EmptyCoroutineContext)
  val customContext = Logger()
  scope.launch(customContext) {
    coroutineContext[Logger]?.log()
  }
  delay(10000)
}

class Logger : AbstractCoroutineContextElement(Logger) {
  companion object Key : CoroutineContext.Key<Logger>

  suspend fun log() {
    println("Current coroutine: $coroutineContext")
  }
}