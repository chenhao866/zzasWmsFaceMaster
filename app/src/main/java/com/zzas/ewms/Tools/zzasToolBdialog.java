/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 * 页面弹窗相关
 */
package com.zzas.ewms.Tools;

import android.content.Context;

import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.zzas.awms.page.Activity04;

public class zzasToolBdialog {
    /***
     * 简单弹出框
     * @param context 当前页面上下文
     * @param str 弹窗需要显示的内容
     * @param tag 弹窗标签，用于区分点击的是哪一个弹窗
     * @param YesClick 弹窗的确定按钮事件
     */
    public void showSimpleTipDialog(Context context, String str, String tag , MaterialDialog.SingleButtonCallback YesClick) {
        new MaterialDialog.Builder(context)
                .title("信息")
                .content(str)
                .positiveText("确定")
                .tag(tag)
                .cancelable(false)//设置对话框点击外部是否可消失
                .onPositive(YesClick)
                .show();
    }

    /***
     * 简单的对话弹出框
     * @param context 当前页面上下文
     * @param str 弹窗需要显示的内容
     * @param tag 弹窗标签，用于区分点击的是哪一个弹窗
     * @param YesClick 弹窗的确定按钮事件
     * @param NoClick 弹窗的取消按钮事件
     */
    public void showSimpleConfirmDialog(Context context,String str, String tag, MaterialDialog.SingleButtonCallback YesClick, MaterialDialog.SingleButtonCallback NoClick) {
        new MaterialDialog.Builder(context)
                .content(str)
                .tag(tag)
                .cancelable(false)
                .onPositive(YesClick)
                .onNegative(NoClick)
                .positiveText("确定")
                .negativeText("取消")
                .show();
    }
}
