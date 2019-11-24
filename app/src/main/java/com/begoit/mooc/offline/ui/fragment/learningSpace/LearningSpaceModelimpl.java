package com.begoit.mooc.offline.ui.fragment.learningSpace;

import com.begoit.mooc.db.HistoryCourseEntityDao;
import com.begoit.mooc.offline.entity.course.learning_space.HistoryCourseEntity;
import com.begoit.mooc.offline.utils.db.DaoManager;
import java.util.List;

/**
 * 推荐课程数据获取实现
 * @Author:gxj
 * @Time 2019/2/25
 */

public class LearningSpaceModelimpl implements LearningSpaceContract.Model {
    @Override
    public List<HistoryCourseEntity> getLearningSpaceCourses(String userAccound) {
        return DaoManager.getInstance().getDaoSession().getHistoryCourseEntityDao().queryBuilder()
                .where(HistoryCourseEntityDao.Properties.UserAccount.eq(userAccound))
                .build().list();

    }
}
