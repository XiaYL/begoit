package com.begoit.mooc.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.begoit.mooc.offline.entity.user.UserEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_ENTITY".
*/
public class UserEntityDao extends AbstractDao<UserEntity, String> {

    public static final String TABLENAME = "USER_ENTITY";

    /**
     * Properties of entity UserEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UserName = new Property(0, String.class, "userName", false, "USER_NAME");
        public final static Property UserId = new Property(1, String.class, "userId", true, "USER_ID");
        public final static Property PassWord = new Property(2, String.class, "passWord", false, "PASS_WORD");
        public final static Property UserAccount = new Property(3, String.class, "userAccount", false, "USER_ACCOUNT");
        public final static Property Token = new Property(4, String.class, "token", false, "TOKEN");
        public final static Property OfflineSn = new Property(5, String.class, "offlineSn", false, "OFFLINE_SN");
    }


    public UserEntityDao(DaoConfig config) {
        super(config);
    }
    
    public UserEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_ENTITY\" (" + //
                "\"USER_NAME\" TEXT," + // 0: userName
                "\"USER_ID\" TEXT PRIMARY KEY NOT NULL ," + // 1: userId
                "\"PASS_WORD\" TEXT," + // 2: passWord
                "\"USER_ACCOUNT\" TEXT," + // 3: userAccount
                "\"TOKEN\" TEXT," + // 4: token
                "\"OFFLINE_SN\" TEXT);"); // 5: offlineSn
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserEntity entity) {
        stmt.clearBindings();
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(1, userName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String passWord = entity.getPassWord();
        if (passWord != null) {
            stmt.bindString(3, passWord);
        }
 
        String userAccount = entity.getUserAccount();
        if (userAccount != null) {
            stmt.bindString(4, userAccount);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(5, token);
        }
 
        String offlineSn = entity.getOfflineSn();
        if (offlineSn != null) {
            stmt.bindString(6, offlineSn);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserEntity entity) {
        stmt.clearBindings();
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(1, userName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String passWord = entity.getPassWord();
        if (passWord != null) {
            stmt.bindString(3, passWord);
        }
 
        String userAccount = entity.getUserAccount();
        if (userAccount != null) {
            stmt.bindString(4, userAccount);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(5, token);
        }
 
        String offlineSn = entity.getOfflineSn();
        if (offlineSn != null) {
            stmt.bindString(6, offlineSn);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1);
    }    

    @Override
    public UserEntity readEntity(Cursor cursor, int offset) {
        UserEntity entity = new UserEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // userName
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // passWord
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userAccount
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // token
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // offlineSn
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserEntity entity, int offset) {
        entity.setUserName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassWord(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserAccount(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setToken(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOfflineSn(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final String updateKeyAfterInsert(UserEntity entity, long rowId) {
        return entity.getUserId();
    }
    
    @Override
    public String getKey(UserEntity entity) {
        if(entity != null) {
            return entity.getUserId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserEntity entity) {
        return entity.getUserId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
