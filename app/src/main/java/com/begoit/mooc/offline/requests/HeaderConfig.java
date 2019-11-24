package com.begoit.mooc.offline.requests;

import android.text.TextUtils;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.offline.utils.SharedPreferencesUtils;
import com.begoit.mooc.offline.utils.ToastUtil;
import java.net.NetworkInterface;
import java.net.SocketException;


/**
 * @author gxj
 * @date 2019/02/16 09:26.
 * Desc：请求头配置
 */

public class HeaderConfig {
    private static SharedPreferencesUtils preferences;
    public static String getDeviceNo() {
        if (TextUtils.isEmpty(deviceNo)) {
            String deviceNoNow;
            deviceNoNow = getMacAddress();
            if (TextUtils.isEmpty(deviceNoNow)) {
                ToastUtil.showShortToast("网络出错，请检查网络");
                return null;
            } else {
                deviceNo = deviceNoNow;
            }
        }
        return deviceNo;
    }

    public static String getMacAddress(){
 /*获取mac地址有一点需要注意的就是android 6.0版本后，以下注释方法不再适用，不管任何手机都会返回"02:00:00:00:00:00"这个默认的mac地址，这是googel官方为了加强权限管理而禁用了getSYstemService(Context.WIFI_SERVICE)方法来获得mac地址。*/
        //        String macAddress= "";
//        WifiManager wifiManager = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        macAddress = wifiInfo.getMacAddress();
//        return macAddress;

        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:02";
        }
        return macAddress;
    }

    // 渠道类型 1-Android 2-IOS,必填
    public static final String channel = "1";

    // 设备号,必填
    public static String deviceNo;

    public static void setToken(String token) {
        if (preferences == null){
            preferences = new SharedPreferencesUtils(BegoitMoocApplication.Companion.getContextInstance());
        }
        preferences.setToken(token);
    }

    public static String getToken() {
        if (preferences == null){
            preferences = new SharedPreferencesUtils(BegoitMoocApplication.Companion.getContextInstance());
        }
        return preferences.getToken();
    }
}
