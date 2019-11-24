package com.begoit.treerecyclerview.factory;

import android.util.SparseArray;

import com.begoit.treerecyclerview.annotation.TreeItemType;
import com.begoit.treerecyclerview.item.TreeItem;

import java.lang.annotation.Annotation;

public class ItemConfig {

    private static final SparseArray<Class<? extends TreeItem>> treeViewHolderTypes = new SparseArray<>();

    public static Class<? extends TreeItem> getTreeViewHolderType(int type) {
        return treeViewHolderTypes.get(type);
    }

    public static void registerTreeItem(int type, Class<? extends TreeItem> clazz) {
        if (null == clazz) {
            return;
        }
        treeViewHolderTypes.put(type, clazz);
    }

    public static void registerTreeItem(Class<? extends TreeItem>... clazz) {
        for (Class<? extends TreeItem> zClass : clazz) {
            Annotation annotation = zClass.getAnnotation(TreeItemType.class);
            if (annotation != null) {
                int type = ((TreeItemType) annotation).type();
                if (type == -1) {
                    continue;
                }
                Class<? extends TreeItem> typeClass = treeViewHolderTypes.get(type);
                if (typeClass == null) {
                    treeViewHolderTypes.put(type, zClass);
                    continue;
                }
                if (zClass != typeClass) {//如果该type,已经添加了,则抛出异常
                    throw new IllegalStateException("该type映射了一个TreeItemClass,不能再映射其他TreeItemClass");
                }
            }
        }
    }

}