package com.example.myfood.activity

import android.Manifest.permission.*
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.myfood.R
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.fragment.CommunicationFragment
import com.example.myfood.fragment.MineFragment
import com.example.myfood.fragment.RecommedFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import java.util.jar.Manifest

class MainActivity : MyBaseActivity() {
    private var recommendFramgent: RecommedFragment? = null
    private var mineFragment: MineFragment? = null
    private var communicationFragment: CommunicationFragment? = null

    private var mFragments: ArrayList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
    }



    override fun init() {
        mFragments = ArrayList()
        recommendFramgent = RecommedFragment()
        mineFragment = MineFragment()
        communicationFragment = CommunicationFragment()

        mFragments!!.add(recommendFramgent!!)
        mFragments!!.add(communicationFragment!!)
        mFragments!!.add(mineFragment!!)

        view_pager.adapter = FragmentsPagerAdapter(supportFragmentManager)
        bottom_view.setOnNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.recommend) {
                view_pager.currentItem = 0
            }
            if (menuItem.itemId == R.id.communication) {
                view_pager.currentItem = 1
            }
            if (menuItem.itemId == R.id.mine) {
                view_pager.currentItem = 2
            }
            true
        }
        view_pager.offscreenPageLimit = mFragments!!.size
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bottom_view.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })



        // Permissions requiring dynamic application
        val permission = READ_EXTERNAL_STORAGE
        // Check Permission
        val checkSelfPermission = context?.let { ActivityCompat.checkSelfPermission(it,READ_EXTERNAL_STORAGE) }
        val checkSelfPermission2 = context?.let { ActivityCompat.checkSelfPermission(it,WRITE_EXTERNAL_STORAGE) }
        val checkSelfPermission3 = context?.let { ActivityCompat.checkSelfPermission(it,CAMERA) }
        val checkSelfPermission4 = context?.let { ActivityCompat.checkSelfPermission(it,
            ACCESS_FINE_LOCATION) }
        val checkSelfPermission5 = context?.let { ActivityCompat.checkSelfPermission(it,
            ACCESS_COARSE_LOCATION) }

        if (checkSelfPermission  == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission2  == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission3  == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission4  == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission5  == PackageManager.PERMISSION_GRANTED
                ) {

        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,permission)){

                val permissions = arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA,
                    ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions,1)
                }

            }else{
                val permissions = arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA,
                    ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions,1)
                }
            }
        }



    }

    /***
     * Permission request result Reinstate this method in the Activity to get the result of obtaining the permission.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //Whether to get the permission
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }

    override fun Layout(): Int {
        return R.layout.activity_main
    }


    inner class  FragmentsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val mFragmentManager: FragmentManager = fm
        override fun getItem(position: Int): Fragment {
            return this@MainActivity.mFragments!![position]
        }

        override fun getCount(): Int {
            return this@MainActivity.mFragments!!.size
        }

    }
}