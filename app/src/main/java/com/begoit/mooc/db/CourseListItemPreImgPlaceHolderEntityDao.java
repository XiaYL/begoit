package com.begoit.mooc.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.begoit.mooc.offline.entity.course.course_list.CourseListItemPreImgPlaceHolderEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COURSE_LIST_ITEM_PRE_IMG_PLACE_HOLDER_ENTITY".
*/
public class CourseListItemPreImgPlaceHolderEntityDao extends AbstractDao<CourseListItemPreImgPlaceHolderEntity, String> {

    public static final String TABLENAME = "COURSE_LIST_ITEM_PRE_IMG_PLACE_HOLDER_ENTITY";

    /**
     * Properties of entity CourseListItemPreImgPlaceHolderEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property PreImgFileid = new Property(1, int.class, "preImgFileid", false, "PRE_IMG_FILEID");
    }


    public CourseListItemPreImgPlaceHolderEntityDao(DaoConfig config) {
        super(config);
    }
    
    public CourseListItemPreImgPlaceHolderEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COURSE_LIST_ITEM_PRE_IMG_PLACE_HOLDER_ENTITY\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"PRE_IMG_FILEID\" INTEGER NOT NULL );"); // 1: preImgFileid
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COURSE_LIST_ITEM_PRE_IMG_PLACE_HOLDER_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CourseListItemPreImgPlaceHolderEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
        stmt.bindLong(2, entity.getPreImgFileid());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CourseListItemPreImgPlaceHolderEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
        stmt.bindLong(2, entity.getPreImgFileid());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public CourseListItemPreImgPlaceHolderEntity readEntity(Cursor cursor, int offset) {
        CourseListItemPreImgPlaceHolderEntity entity = new CourseListItemPreImgPlaceHolderEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.getInt(offset + 1) // preImgFileid
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CourseListItemPreImgPlaceHolderEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPreImgFileid(cursor.getInt(offset + 1));
     }
    
    @Override
    protected final String updateKeyAfterInsert(CourseListItemPreImgPlaceHolderEntity entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(CourseListItemPreImgPlaceHolderEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CourseListItemPreImgPlaceHolderEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
