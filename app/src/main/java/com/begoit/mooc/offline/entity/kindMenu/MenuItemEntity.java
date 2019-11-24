package com.begoit.mooc.offline.entity.kindMenu;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Description:侧边菜单课程类型
 * @Author:gxj
 * @Time 2019/3/12
 */

@Entity
public class MenuItemEntity implements Parcelable, MultiItemEntity {
    public static final int TYPE_COMMON = 3;//头部通用
    public static final int TYPE_FIRST = 1;//一级菜单
    public static final int TYPE_SECOND = 2;//二级菜单

    public int level;
    public String typeName;
    public String id;
    @Generated(hash = 67996308)
    public MenuItemEntity(int level, String typeName, String id) {
        this.level = level;
        this.typeName = typeName;
        this.id = id;
    }
    @Generated(hash = 1099527587)
    public MenuItemEntity() {
    }

    protected MenuItemEntity(Parcel in) {
        level = in.readInt();
        typeName = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(level);
        dest.writeString(typeName);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuItemEntity> CREATOR = new Creator<MenuItemEntity>() {
        @Override
        public MenuItemEntity createFromParcel(Parcel in) {
            return new MenuItemEntity(in);
        }

        @Override
        public MenuItemEntity[] newArray(int size) {
            return new MenuItemEntity[size];
        }
    };

    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getTypeName() {
        return this.typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getItemType() {
        return getLevel();
    }
}