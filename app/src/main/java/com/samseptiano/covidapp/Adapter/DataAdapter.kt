package com.samseptiano.covidapp.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.samseptiano.covidapp.DataClass.CovidDataResponse
import com.samseptiano.covidapp.DataClass.FeaturesItem
import com.samseptiano.covidapp.R
import kotlinx.android.synthetic.main.data_item.view.*


class DataAdapter(private val covidDataResponse: List<FeaturesItem>, private val ctx: Context?,private val activity: Activity?) : RecyclerView.Adapter<DataHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): DataHolder {
        return DataHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.data_item, viewGroup, false))
    }

    override fun getItemCount(): Int = covidDataResponse.size

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        if (ctx != null) {
            if (activity != null) {
                holder.bindEmpList(covidDataResponse[position],ctx,activity,position)
            }
        }
    }
}

class DataHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvprovincename = view.tvProvinceName
    private val tvrecovered = view.tvRecovered
    private val tvdeath = view.tvDeath
    private val tvconfirmed = view.tvConfirmed


    fun bindEmpList(covidDataResponse: FeaturesItem,ctx: Context,activity:Activity,pos:Int) {
        tvprovincename.text = covidDataResponse.attributes?.provinsi ?: ""
        tvrecovered.text = covidDataResponse.attributes?.kasusSemb.toString() ?: "0"
        tvdeath.text = covidDataResponse.attributes?.kasusMeni.toString() ?: "0"
        tvconfirmed.text = covidDataResponse.attributes?.kasusPosi.toString() ?: "0"

    }

}