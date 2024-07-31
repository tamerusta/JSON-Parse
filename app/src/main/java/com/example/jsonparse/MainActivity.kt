package com.example.jsonparse

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        kisiSilRetrofit()
        //kisiSil()

        val jsonKisilerSonuc =
            "{\"kisiler\":[{\"kisi_id\":\"1\",\"kisi_ad\":\"Ahmet\",\"kisi_tel\":null},{\"kisi_id\":\"2\",\"kisi_ad\":\"Ali\",\"kisi_tel\":null}],\"success\":1}"

        try {
            val jsonObject = JSONObject(jsonKisilerSonuc)

            val kisilerListe = jsonObject.getJSONArray("kisiler")

            for (i in 0 until kisilerListe.length()) {

                val b = kisilerListe.getJSONObject(i)

                val kisi_id = b.getInt("kisi_id")
                val kisi_ad = b.getString("kisi_ad")
                val kisi_yas = b.getString("kisi_yas")

                Log.e("******", "******")
                Log.e("Kisi Id", kisi_id.toString())
                Log.e("Kisi Ad", kisi_ad)
                Log.e("Kisi Yas", kisi_yas)
                Log.e("******", "******")
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun kisiSil() {

        val url = "https://tamerusta.com.tr/test/delete_kisiler.php"

        val istek = object : StringRequest(Method.POST, url, Response.Listener { cevap ->

            Log.e("Cevap", cevap)
        }, Response.ErrorListener { e -> e.printStackTrace() }) {

            override fun getParams(): MutableMap<String, String>? {

                val params = HashMap<String,String>()

                params["kisi_id"] = "2"

                return params
            }
        }

        Volley.newRequestQueue(this).add(istek)
    }

    fun kisiSilRetrofit(){

        val kdi = ApiUtils.getKisilerDAoInterface()

        kdi.kisiSil(1).enqueue(object : Callback<CRUDCevap>{

            override fun onResponse(
                call: Call<CRUDCevap>,
                response: retrofit2.Response<CRUDCevap>?) {

                if(response != null){
                    Log.e("Başarı", response.body()?.success.toString())
                    Log.e("Mesaj", response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable) {

            }
        })
    }

    fun tumKisiler(){

        val kdi = ApiUtils.getKisilerDAoInterface()

        kdi.tumKisiler().enqueue(object : Callback<KisilerCevap>{

            override fun onResponse(
                call: Call<KisilerCevap>?,
                response: retrofit2.Response<KisilerCevap>?) {

                    val kisilerListe = response?.body()?.kisiler

                if (kisilerListe != null) {
                    for(k in kisilerListe){
                        Log.e("*********","**********")
                        Log.e("Kişi Id",(k.kisi_id).toString())
                        Log.e("Kişi Ad",k.kisi_ad)
                        Log.e("Kişi Tel",k.kisi_tel)
                    }
                }
            }

            override fun onFailure(call: Call<KisilerCevap>?, t: Throwable?) {

            }

        })

    }

}