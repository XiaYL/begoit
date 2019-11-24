package com.begoit.mooc.offline.ui.activity.success

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.begoit.mooc.offline.R
import com.begoit.mooc.offline.base.BaseActivity
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity
import com.begoit.mooc.offline.ui.adapter.CourseSuccessListAdapter
import com.begoit.mooc.offline.widget.BaseEmptyView
import com.begoit.mooc.offline.widget.LoadingProgressDialog
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout

/**
 *@Description:用户成绩列表
 *@Author:gxj
 *@Time 2019/5/22
 */
class CourseSuccessListActivity: BaseActivity<CourseSuccessListPresenter,CourseSuccessListModel>(),CourseSuccessListContract.view {
    //标题
    @BindView(R.id.tv_title)
    @JvmField var title: TextView? = null
    //分页加载控件
    @BindView(R.id.refresh_layout)
    @JvmField var refreshLayout: TwinklingRefreshLayout? = null
    //列表控件
    @BindView(R.id.recyclerView)
    @JvmField var recyclerView: RecyclerView? = null
    //异常页面
    @BindView(R.id.empty)
    @JvmField var emptyView: BaseEmptyView? = null

    private var listAdapter: CourseSuccessListAdapter? = null
    //当前课程ID
    private var currentCourseId: String? = null//用于加载指定课程成绩
    //退出页面
    @OnClick(R.id.iv_back)
     fun clickBack() {
        finish()
    }

    override fun getLyoutId(): Int {
        return R.layout.activity_success_list
    }

    override fun initPresenter() {
        mPresenter.attachView(this,mModel)
    }

    override fun initView() {
        currentCourseId = intent.getStringExtra("channelId")
        title?.text = "我的成绩"
        refreshLayout!!.setHeaderView(ProgressLayout(mContext))
        refreshLayout!!.setFloatRefresh(false)
        refreshLayout!!.setEnableLoadmore(false)
        refreshLayout!!.setEnableRefresh(false)
        mPresenter.getSuccessList(currentCourseId)

    }

    override fun showLoading() {
        LoadingProgressDialog.showLoading(this,"加载中...")
    }

    override fun cancelLoading() {
        LoadingProgressDialog.stopLoading()
    }

    override fun showList(channelEntityList: List<UserChannelEntity>?) {
        if (listAdapter == null){
            listAdapter = CourseSuccessListAdapter(channelEntityList)
            var layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = VERTICAL
            recyclerView?.layoutManager = layoutManager
            recyclerView?.adapter = listAdapter
        }else {
            listAdapter!!.setNewData(channelEntityList)
        }
        showErrorView(R.mipmap.ic_empty_search, "暂无成绩，快去学习吧！")
    }

    override fun showErrorView(icon: Int, msg: String) {
        if (listAdapter != null && listAdapter!!.data.size > 0) {
            emptyView!!.visibility = View.GONE
            return
        }
        emptyView!!.visibility = View.VISIBLE
        emptyView!!.setIcon(icon)
        emptyView!!.setContent(msg)
    }


}