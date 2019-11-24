package com.begoit.mooc.offline.utils.download.downloadwithlimit;

import com.begoit.mooc.offline.utils.download.DownloadInfo;
import com.begoit.mooc.offline.utils.LogUtils;
import org.greenrobot.eventbus.EventBus;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Description:观察者 （限制最大下载个数）
 * @Author:gxj
 * @Time 2019/3/22
 */
public class DownloadLimitObserver implements Observer<DownloadInfo> {

    public Disposable d;//可以用于取消注册的监听者
    public DownloadInfo downloadInfo;

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(DownloadInfo value) {
        this.downloadInfo = value;
        this.downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD);
        EventBus.getDefault().post(downloadInfo);
        LogUtils.d("onNext: " + this.downloadInfo.getFileName() + " ; " + this.downloadInfo.getChannelId() + " ; Progress: " + this.downloadInfo.getProgress());
    }

    @Override
    public void onError(Throwable e) {
        if (downloadInfo != null) {
            LogUtils.d("onError: " + downloadInfo.getFileName() + " ; " + downloadInfo.getUrl() + " ; errorMessage: " + e.getMessage());
            if (DownloadLimitManager.getInstance().getDownloadUrl(downloadInfo.getUrl())) {
                DownloadLimitManager.getInstance().pauseDownload(downloadInfo);
                downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_ERROR);
                EventBus.getDefault().post(downloadInfo);
            } else {
                downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_PAUSE);
                EventBus.getDefault().post(downloadInfo);
            }
        }else {
            LogUtils.d("onError downloadInfo == null");
            EventBus.getDefault().post(new DownloadInfo("",DownloadInfo.DOWNLOAD_ERROR));
        }

    }

    @Override
    public void onComplete() {
        if (downloadInfo != null){
            LogUtils.d("onComplete： " + downloadInfo.getFileName() + ";  " + downloadInfo.getUrl() );
            downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_OVER);
            EventBus.getDefault().post(downloadInfo);
        }else {
            LogUtils.d("onComplete  downloadInfo == null");
        }
    }
}
