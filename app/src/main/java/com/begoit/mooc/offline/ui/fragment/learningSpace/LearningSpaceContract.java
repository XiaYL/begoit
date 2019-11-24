package com.begoit.mooc.offline.ui.fragment.learningSpace;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseModel;
import com.begoit.mooc.offline.base.IBaseView;
import com.begoit.mooc.offline.entity.course.learning_space.HistoryCourseEntity;
import java.util.List;

/**
 * @Description:学习空间综合合约
 * @Author:gxj
 * @Time 2019/2/25
 */

public interface LearningSpaceContract {
    interface View extends IBaseView{
        void showLearningSpaceCourses(List<HistoryCourseEntity> entityList);
        void loadRemoteError(int code);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        abstract void getLearningSpaceCourses(String userId);

    }
    interface Model extends IBaseModel {
        List<HistoryCourseEntity> getLearningSpaceCourses(String userAccound);
    }
}
