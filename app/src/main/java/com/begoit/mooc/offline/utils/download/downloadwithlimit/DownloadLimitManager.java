package com.begoit.mooc.offline.utils.download.downloadwithlimit;

import com.begoit.mooc.offline.entity.course.course_detail.course_files.FileInformationEntity;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.utils.LogUtils;
import com.begoit.mooc.offline.utils.download.DownloadIO;
import com.begoit.mooc.offline.utils.download.DownloadInfo;
import com.begoit.mooc.offline.utils.FileHelper;
import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description:下载管理（限制最大个数）
 * @Author:gxj
 * @Time 2019/3/22
 */

public class DownloadLimitManager {

    public static DownloadLimitManager mDownloadLimitManager = null;
    private OkHttpClient mClient;
    private HashMap<String, Call> downCalls; // 用来存放各个下载的请求
    private List<FileInformationEntity> downWait; // 用来存放等待下载的对象
    private int maxCount = 8;                // 同时下载的最大个数

    public static DownloadLimitManager getInstance() {
        if (mDownloadLimitManager == null) {
           mDownloadLimitManager = new DownloadLimitManager();
        }
        return mDownloadLimitManager;
    }

    private DownloadLimitManager() {
        downCalls = new HashMap<>();
        mClient = new OkHttpClient.Builder().build();
        downWait = new ArrayList<>();
    }

    /**
     * 查看是否在下载任务中
     * @param url
     * @return
     */
    public boolean getDownloadUrl(String url){
        return downCalls.containsKey(url);
    }

    /**
     * 查看是否在等待任务中
     * @param entity
     * @return
     */
    public boolean getWaitUrl(FileInformationEntity entity){
        for (FileInformationEntity item : downWait){
            if (item.fileUrl.equals(entity.fileUrl)){
                return true;
            }
        }
        return false;
    }

