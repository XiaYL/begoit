package com.begoit.mooc.offline.ui.activity.searchCourse;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseActivity;
import com.begoit.mooc.offline.entity.course.course_list.CourseListItemEntity;
import com.begoit.mooc.offline.ui.activity.coursedetail.CourseDetailActivity;
import com.begoit.mooc.offline.ui.adapter.KindCoursesAdapter;
import com.begoit.mooc.offline.utils.KeyboardUtils;
import com.begoit.mooc.offline.utils.ToastUtil;
import com.begoit.mooc.offline.widget.BaseEmptyView;
import com.begoit.mooc.offline.widget.LoadingProgressDialog;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description:课程搜索
 * @Author:gxj
 * @Time 2019/3/8
 */

public class SearchCourseActivity extends BaseActivity<SearchCoursesPresenter,SearchCoursesModelimpl>
        implements SearchCourseContract.View,KindCoursesAdapter.OnCourseItemClickListener  {
    //分页加载控件
    @BindView(R.id.refresh_layout)
    TwinklingRefreshLayout mTwinklingRefreshLayout;
    //课程列表
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_search_view)
    EditText searchView;
    private int pageNum = 1;//页码标识
    //异常页面
    @BindView(R.id.empty)
    BaseEmptyView emptyView;
    //课程列表适配器
    KindCoursesAdapter coursesAdapter;

    //点击事件处理
    @OnClick(R.id.iv_back)
    public void onBackClick(){
        finish();
    }
    //当前搜索关键字
    private String currentkeyWord;
    @Override
    protected int getLyoutId() {
        return R.layout.activity_search_course;
    }

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this,mModel);
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtils.showSoftInput(SearchCourseActivity.this);
            }
        },500);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pageNum = 1;
                if (!TextUtils.isEmpty(s.toString())) {
                    doSearchWithKey(s.toString());
                }
            }
        });

        mTwinklingRefreshLayout.setHeaderView(new ProgressLayout(mContext));
        mTwinklingRefreshLayout.setFloatRefresh(true);
        mTwinklingRefreshLayout.setEnableLoadmore(true);
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                doSearchWithKey(currentkeyWord);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                doSearchWithKey(currentkeyWord);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy != 0) {
                    KeyboardUtils.hideSoftInput(SearchCourseActivity.this);
                }
            }
        });
    }

    private Map<String,String> params;
    private void doSearchWithKey(String key){
        currentkeyWord = key;
        params = new HashMap();
        params.put("keyWord",key);
        params.put("pageNum",String.valueOf(pageNum));
        params.put("pageSize",String.valueOf(10));
        mPresenter.doSearch(params);
    }

    @Override
    public void showLoading() {
        LoadingProgressDialog.showLoading(this,"加载中");
    }

    @Override
    public void cancelLoading() {
        LoadingProgressDialog.stopLoading();
        mTwinklingRefreshLayout.finishRefreshing();
        mTwinklingRefreshLayout.finishLoadmore();
    }

    @Override
    public void showCourse(List<CourseListItemEntity> courseList) {
        if (coursesAdapter == null){
            coursesAdapter = new KindCoursesAdapter(courseList,mContext);
            coursesAdapter.setOnCourseItemClickListener(this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2,GridLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(coursesAdapter);
        }else {
            if (pageNum > 1){
                coursesAdapter.addData(courseList);
            }else {
                coursesAdapter.setNewData(courseList);
            }
        }
        if (courseList != null && courseList.size() > 0) {
            pageNum++;
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyboardUtils.hideSoftInput(SearchCourseActivity.this);
    }

    /**
     * 异常页面展示
     * @param icon   图标
     * @param msg   提示语
     */
    @Override
    public void showErrorView(int icon, String msg) {
        ToastUtil.showShortToast(msg);
        if (pageNum > 1){
            emptyView.setVisibility(View.GONE);
            return;
        }
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setIcon(icon);
        emptyView.setContent(msg);
    }

    @Override
    public void onCourseClick(CourseListItemEntity entity) {
        Intent intent = new Intent(mContext, CourseDetailActivity.class);
        intent.putExtra("courseId",entity.id);
        startActivity(intent);
    }
}
