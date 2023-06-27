package com.example.myfood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.example.myfood.R
import com.example.myfood.base.MyBaseActivity
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : MyBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        back_img.setOnClickListener {
            finish()
        }
    }

    override fun Layout(): Int {
        return R.layout.activity_about

    }





}