package com.samseptiano.covidapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.samseptiano.hcroadmap.SharedPreferenced.PreferenceHelper
import com.samseptiano.hcroadmap.SharedPreferenced.PreferenceHelper.isLogin
import com.samseptiano.hcroadmap.SharedPreferenced.PreferenceHelper.password
import com.samseptiano.hcroadmap.SharedPreferenced.PreferenceHelper.userId
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val CUSTOM_PREF_NAME = "User_data"
    val USERID="admin"
    val PASSWORD="admin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        this.supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        title=""

        val prefs = PreferenceHelper.customPreference(this, CUSTOM_PREF_NAME)


        if(prefs.isLogin.toString()!=""){
            val mainIntent = Intent(this, MainActivity::class.java)
            this.startActivity(mainIntent)
            this.finish()
        }


        btnLogin.setOnClickListener {
            if(edtUserName.text.toString().equals(USERID) && edtPassword.text.toString().equals(PASSWORD)) {
                prefs.password = edtPassword.text.toString()
                prefs.userId = edtUserName.text.toString()
                prefs.isLogin = "Y"

                val mainIntent = Intent(this, MainActivity::class.java)
                this.startActivity(mainIntent)
                this.finish()
            }
            else{
                Toast.makeText(applicationContext,"Username or Password Wrong!",Toast.LENGTH_SHORT).show()
            }

        }
    }
}