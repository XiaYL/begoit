package com.begoit.mooc.offline.widget.videoview

import android.content.Context
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.provider.Settings
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.SeekBar
import cn.jzvd.*
import com.begoit.mooc.BegoitMoocApplication
import com.begoit.mooc.db.VideoTestScoreEntityDao
import com.begoit.mooc.offline.R
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideonobEntity
import com.begoit.mooc.offline.entity.course.user_download.VideoTestScoreEntity
import com.begoit.mooc.offline.ui.activity.learning.event.WatchVideoEvent
import com.begoit.mooc.offline.utils.LogUtils
import com.begoit.mooc.offline.utils.ToastUtil
import com.begoit.mooc.offline.utils.db.DaoManager
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.Constructor

/**
 * @Description:封装原始播放器，用于定制需求
 * @Author:gxj
 * @Time 2019/2/28
 */

class VideoPlayer : JZVideoPlayerStandard {
    private var fromDuration: Int = 0//记录进度条拖拽前进度，用于判断是快进或快退
    private var targetVideo: VideonobEntity? = null //目标视频
    private var targetDuration: Long = 0 //目标视频合格时间
    private var isFastForward: Boolean = false //是否快进
    private var isSuccess: Boolean = false //是否观看完成
    private var targetVideoTestScoreEntity: VideoTestScoreEntity? = null // 目标分数总计对象

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun init(context: Context) {
        super.init(context)
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        JZVideoPlayer.WIFI_TIP_DIALOG_SHOWED = true
        titleTextView.visibility = View.GONE

    }

    /**
     * 给进度条和全屏按钮覆盖新的事件，获取操作权
     */
    fun reSetViews(videoPlayer: VideoPlayer) {
        videoPlayer.progressBar.setOnSeekBarChangeListener(OnSeekBarChangeListenerForVideo(videoPlayer))
        videoPlayer.fullscreenButton.setOnClickListener {
            LogUtils.i("onClick fullscreen [" + this.hashCode() + "] ")
            if (videoPlayer.currentState == CURRENT_STATE_AUTO_COMPLETE) return@setOnClickListener
            if (videoPlayer.currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                //quit fullscreen
                backPress()
            } else {
                LogUtils.d("toFullscreenActivity [" + this.hashCode() + "] ")
                onEvent(JZUserAction.ON_ENTER_FULLSCREEN)
                setWindowFullscreen()
            }
        }
    }

