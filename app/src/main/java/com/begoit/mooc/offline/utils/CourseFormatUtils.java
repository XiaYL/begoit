package com.begoit.mooc.offline.utils;

import com.begoit.mooc.offline.entity.course.course_detail.course_files.CourseDetailFilesData;

/**
 * @Description:对课程数据的一些操作
 * @Author:gxj
 * @Time 2019/4/15
 */

public class CourseFormatUtils {
    /**
     * 对课程章节添加序号
     * @param entity
     * @return
     */
    public static CourseDetailFilesData addPosition(CourseDetailFilesData entity) {
        if (entity != null && entity.data != null && entity.data.articleClass != null) {
            int i = 0;
            int j = 0;
            for (i = 0; i < entity.data.articleClass.size(); i++) {
                entity.data.articleClass.get(i).chapterPosition = i + 1 + "";
                if (entity.data.articleClass.get(i) != null && entity.data.articleClass.get(i).articel != null) {
                    for (j = 0; j < entity.data.articleClass.get(i).articel.size(); j++) {
                        entity.data.articleClass.get(i).articel.get(j).sectionPosition = (i + 1) + "-" + (j + 1);
                    }
                }
            }
        }
        return entity;
    }
}
