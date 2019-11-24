package com.begoit.mooc.offline.entity.kindMenu;

import java.util.List;

/**
 * @Description:侧边菜单课程类型数据
 * @Author:gxj
 * @Time 2019/3/12
 */
public class CourseKindMenuData {
    public int status;//1为正常返回
    public String msg;
    public String error;
    public List<MenuItemEntity> data;
}