    /**
     * 开启全屏模式
     */
    private fun setWindowFullscreen() {
        LogUtils.i("setWindowFullscreen " + " [" + this.hashCode() + "] ")
        hideSupportActionBar(context)

        val vp = JZUtils.scanForActivity(context)//.getWindow().getDecorView();
                .findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val old = vp.findViewById<View>(R.id.jz_fullscreen_id)
        if (old != null) {
            vp.removeView(old)
        }
        textureViewContainer.removeView(JZMediaManager.textureView)
        try {
            val constructor = this@VideoPlayer.javaClass.getConstructor(Context::class.java) as Constructor<JZVideoPlayer>
            val jzVideoPlayer = constructor.newInstance(context)
            jzVideoPlayer.id = cn.jzvd.R.id.jz_fullscreen_id

            reSetViews((jzVideoPlayer as VideoPlayer?)!!)
            jzVideoPlayer.textureViewContainer.setOnTouchListener(OnTouchListenerForVideo(jzVideoPlayer!!))
            jzVideoPlayer.progressBar.setOnTouchListener(OnTouchListenerForVideo(jzVideoPlayer!!))

            jzVideoPlayer!!.id = R.id.jz_fullscreen_id

            val lp = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            vp.addView(jzVideoPlayer, lp)
            jzVideoPlayer!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            jzVideoPlayer!!.setUp(dataSourceObjects, currentUrlMapIndex, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, *objects)
            jzVideoPlayer!!.setState(currentState)
            jzVideoPlayer!!.addTextureView()
            JZVideoPlayerManager.setSecondFloor(jzVideoPlayer)
            JZUtils.setRequestedOrientation(context, FULLSCREEN_ORIENTATION)

            onStateNormal()
            jzVideoPlayer!!.progressBar.secondaryProgress = progressBar.secondaryProgress
            jzVideoPlayer!!.startProgressTimer()
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun playVideo(targetDuration: Long, targetVideo: VideonobEntity) {
        isFastForward = false
        targetVideoTestScoreEntity = DaoManager.getInstance().daoSession.videoTestScoreEntityDao.queryBuilder()
                .where(VideoTestScoreEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.contextInstance.getCurrentAccound()),
                        VideoTestScoreEntityDao.Properties.VideoId.eq(targetVideo.id)).build().unique()
        if (targetVideoTestScoreEntity == null) {
            isSuccess = false
        } else {
            isSuccess = targetVideoTestScoreEntity!!.watch == 1
        }
        this.targetDuration = targetDuration
        this.targetVideo = targetVideo
        JZVideoPlayer.releaseAllVideos()
        thumbImageView.performClick()
    }

    /**
     * 重写进度条事件监听，获取拖进状态
     */
    inner class OnSeekBarChangeListenerForVideo(private val videoPlayer: JZVideoPlayerStandard) : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            LogUtils.i("seekBar onProgressChanged to [" + this.hashCode() + "] ")
            if (fromUser) {
                //设置这个progres对应的时间，给textview
                val duration = duration
                videoPlayer!!.currentTimeTextView.text = JZUtils.stringForTime(progress * duration / 100)
                if (seekBar!!.progress > fromDuration) {
                    LogUtils.i("向前拖动进度条")
                    isFastForward = true
                } else if (seekBar!!.progress < fromDuration) {
                    LogUtils.i("向后拖动进度条")
                }
            } else {
                val position = videoPlayer.currentPositionWhenPlaying / 1000
                if (position <= 0) {
                    isFastForward = false
                }
                if (!isSuccess && !isFastForward && position >= targetDuration && targetVideo != null) {
                    isSuccess = true
                    LogUtils.i("观看完成  视频 [${targetVideo?.videoTitle}]  播放进度 [ $position ] 目标时间 [$targetDuration]  是否快进  $isFastForward 是否完成 $isSuccess")
                    EventBus.getDefault().post(WatchVideoEvent(targetVideo!!))
                }
                LogUtils.i("视频 [${targetVideo?.videoTitle}]  播放进度 [ $position ] 目标时间 [$targetDuration]  是否快进  $isFastForward 是否完成 $isSuccess")
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            LogUtils.i("seekBar onStartTrackingTouch [" + this.hashCode() + "] ")
            fromDuration = seekBar!!.progress
            videoPlayer.cancelProgressTimer()
            var vpdown: ViewParent? = videoPlayer.parent
            while (vpdown != null) {
                vpdown.requestDisallowInterceptTouchEvent(true)
                vpdown = vpdown.parent
            }
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            LogUtils.i("seekBar onStopTrackingTouch [" + this.hashCode() + "] ")
            videoPlayer.onEvent(JZUserAction.ON_SEEK_POSITION)
            videoPlayer.startProgressTimer()
            var vpup: ViewParent? = videoPlayer.parent
            while (vpup != null) {
                vpup.requestDisallowInterceptTouchEvent(false)
                vpup = vpup.parent
            }
            if (videoPlayer.currentState != CURRENT_STATE_PLAYING && videoPlayer.currentState != CURRENT_STATE_PAUSE)
                return
            val time = seekBar!!.progress * duration / 100
            JZMediaManager.seekTo(time)
            LogUtils.i("seekTo " + time + " [" + this.hashCode() + "] ")
        }

    }


    /**
     * 手势监听，获取拖进状态
     */
    inner class OnTouchListenerForVideo(private val videoPlayer: VideoPlayer) : OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            val x = event!!.x
            val y = event!!.y
            val id = v!!.id
            if (id == cn.jzvd.R.id.surface_container) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        LogUtils.i("onTouch surfaceContainer actionDown [" + this.hashCode() + "] ")
                        videoPlayer.mTouchingProgressBar = true

