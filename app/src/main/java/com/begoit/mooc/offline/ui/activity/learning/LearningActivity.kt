package com.begoit.mooc.offline.ui.activity.learning

import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.begoit.mooc.offline.R
import com.begoit.mooc.offline.base.BaseActivity
import com.begoit.mooc.offline.entity.course.course_detail.course_files.ArticleClassEntity
import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesEntity
import com.begoit.mooc.offline.entity.course.course_detail.course_files.FileInformationEntity
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideoTestEntity
import com.begoit.mooc.offline.entity.course.course_detail.course_files.VideonobEntity
import com.begoit.mooc.offline.requests.ApiConstants
import com.begoit.mooc.offline.ui.activity.learning.courseTreeListDataBind.ArtcleClassDataBinder
import com.begoit.mooc.offline.ui.activity.learning.event.ShowScoreEvent
import com.begoit.mooc.offline.ui.activity.learning.event.TestAginOrWatchVideoEvent
import com.begoit.mooc.offline.ui.fragment.videoTest.MakeTestFragment
import com.begoit.mooc.offline.ui.fragment.videoTest.ShowTestScoreFragment
import com.begoit.mooc.offline.widget.LoadingProgressDialog
import com.begoit.mooc.offline.widget.imageView.GlideCircleTransform
import com.begoit.mooc.offline.widget.videoview.VideoPlayer
import com.begoit.treerecyclerview.adpater.TreeRecyclerAdapter
import com.begoit.treerecyclerview.adpater.TreeRecyclerType
import com.begoit.treerecyclerview.factory.ItemHelperFactory
import com.begoit.treerecyclerview.item.TreeItemGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import butterknife.BindView
import butterknife.OnClick
import cn.jzvd.JZUtils
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import cn.jzvd.JZVideoPlayer.CURRENT_STATE_PLAYING
import com.begoit.mooc.offline.ui.activity.learning.event.WatchVideoEvent
import com.begoit.mooc.offline.ui.activity.success.CourseSuccessListActivity
import com.begoit.mooc.offline.utils.*
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

/**
 * @Description:学习页面
 * @Author:gxj
 * @Time 2019/3/1
 */

class LearningActivity : BaseActivity<LearningPresenter, LearningModelimpl>(), LearningContract.View {
    //标题
    @BindView(R.id.tv_title)
    @JvmField
    var tvTitle: TextView? = null
    //课程章节及视频列表
    @BindView(R.id.recyclerView)
    @JvmField
    var mRecyclerView: RecyclerView? = null
    //根据item的状态展示,可折叠  课程章节多级列表
    private var treeRecyclerAdapter: TreeRecyclerAdapter? = null
    //视频播放器
    @BindView(R.id.video_view)
    @JvmField
    var videoPlayer: VideoPlayer? = null
    @BindView(R.id.iv_video_placeImg)
    @JvmField
    var ivVideoPlaceImg: ImageView? = null
    //测试题
    @BindView(R.id.fl_test_container)
    @JvmField
    var testContainer: FrameLayout? = null
    //总分
    @BindView(R.id.tv_sum_score)
    @JvmField
    var tvSumScore: TextView? = null
    //当前课程ID
    private var currentCourseId: String? = null
    private var preImgFileid: String? = null //课程预览图
    //当前课程
    private var currentCourseDetailFilesEntity: CourseDetailFilesEntity? = null
    //当前正在看的视频id,避免复选
    private var currentVideoId = ""
    private var currentVideo: VideonobEntity? = null//当前正在播放的视频
    //当前做的试题,避免复选
    private var currentTest: VideonobEntity? = null
    //标识在当前页面有没有播放过视频，未播放过不调用JZVideoPlayer.goOnPlayOnResume(); 解决课程详情页视频播放中进入此页面时打开前一页面视频的问题
    private var isVideoPlayed = false

    @OnClick(R.id.iv_back)
    fun clickBack() {
        finish()
    }

    @OnClick(R.id.tv_score_detail)
    fun toDetail() {
        intent = Intent(this, CourseSuccessListActivity::class.java)
        intent.putExtra("channelId", currentCourseId)
        startActivity(intent)
    }

    override fun getLyoutId(): Int {
        return R.layout.activity_course_learning_detail
    }

    override fun initPresenter() {
        mPresenter.attachView(this, mModel)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        currentCourseId = intent.getStringExtra("currentId")
        preImgFileid = intent.getStringExtra("preImgFileid")
        mPresenter.getVideos(currentCourseId)
        JZUtils.clearSavedProgress(this, null)
    }

