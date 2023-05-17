package com.example.interview_api_call.apis

import com.example.interview_api_call.model.main_model
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


const val Base_url="http://accentvision-001-site10.itempurl.com/"
interface Retrofit_interface {

    @GET(Base_url)
    fun getnew():retrofit2.Call<main_model>
}