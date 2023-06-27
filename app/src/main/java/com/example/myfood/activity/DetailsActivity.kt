package com.example.myfood.activity

import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.adapter.DetailsAdapter
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.bean.Food
import kotlinx.android.synthetic.main.activity_details.*
import android.widget.TextView
import android.widget.LinearLayout
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.Glide
import android.text.TextUtils
import android.view.View





class DetailsActivity : MyBaseActivity() {
    private var detailsAdapter: DetailsAdapter? = null
    private var food:Food? = null
    private var item: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_details)
    }


    override fun init() {
        food = intent.getSerializableExtra("data") as Food

        back_img.setOnClickListener {
            finish()
        }


        recycler_view.layoutManager = LinearLayoutManager(context)
        detailsAdapter = DetailsAdapter(context, food!!.process)
        recycler_view.adapter = detailsAdapter
        if (food != null){
            title_tv.text = food!!.name

            if (!TextUtils.isEmpty(food!!.getPic())) {
                Glide.with(activity!!)
                    .load(food!!.getPic())
                    .transition(DrawableTransitionOptions.withCrossFade(600))
                    .into(look_banner_image1)
            }



            //Title
            look_text1.text = food!!.name
            //Recipe Information
            look_text2.text = Html.fromHtml(food!!.content)
            //Cooking method
            look_text8.text = food!!.tag


            //Adding the main ingredient
            for (mainAndAes in food!!.material) {
                item = LinearLayout.inflate(
                    activity,
                    R.layout.ui_look_banner_item,
                    null
                ) // Get primary/secondary item layout
                (item?.findViewById(R.id.look_b_text1) as TextView).text = mainAndAes.mname
                (item?.findViewById(R.id.look_b_text2) as TextView).text = mainAndAes.amount
                look_lin1.addView(item)
            }

        }



    }

    override fun Layout(): Int {
        return R.layout.activity_details
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

}