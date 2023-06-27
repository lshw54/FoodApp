package com.example.myfood.fragment


import android.R.attr
import android.util.Log
import com.example.myfood.R
import com.example.myfood.base.BaseFragment
import androidx.viewpager.widget.ViewPager
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.example.myfood.adapter.MyPagerAdapter
import com.example.myfood.adapter.RecommendAdapter
import kotlinx.android.synthetic.main.fragment_recommed.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import com.example.myfood.bean.Food
import com.example.myfood.bean.FoodRootBean
import com.google.gson.reflect.TypeToken
import com.example.myfood.utils.AssertUtils
import com.google.gson.Gson
import kotlin.concurrent.scheduleAtFixedRate
import android.R.attr.banner

import android.content.Intent
import com.example.myfood.activity.DetailsActivity
import com.example.myfood.activity.ListActivity


class RecommedFragment : BaseFragment() {
    private var adapter: MyPagerAdapter? = null
    private var bannerList: ArrayList<Food>? = null
    private var recommedAdapter: RecommendAdapter? = null
    private var currentItem = 0
    private val CAROUSEL_TIME = 3000L
    private val timer = Timer()

    override fun getLayoutID(): Int {
        return R.layout.fragment_recommed
    }

    override fun initViews() {
        bannerList = ArrayList()

        val root: FoodRootBean =
            Gson().fromJson(
                AssertUtils.getContent(mContext, "food.json"),
                object : TypeToken<FoodRootBean?>() {}.type
            )
        Log.e(TAG, "initViews: " + root.list.size)
        root.list.shuffle()
        bannerList!!.add(root.list[0])
        bannerList!!.add(root.list[1])
        bannerList!!.add(root.list[2])


        adapter = MyPagerAdapter(context, bannerList)
        adapter!!.setOnBannerClickListener { view, food ->
            startActivity(
                Intent(mContext, DetailsActivity::class.java)
                    .putExtra("data", food)
            )
        }


        //Set the number of cache pages
        view_pager.offscreenPageLimit = adapter!!.banners.size - 1;
        view_pager.adapter = adapter
        //Add a page change listener
        view_pager.addOnPageChangeListener(object : ViewPager.OnAdapterChangeListener,
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentItem = position;

                setImageBackground(position % adapter!!.banners.size);
                //Log.e(TAG, "onPageSelected: " + adapter!!.banners.size + "   " + position % adapter!!.banners.size);

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onAdapterChanged(
                viewPager: ViewPager,
                oldAdapter: PagerAdapter?,
                newAdapter: PagerAdapter?
            ) {

            }

        })
        for ((i, b) in adapter!!.banners.withIndex()) {
            val imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(10, 10)
            if (i == 0) {
                //The first one is the default selected state
                imageView.setImageResource(R.drawable.icon_page_select);
            } else {
                imageView.setImageResource(R.drawable.icon_page_normal);
            }
            var params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.leftMargin = 5
            params.rightMargin = 5
            viewGroup.addView(imageView, params)
        }

        currentItem = adapter!!.banners.size * 50;
        view_pager.currentItem = currentItem;


        timer.scheduleAtFixedRate(0, CAROUSEL_TIME) {
            //任务实现
            //Log.e(TAG, "initViews: $currentItem")
            //需要执行的任务
            GlobalScope.launch(Dispatchers.IO) {
                //上下文切换到主线程
                GlobalScope.launch(Dispatchers.Main) {
                    currentItem++
                    view_pager.currentItem = currentItem
                }
            }
        }



        recycler_view.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recommedAdapter = RecommendAdapter(context,root.list)
        recommedAdapter?.setOnClickListener { view, food, position ->
            startActivity(
                Intent(mContext, DetailsActivity::class.java)
                    .putExtra("data", food)
            )
        }
        recycler_view.adapter = recommedAdapter



        breakfast_layout.setOnClickListener {
            startActivity(
                Intent(mContext, ListActivity::class.java)
                    .putExtra("type", "breakfast")
            )

        }
        lunch_layout.setOnClickListener {
            startActivity(
                Intent(mContext, ListActivity::class.java)
                    .putExtra("type", "lunch")
            )

        }
        dinner_layout.setOnClickListener {
            startActivity(
                Intent(mContext, ListActivity::class.java)
                    .putExtra("type", "dinner")
            )

        }
    }

    fun setImageBackground(selectItem: Int) {
        for ((i) in adapter!!.banners.withIndex()) {
            val imageView = viewGroup.getChildAt(i) as ImageView?
            imageView?.background = null;
            if (i == selectItem) {
                imageView?.setImageResource(R.drawable.icon_page_select)
            } else {
                imageView?.setImageResource(R.drawable.icon_page_normal)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

}


