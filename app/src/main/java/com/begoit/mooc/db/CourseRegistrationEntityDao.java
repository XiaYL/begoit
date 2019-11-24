package com.begoit.mooc.db;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.begoit.mooc.offline.entity.course.user_download.CourseRegistrationEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COURSE_REGISTRATION_ENTITY".
*/
public class CourseRegistrationEntityDao extends AbstractDao<CourseRegistrationEntity, String> {

    public static final String TABLENAME = "COURSE_REGISTRATION_ENTITY";

    /**
     * Properties of entity CourseRegistrationEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property GroupName = new Property(1, String.class, "groupName", false, "GROUP_NAME");
        public final static Property GroupId = new Property(2, String.class, "groupId", false, "GROUP_ID");
        public final static Property ChannelId = new Property(3, String.class, "channelId", false, "CHANNEL_ID");
        public final static Property PreChannelId = new Property(4, String.class, "preChannelId", false, "PRE_CHANNEL_ID");
        public final static Property AddTime = new Property(5, String.class, "addTime", false, "ADD_TIME");
    }

    private Query<CourseRegistrationEntity> preCourseGroupEntity_ChannelPrerequisiteQuery;

    public CourseRegistrationEntityDao(DaoConfig config) {
        super(config);
    }
    
    public CourseRegistrationEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COURSE_REGISTRATION_ENTITY\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"GROUP_NAME\" TEXT," + // 1: groupName
                "\"GROUP_ID\" TEXT," + // 2: groupId
                "\"CHANNEL_ID\" TEXT," + // 3: channelId
                "\"PRE_CHANNEL_ID\" TEXT," + // 4: preChannelId
                "\"ADD_TIME\" TEXT);"); // 5: addTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COURSE_REGISTRATION_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CourseRegistrationEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(2, groupName);
        }
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(3, groupId);
        }
 
        String channelId = entity.getChannelId();
        if (channelId != null) {
            stmt.bindString(4, channelId);
        }
 
        String preChannelId = entity.getPreChannelId();
        if (preChannelId != null) {
            stmt.bindString(5, preChannelId);
        }
 
        String addTime = entity.getAddTime();
        if (addTime != null) {
            stmt.bindString(6, addTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CourseRegistrationEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String groupName = entity.getGroupName();
        if (groupName != null) {
            stmt.bindString(2, groupName);
        }
 
        String groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindString(3, groupId);
        }
 
        String channelId = entity.getChannelId();
        if (channelId != null) {
            stmt.bindString(4, channelId);
        }
 
        String preChannelId = entity.getPreChannelId();
        if (preChannelId != null) {
            stmt.bindString(5, preChannelId);
        }
 
        String addTime = entity.getAddTime();
        if (addTime != null) {
            stmt.bindString(6, addTime);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public CourseRegistrationEntity readEntity(Cursor cursor, int offset) {
        CourseRegistrationEntity entity = new CourseRegistrationEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // groupName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // groupId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // channelId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // preChannelId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // addTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CourseRegistrationEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setGroupName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGroupId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setChannelId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPreChannelId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAddTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final String updateKeyAfterInsert(CourseRegistrationEntity entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(CourseRegistrationEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CourseRegistrationEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "channelPrerequisite" to-many relationship of PreCourseGroupEntity. */
    public List<CourseRegistrationEntity> _queryPreCourseGroupEntity_ChannelPrerequisite(String groupId) {
        synchronized (this) {
            if (preCourseGroupEntity_ChannelPrerequisiteQuery == null) {
                QueryBuilder<CourseRegistrationEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.GroupId.eq(null));
                preCourseGroupEntity_ChannelPrerequisiteQuery = queryBuilder.build();
            }
        }
        Query<CourseRegistrationEntity> query = preCourseGroupEntity_ChannelPrerequisiteQuery.forCurrentThread();
        query.setParameter(0, groupId);
        return query.list();
    }

}
