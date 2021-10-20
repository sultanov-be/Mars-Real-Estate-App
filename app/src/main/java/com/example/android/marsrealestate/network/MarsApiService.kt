/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://mars.udacity.com/" // for getting response from the JSON and returning it as a string

enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    //.addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi)) // let Retrofit use Moshi convert JSON response into a Kt objects
    .addCallAdapterFactory(CoroutineCallAdapterFactory()) // let replace the call and get properties

    .baseUrl(BASE_URL)// specifying the root-path
    .build() // to create a retrofit object

interface MarsApiService {
    @GET("realestate")
    fun getProperties(@Query("filter") type: String):
        Deferred<List<MarsProperty>>
//Call<List<MarsProperty>> //return a List instead of Str
//    @GET("realestate") // specifying an end point
//    fun getProperties():
//            Call<String> // is used to start a request
}

object MarsApi {
    val retrofitService : MarsApiService by lazy { // return Retorfit that release a MarsApiService
        retrofit.create(MarsApiService::class.java)
    }
}
