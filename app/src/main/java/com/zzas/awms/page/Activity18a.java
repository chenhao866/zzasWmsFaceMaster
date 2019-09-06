/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.FaceSDKManager;
import com.baidu.aip.fl.APIService;
import com.baidu.aip.fl.FaceDetectActivity;
import com.baidu.aip.fl.RegActivity;
import com.baidu.aip.fl.exception.FaceError;
import com.baidu.aip.fl.model.RegResult;
import com.baidu.aip.fl.utils.ImageSaveUtil;
import com.baidu.aip.fl.utils.Md5;
import com.baidu.aip.fl.utils.OnResultListener;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.zzas.awms.wmsapplication.R;
import com.zzas.ewms.Tools.zzasToolBdialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Activity18a extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_DETECT_FACE = 1000;
    private static final int REQUEST_CODE_PICK_IMAGE = 1001;
    private TextView usernameEt;
    private ImageView avatarIv;
    private SuperButton submitBtn;
    private String facePath;
    private Bitmap mHeadBmp;
    private String PassWord = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_18a);
        //初始化人脸检查配置
        init();
        //初始化控件
        usernameEt = findViewById(R.id.username_et);
        avatarIv = findViewById(R.id.avatar_iv);
        submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(this);
        //初始或相片显示
        Bitmap bmp = ImageSaveUtil.loadCameraBitmap(this, "head_tmp.jpg");
        if (bmp != null) {
            avatarIv.setImageBitmap(bmp);
        }
        facePath = ImageSaveUtil.loadCameraBitmapPath(this, "head_tmp.jpg");
        //初始化账户密码显示
        SetEditData();
    }
    //人脸检查配置
    private void init() {
        // 如果图片中的人脸小于200*200个像素，将不能检测出人脸，可以根据需求在100-400间调节大小
        FaceSDKManager.getInstance().getFaceTracker(this).set_min_face_size(200);
        FaceSDKManager.getInstance().getFaceTracker(this).set_isCheckQuality(true);
        // 该角度为商学，左右，偏头的角度的阀值，大于将无法检测出人脸，为了在1：n的时候分数高，注册尽量使用比较正的人脸，可自行条件角度
        FaceSDKManager.getInstance().getFaceTracker(this).set_eulur_angle_thr(45, 45, 45);
        FaceSDKManager.getInstance().getFaceTracker(this).set_isVerifyLive(true);
    }
    //点击按钮事件
    @Override
    public void onClick(View v) {
        //征求摄像头权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            return;
        }
        //提交注册
         if (v == submitBtn) {
            if (TextUtils.isEmpty(facePath)) {
                toast("请先进行人脸采集");
            } else {
                DialogFun("您确定要将当前账号与人脸绑定吗？", "submitBtn");
            }
        }
    }
    //扫描回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DETECT_FACE && resultCode == Activity.RESULT_OK) {

            facePath = ImageSaveUtil.loadCameraBitmapPath(this, "head_tmp.jpg");
            if (mHeadBmp != null) {
                mHeadBmp.recycle();
            }
            mHeadBmp = ImageSaveUtil.loadBitmapFromPath(this, facePath);
            if (mHeadBmp != null) {
                avatarIv.setImageBitmap(mHeadBmp);
            }
        } else if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            facePath = getRealPathFromURI(uri);

            if (mHeadBmp != null) {
                mHeadBmp.recycle();
            }
            mHeadBmp = ImageSaveUtil.loadBitmapFromPath(this, facePath);
            if (mHeadBmp != null) {
                avatarIv.setImageBitmap(mHeadBmp);
            }
        }
    }
    //用于处理本地图片人脸识别，本工程暂时没使用
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    //验证用户名和图片，并提交人脸注册
    private void reg(String filePath) {
        String username = usernameEt.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(Activity18a.this, "绑定账户不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(PassWord)) {
            Toast.makeText(Activity18a.this, "绑定账户出错", Toast.LENGTH_SHORT).show();
            return;
        }
        final File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(Activity18a.this, "文件不存在", Toast.LENGTH_LONG).show();
            return;
        }
        // TODO 人脸注册说明 https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add
        // 模拟注册，先提交信息注册获取uid，再使用人脸+uid到百度人脸库注册，
        // TODO 实际使用中，建议注册放到您的服务端进行（这样可以有效防止ak，sk泄露） 把注册信息包括人脸一次性提交到您的服务端，
        // TODO 注册获得uid，然后uid+人脸调用百度人脸注册接口，进行注册。

        // 每个开发者账号只能创建一个人脸库；
        // 每个人脸库下，用户组（group）数量没有限制；
        // 每个用户组（group）下，可添加最多300000张人脸，如每个uid注册一张人脸，则最多300000个用户uid；
        // 每个用户（uid）所能注册的最大人脸数量没有限制；
        // 说明：人脸注册完毕后，生效时间最长为35s，之后便可以进行识别或认证操作。
        // 说明：注册的人脸，建议为用户正面人脸。
        // 说明：uid在库中已经存在时，对此uid重复注册时，新注册的图片默认会追加到该uid下，如果手动选择action_type:replace，
        // 则会用新图替换库中该uid下所有图片。
        // uid          是	string	用户id（由数字、字母、下划线组成），长度限制128B
        // user_info    是	string	用户资料，长度限制256B
        // group_id	    是	string	用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。
        // 如果需要将一个uid注册到多个group下，group_id,需要用多个逗号分隔，每个group_id长度限制为48个英文字符
        // image	    是	string	图像base64编码，每次仅支持单张图片，图片编码后大小不超过10M
        // action_type	否	string	参数包含append、replace。如果为“replace”，则每次注册时进行替换replace（新增或更新）操作，
        // 默认为append操作
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               faceReg(file);
            }
        }, 1000);


    }
    //处理人脸注册，以及回调函数信息
    private void faceReg(File file) {

        // 用户id（由数字、字母、下划线组成），长度限制128B
        // uid为用户的id,百度对uid不做限制和处理，应该与您的帐号系统中的用户id对应。

        //   String uid = UUID.randomUUID().toString().substring(0, 8) + "_123";
        // String uid = 修改为自己用户系统中用户的id;
        // 模拟使用username替代
        String username = usernameEt.getText().toString().trim()+","+PassWord;
        long timeStr = new Date().getTime();
        String uid = Md5.MD5(username + timeStr, "utf-8");
        APIService.getInstance().reg(new OnResultListener<RegResult>() {
            @Override
            public void onResult(RegResult result) {
                Log.i("wtf", "orientation->" + result.getJsonRes());
                toast("注册成功！");
                finish();
            }

            @Override
            public void onError(FaceError error) {
                toast("注册失败");
            }
        }, file, uid, username);
    }
    private void toast(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Activity18a.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHeadBmp != null) {
            mHeadBmp.recycle();
        }
    }
    //初始化输入框
    private void SetEditData() {
        //获取存储的信息
        Map map = GetHomeData();
        String  username = map.get("userName").toString();
        String  password = map.get("passWord").toString();
        usernameEt.setText(username);
        PassWord = password;


    }
    //获取账号密码
    private Map GetHomeData() {
        //创建一个名称HomeData的数据文件,如果没有就创建
        SharedPreferences sharedPreferences = getSharedPreferences("HomeData", MODE_PRIVATE);
        //获取键值对信息,如果有就获取对应值，如果没有就获得括号中的值
        String username = sharedPreferences.getString("userName","");
        String password = sharedPreferences.getString("passWord","");
        Map map = new HashMap();
        map.put("userName", username);
        map.put("passWord",password);
        return map;
    }

    //简单弹窗封装
    private void DialogFun(String str, String tag){
        zzasToolBdialog zzasdialog = new zzasToolBdialog();
        zzasdialog.showSimpleConfirmDialog(Activity18a.this,str,tag,YesClick,null);
    }
    //弹窗确定按钮点击事件
    MaterialDialog.SingleButtonCallback YesClick = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            if(dialog.getTag().equals("submitBtn")){
                reg(facePath);
            }
        }
    };



}
