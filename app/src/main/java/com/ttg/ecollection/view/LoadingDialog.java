package com.ttg.ecollection.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ttg.ecollection.R;

public class LoadingDialog extends Dialog {
    private static LoadingDialog mLoadingProgress;

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }
    public static void showProgress(Context context,CharSequence message,boolean isCanCancel){

        dismissProgress();

        mLoadingProgress=new LoadingDialog(context, R.style.loading_dialog);//自定义style文件主要让背景变成透明并去掉标题部分<!-- 自定义loading dialog -->
        mLoadingProgress.setCanceledOnTouchOutside(false);

        mLoadingProgress.setTitle("");
        mLoadingProgress.setContentView(R.layout.dialog_loading);
        mLoadingProgress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if(message==null|| TextUtils.isEmpty(message)){
            mLoadingProgress.findViewById(R.id.loading_tv).setVisibility(View.GONE);
        }else {
            TextView tv = mLoadingProgress.findViewById(R.id.loading_tv);
            tv.setText(message);
        }
        //按返回键响应是否取消等待框的显示
        mLoadingProgress.setCancelable(isCanCancel);

        mLoadingProgress.show();

    }

    public static void dismissProgress(){
        if(mLoadingProgress!= null && mLoadingProgress.isShowing()){
            mLoadingProgress.dismiss();
            mLoadingProgress = null;
        }
    }
}
