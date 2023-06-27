package com.example.myfood.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myfood.FoodApplication
import com.example.myfood.R
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.bean.User
import com.example.myfood.mydb.UserDao
import com.example.myfood.utils.GlideEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_info.back_img
import kotlinx.android.synthetic.main.activity_info.password_edit
import kotlinx.android.synthetic.main.activity_info.sure_btn
import java.util.ArrayList

class InfoActivity : MyBaseActivity() {
    var img:String? = null
    var loginUser: User?= null
    var userDao: UserDao? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_info)
    }

    override fun init() {
        loginUser = FoodApplication.getInstance().user
        userDao = UserDao(this)

        if (loginUser != null){
            img = loginUser?.avatar
            Glide.with(context!!).load(img).into(circle_img)
            account_edit.setText(loginUser!!.account)
            nickname_edit.setText(loginUser!!.name)
            password_edit.setText(loginUser!!.password)



        }

        sure_btn.setOnClickListener {
            var name = nickname_edit.text.toString()
            if (name.isEmpty()){
                Toast.makeText(context,""+nickname_edit.hint.toString(), Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            loginUser?.avatar = img
            loginUser?.name = name
            if (userDao?.update(loginUser)!! > 0){
                FoodApplication.getInstance().setLoginUser(loginUser)
                finish()
            }
        }

        circle_img.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .isDisplayCamera(true)
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: ArrayList<LocalMedia?>) {
                        if (result.size > 0) {
                            Log.e("TAG", "onResult: " + (result[0]?.realPath))
                            img = result[0]?.realPath
                            Glide.with(context!!).load(img).into(circle_img)
                        }
                    }

                    override fun onCancel() {

                    }
                })

        }

        back_img.setOnClickListener {
            finish()
        }
    }

    override fun Layout(): Int {
        return R.layout.activity_info
    }




    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}