package com.example.myfood.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.FoodApplication
import com.example.myfood.R
import com.example.myfood.adapter.CommunicationAdapter
import com.example.myfood.base.MyBaseActivity
import com.example.myfood.bean.CircleListBean
import com.example.myfood.bean.Comment
import com.example.myfood.mydb.CommentDao
import com.example.myfood.mydb.DynamicDao
import com.example.myfood.mydb.UserDao
import com.example.myfood.utils.SoftKeyboardUtil
import com.example.myfood.wight.CommentDialogFragment
import kotlinx.android.synthetic.main.activity_my_dynamic.*
import kotlinx.android.synthetic.main.fragment_communication.*
import java.util.ArrayList

class MyDynamicActivity : MyBaseActivity() {
    private var dynamicDao: DynamicDao? = null
    private var userDao: UserDao? = null
    private var commentDao: CommentDao? = null
    private var adapter: CommunicationAdapter? = null
    private var commentDialog: CommentDialogFragment? = null
    private var list: ArrayList<CircleListBean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {

        recycler_view.layoutManager = LinearLayoutManager(context)
        dynamicDao = DynamicDao(context)
        userDao = UserDao(context)
        commentDao = CommentDao(context)
        if (list == null)
            list = ArrayList()
        list?.clear()
        list?.addAll(dynamicDao!!.queryOfData(FoodApplication.getInstance().user.account))
        for (i in list!!){
            val userList = userDao!!.queryOfData(i.account)
            if (userList != null && userList.size > 0){
                i.user = userList[0]
            }
        }
        list!!.reverse()


        adapter = CommunicationAdapter(context,list,1)
        adapter!!.setOnMyClickListener(object : CommunicationAdapter.OnMyClickListener {
            override fun onClickListener(view: View?, circleListBean: CircleListBean?, position: Int) {
                if (FoodApplication.getInstance().user == null){
                    Toast.makeText(context,"Please login", Toast.LENGTH_SHORT).show();
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
                    commentDialog!!.show(supportFragmentManager, "CommentDialogFragment")
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
                    Toast.makeText(context,"Please login", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(context,"Delete successfully", Toast.LENGTH_SHORT).show()
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
                        commentDialog!!.show(supportFragmentManager, "CommentDialogFragment2")
                    }
                }
            }

        })

        recycler_view.adapter = adapter



        back_img.setOnClickListener {
            finish()
        }
    }

    override fun Layout(): Int {
        return R.layout.activity_my_dynamic
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}