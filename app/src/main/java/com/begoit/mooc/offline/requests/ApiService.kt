package com.begoit.mooc.offline.requests

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * @author 接口部署
 * @date 2018/02/06 09:26.
 * Desc：Api Service
 */

interface ApiService {
    //登录
    @POST("user/login")
    @FormUrlEncoded
    fun login(@FieldMap params: Map<String, String>): Observable<ResponseBody>

    //用户下载（同步学习和用户数据至本地）
    @GET("user/download")
    fun userDownload(@QueryMap params: Map<String, String>): Observable<ResponseBody>

    //课程类别菜单
    @GET("channel/typeList")
    fun typeList(): Observable<ResponseBody>

    //分类课程列表  搜索课程列表
    @GET("channel/channelList")
    fun channelList(@QueryMap params: Map<String, String>): Observable<ResponseBody>

    @GET("channelRecommend/channelList")
    fun channelRecommendList(): Observable<ResponseBody>

    //课程详情
    @GET("channel/channelDetails")
    fun channelDetails(@QueryMap params: Map<String, String>): Observable<ResponseBody>

    //课程详情
    @GET("channel/download")
    fun download(@QueryMap params: Map<String, String>): Observable<ResponseBody>

    //上传学习记录
    @Headers("Content-Type:application/json")
    @POST("channel/courseStudy")
    fun courseStudy(@Body body: RequestBody): Observable<ResponseBody>

    //用户解绑
    @GET("user/unbind")
    fun unbind(@QueryMap params: Map<String, String>): Observable<ResponseBody>
}
