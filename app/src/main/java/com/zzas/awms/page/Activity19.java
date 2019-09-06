/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.awms.page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.baidu.tts.sample.control.InitConfig;
import com.baidu.tts.sample.listener.UiMessageListener;
import com.google.gson.Gson;
import com.xuexiang.xui.XUI;
import com.zzas.awms.wmsapplication.R;
import com.zzas.cwms.Adapter.nwmsActivity17aAdapter;
import com.zzas.cwms.Adapter.nwmsActivity17aModel;
import com.zzas.cwms.Adapter.owmsActivity19Adapter;
import com.zzas.cwms.Adapter.owmsActivity19Model;
import com.zzas.dwms.Beans.wmsajAdjustBean;
import com.zzas.ewms.Tools.PinYinUtil;
import com.zzas.ewms.Tools.zzasToolCJsonMap;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Activity19 extends AppCompatActivity implements EventListener, View.OnClickListener {

    protected ImageButton btn;
    private ImageButton backBut;
    private String ZZasResut = null;//用来记录语音返回结果
    private EventManager asr;
    private ListView lv_list;
    private List<Map<String,String>>  ListValue = new ArrayList();


    private String appId = "16787514";
    private String appKey = "ldCUDjDj40TKvG8BoKAySTbv";
    private String secretKey = "6ixwh1M3qjQ6DiXlfycPlCeCPD1n8hai";

    private TtsMode  ttsMode = TtsMode.ONLINE;
    private SpeechSynthesizer mSpeechSynthesizer;
    private Handler mainHandler;
    private static final String TEMP_DIR = "/sdcard/baiduTTS";
    private static final String TEXT_FILENAME = TEMP_DIR + "/" + "bd_etts_text.dat";

    // 请确保该PATH下有这个文件 ，m15是离线男声
    private static final String MODEL_FILENAME =
            TEMP_DIR + "/" + "bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_19);
        initPermission();//申请动态权限
        //按钮
        btn = findViewById(R.id.btn);
        backBut = findViewById(R.id.backBut);
        btn.setOnClickListener(this);
        backBut.setOnClickListener(this);
        //listView
        lv_list = findViewById(R.id.lv_list);
        ShowListView();//初始化显示listView
        // 基于sdk集成1.1 初始化EventManager对象
        asr = EventManagerFactory.create(this, "asr");
        // 基于sdk集成1.3 注册自己的输出事件类
        asr.registerListener(this); //  EventListener 中 onEvent方法

        //初始化声音合成
        PlayVoiceInit();
    }


    //申请动态权限
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ArrayList<String> toApplyList = new ArrayList<String>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }
    }
    //按钮点击事件
    @Override
    public void onClick(View v) {
        if (v.equals(btn)){//开启语音识别
            StopHeChen();//关闭语音合成
            btn.setImageResource(R.mipmap.luyinopen);
            start();//开启语音识别
        }if (v.equals(backBut)){
            finish();
        }
    }

    //开启语音识别
    private void start() {

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        (new AutoCheck(getApplicationContext(), new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
                    }
                }
            }
        },false)).checkAsr(params);
        String json = null; // 可以替换成自己的json
        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);
    }
    //关闭语音识别
    private void stop() {
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }
    //语音回调函数
    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        if (name.equals("asr.partial")){//语音片段
            ZZasResut = params;
        }else if (name.equals("asr.exit")){//语音完成
            btn.setImageResource(R.mipmap.luyinclose);
            voiceData(ZZasResut);//解析语音返回结果
            ZZasResut = null;
        }
    }



    //页面被遮挡时调用
    @Override
    protected void onPause(){
        super.onPause();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        Log.i("ActivityMiniRecog","On pause");
    }
    //页面被销毁时调用
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放语音识别
        // 基于SDK集成4.2 发送取消事件
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        // 基于SDK集成5.2 退出事件管理器
        // 必须与registerListener成对出现，否则可能造成内存泄露
        asr.unregisterListener(this);
        //释放语音合成
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stop();
            mSpeechSynthesizer.release();
            mSpeechSynthesizer = null;
        }
    }

    //动态权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }
    //初始化显示listView
    private void ShowListView() {
        ListValue.add(SetListMap("1","","您好！请说出您要使用的功能。",""));
        ListValue.add(SetListMap("1","","您可以给我说下面所对应功能。",""));
        String strChar = "到货复核 \t， 入库上架 \n" +
                "拣货确认 \t， 出库复核 \n" +
                "库存盘点 \t， 库位调整 \n" +
                "设备巡检 \t， 安全巡检 \n" +
                "派送管理 \t， 运输管理 ";
        ListValue.add(SetListMap("1","",strChar,""));
        //初始化listView显示
        SetListViewData();
    }
    //用于返回map。初始化显示listView
    private Map<String, String> SetListMap(String LayoutOne,String LayoutTwo,String textYuYin,String KeHuYuYin) {
        Map<String,String> map = new HashMap<>();
        map.put("LayoutOne",LayoutOne);
        map.put("LayoutTwo",LayoutTwo);
        map.put("textYuYin",textYuYin);
        map.put("KeHuYuYin",KeHuYuYin);
        return map;
    }
    //根据信息显示listview
    private void SetListViewData(){
        List<owmsActivity19Model> lists = new ArrayList<>();
        for (int i = 0; i < ListValue.size(); i++) {
            owmsActivity19Model init = new owmsActivity19Model();
            init.setLayoutOne(ListValue.get(i).get("LayoutOne"));
            init.setLayoutTwo(ListValue.get(i).get("LayoutTwo"));
            init.setTextYuYin(ListValue.get(i).get("textYuYin"));
            init.setKeHuYuYin(ListValue.get(i).get("KeHuYuYin"));
            lists.add(init);
        }
        owmsActivity19Adapter mAdapter = new owmsActivity19Adapter(Activity19.this,lists);
        lv_list.setAdapter(mAdapter);
        lv_list.setSelection(mAdapter.getCount() - 1);
    }
    //解析语音返回结果
    private void voiceData(String resultStr){
        if (resultStr == null || resultStr.equals("") ){
            Toast.makeText(getApplicationContext(), "未获取到声音，请检查网络连接，重新录音。", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> map =new HashMap<>();
        zzasToolCJsonMap zzasJsonMap = new zzasToolCJsonMap();
        map = zzasJsonMap.getMap(resultStr);
        if (map == null){
            Toast.makeText(getApplicationContext(), "声音不清晰，请重新录音。", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("zzzas","结果："+map.get("best_result"));
        String strV = map.get("best_result").toString();
        PinYinUtil pinYinUtil = new PinYinUtil();
        String pinyinStr = pinYinUtil.getPingYin(strV);
        StrFun(pinyinStr,strV);//处理语音返回结果
    }
    //根据语音结果处理对应逻辑
    private void StrFun(String keyStr,String YuYan){
        if (keyStr.equals("daohuofuhe")){
            Map<String,String> map = SetListMap("0","1","","到货复核");
            ListValue.add(map);
            itemFun("到货复核");
        }else if (keyStr.equals("rukushangjia")){
            Map<String,String> map = SetListMap("0","1","","入库上架");
            ListValue.add(map);
            itemFun("入库上架");
        }else if (keyStr.equals("jianhuoqueren")){
            Map<String,String> map = SetListMap("0","1","","拣货确认");
            ListValue.add(map);
            itemFun("拣货确认");
        }else if (keyStr.equals("chukufuhe")){
            Map<String,String> map = SetListMap("0","1","","出库复核");
            ListValue.add(map);
            itemFun("出库复核");
        }else if (keyStr.equals("kucunpandian")){
            Map<String,String> map = SetListMap("0","1","","库存盘点");
            ListValue.add(map);
            itemFun("库存盘点");
        }else if (keyStr.equals("kuweidiaozheng")){
            Map<String,String> map = SetListMap("0","1","","库位调整");
            ListValue.add(map);
            itemFun("库位调整");
        }else if (keyStr.equals("shebeixunjian")){
            Map<String,String> map = SetListMap("0","1","","设备巡检");
            ListValue.add(map);
            itemFun("设备巡检");
        }else if (keyStr.equals("anquanxunjian")){
            Map<String,String> map = SetListMap("0","1","","安全巡检");
            ListValue.add(map);
            itemFun("安全巡检");
        }else if (keyStr.equals("paisongguanli")){
            Map<String,String> map = SetListMap("0","1","","派送管理");
            ListValue.add(map);
            itemFun("派送管理");
        }else if (keyStr.equals("yunshuguanli")){
            Map<String,String> map = SetListMap("0","1","","运输管理");
            ListValue.add(map);
            itemFun("运输管理");
        }else {
            Map<String,String> mapL = SetListMap("0","1","",YuYan);
            ListValue.add(mapL);
            String str = "我没听清，请按照上面的功能选项再说一遍";
            Map<String,String> map = SetListMap("1","0",str,"");
            ListValue.add(map);
            PlayHeChen(str);
        }
        SetListViewData();//显示listView
    }

    //根据所点击的listView执行对应的方法
    private void itemFun( String keyName){
        switch (keyName){
            case "到货复核":
                Intent intent01 = new Intent(Activity19.this,Activity03.class);
                startActivity(intent01);
                finish();
                break;
            case "入库上架":
                Intent intent02 = new Intent(Activity19.this,Activity06.class);
                startActivity(intent02);
                finish();
                break;
            case "拣货确认":
                Intent intent03 = new Intent(Activity19.this,Activity09.class);
                startActivity(intent03);
                finish();
                break;
            case "出库复核":
                Intent intent04 = new Intent(Activity19.this,Activity10.class);
                startActivity(intent04);
                finish();
                break;
            case "库存盘点":
                Intent intent05 = new Intent(Activity19.this,Activity11.class);
                startActivity(intent05);
                finish();
                break;
            case "库位调整":
                Intent intent06 = new Intent(Activity19.this,Activity12.class);
                startActivity(intent06);
                finish();
                break;
            case "设备巡检":
                Intent intent07 = new Intent(Activity19.this,Activity13.class);
                startActivity(intent07);
                finish();
                break;
            case "安全巡检":
                Intent intent08 = new Intent(Activity19.this,Activity14.class);
                startActivity(intent08);
                finish();
                break;
            case "派送管理":
                Intent intent09 = new Intent(Activity19.this,Activity15.class);
                startActivity(intent09);
                finish();
                break;
            case "运输管理":
                Intent intent10 = new Intent(Activity19.this,Activity16.class);
                startActivity(intent10);
                finish();
                break;
            default:
                Toast.makeText(getApplicationContext(), "执行错误，请联系管理员！", Toast.LENGTH_SHORT).show();
                break;

        }

    }



    /*****************************语音合成部分*****************************************/
    //9.0版本需要配置文件添加<uses-library android:name="org.apache.http.legacy" android:required="false"/>标签
    //初始化声音播放
    private void PlayVoiceInit() {
        mainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.obj != null) {
                    print(msg.obj.toString());
                }
            }
        };
        //声音播放权限
        initPermissionTwo();
        //初始化声音配置
        initTTs();
        //播放声音合成
        String str  ="您好！请说出您要使用的功能。您可以给我说下面所对应功能。" +
                "到货复核,入库上架,拣货确认,出库复核,库存盘点,库位调整" +
                ",设备巡检,安全巡检,派送管理,运输管理";
        PlayHeChen(str);//播放语音
    }
    //根据内容播放语音合成
    private void PlayHeChen(String str){
        mSpeechSynthesizer.speak(str);
    }
    //停止播放语音合成
    private void StopHeChen() {
        mSpeechSynthesizer.stop();
    }
    //语音读取
    private void initTTs() {
        LoggerProxy.printable(true); // 日志打印在logcat中
        boolean isMix = ttsMode.equals(TtsMode.MIX);
        boolean isSuccess;
        if (isMix) {
            // 检查2个离线资源是否可读
            isSuccess = checkOfflineResources();
            if (!isSuccess) {
                return;
            } else {
                print("离线资源存在并且可读, 目录：" + TEMP_DIR);
            }
        }
        // 日志更新在UI中，可以换成MessageListener，在logcat中查看日志
        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler);

        // 1. 获取实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(this);

        // 2. 设置listener
        mSpeechSynthesizer.setSpeechSynthesizerListener(listener);

        // 3. 设置appId，appKey.secretKey
        int result = mSpeechSynthesizer.setAppId(appId);
        checkResult(result, "setAppId");
        result = mSpeechSynthesizer.setApiKey(appKey, secretKey);
        checkResult(result, "setApiKey");

        // 4. 支持离线的话，需要设置离线模型
        if (isMix) {
            // 检查离线授权文件是否下载成功，离线授权文件联网时SDK自动下载管理，有效期3年，3年后的最后一个月自动更新。
            isSuccess = checkAuth();
            if (!isSuccess) {
                return;
            }
            // 文本模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
            // 声学模型文件路径 (离线引擎使用)， 注意TEXT_FILENAME必须存在并且可读
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
        }

        // 5. 以下setParam 参数选填。不填写则默认值生效
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // x. 额外 ： 自动so文件是否复制正确及上面设置的参数
        Map<String, String> params = new HashMap<>();
        // 复制下上面的 mSpeechSynthesizer.setParam参数
        // 上线时请删除AutoCheck的调用
        if (isMix) {
            params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, TEXT_FILENAME);
            params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, MODEL_FILENAME);
        }
        InitConfig initConfig =  new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);
        com.baidu.tts.sample.util.AutoCheck.getInstance(getApplicationContext()).check(initConfig, new Handler() {
            @Override
            /**
             * 开新线程检查，成功后回调
             */
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    com.baidu.tts.sample.util.AutoCheck autoCheck = (com.baidu.tts.sample.util.AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainDebugMessage();
                        print(message); // 可以用下面一行替代，在logcat中查看代码
                        // Log.w("AutoCheckMessage", message);
                    }
                }
            }

        });

        // 6. 初始化
        result = mSpeechSynthesizer.initTts(ttsMode);
        checkResult(result, "initTts");

    }
    private boolean checkOfflineResources() {
        String[] filenames = {TEXT_FILENAME, MODEL_FILENAME};
        for (String path : filenames) {
            File f = new File(path);
            if (!f.canRead()) {
                print("[ERROR] 文件不存在或者不可读取，请从assets目录复制同名文件到：" + path);
                print("[ERROR] 初始化失败！！！");
                return false;
            }
        }
        return true;
    }
    private void print(String message) {
        Log.e("zzas", message);
    }
    private void checkResult(int result, String method) {
        if (result != 0) {
            print("error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }
    private boolean checkAuth() {
        AuthInfo authInfo = mSpeechSynthesizer.auth(ttsMode);
        if (!authInfo.isSuccess()) {
            // 离线授权需要网站上的应用填写包名。本demo的包名是com.baidu.tts.sample，定义在build.gradle中
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            print("【error】鉴权失败 errorMsg=" + errorMsg);
            return false;
        } else {
            print("验证通过，离线正式授权文件存在。");
            return true;
        }
    }

    //语音合成需要的权限
    private void initPermissionTwo() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }
}
