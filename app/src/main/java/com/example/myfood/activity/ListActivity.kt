package com.example.myfood.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myfood.R
import com.example.myfood.adapter.RecommendAdapter
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.bean.Food
import com.example.myfood.bean.FoodRootBean
import com.example.myfood.utils.AssertUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_list.recycler_view
import kotlinx.android.synthetic.main.fragment_recommed.*

class ListActivity : MyBaseActivity() {

    private  var recommedAdapter: RecommendAdapter? =null
    private var type:String? = null
    private var list:ArrayList<Food>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_list)
    }

    override fun init() {
        type = intent.getStringExtra("type")

        title_tv.text = type
        list = ArrayList()
        val root: FoodRootBean =
            Gson().fromJson(
                AssertUtils.getContent(context, "food.json"),
                object : TypeToken<FoodRootBean?>() {}.type
            )

        for (i in root.list){
            if (i.type == type){
                list!!.add(i)
            }
        }

        back_img.setOnClickListener {
            finish()
        }
        recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recommedAdapter = RecommendAdapter(context,list)
        recommedAdapter?.setOnClickListener { view, food, position ->
            startActivity(
                Intent(context, DetailsActivity::class.java)
                    .putExtra("data", food)
            )
        }
        recycler_view.adapter = recommedAdapter

    }


    override fun Layout(): Int {
        return R.layout.activity_list
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

}