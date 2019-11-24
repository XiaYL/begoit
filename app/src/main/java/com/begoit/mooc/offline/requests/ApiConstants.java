package com.begoit.mooc.offline.requests;

/**
 * @author gxj
 * @date 2019/02/16 09:26.
 * Desc：接口配置
 */

public class ApiConstants {

    public static final int SUCCESSCODE = 1;
    public static String APP_HOST = "http://192.168.20.211:8081/transferserver/app/";//192.168.0.114  192.168.20.118
    public static String FILE_HOST = "http://120.78.168.211/";//120.78.168.211
    public static String TEST_HOST = "http://easy-mock.com/mock/5997cce9059b9c566dc7e771/leitaijingji_list/";


    public static String getFileUrl(String url){
        if (url == null){
            return null;
        }
        if (url.startsWith("/"))
            url = url.substring(1);
        return FILE_HOST + url;
    }


    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.TYPE_BAIDU:
                host = TEST_HOST;
                break;
            case HostType.TYPE_APP:
                host = APP_HOST;
                break;
            case HostType.TYPE_FILE_UPLOAD:
                host = APP_HOST;
                break;
            default:
                host = "";
                break;
        }
        return host;
    }
}
