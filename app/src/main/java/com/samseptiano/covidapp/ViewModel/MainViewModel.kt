package com.samseptiano.covidapp.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samseptiano.covidapp.DataClass.CovidDataResponse
import com.samseptiano.covidapp.Repository.GetAPIRepo

class MainViewModel(where:String, outFields:String,outSR:String,f:String, context:Context) : ViewModel() {
    private val getAPIRepo: GetAPIRepo
    private val ctx = context

    private val where = where
    private val outFields = outFields
    private val outSR = outSR
    private val f = f


    private var mutableLiveData: MutableLiveData<CovidDataResponse>? = null
    val getData: LiveData<CovidDataResponse>
        get() {
            if (mutableLiveData == null) {
                getAPIRepo.getcontext(ctx)
                mutableLiveData = getAPIRepo.getData(where,outFields,outSR,f)
            }
            return mutableLiveData!!
        }
    val getData2: LiveData<CovidDataResponse>
        get() {
            if (mutableLiveData == null) {
                getAPIRepo.getcontext(ctx)
                mutableLiveData = getAPIRepo.getData2()
            }
            return mutableLiveData!!
        }
    init {
        getAPIRepo = GetAPIRepo()
    }
}