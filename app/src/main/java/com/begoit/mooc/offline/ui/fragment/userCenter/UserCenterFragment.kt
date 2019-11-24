package com.begoit.mooc.offline.ui.fragment.userCenter

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.begoit.mooc.BegoitMoocApplication
import com.begoit.mooc.offline.R
import com.begoit.mooc.offline.base.BaseFragment
import com.begoit.mooc.offline.entity.user.UserEntity
import com.begoit.mooc.offline.event.NetChangeEvent
import com.begoit.mooc.offline.event.UploadingStudyLogsEvent
import com.begoit.mooc.offline.ui.activity.success.CourseSuccessListActivity
import com.begoit.mooc.offline.utils.ActivityUtils
import com.begoit.mooc.offline.utils.AppLogUtil
import com.begoit.mooc.offline.utils.NetworkUtils
import com.begoit.mooc.offline.utils.StudyLogUploadUtil
import com.begoit.mooc.offline.utils.ToastUtil
import com.begoit.mooc.offline.widget.LoadingProgressDialog
import com.begoit.mooc.offline.widget.basedialog.DialogUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import butterknife.BindView
import butterknife.OnClick
import com.begoit.mooc.db.*
import com.begoit.mooc.offline.utils.db.DaoManager

/**
 * @Description:用户个人中心
 * @Author:gxj
 * @Time 2019/3/1
 */

class UserCenterFragment : BaseFragment<UserCenterPresenter, UsercenterModelimpl>(), UserCenterContract.View {
    //用户名
    @BindView(R.id.tv_user_name)
    @JvmField
    var userName: TextView? = null
    @BindView(R.id.iv_upload)
    @JvmField
    var ivIpload: ImageView? = null
    @BindView(R.id.tv_upload)
    @JvmField
    var tvUpload: TextView? = null
    @BindView(R.id.progress)
    @JvmField
    var progressBar: ProgressBar? = null
    //当前登录用户
    var mUserEntity: UserEntity? = null
    var isUnBinding: Boolean = false

    @OnClick(R.id.tv_user_success, R.id.tv_use_page, R.id.tv_about, R.id.tv_unbind, R.id.tv_check_version, R.id.tv_exit, R.id.upload_learned)
    fun clickUsePage(view: View) {
        val intent: Intent
        when (view.id) {
            R.id.tv_exit -> {
                ActivityUtils.logout(null)
                AppLogUtil.setLog(AppLogUtil.TPYE_SIGN_OUT)
            }
            R.id.tv_user_success -> {
                intent = Intent(activity, CourseSuccessListActivity::class.java)
                startActivity(intent)
            }
            R.id.upload_learned -> if (NetworkUtils.isAvailable()) {
                StudyLogUploadUtil.upload(mContext)
            } else {
                ToastUtil.showShortToast("当前网络不可用，请检查后重试")
            }
            R.id.tv_unbind -> if (NetworkUtils.isAvailable())
                DialogUtils.getInstance()
                        .showGenericDialogForTwoButtonCommon(mContext, "解绑", "解绑后，您将无法在此设备上进行学习", "取消", "解绑") { view ->
                            if (view.id == R.id.tv_sure) {
                                isUnBinding = true
                                StudyLogUploadUtil.upload(mContext)
                            }
                        }
            else
                ToastUtil.showShortToast("请将设备连接到服务器")

            else -> ToastUtil.showShortToast("功能暂未开放")
        }
    }

    override fun getLyoutId(): Int {
        return R.layout.fragment_user_center
    }

    override fun onVisible() {
        setUploadStatus()
    }