    /**
     * 初始化播放器View
     */
    private fun initVideoView(currentCourse: CourseDetailFilesEntity) {
        videoPlayer!!.reSetViews(videoPlayer!!)
        if (TextUtils.isEmpty(currentCourse.preVideoFileid)) {
            ivVideoPlaceImg!!.visibility = View.VISIBLE
            videoPlayer!!.visibility = View.GONE
            if (TextUtils.isEmpty(preImgFileid)) {
                ivVideoPlaceImg!!.setImageResource(ImagePlaceHolderUtil.getPlaceholderPreImg(currentCourse.id))
            } else {
                Glide.with(this)
                        .load(ApiConstants.getFileUrl(preImgFileid))
                        .apply(RequestOptions
                                .bitmapTransform(GlideCircleTransform(mContext, 1f)))
                        .into(ivVideoPlaceImg!!)
            }
        } else {
            ivVideoPlaceImg!!.visibility = View.GONE
            videoPlayer!!.visibility = View.VISIBLE
            videoPlayer?.thumbImageView?.scaleType = ImageView.ScaleType.FIT_XY
            if (TextUtils.isEmpty(preImgFileid)) {
                videoPlayer!!.thumbImageView.setImageResource(ImagePlaceHolderUtil.getPlaceholderPreImg(currentCourse.id))
            } else {
                Glide.with(this)
                        .load(ApiConstants.getFileUrl(preImgFileid))
                        .apply(RequestOptions
                                .bitmapTransform(GlideCircleTransform(mContext, 1f)))
                        .into(videoPlayer!!.thumbImageView)
            }
            val preVideoEntity: FileInformationEntity = mModel.getCurrentVideo(currentCourse.preVideoFileid)
            var videoPath = ""
            if (preVideoEntity != null) {
                videoPath = FileHelper.getCourseFilePath(currentCourseId, preVideoEntity.fileName)
            }
            videoPlayer?.setUp(videoPath, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL)
        }

    }


        override fun showLoading() {
            LoadingProgressDialog.showLoading(this, "加载中")
        }

        override fun cancelLoading() {
            LoadingProgressDialog.stopLoading()
        }

        override fun showVideos(currentCourse: CourseDetailFilesEntity) {
            initVideoView(currentCourse)
            currentCourseDetailFilesEntity = currentCourse
            tvTitle!!.text = currentCourse.channelName
            setArtcleClass(currentCourse)

        }

        private fun setArtcleClass(currentCourse: CourseDetailFilesEntity) {
            treeRecyclerAdapter = TreeRecyclerAdapter(TreeRecyclerType.SHOW_DEFAULT)
            mRecyclerView!!.layoutManager = LinearLayoutManager(this)
            mRecyclerView!!.itemAnimator = DefaultItemAnimator()
            mRecyclerView!!.adapter = treeRecyclerAdapter
            refresh(currentCourse.getArticleClass())
            tvSumScore!!.text = "总分：" + mPresenter.getSumScore(currentCourse)
        }

        private fun refresh(articleClass: List<ArticleClassEntity>) {
            ItemHelperFactory.createTreeItemList(articleClass, ArtcleClassDataBinder::class.java, null)
            //创建item
            val items = ItemHelperFactory.createItems(articleClass, null)
            //遍历设置展开状态
            for (item in items) {
                if (item is TreeItemGroup<*>) {
                    item.isExpand = false
                }
            }
            //添加到adapter
            treeRecyclerAdapter!!.itemManager.replaceAllItem(items)
        }

        /**
         * 课程列表视频或测试题点击时调用
         * @param videonobEntity
         */
        @Subscribe(threadMode = ThreadMode.MAIN)
        fun onEventMainThread(videonobEntity: VideonobEntity?) {
            if (videonobEntity!!.isVideo == 0) {
                if (!isFinishing && videonobEntity != null) {
                    checkVideoOrTest(true)
                    currentVideo = videonobEntity
                    if (currentVideoId != videonobEntity.videoFileId) {
                        mPresenter.getCurrentVideo(videonobEntity)
                    } else {
                        videoPlayer!!.startButton.performClick()
                    }
                }
            } else {
                if (!isFinishing && videonobEntity != null) {
                    if (currentTest == null || currentTest!!.id != videonobEntity.id) {
                        mPresenter.getTestList(currentCourseDetailFilesEntity!!.limitVideoTestNum, videonobEntity.id.substring(0, videonobEntity.id.indexOf("*")), videonobEntity)
                    }
                }
            }
        }

