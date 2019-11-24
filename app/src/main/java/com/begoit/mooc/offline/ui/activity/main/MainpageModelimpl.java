package com.begoit.mooc.offline.ui.activity.main;

import com.begoit.mooc.offline.requests.Api;
import com.begoit.mooc.offline.requests.HostType;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Description:主页数据业务
 * @Author:gxj
 * @Time 2019/2/22
 */

public class MainpageModelimpl implements MainpageContract.MainpageModel {
    @Override
    public Observable<String> getLeftMenuItem() {

        return Api.getDefault(HostType.TYPE_APP)//
                .typeList()//
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        return responseBody.string();
                    }
                }).subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread());


//        List<MainpageLeftNavItemEntity> leftNavItemEntities = new ArrayList<>();
//
//        MainpageLeftNavItemEntity leftNavItemEntity101 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_COMMON);
//        leftNavItemEntity101.fId = 101;
//        leftNavItemEntity101.fName = "全部课程";
//        leftNavItemEntity101.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity101);
//
//        MainpageLeftNavItemEntity leftNavItemEntity102 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_COMMON);
//        leftNavItemEntity102.fId = 102;
//        leftNavItemEntity102.fName = "推荐课程";
//        leftNavItemEntity102.fIcon = R.mipmap.ic_course_type_recommend;
//        leftNavItemEntities.add(leftNavItemEntity102);
//
//        MainpageLeftNavItemEntity leftNavItemEntity11 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_FIRST);
//        leftNavItemEntity11.fId = 1;
//        leftNavItemEntity11.fName = "计算机";
//        leftNavItemEntity11.fIcon = R.mipmap.ic_course_type_computer;
//        leftNavItemEntities.add(leftNavItemEntity11);
//
//        MainpageLeftNavItemEntity leftNavItemEntity121 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity121.fId = 121;
//        leftNavItemEntity121.fName = "计算机基础";
//        leftNavItemEntity121.typeSecond = 1;
//        leftNavItemEntity121.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity121);
//
//        MainpageLeftNavItemEntity leftNavItemEntity122 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity122.fId = 122;
//        leftNavItemEntity122.fName = "编程语言";
//        leftNavItemEntity122.typeSecond = 0;
//        leftNavItemEntity122.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity122);
//
//        MainpageLeftNavItemEntity leftNavItemEntity123 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity123.fId = 123;
//        leftNavItemEntity123.fName = "数据库与数据结构";
//        leftNavItemEntity123.typeSecond = 1;
//        leftNavItemEntity123.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity123);
//
//        MainpageLeftNavItemEntity leftNavItemEntity124 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity124.fId = 124;
//        leftNavItemEntity124.fName = "计算机应用";
//        leftNavItemEntity124.typeSecond = 0;
//        leftNavItemEntity124.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity124);
//
//        MainpageLeftNavItemEntity leftNavItemEntity125 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity125.fId = 125;
//        leftNavItemEntity125.fName = "软件工程";
//        leftNavItemEntity125.typeSecond = 1;
//        leftNavItemEntity125.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity125);
//
//        MainpageLeftNavItemEntity leftNavItemEntity12 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_FIRST);
//        leftNavItemEntity12.fId = 2;
//        leftNavItemEntity12.fName = "经济管理";
//        leftNavItemEntity12.fIcon = R.mipmap.ic_course_type_economic_management;
//        leftNavItemEntities.add(leftNavItemEntity12);
//
//        MainpageLeftNavItemEntity leftNavItemEntity221 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity221.fId = 221;
//        leftNavItemEntity221.fName = "经济学";
//        leftNavItemEntity221.typeSecond = 1;
//        leftNavItemEntity221.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity221);
//
//        MainpageLeftNavItemEntity leftNavItemEntity222 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity222.fId = 222;
//        leftNavItemEntity222.fName = "电子商务";
//        leftNavItemEntity222.typeSecond = 0;
//        leftNavItemEntity222.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity222);
//
//        MainpageLeftNavItemEntity leftNavItemEntity223 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity223.fId = 223;
//        leftNavItemEntity223.fName = "金融学";
//        leftNavItemEntity223.typeSecond = 1;
//        leftNavItemEntity223.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity223);
//
//        MainpageLeftNavItemEntity leftNavItemEntity224 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity224.fId = 224;
//        leftNavItemEntity224.fName = "工商管理";
//        leftNavItemEntity224.typeSecond = 0;
//        leftNavItemEntity224.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity224);
//
//        MainpageLeftNavItemEntity leftNavItemEntity225 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity225.fId = 225;
//        leftNavItemEntity225.fName = "创新创业";
//        leftNavItemEntity225.typeSecond = 1;
//        leftNavItemEntity225.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity225);
//
//        MainpageLeftNavItemEntity leftNavItemEntity13 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_FIRST);
//        leftNavItemEntity13.fId = 3;
//        leftNavItemEntity13.fName = "外语";
//        leftNavItemEntity13.fIcon = R.mipmap.ic_course_type_foreign_language;
//        leftNavItemEntities.add(leftNavItemEntity13);
//
//        MainpageLeftNavItemEntity leftNavItemEntity321 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity321.fId = 321;
//        leftNavItemEntity321.fName = "商务英语";
//        leftNavItemEntity321.typeSecond = 1;
//        leftNavItemEntity321.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity321);
//
//        MainpageLeftNavItemEntity leftNavItemEntity322 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity322.fId = 322;
//        leftNavItemEntity322.fName = "计算机英语";
//        leftNavItemEntity322.typeSecond = 0;
//        leftNavItemEntity322.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity322);
//
//        MainpageLeftNavItemEntity leftNavItemEntity323 = new MainpageLeftNavItemEntity(MainpageLeftNavItemEntity.TYPE_SECOND);
//        leftNavItemEntity323.fId = 323;
//        leftNavItemEntity323.fName = "日常英语";
//        leftNavItemEntity323.typeSecond = 1;
//        leftNavItemEntity323.fIcon = R.mipmap.ic_course_type_all;
//        leftNavItemEntities.add(leftNavItemEntity323);
//
//        return leftNavItemEntities;
    }
}
