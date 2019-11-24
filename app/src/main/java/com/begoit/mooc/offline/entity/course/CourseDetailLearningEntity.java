package com.begoit.mooc.offline.entity.course;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:学习中课程详情
 * @Author:gxj
 * @Time 2019/3/2
 */

public class CourseDetailLearningEntity {

    public ArrayList<Chapters> chapters;
    public int categoryId;

   public class Chapters {
       public String chapterId;
       public String chapterTitle;
       public List<CourseVideo> reference;
   }

   public class CourseVideo {
       public String knobbleId;
       public String knobbleName;
       public String videoPath;
   }
}
