package com.begoit.mooc.offline.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.begoit.mooc.offline.R;
import com.begoit.mooc.offline.requests.ApiConstants;
import com.begoit.mooc.offline.entity.course.teacher.TeacherEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/**
 * @Description:课程讲师列表适配器
 * @Author:gxj
 * @Time 2019/2/25
 */

public class CourseLectorsAdapter extends BaseQuickAdapter<TeacherEntity,BaseViewHolder> {
    private Context mContext;
    private OnLectorItemClickListener onLectorItemClickListener;

    public CourseLectorsAdapter(@Nullable List<TeacherEntity> data, Context mContext) {
        super(R.layout.item_course_lector, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, final TeacherEntity item) {
        if (item == null){
            return;
        }
        helper.setText(R.id.tv_job_title,item.posTitle);
        helper.setText(R.id.tv_lector_name,item.teacherName);
        Glide.with(mContext)
                .load(ApiConstants.getFileUrl(item.getImgFileid()))
                .apply(RequestOptions.circleCropTransform()//圆形
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)) //只缓存最终的图片)
                .into((ImageView) helper.getView(R.id.iv_lector_img));
    }

    public interface OnLectorItemClickListener{
        void onCourseClick(TeacherEntity entity);
    }

}
