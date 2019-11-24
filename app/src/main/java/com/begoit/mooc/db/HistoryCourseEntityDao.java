package com.begoit.mooc.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.begoit.mooc.offline.entity.course.learning_space.HistoryCourseEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HISTORY_COURSE_ENTITY".
*/
public class HistoryCourseEntityDao extends AbstractDao<HistoryCourseEntity, String> {

    public static final String TABLENAME = "HISTORY_COURSE_ENTITY";

    /**
     * Properties of entity HistoryCourseEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property ChannelId = new Property(1, String.class, "channelId", false, "CHANNEL_ID");
        public final static Property CourseName = new Property(2, String.class, "courseName", false, "COURSE_NAME");
        public final static Property PreImgFileid = new Property(3, String.class, "preImgFileid", false, "PRE_IMG_FILEID");
        public final static Property CourseWithSchool = new Property(4, String.class, "courseWithSchool", false, "COURSE_WITH_SCHOOL");
        public final static Property UserAccount = new Property(5, String.class, "userAccount", false, "USER_ACCOUNT");
        public final static Property IsDelete = new Property(6, int.class, "isDelete", false, "IS_DELETE");
    }


    public HistoryCourseEntityDao(DaoConfig config) {
        super(config);
    }
    
    public HistoryCourseEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HISTORY_COURSE_ENTITY\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"CHANNEL_ID\" TEXT," + // 1: channelId
                "\"COURSE_NAME\" TEXT," + // 2: courseName
                "\"PRE_IMG_FILEID\" TEXT," + // 3: preImgFileid
                "\"COURSE_WITH_SCHOOL\" TEXT," + // 4: courseWithSchool
                "\"USER_ACCOUNT\" TEXT," + // 5: userAccount
                "\"IS_DELETE\" INTEGER NOT NULL );"); // 6: isDelete
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HISTORY_COURSE_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HistoryCourseEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String channelId = entity.getChannelId();
        if (channelId != null) {
            stmt.bindString(2, channelId);
        }
 
        String courseName = entity.getCourseName();
        if (courseName != null) {
            stmt.bindString(3, courseName);
        }
 
        String preImgFileid = entity.getPreImgFileid();
        if (preImgFileid != null) {
            stmt.bindString(4, preImgFileid);
        }
 
        String courseWithSchool = entity.getCourseWithSchool();
        if (courseWithSchool != null) {
            stmt.bindString(5, courseWithSchool);
        }
 
        String userAccount = entity.getUserAccount();
        if (userAccount != null) {
            stmt.bindString(6, userAccount);
        }
        stmt.bindLong(7, entity.getIsDelete());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HistoryCourseEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String channelId = entity.getChannelId();
        if (channelId != null) {
            stmt.bindString(2, channelId);
        }
 
        String courseName = entity.getCourseName();
        if (courseName != null) {
            stmt.bindString(3, courseName);
        }
 
        String preImgFileid = entity.getPreImgFileid();
        if (preImgFileid != null) {
            stmt.bindString(4, preImgFileid);
        }
 
        String courseWithSchool = entity.getCourseWithSchool();
        if (courseWithSchool != null) {
            stmt.bindString(5, courseWithSchool);
        }
 
        String userAccount = entity.getUserAccount();
        if (userAccount != null) {
            stmt.bindString(6, userAccount);
        }
        stmt.bindLong(7, entity.getIsDelete());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public HistoryCourseEntity readEntity(Cursor cursor, int offset) {
        HistoryCourseEntity entity = new HistoryCourseEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // channelId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // courseName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // preImgFileid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // courseWithSchool
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // userAccount
            cursor.getInt(offset + 6) // isDelete
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HistoryCourseEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setChannelId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCourseName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPreImgFileid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCourseWithSchool(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUserAccount(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsDelete(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final String updateKeyAfterInsert(HistoryCourseEntity entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(HistoryCourseEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HistoryCourseEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}