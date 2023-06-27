package com.example.myfood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.ShareActionProvider
import android.widget.Toast
import com.example.myfood.FoodApplication
import com.example.myfood.R
import com.example.myfood.activity.LoginActivity
import com.example.myfood.activity.MainActivity
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.bean.User
import com.example.myfood.mydb.UserDao
import com.example.myfood.wight.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_change_pwd.*
import kotlinx.android.synthetic.main.activity_change_pwd.back_img
import kotlinx.android.synthetic.main.activity_change_pwd.password_edit

class ChangePwdActivity : MyBaseActivity() {
    var loginUser: User?= null
    var userDao: UserDao? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        userDao = UserDao(this)
        loginUser = FoodApplication.getInstance().user
        sure_btn.setOnClickListener {
            val old = old_password_edit.text.toString()
            val password = password_edit.text.toString()
            val againPwd = again_password_edit.text.toString()

            if (TextUtils.isEmpty(old)){
                Toast.makeText(context,""+old_password_edit.hint.toString(), Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(context,""+password_edit.hint.toString(), Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(againPwd)){
                Toast.makeText(context,""+again_password_edit.hint.toString(), Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if (old == loginUser?.password){
                if (againPwd != password){
                    Toast.makeText(context,"The two passwords are different", Toast.LENGTH_SHORT).show();
                    return@setOnClickListener
                }
                loginUser!!.password = password
                if (userDao!!.update(loginUser) > 0){
                    Toast.makeText(context,"Password changed successfully,Please login again", Toast.LENGTH_SHORT).show();
                    FoodApplication.getInstance().setLoginUser(null)
                    SharedPreferenceUtil.putString(context,"account","")
                    startActivity(
                        Intent(context, MainActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                }

            }else{
                Toast.makeText(context,"The old password is incorrect", Toast.LENGTH_SHORT).show();
            }

        }




    }

    override fun Layout(): Int {
        return R.layout.activity_change_pwd
    }





    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}