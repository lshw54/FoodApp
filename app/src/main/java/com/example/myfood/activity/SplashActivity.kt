package com.example.myfood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.myfood.FoodApplication
import com.example.myfood.R
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.bean.CircleListBean
import com.example.myfood.bean.Comment
import com.example.myfood.bean.FoodRootBean
import com.example.myfood.bean.User
import com.example.myfood.mydb.DynamicDao
import com.example.myfood.mydb.UserDao
import com.example.myfood.utils.AssertUtils
import com.example.myfood.wight.SharedPreferenceUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : MyBaseActivity() {
    private var userDao:UserDao? = null
    private var dynamicDao:DynamicDao? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)
    }

    override fun init() {
        userDao = UserDao(this)
        if (!SharedPreferenceUtil.getBoolean(context,"first",false)){
            dynamicDao = DynamicDao(context)
            val root: List<User> =
                Gson().fromJson(
                    AssertUtils.getContent(context, "user.json"),
                    object : TypeToken<List<User>?>() {}.type
                )
            for (i in root){
                userDao!!.add(i)
            }
            val cir: List<CircleListBean> =
                Gson().fromJson(
                    AssertUtils.getContent(context, "cir.json"),
                    object : TypeToken<List<CircleListBean>?>() {}.type
                )
            for (i in cir){
                dynamicDao!!.add(i)
            }
            SharedPreferenceUtil.putBoolean(context,"first",true)
        }
        if (!TextUtils.isEmpty(SharedPreferenceUtil.getString(context,"account",""))){
            val list:ArrayList<User> = userDao!!.queryOfData(SharedPreferenceUtil.getString(context,"account",""))
            if (list.size > 0){
                FoodApplication.getInstance().setLoginUser(list[0])
            }
        }

        Timer().schedule(3000) {
            startActivity(Intent(activity,MainActivity))
            finish()
        }
    }

    override fun Layout(): Int {
        return R.layout.activity_splash
    }
}