package com.example.jsonparse

import android.telecom.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface KisilerDaoInterface {

    @POST("test/delete_kisiler.php")
    @FormUrlEncoded
    fun kisiSil(@Field("kisi_id")kisi_id:Int):retrofit2.Call<CRUDCevap>

    @GET("test/tum_kisiler.php")
    @FormUrlEncoded
    fun tumKisiler():retrofit2.Call<KisilerCevap>
}