                        videoPlayer.mDownX = x
                        videoPlayer.mDownY = y
                        videoPlayer.mChangeVolume = false
                        videoPlayer.mChangePosition = false
                        videoPlayer.mChangeBrightness = false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        LogUtils.i("onTouch surfaceContainer actionMove [" + this.hashCode() + "] ")
                        val deltaX = x - videoPlayer.mDownX
                        var deltaY = y - videoPlayer.mDownY
                        val absDeltaX = Math.abs(deltaX)
                        val absDeltaY = Math.abs(deltaY)
                        if (videoPlayer.currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                            if (!videoPlayer.mChangePosition && !videoPlayer.mChangeVolume && !videoPlayer.mChangeBrightness) {
                                if (absDeltaX > THRESHOLD || absDeltaY > THRESHOLD) {
                                    videoPlayer.cancelProgressTimer()
                                    if (absDeltaX >= THRESHOLD) {
                                        // 全屏模式下的CURRENT_STATE_ERROR状态下,不响应进度拖动事件.
                                        // 否则会因为mediaplayer的状态非法导致App Crash
                                        if (videoPlayer.currentState != CURRENT_STATE_ERROR) {
                                            val duration = videoPlayer.duration
                                            fromDuration = (videoPlayer.mSeekTimePosition * 100 / if (duration == 0L) 1 else duration).toInt()
                                            videoPlayer.mChangePosition = true
                                            videoPlayer.mGestureDownPosition = videoPlayer.currentPositionWhenPlaying
                                        }
                                    } else {
                                        //如果y轴滑动距离超过设置的处理范围，那么进行滑动事件处理
                                        if (videoPlayer.mDownX < videoPlayer.mScreenWidth * 0.5f) {//左侧改变亮度
                                            videoPlayer.mChangeBrightness = true
                                            val lp = JZUtils.getWindow(context).attributes
                                            if (lp.screenBrightness < 0) {
                                                try {
                                                    videoPlayer.mGestureDownBrightness = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS).toFloat()
                                                    LogUtils.i("current system brightness: " + mGestureDownBrightness)
                                                } catch (e: Settings.SettingNotFoundException) {
                                                    e.printStackTrace()
                                                }

                                            } else {
                                                videoPlayer.mGestureDownBrightness = lp.screenBrightness * 255
                                                LogUtils.i("current activity brightness: " + mGestureDownBrightness)
                                            }
                                        } else {//右侧改变声音
                                            videoPlayer.mChangeVolume = true
                                            videoPlayer.mGestureDownVolume = videoPlayer.mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                                        }
                                    }
                                }
                            }
                        }
                        if (videoPlayer.mChangePosition) {
                            val totalTimeDuration = videoPlayer.duration
                            videoPlayer.mSeekTimePosition = (videoPlayer.mGestureDownPosition + deltaX * totalTimeDuration / videoPlayer.mScreenWidth).toInt().toLong()
                            if (videoPlayer.mSeekTimePosition > totalTimeDuration)
                                videoPlayer.mSeekTimePosition = totalTimeDuration
                            val seekTime = JZUtils.stringForTime(videoPlayer.mSeekTimePosition)
                            val totalTime = JZUtils.stringForTime(totalTimeDuration)

                            videoPlayer.showProgressDialog(deltaX, seekTime, videoPlayer.mSeekTimePosition, totalTime, totalTimeDuration)
                        }
                        if (videoPlayer.mChangeVolume) {
                            deltaY = -deltaY
                            val max = videoPlayer.mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                            val deltaV = (max.toFloat() * deltaY * 3f / videoPlayer.mScreenHeight).toInt()
                            videoPlayer.mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, videoPlayer.mGestureDownVolume + deltaV, 0)
                            //dialog中显示百分比
                            val volumePercent = (videoPlayer.mGestureDownVolume * 100 / max + deltaY * 3f * 100f / videoPlayer.mScreenHeight).toInt()
                            videoPlayer.showVolumeDialog(-deltaY, volumePercent)
                        }

                        if (videoPlayer.mChangeBrightness) {
                            deltaY = -deltaY
                            val deltaV = (255f * deltaY * 3f / videoPlayer.mScreenHeight).toInt()
                            val params = JZUtils.getWindow(context).attributes
                            if ((videoPlayer.mGestureDownBrightness + deltaV) / 255 >= 1) {//这和声音有区别，必须自己过滤一下负值
                                params.screenBrightness = 1f
                            } else if ((videoPlayer.mGestureDownBrightness + deltaV) / 255 <= 0) {
                                params.screenBrightness = 0.01f
                            } else {
                                params.screenBrightness = (videoPlayer.mGestureDownBrightness + deltaV) / 255
                            }
                            JZUtils.getWindow(context).attributes = params
                            //dialog中显示百分比
                            val brightnessPercent = (videoPlayer.mGestureDownBrightness * 100 / 255 + deltaY * 3f * 100f / videoPlayer.mScreenHeight).toInt()
                            videoPlayer.showBrightnessDialog(brightnessPercent)
                            //                        mDownY = y;
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        LogUtils.i("onTouch surfaceContainer actionUp [" + this.hashCode() + "] ")
                        videoPlayer.mTouchingProgressBar = false
                        videoPlayer.dismissProgressDialog()
                        videoPlayer.dismissVolumeDialog()
                        videoPlayer.dismissBrightnessDialog()
                        if (videoPlayer.mChangePosition) {
                            videoPlayer.onEvent(JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION)
                            JZMediaManager.seekTo(videoPlayer.mSeekTimePosition)
                            val duration = videoPlayer.duration
                            val progress = (videoPlayer.mSeekTimePosition * 100 / if (duration == 0L) 1 else duration).toInt()
                            if (progress > fromDuration) {
                                LogUtils.i("向前拖动进度条")
                                isFastForward = true
                            } else if (progress < fromDuration) {
                                LogUtils.i("向后拖动进度条")
                            }
                            videoPlayer.progressBar.progress = progress
                        }
                        if (videoPlayer.mChangeVolume) {
                            videoPlayer.onEvent(JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME)
                        }
                        videoPlayer.startProgressTimer()

                        videoPlayer.startDismissControlViewTimer()
                        if (!videoPlayer.mChangePosition && !videoPlayer.mChangeVolume) {
                            videoPlayer.onEvent(JZUserActionStandard.ON_CLICK_BLANK)
                            videoPlayer.onClickUiToggle()
                        }
                    }
                }
            } else if (id == cn.jzvd.R.id.bottom_seek_progress) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> videoPlayer.cancelDismissControlViewTimer()
                    MotionEvent.ACTION_UP -> videoPlayer.startDismissControlViewTimer()
                }
            }
            return false
        }
    }

}