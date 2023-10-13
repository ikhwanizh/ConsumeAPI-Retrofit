package com.ikhwanizh.apidata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikhwanizh.apidata.data.MainAdapter
import com.ikhwanizh.apidata.data.remote.ApiService
import com.ikhwanizh.apidata.data.remote.UsersItem
import com.ikhwanizh.apidata.databinding.ActivityMainBinding
import com.ikhwanizh.apidata.util.constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewManager = LinearLayoutManager(this)
        getUsersData()
    }

    private fun getUsersData() {
        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        retrofit.getUsers().enqueue(object : retrofit2.Callback<List<UsersItem>> {
            override fun onResponse(
                call: retrofit2.Call<List<UsersItem>>,
                response: retrofit2.Response<List<UsersItem>>
            ){
                if (response.isSuccessful){
                    val data = response.body()!!
                    viewAdapter = MainAdapter(baseContext, data)
                    binding.itemRv.apply {
                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<List<UsersItem>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


}