package com.begoit.mooc.offline.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.begoit.mooc.BegoitMoocApplication;
import com.begoit.mooc.db.CourseListItemPreImgPlaceHolderEntityDao;
import com.begoit.mooc.db.UserChannelEntityDao;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_list.CourseListItemEntity;
import com.begoit.mooc.offline.entity.course.course_list.CourseListItemPreImgPlaceHolderEntity;
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.utils.ImagePlaceHolderUtil;
import com.begoit.mooc.offline.utils.CourseComputeUtil;
import com.begoit.mooc.offline.utils.ToastUtil;
import com.begoit.mooc.offline.utils.db.DaoManager;
import com.begoit.mooc.offline.widget.imageView.GlideCircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/**
 * @Description:分类课程列表适配器
 * @Author:gxj
 * @Time 2019/2/25
 */

public class KindCoursesAdapter extends BaseQuickAdapter<CourseListItemEntity,BaseViewHolder> {
    private Context mContext;
    private OnCourseItemClickListener onCourseItemClickListener;

    public KindCoursesAdapter(@Nullable List<CourseListItemEntity> data, Context mContext) {
        super(R.layout.item_kind_course, data);
        this.mContext = mContext;
    }

    public void setOnCourseItemClickListener(OnCourseItemClickListener onCourseItemClickListener){
        this.onCourseItemClickListener = onCourseItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CourseListItemEntity item) {
        helper.setText(R.id.tv_learned,CourseComputeUtil.Companion.computeFinishVideo(item.id));
        StringBuilder schoolName = new StringBuilder();
        schoolName.append(item.schoolName);
        if (item.teacherConfig != null) {
            schoolName.append("   ");
            for (int i = 0; i < item.teacherConfig.size(); i++) {
                if (i > 0) {
                    schoolName.append("、");
                }
                schoolName.append(item.teacherConfig.get(i).teachers.teacherName);
            }
        }

        helper.setText(R.id.tv_course_name,item.channelName);
        helper.setText(R.id.tv_course_school,schoolName.toString());
        helper.setText(R.id.tv_learned_count,11948 + "人学习");

        if (TextUtils.isEmpty(item.preImgFileid)){
            helper.setImageResource(R.id.iv_course_img,ImagePlaceHolderUtil.INSTANCE.getPlaceholderPreImg(item.id));
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



    public interface OnCourseItemClickListener{
        void onCourseClick(CourseListItemEntity entity);
    }

}
