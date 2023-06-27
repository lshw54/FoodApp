package com.example.myfood.base

import androidx.fragment.app.Fragment
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseFragment:Fragment() {
    protected var mActivity: Activity? = null
    protected var TAG: String? = null

    protected var mContext: Context? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
        TAG = this.javaClass.simpleName
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutID(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }



    protected abstract fun getLayoutID(): Int
    protected abstract fun initViews()
}