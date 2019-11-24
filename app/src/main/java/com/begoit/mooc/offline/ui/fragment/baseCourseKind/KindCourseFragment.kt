package com.begoit.mooc.offline.ui.fragment.baseCourseKind

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.begoit.mooc.offline.R
import com.begoit.mooc.offline.base.BaseFragment
import com.begoit.mooc.offline.entity.course.course_list.CourseListItemEntity
import com.begoit.mooc.offline.entity.kindMenu.MenuItemEntity
import com.begoit.mooc.offline.ui.activity.coursedetail.CourseDetailActivity
import com.begoit.mooc.offline.ui.adapter.KindCoursesAdapter
import com.begoit.mooc.offline.utils.NetworkUtils
import com.begoit.mooc.offline.widget.BaseEmptyView
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout

import java.util.HashMap
import butterknife.BindView

/**
 * @Description:选定类别课程展示
 * @Author:gxj
 * @Time 2019/3/4
 */

class KindCourseFragment : BaseFragment<KindCoursePresenter, KindCourseModelimpl>(), KindCourseContract.View, KindCoursesAdapter.OnCourseItemClickListener {
    //数据列表
    @BindView(R.id.recyclerView)
    @JvmField var recyclerView: RecyclerView? = null
    //分页加载控件
    @BindView(R.id.refresh_layout)
    @JvmField var mTwinklingRefreshLayout: TwinklingRefreshLayout? = null
    private var pageNum = 1
    @BindView(R.id.empty)
    @JvmField var emptyView: BaseEmptyView? = null
    //课程列表适配器
    var coursesAdapter: KindCoursesAdapter? = null
    //当前
    private var currentItem: MenuItemEntity? = null


    override fun getLyoutId(): Int {
        return R.layout.fragment_kind_courses
    }

    override fun initView() {
        currentItem = arguments!!.getParcelable("item")
        recyclerView!!.isNestedScrollingEnabled = false
        mTwinklingRefreshLayout!!.setHeaderView(ProgressLayout(mContext))
        mTwinklingRefreshLayout!!.setFloatRefresh(true)
        if (currentItem!!.typeName == "推荐课程") {
            mTwinklingRefreshLayout!!.setEnableLoadmore(false)
        } else {
            mTwinklingRefreshLayout!!.setEnableLoadmore(true)
        }
        mTwinklingRefreshLayout!!.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                pageNum = 1
                loadData()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                loadData()
            }
        })
        if (NetworkUtils.isAvailable()) {
            mTwinklingRefreshLayout!!.startRefresh()
        } else {
            showErrorView(R.mipmap.ic_no_net, "当前为离线状态")
        }
    }

    private fun loadData() {
        if (currentItem!!.typeName == "推荐课程") {
            mPresenter.getRemoteRecommendCourses()
        } else {
            val params = HashMap<String, String>()
            params.put("typeId", currentItem!!.getId())
            params.put("pageNum", pageNum.toString())
            params.put("pageSize", 10.toString())
            mPresenter.getCourses(params)
        }
    }

    override fun initPresenter() {
        mPresenter.attachView(this, mModel)
    }

    override fun showLoading() {

    }

    override fun cancelLoading() {
        endLoad()
    }

    private fun endLoad() {
        if (!isDetached) {
            mTwinklingRefreshLayout!!.finishRefreshing()
            mTwinklingRefreshLayout!!.finishLoadmore()
        }
    }

    override fun showCourses(courseList: List<CourseListItemEntity>) {
        if (coursesAdapter == null) {
            coursesAdapter = KindCoursesAdapter(courseList, mContext)
            coursesAdapter!!.setOnCourseItemClickListener(this)
            val gridLayoutManager = GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false)
            recyclerView!!.layoutManager = gridLayoutManager
            recyclerView!!.adapter = coursesAdapter
        } else {
            if (pageNum > 1) {
                coursesAdapter!!.addData(courseList)
            } else {
                coursesAdapter!!.setNewData(courseList)
            }
        }
        if (courseList != null && courseList.isNotEmpty()) {
            pageNum++
            emptyView!!.visibility = View.GONE
        }
    }

    /**
     * 异常页面展示
     * @param icon   图标
     * @param msg   提示语
     */
    override fun showErrorView(icon: Int, msg: String) {
        if (coursesAdapter != null && coursesAdapter!!.data.size > 0) {
            emptyView!!.visibility = View.GONE
            return
        }

        if (currentItem!!.typeName == "推荐课程" && !NetworkUtils.isAvailable()) {
            emptyView!!.visibility = View.VISIBLE
            emptyView!!.setIcon(R.mipmap.ic_no_net)
            emptyView!!.setContent("当前为离线状态")
            return
        }

        emptyView!!.visibility = View.VISIBLE
        emptyView!!.setIcon(icon)
        emptyView!!.setContent(msg)
    }

    override fun onCourseClick(entity: CourseListItemEntity) {
        val intent = Intent(mContext, CourseDetailActivity::class.java)
        intent.putExtra("courseId", entity.id)
        startActivity(intent)
    }

    companion object {

        //获取实体
        fun newInstance(currentItem: MenuItemEntity): KindCourseFragment {
            val bundle = Bundle()
            val fragment = KindCourseFragment()
            bundle.putParcelable("item", currentItem)
            fragment.arguments = bundle
            return fragment
        }
    }
}
