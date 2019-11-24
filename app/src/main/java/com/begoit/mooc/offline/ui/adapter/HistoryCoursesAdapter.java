package com.begoit.mooc.offline.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.begoit.mooc.db.FileInformationEntityDao;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.entity.course.course_detail.course_files.FileInformationEntity;
import com.begoit.mooc.offline.entity.course.learning_space.HistoryCourseEntity;
import com.begoit.mooc.offline.entity.course.user_download.UserChannelEntity;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.utils.CourseComputeUtil;
import com.begoit.mooc.offline.utils.ImagePlaceHolderUtil;
import com.begoit.mooc.offline.utils.db.DaoManager;
import com.begoit.mooc.offline.widget.imageView.GlideCircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @Description:推荐课程列表适配器（适用于竖向素材的item）
 * @Author:gxj
 * @Time 2019/2/25
 */

public class HistoryCoursesAdapter extends BaseQuickAdapter<HistoryCourseEntity,BaseViewHolder> {
    private Context mContext;
    private OnCourseItemClickListener onCourseItemClickListener;

    public HistoryCoursesAdapter(@Nullable List<HistoryCourseEntity> data, Context mContext) {
        super(R.layout.item_history_course, data);
        this.mContext = mContext;
    }

    public void setOnCourseItemClickListener(OnCourseItemClickListener onCourseItemClickListener){
        this.onCourseItemClickListener = onCourseItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HistoryCourseEntity item) {
        if (item.isDelete == 1) {
            helper.setGone(R.id.tv_deleted, true);
            helper.setTextColor(R.id.tv_course_name, Color.parseColor("#999999"));
        }else {
            helper.setGone(R.id.tv_deleted, false);
            helper.setTextColor(R.id.tv_course_name, Color.parseColor("#333333"));
        }
        helper.setText(R.id.tv_course_school, CourseComputeUtil.Companion.computeFinishVideo(item.channelId));
        helper.setText(R.id.tv_course_name,item.courseName);
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
        void onCourseClick(HistoryCourseEntity entity);
    }

}
