package com.example.myfood.activity

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.text.TextUtils
import com.example.myfood.FoodApplication
import com.example.myfood.R
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.bean.FoodRootBean
import com.example.myfood.bean.User
import com.example.myfood.mydb.UserDao
import com.example.myfood.utils.AssertUtils
import com.example.myfood.wight.SharedPreferenceUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import android.os.Vibrator
import android.widget.Toast







class LoginActivity : MyBaseActivity() {
    var userDao:UserDao? = null
    private val mDuration = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
    }

    override fun init() {
        userDao = UserDao(context)


        login_btn.setOnClickListener {
            val account = account_edit.text.toString()
            val password = password_edit.text.toString()
            if (TextUtils.isEmpty(account)){
                showToast(account_edit.hint.toString())
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)){
                showToast(password_edit.hint.toString())
                return@setOnClickListener
            }

            val list:ArrayList<User> = userDao!!.queryOfData(account)
            if (list.size == 0){
                showToast("This account is not registered")
            }else{
                var user = list[0]
                if (password == user.password){
                    val vb = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                    if(vb.hasVibrator()){
                        //振动的秒数
                        val vibe:Vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        var effect: VibrationEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            VibrationEffect.createOneShot(mDuration, VibrationEffect.DEFAULT_AMPLITUDE)

                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                        vibe.vibrate(effect)

                    }
                    showToast("Login successfully,Vibrated "+(mDuration/1000)+" two seconds")
                    SharedPreferenceUtil.putString(context,"account",user.account)
                    FoodApplication.getInstance().setLoginUser(user)
                    startActivity(Intent(this,MainActivity::class.java))
                }else{
                    showToast("Password entered incorrectly")
                }

            }


        }


        create_account.setOnClickListener {

            startActivity(Intent(this,CreateAccountActivity::class.java))

        }


        back_img.setOnClickListener {
            finish()
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



    override fun Layout(): Int {
       return R.layout.activity_login
    }
}