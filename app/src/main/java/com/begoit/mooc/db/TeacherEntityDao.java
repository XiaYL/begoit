package com.begoit.mooc.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.begoit.mooc.offline.entity.course.teacher.TeacherEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEACHER_ENTITY".
*/
public class TeacherEntityDao extends AbstractDao<TeacherEntity, String> {

    public static final String TABLENAME = "TEACHER_ENTITY";

    /**
     * Properties of entity TeacherEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property TeacherName = new Property(1, String.class, "teacherName", false, "TEACHER_NAME");
        public final static Property PosTitle = new Property(2, String.class, "posTitle", false, "POS_TITLE");
        public final static Property ImgFileid = new Property(3, String.class, "imgFileid", false, "IMG_FILEID");
    }


    public TeacherEntityDao(DaoConfig config) {
        super(config);
    }
    
    public TeacherEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEACHER_ENTITY\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"TEACHER_NAME\" TEXT," + // 1: teacherName
                "\"POS_TITLE\" TEXT," + // 2: posTitle
                "\"IMG_FILEID\" TEXT);"); // 3: imgFileid
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEACHER_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TeacherEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String teacherName = entity.getTeacherName();
        if (teacherName != null) {
            stmt.bindString(2, teacherName);
        }
 
        String posTitle = entity.getPosTitle();
        if (posTitle != null) {
            stmt.bindString(3, posTitle);
        }
 
        String imgFileid = entity.getImgFileid();
        if (imgFileid != null) {
            stmt.bindString(4, imgFileid);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TeacherEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String teacherName = entity.getTeacherName();
        if (teacherName != null) {
            stmt.bindString(2, teacherName);
        }
 
        String posTitle = entity.getPosTitle();
        if (posTitle != null) {
            stmt.bindString(3, posTitle);
        }
 
        String imgFileid = entity.getImgFileid();
        if (imgFileid != null) {
            stmt.bindString(4, imgFileid);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public TeacherEntity readEntity(Cursor cursor, int offset) {
        TeacherEntity entity = new TeacherEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // teacherName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // posTitle
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // imgFileid
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TeacherEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTeacherName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPosTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImgFileid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final String updateKeyAfterInsert(TeacherEntity entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(TeacherEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TeacherEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
