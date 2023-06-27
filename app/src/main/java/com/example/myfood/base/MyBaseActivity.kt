package com.example.myfood.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.FoodApplication
import java.lang.StringBuilder

abstract class MyBaseActivity : AppCompatActivity() {
    protected var activity:Activity? = null
    protected var context:Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(Layout())
        activity = this
        context = this
        FoodApplication.getInstance().addActivity(this)
        init()

    }

    abstract fun init()


    abstract fun Layout():Int


    protected fun showToast(msg:String){
        if (!TextUtils.isEmpty(msg)){
            Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FoodApplication.getInstance().removeActivity(this)

    }
}