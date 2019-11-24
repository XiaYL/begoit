package com.begoit.mooc.offline.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.user_download.CourseDownLoadedEntity;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.utils.ImagePlaceHolderUtil;
import com.begoit.mooc.offline.widget.imageView.GlideCircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:管理课程列表适配器
 * @Author:gxj
 * @Time 2019/2/25
 */

public class ManageCoursesAdapter extends BaseQuickAdapter<CourseDownLoadedEntity,BaseViewHolder> {
    private Context mContext;
    public static final int MANAGEMODELEARNING = 0;//浏览模式
    public static final int MANAGEMODEDELETE = 1;//整理模式

    private List<CourseDownLoadedEntity> pickedCourses = new ArrayList<>();

    private OnCourseItemClickListener onCourseItemClickListener;
    private int manageMode = MANAGEMODELEARNING;
    //全选按钮，用于选择阶段状态切换
    private TextView selectAllButton;
    public void setSelectAllButton(TextView button){
        selectAllButton = button;
    }

    public ManageCoursesAdapter(@Nullable List<CourseDownLoadedEntity> data, Context mContext) {
        super(R.layout.item_manage_course, data);
        this.mContext = mContext;
    }

    public void setOnCourseItemClickListener(OnCourseItemClickListener onCourseItemClickListener){
        this.onCourseItemClickListener = onCourseItemClickListener;
    }

    /**
     * 切换管理模式进行页面切换
     * @param newMode
     */
    public void checkManageMode(int newMode){
        manageMode = newMode;
        notifyItemRangeChanged(0,getData().size());
    }

    /**
     * 全选
     */
    public boolean pickAll(){
        try {
            for (int i = 0;i < getItemCount();i++){
                if (!isPicked(getItem(i))) {
                    pickedCourses.add(getItem(i));
                    notifyItemChanged(i);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 反选
     */
    public boolean cancelAll(){
        try {
            pickedCourses.clear();
            for (int i = 0;i < getItemCount();i++){
                notifyItemChanged(i);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CourseDownLoadedEntity item) {
        helper.setText(R.id.tv_course_name,item.courseName);
        helper.setText(R.id.tv_course_school,item.courseWithSchool);
        if (manageMode == MANAGEMODEDELETE){
            helper.setGone(R.id.rb_select_manage,true);
        }else {
            helper.setGone(R.id.rb_select_manage,false);
        }

        if (isPicked(item)){
            helper.setChecked(R.id.rb_select_manage,true);
        }else {
            helper.setChecked(R.id.rb_select_manage,false);
        }

        helper.setOnCheckedChangeListener(R.id.rb_select_manage, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pickingCourse(item,isChecked);
            }
        });
//        helper.setText(R.id.tv_learned_count,11948 + "人学习");
//        helper.setProgress(R.id.progress,item.courseDownloadedFilesCound / item.courseFilesCount * 100);
        if (TextUtils.isEmpty(item.preImgFileid)){
            helper.setImageResource(R.id.iv_course_img,ImagePlaceHolderUtil.INSTANCE.getPlaceholderPreImg(item.courseId));
        }else {
            Glide.with(mContext)
                    .load(ApiConstants.getFileUrl(item.preImgFileid))
                    .apply(RequestOptions
                            .bitmapTransform(new GlideCircleTransform(mContext, 12f))
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into((ImageView) helper.getView(R.id.iv_course_img));
        }

        helper.setOnClickListener(R.id.ll_course_container, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCourseItemClickListener.onCourseClick(item);
            }
        });
    }

    /**
     * 判断课程是否已选择
     * @param entity
     * @return
     */
    private boolean isPicked(CourseDownLoadedEntity entity){
        for (CourseDownLoadedEntity item:pickedCourses){
            if (item.courseId.equals(entity.courseId)){
                return true;
            }
        }
          return false;
    }

    /**
     * 课程管理选择过程
     * @param entity 当前要操作的课程
     * @param isAdd 是否选择
     */
    private void pickingCourse(CourseDownLoadedEntity entity,boolean isAdd){
        if (isAdd){
            if (!isPicked(entity)) {
                pickedCourses.add(entity);
            }
        }else {
            pickedCourses.remove(entity);
        }
        if (selectAllButton == null){
            return;
        }
        if (pickedCourses.size() == getItemCount()){
            selectAllButton.setText("反选");
        }else if (pickedCourses.size() == 0){
            selectAllButton.setText("全选");
        }
    }

    /**
     * 获取已选择课程
     * @return
     */
    public List<CourseDownLoadedEntity> getPickedCourse(){
        return pickedCourses;
    }

    public interface OnCourseItemClickListener{
        void onCourseClick(CourseDownLoadedEntity entity);
    }

}
