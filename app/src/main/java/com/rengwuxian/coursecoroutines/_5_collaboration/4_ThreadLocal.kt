package com.rengwuxian.coursecoroutines._5_collaboration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

val kotlinLocalString = ThreadLocal<String>()

fun main() = runBlocking {
  val scope = CoroutineScope(EmptyCoroutineContext)
  scope.launch {
    val stringContext = kotlinLocalString.asContextElement("rengwuxian")
    withContext(stringContext) {
      delay(100)
      kotlinLocalString.get()
    }
  }
  delay(10000)
}