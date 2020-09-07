package com.samseptiano.hcroadmap.API

import com.samseptiano.covidapp.DataClass.CovidDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {


    @GET("VS6HdKS0VfIhv8Ct/arcgis/rest/services/COVID19_Indonesia_per_Provinsi/FeatureServer/0/query")
    fun getData(@Query("where") where:String, @Query("outFields")outFields:String,@Query("outSR")outSR:String,@Query("f")f:String="json"): Call<CovidDataResponse>
    @GET("VS6HdKS0VfIhv8Ct/arcgis/rest/services/COVID19_Indonesia_per_Provinsi/FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json")
    fun getData2(): Call<CovidDataResponse>

}