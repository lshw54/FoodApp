package com.example.myfood.activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.example.myfood.FoodApplication
import com.example.myfood.R
import com.example.myfood.adapter.GridViewAdapter
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.bean.CircleListBean
import com.example.myfood.bean.User
import com.example.myfood.mydb.DynamicDao
import com.example.myfood.utils.DateUtils
import com.example.myfood.utils.GlideEngine
import com.example.myfood.wight.SharedPreferenceUtil
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import kotlinx.android.synthetic.main.activity_release.*
import java.lang.StringBuilder
import java.util.ArrayList
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.MapsInitializer
import com.amap.api.location.AMapLocationListener


class ReleaseActivity : MyBaseActivity() {
    private var gridViewAdapter: GridViewAdapter? = null
    private val mPicList = ArrayList<String>()
    private var dynamicDao: DynamicDao? = null
    private var loginUser: User? = null
    private var locationStr:String?  = null
    private var lat:String?  = null
    private var lon:String?  = null

    //Declare the AMapLocationClient class object
    private var locationClient: AMapLocationClient? = null
    private var locationOption: AMapLocationClientOption? = null
    //Declaring the location callback listener
    var locationListener: AMapLocationListener? =
        AMapLocationListener { location ->
            if (null != location) {
                val sb = StringBuffer()
                //errCode is equal to 0 on behalf of the positioning success, the other for the positioning failure, the specific can refer to the official website positioning error code description
                if (location.errorCode == 0) {
                    sb.append(
                        """
                            Positioning Success
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Positioning Type: ${location.locationType}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Longitude: ${location.longitude}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Latitude: ${location.latitude}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Accuracy: ${location.accuracy}m
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Providers: ${location.provider}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Speed: ${location.speed}m/s
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Angle: ${location.bearing}
                            
                            """.trimIndent()
                    )
                    // Get the number of satellites currently providing location services
                    sb.append(
                        """
                            Start: ${location.satellites}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Country: ${location.country}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Province: ${location.province}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            City: ${location.city}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            City Code: ${location.cityCode}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            District: ${location.district}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Area Code: ${location.adCode}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Address: ${location.address}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            Poi: ${location.poiName}
                            
                            """.trimIndent()
                    )
                    // Positioning completion time
                } else {
                    //Positioning failure
                    sb.append(
                        """
                            Positioning failure
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            errorCode:${location.errorCode}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            errorMsg:${location.errorInfo}
                            
                            """.trimIndent()
                    )
                    sb.append(
                        """
                            errorDes:${location.locationDetail}
                            
                            """.trimIndent()
                    )
                }
                sb.append("***Positioning Quality Report***").append("\n")
                sb.append("* WIFI Switch：")
                    .append(if (location.locationQualityReport.isWifiAble) "Open" else "Close")
                    .append("\n")
                sb.append("* GPS：").append(location.locationQualityReport.gpsSatellites)
                    .append("\n")
                sb.append("* Network Type：" + location.locationQualityReport.networkType).append("\n")
                sb.append("* Network time consumption：" + location.locationQualityReport.netUseTime).append("\n")
                sb.append("****************").append("\n")
                locationStr = location.address
                lat = location.latitude.toString()
                lon = location.longitude.toString()
                location_tv.text = locationStr
                //解析定位结果，
                val result = sb.toString()
                Log.e("TAG", "onLocationChanged: $result")
            } else {
                Log.e("TAG", "Positioning failure，loc is null: ")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_release)
    }

    override fun init() {

        MapsInitializer.updatePrivacyShow(this,true,true)
        MapsInitializer.updatePrivacyAgree(this,true)
        initLocation()

        checkbox.isChecked = SharedPreferenceUtil.getBoolean(context,"location",true)
        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            SharedPreferenceUtil.putBoolean(context,"location",isChecked)
            if (isChecked){
                location_tv.text = locationStr
            }else{
                location_tv.text = ""
            }
        }

        loginUser = FoodApplication.getInstance().user
        dynamicDao = DynamicDao(this)
        gridViewAdapter = GridViewAdapter(this, mPicList)
        grid_view.adapter = gridViewAdapter
        grid_view.setOnItemClickListener { adapterView, view, i, l ->
            if (i === adapterView.childCount - 1) {
                if (mPicList.size == 8) {
                    //Add up to 3 images
                    //viewPluImg(i);
                } else {
                    //Adding a voucher image
                    selectPic(8 - mPicList.size)
                }
            } else {
                //viewPluImg(i);
            }
        }

        back_img.setOnClickListener {
            finish()
        }

        release_btn.setOnClickListener {
            val commentString = edit.text.toString()
            if (TextUtils.isEmpty(commentString)) {
                Toast.makeText(context, "" + edit.hint.toString(), Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            var comment = CircleListBean()
            comment.content = commentString
            if (mPicList.size > 0) {
                var builder = StringBuilder()
                for (str in mPicList) {
                    if (builder.isEmpty())
                        builder.append(str)
                    else builder.append(",").append(str)
                }
                comment.imgs = builder.toString()
            }
            if (checkbox.isChecked){
                comment.location = locationStr
                comment.lat = lat
                comment.lon = lon
            }
            comment.account = loginUser?.account
            comment.time = DateUtils.getStringDateToSecond()
            Log.e("TAG", "initViewData: $comment")
            if (dynamicDao!!.add(comment) >0){
                Toast.makeText(context, "Release success", Toast.LENGTH_SHORT).show();
                finish()
            }else{
                Toast.makeText(context, "Release failed", Toast.LENGTH_SHORT).show();

            }

        }

    }


    private fun initLocation() {
        //初始化client
        try {
            locationClient = AMapLocationClient(this.applicationContext)
            locationOption = AMapLocationClientOption()
            // Set whether to display address information
            locationOption!!.isNeedAddress = true

            // Set whether to enable caching
            locationOption!!.isLocationCacheEnable = true
            // Set whether single positioning
            locationOption!!.isOnceLocation = false
            locationOption!!.interval = 5000
            // Set positioning parameters
            locationClient!!.setLocationOption(locationOption)
            // Setting up a location listener
            locationClient!!.setLocationListener(locationListener)
            // Start Positioning
            locationClient!!.startLocation()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun selectPic(i: Int) {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .isDisplayCamera(true)
            .setImageEngine(GlideEngine.createGlideEngine())
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: ArrayList<LocalMedia?>) {
                    if (result.size > 0) {
                        Log.e("TAG", "onResult: " + (result[0]?.realPath))
                        for (l in result) {
                            if (l != null) {
                                mPicList.add(l.realPath)
                            }
                        }
                        gridViewAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancel() {

                }
            })
    }


    override fun Layout(): Int {
        return R.layout.activity_release
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}