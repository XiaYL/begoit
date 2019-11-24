package com.begoit.mooc.offline.ui.fragment.courseSummary;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseFragment;
import com.begoit.mooc.offline.utils.FormatStringUtils;
import com.begoit.mooc.offline.widget.BaseEmptyView;

import butterknife.BindView;

/**
 * @Description:课程简介、所需知识、教学大纲
 * @Author:gxj
 * @Time 2019/2/27
 */

public class CourseSummaryFragment extends BaseFragment {
    //异常页面
    @BindView(R.id.empty)
    BaseEmptyView emptyView;
    //带标签内容展示
    @BindView(R.id.tv_summary)
    WebView tvSummary;
    private String summary;
    @Override
    protected int getLyoutId() {
        return R.layout.fragment_course_summary;
    }

    public void setTvSummary(String summary){
        if (emptyView != null) {
            if (TextUtils.isEmpty(summary)) {
                emptyView.setVisibility(View.VISIBLE);
                return;
            }
            emptyView.setVisibility(View.GONE);
        }
        this.summary = FormatStringUtils.fillAllHtmlForImgFromNet(summary);
        if (tvSummary != null) {
            if (this.summary == null){
                tvSummary.loadData("", "text/html; charset=UTF-8" , null);
            }else {
                tvSummary.loadData(this.summary, "text/html; charset=UTF-8" , null);
            }
        }
    }


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        if (emptyView != null) {
            if (TextUtils.isEmpty(summary)) {
                emptyView.setVisibility(View.VISIBLE);
                return;
            }
            emptyView.setVisibility(View.GONE);
        }
        if (tvSummary != null) {
            if (summary == null){
                tvSummary.loadData("", "text/html; charset=UTF-8" , null);
            }else {
                tvSummary.loadData(summary, "text/html; charset=UTF-8" , null);
            }
        }
    }

}
