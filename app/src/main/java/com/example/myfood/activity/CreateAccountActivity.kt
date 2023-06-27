package com.example.myfood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.myfood.FoodApplication
import com.example.myfood.R
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.bean.User
import com.example.myfood.mydb.UserDao
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_create_account.account_edit
import kotlinx.android.synthetic.main.activity_create_account.back_img
import kotlinx.android.synthetic.main.activity_create_account.password_edit
import kotlinx.android.synthetic.main.activity_login.*

class CreateAccountActivity : MyBaseActivity() {
    private var userDao:UserDao? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_create_account)
    }

    override fun init() {
        userDao = UserDao(this)


        back_img.setOnClickListener {
            finish()
        }


        create_btn.setOnClickListener {
            val account = account_edit.text.toString()
            val name = name_edit.text.toString()
            val password = password_edit.text.toString()
            val againPassword = again_password_edit.text.toString()

            if (TextUtils.isEmpty(account)){
                showToast(account_edit.hint.toString())
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(name)){
                showToast(name_edit.hint.toString())
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)){
                showToast(password_edit.hint.toString())
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(againPassword)){
                showToast(again_password_edit.hint.toString())
                return@setOnClickListener
            }
            if (againPassword != password){
                showToast("The two passwords are different")
                return@setOnClickListener
            }



        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun Layout(): Int {
        return R.layout.activity_create_account
    }
}