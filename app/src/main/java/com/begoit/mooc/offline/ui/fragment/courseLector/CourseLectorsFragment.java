package com.begoit.mooc.offline.ui.fragment.courseLector;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.base.BaseFragment;
import com.begoit.mooc.offline.entity.course.teacher.TeacherEntity;
import com.begoit.mooc.offline.ui.adapter.CourseLectorsAdapter;
import com.begoit.mooc.offline.widget.BaseEmptyView;
import java.util.List;
import butterknife.BindView;

/**
 * @Description:课程讲师
 * @Author:gxj
 * @Time 2019/2/27
 */

public class CourseLectorsFragment extends BaseFragment {
    //异常页面
    @BindView(R.id.empty)
    BaseEmptyView emptyView;
    //讲师列表展示
    @BindView(R.id.rl_lectors)
    RecyclerView rlLectors;

    private List<TeacherEntity> teacherList;
    private CourseLectorsAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    @Override
    protected int getLyoutId() {
        return R.layout.fragment_course_lectors;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        refreshView();
     }

     public void refreshData(List<TeacherEntity> list){
         teacherList = list;
         refreshView();
     }

    private void refreshView(){
         if (rlLectors == null || emptyView == null){
             return;
         }
        if (teacherList != null && teacherList.size() > 0){
            emptyView.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.VISIBLE);
            return;
        }
         if (adapter == null){
             adapter = new CourseLectorsAdapter(teacherList,getActivity());
             gridLayoutManager = new GridLayoutManager(mContext,6,GridLayoutManager.VERTICAL,false);
             rlLectors.setLayoutManager(gridLayoutManager);
             rlLectors.setAdapter(adapter);
         }else {
             adapter.setNewData(teacherList);
         }
     }
}
