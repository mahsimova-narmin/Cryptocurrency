package com.narminmahsimova.cryptocurrency.service

import com.narminmahsimova.cryptocurrency.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")

    //fun getData(): Call<List<CryptoModel>>

    fun getData(): Observable<List<CryptoModel>>

}