package com.rengwuxian.coursecoroutines.common

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeoutException

private val retrofit =
  Retrofit.Builder().baseUrl(GITHUB_API)
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
//val gitHub: GitHub = retrofit.create(GitHub::class.java

// Create a MockRetrofit object with a NetworkBehavior which manages the fake behavior of calls.
private val behavior = NetworkBehavior.create()
private val mockRetrofit = MockRetrofit.Builder(retrofit).networkBehavior(behavior).build()
private val delegate = mockRetrofit.create(GitHub::class.java)
val gitHub: GitHub = MockGitHub(delegate)
private val unstableBehavior = NetworkBehavior.create().apply {
  setFailurePercent(40)
  setFailureException(TimeoutException("Connection time out!"))
}
private val mockUnstableRetrofit = MockRetrofit.Builder(retrofit).networkBehavior(unstableBehavior).build()
private val unstableDelegate = mockUnstableRetrofit.create(GitHub::class.java)
val unstableGitHub: GitHub = MockGitHub(unstableDelegate)