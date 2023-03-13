package me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote

import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote.network.DicodingApiService
import retrofit2.Retrofit

interface RetrofitManager {

    val retrofit: Retrofit
    val api: DicodingApiService

}