/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zzas.aclass;

import com.zzas.dwms.Beans.wmsaaClientBean;
import com.zzas.dwms.Beans.wmsabwahoBean;
import com.zzas.dwms.Beans.wmsacEnterlistBean;
import com.zzas.dwms.Beans.wmsadEntercomlistBean;
import com.zzas.dwms.Beans.wmsaeTrayBean;
import com.zzas.dwms.Beans.wmsafStolocBean;
import com.zzas.dwms.Beans.wmsagOutlistBean;
import com.zzas.dwms.Beans.wmsahOutcomlistBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsaBean;
import com.zzas.dwms.Beans.wmsaiInventoryrecordsbBean;
import com.zzas.dwms.Beans.wmsajAdjustBean;
import com.zzas.dwms.Beans.wmsakAdjustaBean;
import com.zzas.dwms.Beans.wmsalShenBeiBean;
import com.zzas.dwms.Beans.wmsalShenBeiaBean;
import com.zzas.dwms.Beans.wmsalShenBeibBean;
import com.zzas.dwms.Beans.wmsamAnQuanBean;
import com.zzas.dwms.Beans.wmsamAnQuanaBean;
import com.zzas.dwms.Beans.wmsamAnQuanbBean;
import com.zzas.dwms.Beans.wmsanPaiSongBean;
import com.zzas.dwms.Beans.wmsaoYunShuBean;
import com.zzas.dwms.Beans.wmsaoYunShuaBean;
import com.zzas.dwms.Beans.wmsaoYunShubBean;
import com.zzas.dwms.Beans.wmsaoYunShucBean;
import com.zzas.dwms.Beans.wmsapSheBeiItemBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface zzasabService {

    //post多参数请求登录
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.aLoad.biz.ext")
    Observable<zzasaawmsMuo> postMapLogin(@FieldMap Map<String,String> map);
    //post多参数请求手机端权限
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.bGetMenu.biz.ext")
    Observable<zzasafMenu> postMapJurisdiction(@FieldMap Map<String,String> map);
    //post多参数请求根据顶层机构id获取客户信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.BtoolBiz.aGet.biz.ext")
    Observable<wmsaaClientBean> postMapCilen(@FieldMap Map<String,String> map);
    //post多参数请求根据顶层机构id获取仓库信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.BtoolBiz.bGet.biz.ext")
    Observable<wmsabwahoBean> postMapWaho(@FieldMap Map<String,String> map);
    //post多参数请求根据顶层机构id获取入库清单信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.cGet.biz.ext")
    Observable<wmsacEnterlistBean> postMapEntrlist(@FieldMap Map<String,String> map);
    //post多参数请求获取入库清单详情信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.dGet.biz.ext")
    Observable<wmsadEntercomlistBean> postMapEntercomlist(@FieldMap Map<String,String> map);
    //post多参数通仓库id请求获取库区信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.BtoolBiz.cGet.biz.ext")
    Observable<wmsaeTrayBean> postMapTray(@FieldMap Map<String,String> map);
    //post多参数通库区id请求获取库位信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.BtoolBiz.dGet.biz.ext")
    Observable<wmsafStolocBean> postMapStoloc(@FieldMap Map<String,String> map);
    //post入库请求接口，传值为json格式
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.eUpdata.biz.ext")
    Observable<wmsafStolocBean> postMapSetStorage(@Body RequestBody setdata);
    //请求出库清单信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.fGet.biz.ext")
    Observable<wmsagOutlistBean> postMapOutlist(@FieldMap Map<String,String> map);
    //请求出库清单详情信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.gGet.biz.ext")
    Observable<wmsahOutcomlistBean> postMapOutcomlist(@FieldMap Map<String,String> map);
    //post请求接口，传值为json格式,将出库清单更新为拣货完成
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.hUpdate.biz.ext")
    Observable<wmsafStolocBean> postMapUpadteOutlist(@Body RequestBody setdata);
    //post请求接口，传值为json格式,完成出库操作，并更新实时库存，库存调整等信息
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.iUpdate.biz.ext")
    Observable<wmsafStolocBean> postMapSetOutlist(@Body RequestBody setdata);
    //请求库存盘点任务详情信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.jGet.biz.ext")
    Observable<wmsaiInventoryrecordsBean> postMapInventoryrecords(@FieldMap Map<String,String> map);
    //请求盘点库位详情信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.kGet.biz.ext")
    Observable<wmsaiInventoryrecordsaBean> postMapInventoryrecordsa(@FieldMap Map<String,String> map);
    //请求盘点库位商品详情信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.lGet.biz.ext")
    Observable<wmsaiInventoryrecordsbBean> postMapInventoryrecordsb(@FieldMap Map<String,String> map);
    //post请求接口，传值为json格式,完成盘点操作，
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.mUpdte.biz.ext")
    Observable<wmsafStolocBean> postMapSetPanDian(@Body RequestBody setdata);
    //post请求接口，传值为json格式,完成空库位盘点操作，
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.nUpdate.biz.ext")
    Observable<wmsafStolocBean> postMapSetPanDianVoid(@Body RequestBody setdata);
    //库位调整时请求库位调整表详情信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.oGet.biz.ext")
    Observable<wmsajAdjustBean> postMapAdjust(@FieldMap Map<String,String> map);
    //post请求 json数据，添加调库任务数据
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.pAdd.biz.ext")
    Observable<wmsafStolocBean> postMapAdjusta(@Body RequestBody setdata);
    //库位调整时请求库位调整任务单信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.qGet.biz.ext")
    Observable<wmsakAdjustaBean> postMapAdjusta(@FieldMap Map<String,String> map);
    //post请求 json数据，确认库位调整，更新信息
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.rUpdate.biz.ext")
    Observable<wmsajAdjustBean> postMapAdjustaUpdateY(@Body RequestBody setdata);
    //post请求 json数据，放弃库位调整，更新信息
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.sUpdate.biz.ext")
    Observable<wmsajAdjustBean> postMapAdjustaUpdateN(@Body RequestBody setdata);
    //设备巡检时请求巡检任务信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.tGet.biz.ext")
    Observable<wmsalShenBeiBean> postMapSheBei(@FieldMap Map<String,String> map);
    //设备巡检时请求巡检任务中的设备信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.tGeta.biz.ext")
    Observable<wmsalShenBeiaBean> postMapSheBeia(@FieldMap Map<String,String> map);
    //设备巡检时请求巡检任务中的设备巡检项信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.tGetb.biz.ext")
    Observable<wmsalShenBeibBean> postMapSheBeib(@FieldMap Map<String,String> map);
    //post请求 json数据，设备巡检确认，更新信息
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.tUpdate.biz.ext")
    Observable<wmsajAdjustBean> postMapSheBiebeiUpdate(@Body RequestBody setdata);
    //安全巡检时请求巡检任务信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.uGet.biz.ext")
    Observable<wmsamAnQuanBean> postMapAnQuan(@FieldMap Map<String,String> map);
    //安全巡检时请求巡检任务中的制度信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.uGeta.biz.ext")
    Observable<wmsamAnQuanaBean> postMapAnQuana(@FieldMap Map<String,String> map);
    //安全巡检时请求巡检制度中的巡检项信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.uGetb.biz.ext")
    Observable<wmsamAnQuanbBean> postMapAnQuanb(@FieldMap Map<String,String> map);
    //post请求 json数据，安全巡检确认，更新信息
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.uUpdate.biz.ext")
    Observable<wmsajAdjustBean> postMapAnQuanUpdate(@Body RequestBody setdata);
    //获取派送信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.vGet.biz.ext")
    Observable<wmsanPaiSongBean> postMapPaiSong(@FieldMap Map<String,String> map);
    //post请求 json数据，派送确认，更新信息
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.vUpdate.biz.ext")
    Observable<wmsajAdjustBean> postMapPaiSongUpdate(@Body RequestBody setdata);
    //获取运输管理信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.wGet.biz.ext")
    Observable<wmsaoYunShuBean> postMapYunShu(@FieldMap Map<String,String> map);
    //获取调度清单客户信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.wGeta.biz.ext")
    Observable<wmsaoYunShuaBean> postMapYunShua(@FieldMap Map<String,String> map);
    //根据运输单号获取商品信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.wGetb.biz.ext")
    Observable<wmsaoYunShubBean> postMapYunShub(@FieldMap Map<String,String> map);
    //根据车辆id获取车辆信息
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.wGetc.biz.ext")
    Observable<wmsaoYunShucBean> postMapYunShuc(@FieldMap Map<String,String> map);
    //post请求 json数据，装车确认，更新信息
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.xAdd.biz.ext")
    Observable<wmsafStolocBean> postMapZhuangCheUpdate(@Body RequestBody setdata);
    //post请求 json数据，发车确认，更新信息
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.xAdda.biz.ext")
    Observable<wmsafStolocBean> postMapFaCheUpdate(@Body RequestBody setdata);
    //获取设备项信息，用于设备二维码扫描
    @FormUrlEncoded
    @POST("/default/com.zzas.alwms.mobile.AfaceBiz.yGet.biz.ext")
    Observable<wmsapSheBeiItemBean> postMapSheBeiItem(@FieldMap Map<String,String> map);
}
