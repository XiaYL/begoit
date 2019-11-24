package com.begoit.mooc.offline.ui.fragment.learningSpace;

/**
 * @Description:学习空间presenter实现
 * @Author:gxj
 * @Time 2019/2/25
 */

public class LearningSpacePresenter extends LearningSpaceContract.Presenter {
    @Override
    void getLearningSpaceCourses(String userId) {
        mView.showLearningSpaceCourses(mModel.getLearningSpaceCourses(userId));
    }
}
