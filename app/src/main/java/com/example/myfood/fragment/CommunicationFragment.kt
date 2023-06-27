package com.example.myfood.fragment


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.FoodApplication
import com.example.myfood.R
import com.example.myfood.activity.LoginActivity
import com.example.myfood.activity.ReleaseActivity
import com.example.myfood.adapter.CommunicationAdapter
import com.example.myfood.base.BaseFragment
import com.example.myfood.mydb.DynamicDao
import com.example.myfood.mydb.UserDao
import kotlinx.android.synthetic.main.fragment_communication.*
import java.util.ArrayList

import android.os.Bundle
import android.view.View
import com.example.myfood.bean.CircleListBean
import com.example.myfood.bean.Comment
import com.example.myfood.mydb.CommentDao
import com.example.myfood.utils.SoftKeyboardUtil

import com.example.myfood.wight.CommentDialogFragment





class CommunicationFragment : BaseFragment() {
    private var dynamicDao: DynamicDao? = null
    private var userDao:UserDao? = null
    private var commentDao:CommentDao? = null
    private var adapter: CommunicationAdapter? = null
    private var commentDialog:CommentDialogFragment? = null
    private var list:ArrayList<CircleListBean>? = null
    override fun onResume() {
        super.onResume()
        if (list == null)
            list = ArrayList()
        list?.clear()
        list?.addAll(dynamicDao!!.select())
        list!!.reverse()
        for (i in list!!){
            val userList = userDao!!.queryOfData(i.account)
            if (userList != null && userList.size > 0){
                i.user = userList[0]
                Log.e(TAG, "initViews: "+i.user)
            }
        }
        adapter?.notifyDataSetChanged()
    }
    override fun getLayoutID(): Int {
        return R.layout.fragment_communication
    }

    override fun initViews() {
        dynamicDao = DynamicDao(context)
        userDao = UserDao(context)
        commentDao = CommentDao(context)
        if (list == null)
            list = ArrayList()
        list?.clear()
        list?.addAll(dynamicDao!!.select())
        for (i in list!!){
            val userList = userDao!!.queryOfData(i.account)
            if (userList != null && userList.size > 0){
                i.user = userList[0]
                Log.e(TAG, "initViews: "+i.user)
            }
        }
        list!!.reverse()


        adapter = CommunicationAdapter(context,list)
        adapter!!.setOnMyClickListener(object : CommunicationAdapter.OnMyClickListener {
            override fun onClickListener(view: View?, circleListBean: CircleListBean?, position: Int) {
                if (FoodApplication.getInstance().user == null){
                    Toast.makeText(context,"Please login",Toast.LENGTH_SHORT).show();
                    startActivity(Intent(context,LoginActivity::class.java))
                }else{
                    commentDialog = CommentDialogFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("data", circleListBean)
                    commentDialog!!.arguments = bundle
                    commentDialog!!.setOnDismissListener { dialog ->
                        commentDialog = null
                        SoftKeyboardUtil.hideSoftKeyboard(activity)
                    }
                    commentDialog!!.setOnCommentSuccess { comment ->
                        var commentList: MutableList<Comment?>? =
                            circleListBean?.commentList
                        if (commentList == null) commentList = ArrayList<Comment?>()
                        commentList!!.add(comment)
                        adapter!!.notifyDataSetChanged()
                    }
                    commentDialog!!.show(childFragmentManager, "CommentDialogFragment")
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onClickListener(
                view: View?,
                bean: CircleListBean?,
                comment: Comment?,
                position: Int,
                childerPosti: Int
            ) {
                if (FoodApplication.getInstance().user == null){
                    Toast.makeText(context,"Please login",Toast.LENGTH_SHORT).show();
                    startActivity(Intent(context,LoginActivity::class.java))
                    return
                }
                if (comment != null){
                    if (comment.userNid.equals(FoodApplication.getInstance().user.account)){
                        AlertDialog.Builder(context)
                            .setMessage("Are you sure you want to delete it")
                            .setTitle("Tip")
                            .setPositiveButton("sure", DialogInterface.OnClickListener { dialogInterface, i ->
                                if (commentDao!!.delelte(comment.id.toString()) > 0){
                                    Toast.makeText(context,"Delete successfully",Toast.LENGTH_SHORT).show()
                                    adapter!!.notifyDataSetChanged()
                                }
                            })
                            .setNeutralButton("cancel", null)
                            .create()
                            .show()

                    }else{
                        commentDialog = CommentDialogFragment()
                        val bundle = Bundle()
                        bundle.putSerializable("data", bean)
                        bundle.putSerializable("data2", comment)
                        commentDialog!!.arguments = bundle
                        commentDialog!!.setOnDismissListener { dialog ->
                            commentDialog = null
                        }
                        commentDialog!!.setOnCommentSuccess { comment1 ->
                            adapter!!.notifyDataSetChanged()
                        }
                        commentDialog!!.show(childFragmentManager, "CommentDialogFragment2")
                    }
                }
            }

        })

        recyclerView_list.layoutManager = LinearLayoutManager(context)
        recyclerView_list.adapter = adapter


        fab.setOnClickListener {
            if (FoodApplication.getInstance().user == null){
                Toast.makeText(context,"Please login",Toast.LENGTH_SHORT).show();
                startActivity(Intent(context,LoginActivity::class.java))
            }else{
                startActivity(Intent(context,ReleaseActivity::class.java))
            }
        }

    }

}