    /**
     * 开始下载
     *
     * @param entity 下载对象
     */
    public void download(final FileInformationEntity entity) {
        Observable.just(ApiConstants.getFileUrl(entity.fileUrl))
                .filter(new Predicate<String>() { // 过滤 call的map中已经有了,就证明正在下载,则这次不下载
                    @Override
                    public boolean test(String s) {
                        boolean flag = downCalls.containsKey(s);
                        if (flag){
                            // 如果已经在下载，查找下一个文件进行下载
                            downNext();
                            return false;
                        }else{
                            // 判断如果正在下载的个数达到最大限制，存到等下下载列表中
                            if (downCalls.size() >= maxCount){
                                if (!getWaitUrl(entity)){
                                    downWait.add(entity);
                                }
                                return false;
                            }else{
                                return true;
                            }
                        }
                    }
                })
                .flatMap(new Function<String, ObservableSource<DownloadInfo>>() { // 生成 DownloadInfo
                    @Override
                    public ObservableSource<DownloadInfo> apply(String s) {
                        return Observable.just(createDownInfo(entity,null));
                    }
                })
                .map(new Function<DownloadInfo, DownloadInfo>() { // 如果已经下载，重新命名
                    @Override
                    public DownloadInfo apply(DownloadInfo o) {
                        return getRealFileName(o);
                    }
                })
                .flatMap(new Function<DownloadInfo, ObservableSource<DownloadInfo>>() { // 下载
                    @Override
                    public ObservableSource<DownloadInfo> apply(DownloadInfo downloadInfo) {
                        return Observable.create(new DownloadSubscribe(downloadInfo));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程中回调
                .subscribeOn(Schedulers.io()) //  在子线程中执行
                .subscribe(new DownloadLimitObserver()); //  添加观察者，监听下载进度

    }

    /**
     * 下载等待下载中的第一条
     */
    public void downNext(){
        if (downCalls.size() < maxCount && downWait.size() > 0){
            download(downWait.get(0));
            downWait.remove(0);
        }
    }

    /**
     * 取消指定课程下载
     * @param courseId
     */
    public void removeCourse(String courseId){
        if (downWait != null && downWait.size() > 0){
            Iterator<FileInformationEntity> iterator = downWait.iterator();
            while (iterator.hasNext()) {
                FileInformationEntity item = iterator.next();
                if (item.channelId.equals(courseId)){
                    iterator.remove();
                    Call call = downCalls.get(ApiConstants.getFileUrl(item.fileUrl));
                    if (call != null) {
                        call.cancel();//取消
                    }
                    downCalls.remove(ApiConstants.getFileUrl(item.fileUrl));
                }
            }
        }
        downNext();
    }

    /**
     * 下载取消或者暂停
     * @param info
     */
    public void pauseDownload(DownloadInfo info) {
        Call call = downCalls.get(info.getUrl());
        if (call != null) {
            call.cancel();//取消
        }
        downCalls.remove(info.getUrl());
        downNext();
    }

    /**
     * 取消下载 删除本地文件
     * @param info
     */
    public void cancelDownload(DownloadInfo info){
        pauseDownload(info);
        info.setProgress(0);
        info.setDownloadStatus(DownloadInfo.DOWNLOAD_CANCEL);
        EventBus.getDefault().post(info);
        FileHelper.deleteFile(FileHelper.getCourseFilePath(info.getChannelId(),info.getFileName()));
    }

    /**
     * 创建DownInfo
     * @param entity 目标下载对象
     * @return DownInfo
     */
    private DownloadInfo createDownInfo(FileInformationEntity entity,String downLoadStatus) {
        DownloadInfo downloadInfo = new DownloadInfo(ApiConstants.getFileUrl(entity.fileUrl));
        long contentLength = getContentLength(ApiConstants.getFileUrl(entity.fileUrl));//获得文件大小
        if (downLoadStatus != null){
            downloadInfo.setDownloadStatus(downLoadStatus);
        }
        downloadInfo.setTotal(contentLength);
        String fileName = entity.fileUrl.substring(entity.fileUrl.lastIndexOf("/"));
        downloadInfo.setFileName(fileName);
        downloadInfo.setChannelId(entity.channelId);
        return downloadInfo;
    }

    /**
     * 如果文件已下载,设置下载位
     * @param downloadInfo
     * @return
     */
    private DownloadInfo getRealFileName(DownloadInfo downloadInfo){
        try {
            String fileName = downloadInfo.getFileName();
            long downloadLength = 0, contentLength = downloadInfo.getTotal();
            File path = new File(FileHelper.ABSOLUTEPATH);
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(FileHelper.getCourseFilePath(downloadInfo.getChannelId(),fileName));
            if (file.exists()) {
                //找到了文件,代表已经下载过,则获取其长度
                downloadLength = file.length();
            }
            //设置改变过的文件名/大小
            downloadInfo.setProgress(downloadLength);
            downloadInfo.setFileName(file.getName());
        }catch (Exception e){
            LogUtils.e(e.getMessage());
        }
        return downloadInfo;
    }

    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {
        private DownloadInfo downloadInfo;

        public DownloadSubscribe(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void subscribe(ObservableEmitter<DownloadInfo> e) throws Exception {
            if (downloadInfo == null){
                return;
            }
            String url = downloadInfo.getUrl();
            long downloadLength = downloadInfo.getProgress();//已经下载好的长度
            long contentLength = downloadInfo.getTotal();//文件的总长度
            //初始进度信息
            e.onNext(downloadInfo);
            Request request = new Request.Builder()
                    //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                    .url(url)
                    .build();
            Call call = mClient.newCall(request);
            downCalls.put(url, call);//把这个添加到call里,方便取消

            Response response = call.execute();
            InputStream is = null;
            FileOutputStream fileOutputStream = null;
            try {
                File file = new File(FileHelper.getCourseFilePath(downloadInfo.getChannelId(),downloadInfo.getFileName()));
                if (!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                if (!file.exists()){
                    file.createNewFile();
                }
                is = response.body().byteStream();
                fileOutputStream = new FileOutputStream(file, true);
                byte[] buffer = new byte[1024 * 1024];//缓冲数组1M
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    downloadLength += len;
                    downloadInfo.setProgress(downloadLength);
                    downloadInfo.setLen(len);
                    e.onNext(downloadInfo);
                }
                fileOutputStream.flush();
                downCalls.remove(url);
                downNext();
            } finally {
                //关闭IO流
                DownloadIO.closeAll(is, fileOutputStream);
            }
            e.onComplete();//完成
        }
    }



    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? DownloadInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownloadInfo.TOTAL_ERROR;
    }
}