    /**
     * 网络切换事件执行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: NetChangeEvent) {
        setUploadStatus()
    }

    /**
     * 学习记录上传事件执行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: UploadingStudyLogsEvent) {
        if (isUnBinding) {
            when (event.isSuccess) {
                0 -> DialogUtils.getInstance().showGenericDialogForNoButtonCommon(mContext, "解绑中", "正在上传学习记录", true)
                1 -> {
                    DialogUtils.getInstance().showGenericDialogForNoButtonCommon(mContext, "解绑中", "正在解绑", true)
                    mPresenter.unbind()
                    isUnBinding = false
                }
                2 -> {
                    DialogUtils.getInstance().dismissDialog()
                    isUnBinding = false
                }
            }
        } else {
            setUploadStatus()
        }
    }

    /**
     * 设置上传按钮状态
     */
    private fun setUploadStatus() {
        if (ivIpload != null && tvUpload != null) {
            if (NetworkUtils.isConnected()) {
                ivIpload!!.setImageResource(R.mipmap.ic_upload_hasnew)
                tvUpload!!.setTextColor(Color.parseColor("#F5A624"))
                if (StudyLogUploadUtil.isDoUpload) {
                    ivIpload!!.visibility = View.INVISIBLE
                    progressBar!!.visibility = View.VISIBLE
                } else {
                    ivIpload!!.visibility = View.VISIBLE
                    progressBar!!.visibility = View.INVISIBLE
                }
            } else {
                ivIpload!!.setImageResource(R.mipmap.ic_upload_normal)
                tvUpload!!.setTextColor(Color.parseColor("#8A8A8A"))
            }
        }
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        mUserEntity = BegoitMoocApplication.contextInstance!!.getCurrentUser()
        if (userName != null && mUserEntity != null) {
            userName!!.text = mUserEntity!!.getUserName()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun initPresenter() {
        mPresenter.attachView(this, mModel)
    }

    /**
     * 根据服务器返回情况处理
     */
    override fun unbindStatus(status: Int) {
        when (status) {
            1 -> {
                DialogUtils.getInstance().dismissDialog()
                ToastUtil.showShortToast("解绑成功")
                removeUserData()
                ActivityUtils.logout(null)
            }
            3 -> {
                DialogUtils.mInstance.showGenericDialogForOneButtonCommon(mContext, "无法解绑", "服务端尚未解绑，请先联系管理员", "知道了")
            }
            else -> {
                DialogUtils.getInstance().dismissDialog()
            }
        }
    }

    /**
     * 删除用户数据
     */
    fun removeUserData() {
        //删除此用户
        DaoManager.getInstance().daoSession.userEntityDao.queryBuilder()
                .where(UserEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.contextInstance.getCurrentAccound()))
                .buildDelete().executeDeleteWithoutDetachingEntities()
        //删除已选课程
        DaoManager.getInstance().daoSession.userChannelEntityDao.queryBuilder()
                .where(UserChannelEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.contextInstance.getCurrentAccound()))
                .buildDelete().executeDeleteWithoutDetachingEntities()
        //删除视频与测试题分数
        DaoManager.getInstance().daoSession.videoTestScoreEntityDao.queryBuilder()
                .where(VideoTestScoreEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.contextInstance.getCurrentAccound()))
                .buildDelete().executeDeleteWithoutDetachingEntities()
        //删除测试题日志
        DaoManager.getInstance().daoSession.videoTestLogEntityDao.queryBuilder()
                .where(VideoTestLogEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.contextInstance.getCurrentAccound()))
                .buildDelete().executeDeleteWithoutDetachingEntities()
        //删除操作日志
        DaoManager.getInstance().daoSession.appLogEntityDao.queryBuilder()
                .where(AppLogEntityDao.Properties.UserAccount.eq(BegoitMoocApplication.contextInstance.getCurrentAccound()))
                .buildDelete().executeDeleteWithoutDetachingEntities()

        DaoManager.getInstance().daoSession.clear()
    }

    override fun showLoading() {
//        LoadingProgressDialog.showLoading(mContext, "正在解绑，请稍后")
    }

    override fun cancelLoading() {
//        LoadingProgressDialog.stopLoading()
    }

    companion object {
        //获取实体
        fun newInstance(): UserCenterFragment {
            val bundle = Bundle()
            val fragment = UserCenterFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
