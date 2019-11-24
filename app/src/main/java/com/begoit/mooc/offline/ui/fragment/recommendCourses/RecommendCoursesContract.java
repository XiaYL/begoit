package com.begoit.mooc.offline.ui.fragment.recommendCourses;

import com.begoit.mooc.offline.base.BasePresenter;
import com.begoit.mooc.offline.base.IBaseView;
import com.begoit.mooc.offline.base.IBaseModel;

/**
 * @Description:课程推荐综合合约
 * @Author:gxj
 * @Time 2019/2/25
 */

public interface RecommendCoursesContract {
    interface View extends IBaseView{
    }
    abstract class Presenter extends BasePresenter<View,Model>{

    }
    interface Model extends IBaseModel {
    }
}
