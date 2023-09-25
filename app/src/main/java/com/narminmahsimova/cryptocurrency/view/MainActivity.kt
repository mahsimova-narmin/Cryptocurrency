package com.narminmahsimova.cryptocurrency.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.narminmahsimova.cryptocurrency.R
import com.narminmahsimova.cryptocurrency.adapter.RecyclerViewAdapter
import com.narminmahsimova.cryptocurrency.model.CryptoModel
import com.narminmahsimova.cryptocurrency.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() , RecyclerViewAdapter.Listener {

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    private var compositDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerViewId)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager


        compositDisposable = CompositeDisposable()

        loadData()
    }


    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)


        compositDisposable?.add(
            retrofit.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerResponse)
        )


        /*
        val service = retrofit.create(CryptoAPI::class.java)

        val call = service.getData()
        call.enqueue(object: Callback<List<CryptoModel>>{
            override fun onResponse(call: Call<List<CryptoModel>>,response: Response<List<CryptoModel>>) {

                if(response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let{
                            recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
                            recyclerView.adapter = recyclerViewAdapter
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })


         */
    }

    private fun handlerResponse(cryptoList: List<CryptoModel>) {
        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let {
            recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
            recyclerView.adapter = recyclerViewAdapter
        }

    }
        override fun onItemClick(cryptoModel: CryptoModel) {
            Toast.makeText(this, "Clicked : ${cryptoModel.currency}", Toast.LENGTH_LONG).show()

        }

        override fun onDestroy() {
            super.onDestroy()
            // Dispose of your disposables when the activity is destroyed
            compositDisposable?.clear()
        }


}