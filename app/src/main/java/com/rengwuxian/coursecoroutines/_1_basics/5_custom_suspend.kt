package com.rengwuxian.coursecoroutines._1_basics

import com.rengwuxian.coursecoroutines.common.Contributor
import com.rengwuxian.coursecoroutines.common.gitHub

suspend fun getRetrofitContributors(): List<Contributor> {
  return gitHub.contributors("square", "retrofit")
}

suspend fun customSuspendFun() {

}