        /**
         * 提交考核核算分数后调用，展示得分情况
         * @param event
         */
        @Subscribe(threadMode = ThreadMode.MAIN)
        fun onEventMainThread(event: ShowScoreEvent) {
            mPresenter.setScore(null, currentCourseDetailFilesEntity, event, false)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_test_container, ShowTestScoreFragment(event.testList, currentCourseId, event.hasScore, event.totalScore))
            transaction.commit()
            tvSumScore!!.text = "总分：" + mPresenter.getSumScore(currentCourseDetailFilesEntity)
        }


        /**
         * 继续学习或重新测试
         * @param event
         */
        @Subscribe(threadMode = ThreadMode.MAIN)
        fun onEventMainThread(event: TestAginOrWatchVideoEvent) {
            if (event.isTestAgin) {
                if (currentTest != null) {
                    mPresenter.getTestList(currentCourseDetailFilesEntity!!.limitVideoTestNum, currentTest!!.id.substring(0, currentTest!!.id.indexOf("*")), currentTest)
                }
            } else {
                checkVideoOrTest(true)
            }
        }

        /**
         * 视频学习进度
         */
        @Subscribe(threadMode = ThreadMode.MAIN)
        fun onEventMainThread(enent: WatchVideoEvent) {
            if (enent.targetVideo?.id.equals(currentVideo?.id)) {
                AppLogUtil.setLog(AppLogUtil.TYPE_LEARNING_VIDEO, currentCourseDetailFilesEntity!!.channelName, enent.targetVideo!!.videoTitle)
                mPresenter.setScore(enent.targetVideo!!.id, currentCourseDetailFilesEntity, null, true)
                tvSumScore!!.text = "总分：" + mPresenter.getSumScore(currentCourseDetailFilesEntity)
            }
        }

        override fun playVideo(fileInformationEntity: FileInformationEntity, videonobEntity: VideonobEntity) {
            if (fileInformationEntity == null) {
                ToastUtil.showShortToast("视频文件异常")
                return
            }
            ivVideoPlaceImg!!.visibility = View.GONE
            videoPlayer!!.visibility = View.VISIBLE
            isVideoPlayed = true
            KeyboardUtils.hideSoftInput(this)
            currentVideoId = fileInformationEntity.fileId
            playVideo(fileInformationEntity.fileName, videonobEntity)
        }

        override fun makeTest(testList: List<VideoTestEntity>?, videonobEntity: VideonobEntity) {
            if (testList != null) {
                if (testList.isNotEmpty()) {
                    KeyboardUtils.hideSoftInput(this)
                    if (videoPlayer!!.currentState == CURRENT_STATE_PLAYING) {
                        videoPlayer!!.startButton.performClick()
                    }
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fl_test_container, MakeTestFragment(testList, currentCourseId))
                    transaction.commit()
                    currentTest = videonobEntity
                    checkVideoOrTest(false)
                } else {
                    ToastUtil.showShortToast("没有测试题目")
                }
            } else {
                ToastUtil.showShortToast("已达到最大考核次数")
            }

        }

        /**
         * 播放指定视频
         * @param videoName
         */
        private fun playVideo(videoName: String, videonobEntity: VideonobEntity) {
            requestPermission(com.yanzhenjie.permission.Permission.STORAGE)
            val path = FileHelper.getCourseFilePath(currentCourseId, videoName)
            videoPlayer!!.setUp(path, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, path)
            videoPlayer!!.playVideo(getVideoLength(), videonobEntity)
        }

        /**
         * 1. 视频观看超过指定时间点才算完成,快进不算.完成之后才记录观看成绩
         * 2. 根据视频长度生成一个随机数,此数为合格时长
         */
        private fun getVideoLength(): Long {
            var number = 30//随机记录时间(视频长度单位为秒)
            if (Integer.parseInt(currentVideo!!.getVideoLength()) != 0) {
                number = Random().nextInt(Integer.parseInt(currentVideo!!.getVideoLength())) - 9
                if (number < 30 && Integer.parseInt(currentVideo!!.getVideoLength()) > 30) {
                    number = 30
                }
            }
            return number.toLong()
        }

        /**
         * 切换视频和考核页面
         * @param isPlaying
         */
        private fun checkVideoOrTest(isPlaying: Boolean) {
            if (isPlaying) {
                currentTest = null
                videoPlayer!!.visibility = View.VISIBLE
                testContainer!!.visibility = View.GONE
            } else {
                videoPlayer!!.visibility = View.GONE
                testContainer!!.visibility = View.VISIBLE
            }
        }

        override fun onResume() {
            super.onResume()
            if (isVideoPlayed) {
                JZVideoPlayer.goOnPlayOnResume()
            }
        }

        override fun onPause() {
            super.onPause()
            JZVideoPlayer.goOnPlayOnPause()
        }

        override fun onBackPressed() {
            if (JZVideoPlayer.backPress()) {
                return
            }
            super.onBackPressed()
        }

        override fun onDestroy() {
            super.onDestroy()
            JZVideoPlayer.releaseAllVideos()
            EventBus.getDefault().unregister(this)
        }
    }
