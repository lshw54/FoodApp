package com.example.myfood.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myfood.FoodApplication
import com.example.myfood.R
import com.example.myfood.activity.*
import com.example.myfood.base.BaseFragment
import com.example.myfood.bean.User
import com.example.myfood.wight.SharedPreferenceUtil
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : BaseFragment() {
    private var loginUser:User? = null

    override fun getLayoutID(): Int {
        return R.layout.fragment_mine
    }

    override fun onResume() {
        super.onResume()
        loginUser = FoodApplication.getInstance().user
        if (loginUser != null){
            if (loginUser!!.avatar.isNotEmpty()){
                context?.let { Glide.with(it).load(loginUser!!.avatar).into(image) }
            }
            name_tv.text = loginUser!!.name
        }

    }

    override fun initViews() {

        logout_tv.setOnClickListener {

            AlertDialog.Builder(context)
                .setMessage("Are you sure you want to log out")
                .setTitle("Tip")
                .setPositiveButton("sure", DialogInterface.OnClickListener { dialogInterface, i ->
                    SharedPreferenceUtil.putString(context,"account","")
                    FoodApplication.getInstance().setLoginUser(null)
                    startActivity(Intent(mContext,MainActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                })
                .setNeutralButton("cancel", null)
                .create()
                .show()

        }

        dynamic_tv.setOnClickListener {
            if (loginUser == null) {
                Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, LoginActivity::class.java))
            } else {
                startActivity(Intent(context, MyDynamicActivity::class.java))

            }

        }
        info_tv.setOnClickListener {
            if (loginUser == null) {
                Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, LoginActivity::class.java))
            } else {
                startActivity(Intent(context, InfoActivity::class.java))

            }

        }
        change_tv.setOnClickListener {

            if (loginUser == null) {
                Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, LoginActivity::class.java))
            } else {
                startActivity(Intent(context, ChangePwdActivity::class.java))

            }
        }
        about_tv.setOnClickListener {
            startActivity(Intent(context, AboutActivity::class.java))

        }
    }


}