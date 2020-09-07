package com.samseptiano.covidapp

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.samseptiano.covidapp.Adapter.DataAdapter
import com.samseptiano.covidapp.DataClass.Attributes
import com.samseptiano.covidapp.DataClass.CovidDataResponse
import com.samseptiano.covidapp.DataClass.FeaturesItem
import com.samseptiano.covidapp.ViewModel.MainViewModel
import com.samseptiano.hcroadmap.API.APIConfig
import com.samseptiano.hcroadmap.Room.EmpList.DataDao
import com.samseptiano.hcroadmap.Room.EmpList.DataRoom
import com.samseptiano.hcroadmap.Room.EmpList.DataRoomDatabase
import com.samseptiano.hcroadmap.SharedPreferenced.PreferenceHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.sort_dialog.*
import java.util.*

class MainActivity : AppCompatActivity(){
    var where: String = "1%3D1"
    var outFields: String = "*"
    var outSR: String = "4326"
    var f = "json"
    val CUSTOM_PREF_NAME = "User_data"

    private var snackBar: Snackbar? = null


    lateinit var dataAdapter: DataAdapter
    var Data = CovidDataResponse()
    var featureFilter = mutableListOf<FeaturesItem?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        this.supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar_main)
        val view: View = supportActionBar!!.customView

        var imgRefresh = view.findViewById<ImageView>(R.id.imgRefresh)
        var imgSort = view.findViewById<ImageView>(R.id.imgSort)

        //registerReceiver(CheckConnection(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        imgRefresh.setOnClickListener {
            Toast.makeText(applicationContext, "Refreshing...", Toast.LENGTH_LONG).show()
            loadViewModel()
        }

        imgSort.setOnClickListener {
            showSortDialog()
        }

        loadViewModel()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.setting, menu)

        val mSearch = menu?.findItem(R.id.mi_search)
        val mSearchView = mSearch?.actionView as SearchView
        mSearchView.queryHint = "Search"
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                var charText = s.toString().toLowerCase(Locale.getDefault())
                Data.features = mutableListOf()
                if (charText.isEmpty()) {
                    Data.features = featureFilter
                } else {
                    for (i in 0 until featureFilter!!.size) {
                        if (featureFilter?.get(i)?.attributes?.provinsi.toString()
                                .toLowerCase(Locale.getDefault())
                                .contains(charText)
                        ) {
                            (Data.features as MutableList<FeaturesItem?>)?.add(featureFilter?.get(i)!!)
                        }
                    }
                }
                insertToRV()
                return true
            }
        })


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
            R.id.logout -> logout()
            else -> return super.onOptionsItemSelected(item)

        }
        return true
    }

    fun loadViewModel() {

        if(ConnectivityReceiver.isConnected()) {

            val mainViewModel = MainViewModel(where, outFields, outSR, f, applicationContext)
            mainViewModel.getData2
                .observe(this, object : Observer<CovidDataResponse?> {
                    override fun onChanged(currencyPojos: CovidDataResponse?) {
                        if (currencyPojos != null) {
                            Data = CovidDataResponse()
                            featureFilter = mutableListOf()
                            Data = currencyPojos
                            featureFilter = currencyPojos.features!!

                            insertToRV()
                            lateinit var database: DataRoomDatabase
                            lateinit var dao: DataDao
                            database = DataRoomDatabase.getDatabase(applicationContext)
                            dao = database.getData()
                            dao.deleteAll()
                            for (i in 0 until featureFilter.size){
                                featureFilter[i]?.attributes?.let {


                                    addToRoom(it)
                                }
                            }

                        }
                    }
                })
        }
        else{
            lateinit var database: DataRoomDatabase
            lateinit var dao: DataDao
            database = DataRoomDatabase.getDatabase(applicationContext)
            dao = database.getData()
            var DataTemp = dao.getAll()
            var featuresItemList = mutableListOf<FeaturesItem?>();
            if(DataTemp.size>0) {
                for (i in 0 until DataTemp.size) {
                    var featuresItem = FeaturesItem();
                    var attributes = Attributes();
                    attributes?.FID = i
                    attributes?.kasusMeni = DataTemp[i].Kasus_Meni.toInt()
                    attributes?.kasusPosi = DataTemp[i].Kasus_Posi.toInt()
                    attributes?.kasusSemb = DataTemp[i].Kasus_Semb.toInt()
                    attributes?.kodeProvi = DataTemp[i].Kode_Provi.toInt()
                    attributes?.provinsi = DataTemp[i].Provinsi
                    featuresItem.attributes=attributes
                    featuresItemList.add(featuresItem)
                }
                Data = CovidDataResponse()
                featureFilter = mutableListOf()
                Data.features = featuresItemList
                featureFilter = featuresItemList
                insertToRV()
            }
            Toast.makeText(applicationContext,"Not Connected!!",Toast.LENGTH_LONG).show()
        }
    }

    fun insertToRV(): Unit {
        dataAdapter = Data.features?.let {
            DataAdapter(
                it as List<FeaturesItem>, applicationContext, this
            )
        }!!

        rvData.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dataAdapter
        }


    }


    fun logout() {
        val prefs = PreferenceHelper.customPreference(this, CUSTOM_PREF_NAME)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()

        lateinit var database: DataRoomDatabase
        lateinit var dao: DataDao
        database = DataRoomDatabase.getDatabase(applicationContext)
        dao = database.getData()
        dao.deleteAll()

        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }


    fun showSortDialog() {
        val dialog = this?.let { Dialog(it) }
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(true)
        dialog!!.setContentView(R.layout.sort_dialog)
        dialog!!.show()

        dialog.imgSortConfirmed.setOnClickListener {
            var sortedList = Data.features?.sortedWith(compareBy({ it?.attributes?.kasusPosi }))
                ?.reversed()
            Data.features = sortedList as MutableList<FeaturesItem?>?
            insertToRV()
            dialog.dismiss()
        }
        dialog.imgSortProvince.setOnClickListener {
            var sortedList = Data.features?.sortedWith(compareBy({ it?.attributes?.provinsi }))

            Data.features = sortedList as MutableList<FeaturesItem?>?
            insertToRV()
            dialog.dismiss()
        }

    }

    fun addToRoom(attributes:Attributes):Unit{
        lateinit var database: DataRoomDatabase
        lateinit var dao: DataDao
        database = DataRoomDatabase.getDatabase(applicationContext)
        dao = database.getData()

            var userRoom = DataRoom()
            userRoom?.id=Integer.parseInt(attributes.FID!!.toString())
            userRoom?.Kasus_Meni= attributes.kasusMeni!!.toString()
            userRoom?.Kasus_Posi= attributes.kasusPosi!!.toString()
            userRoom?.Kasus_Semb= attributes.kasusSemb!!.toString()
            userRoom?.Kode_Provi= attributes.kodeProvi!!.toString()
            userRoom?.Provinsi= attributes.provinsi!!.toString()
            userRoom?.FID= attributes.FID!!.toString()
            dao.insert(userRoom)
        }


}