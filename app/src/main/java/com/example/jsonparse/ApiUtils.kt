package com.example.jsonparse

class ApiUtils {

    companion object{

        val BASE_URL = "https://tamerusta.com.tr/"

        fun getKisilerDAoInterface():KisilerDaoInterface{

            return RetrofitClient.getClient(BASE_URL).create(KisilerDaoInterface::class.java)
        }
    }
}