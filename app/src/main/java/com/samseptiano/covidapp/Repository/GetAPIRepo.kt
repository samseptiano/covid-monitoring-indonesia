package com.samseptiano.covidapp.Repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.samseptiano.covidapp.DataClass.CovidDataResponse
import com.samseptiano.hcroadmap.API.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAPIRepo {

    lateinit var context: Context
    fun getcontext(context: Context){
        this.context = context
    }
    fun getData(where:String,outFields:String,outSR:String,f:String): MutableLiveData<CovidDataResponse> {

        val mutableLiveData: MutableLiveData<CovidDataResponse> =
            MutableLiveData<CovidDataResponse>()

            APIConfig().getService()
                .getData(where,outFields,outSR,f)
                .enqueue(object : Callback<CovidDataResponse> {
                    override fun onFailure(call: Call<CovidDataResponse>, t: Throwable) {
                        Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<CovidDataResponse>,
                        response: Response<CovidDataResponse>
                    ) {

                        if(response.body()!=null){
                            mutableLiveData.setValue(response.body())
                        }
                    }
                })

        return mutableLiveData
    }
    fun getData2(): MutableLiveData<CovidDataResponse> {

        val mutableLiveData: MutableLiveData<CovidDataResponse> =
            MutableLiveData<CovidDataResponse>()

        APIConfig().getService()
            .getData2()
            .enqueue(object : Callback<CovidDataResponse> {
                override fun onFailure(call: Call<CovidDataResponse>, t: Throwable) {
                    Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<CovidDataResponse>,
                    response: Response<CovidDataResponse>
                ) {

                    if(response.body()!=null){
                        mutableLiveData.setValue(response.body())
                    }
                }
            })

        return mutableLiveData
    }

}