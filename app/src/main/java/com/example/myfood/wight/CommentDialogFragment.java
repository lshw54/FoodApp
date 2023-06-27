package com.example.myfood.wight;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.myfood.FoodApplication;
import com.example.myfood.R;
import com.example.myfood.bean.CircleListBean;
import com.example.myfood.bean.Comment;
import com.example.myfood.bean.User;
import com.example.myfood.mydb.CommentDao;
import com.example.myfood.utils.DateUtils;
import com.example.myfood.utils.SoftKeyboardUtil;

public class CommentDialogFragment extends DialogFragment implements View.OnClickListener {

    private DialogInterface.OnDismissListener onDismissListener;
    private Activity activity;
    private EditText editText;
    private TextView cancleTv,sureTv;
    private CircleListBean circleListBean;
    private User loginUser;
    private OnCommentSuccess onCommentSuccess;
    private Comment comment;
    private CommentDao commentDao;

    public void setOnCommentSuccess(OnCommentSuccess onCommentSuccess) {
        this.onCommentSuccess = onCommentSuccess;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        //设置关闭弹框的回调
        this.onDismissListener = listener;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        commentDao = new CommentDao(FoodApplication.getInstance().getApplicationContext());
        Dialog alertDialog = new Dialog(getContext());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View customView = LayoutInflater.from(getContext()).inflate(R.layout.comment_fragment, null);
        alertDialog.setContentView(customView);
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowParams = alertDialog.getWindow().getAttributes();
        windowParams.width = (int) (getResources().getDisplayMetrics().widthPixels -
                getResources().getDisplayMetrics().density * 20);
        alertDialog.getWindow().setAttributes(windowParams);
        Bundle bundle = getArguments();
        if (bundle != null) {
            circleListBean = (CircleListBean) bundle.getSerializable("data");
            comment = (Comment)bundle.getSerializable("data2");
        }
        findView(customView);

        return alertDialog;
    }


    private void findView(View view){
        editText = view.findViewById(R.id.user_edit);


        sureTv = view.findViewById(R.id.loggin_tv);
        cancleTv = view.findViewById(R.id.cancle_tv);

        cancleTv.setOnClickListener(this);
        sureTv.setOnClickListener(this);

        loginUser = FoodApplication.getInstance().getUser();
        if (comment != null){
            editText.setHint("Reply"+comment.getUserName()+":");
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loggin_tv) {
            String user = editText.getText().toString();
            if (!TextUtils.isEmpty(user)){
                Comment comment = new Comment();
                comment.setUserNid(loginUser.getAccount());
                comment.setPid(circleListBean.getId());
                comment.setTime(DateUtils.getStringDateToSecond());
                comment.setCommentContent(user);
                comment.setUserName(loginUser.getName());
                if (this.comment != null){
                    comment.setReplyName(this.comment.getUserName());
                }
                Log.e("TAG", "onClick: "+commentDao.add(comment));

                if (onCommentSuccess != null){
                    onCommentSuccess.onCommentSuccess(comment);
                }
                SoftKeyboardUtil.HideKeyboard(v);
                dismiss();
            }
        }

        if (v.getId() == R.id.cancle_tv) {
            SoftKeyboardUtil.HideKeyboard(v);
            dismiss();
        }
    }


    public interface OnCommentSuccess {
        void onCommentSuccess(Comment comment);
    }
}
