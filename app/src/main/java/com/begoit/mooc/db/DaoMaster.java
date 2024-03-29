package com.begoit.mooc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 17): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 17;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        CourseDetailEntityDao.createTable(db, ifNotExists);
        ArticelEntityDao.createTable(db, ifNotExists);
        ArticleClassEntityDao.createTable(db, ifNotExists);
        CourseDetailFilesEntityDao.createTable(db, ifNotExists);
        FileInformationEntityDao.createTable(db, ifNotExists);
        PreCourseEntityDao.createTable(db, ifNotExists);
        PreCourseGroupEntityDao.createTable(db, ifNotExists);
        VideonobEntityDao.createTable(db, ifNotExists);
        VideoTestEntityDao.createTable(db, ifNotExists);
        CourseListEntityDao.createTable(db, ifNotExists);
        CourseListItemPreImgPlaceHolderEntityDao.createTable(db, ifNotExists);
        HistoryCourseEntityDao.createTable(db, ifNotExists);
        TeacherConfigEntityDao.createTable(db, ifNotExists);
        TeacherEntityDao.createTable(db, ifNotExists);
        AppLogEntityDao.createTable(db, ifNotExists);
        CourseDownLoadedEntityDao.createTable(db, ifNotExists);
        CourseRegistrationEntityDao.createTable(db, ifNotExists);
        UserChannelEntityDao.createTable(db, ifNotExists);
        VideoTestLogEntityDao.createTable(db, ifNotExists);
        VideoTestScoreEntityDao.createTable(db, ifNotExists);
        MenuItemEntityDao.createTable(db, ifNotExists);
        UserEntityDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        CourseDetailEntityDao.dropTable(db, ifExists);
        ArticelEntityDao.dropTable(db, ifExists);
        ArticleClassEntityDao.dropTable(db, ifExists);
        CourseDetailFilesEntityDao.dropTable(db, ifExists);
        FileInformationEntityDao.dropTable(db, ifExists);
        PreCourseEntityDao.dropTable(db, ifExists);
        PreCourseGroupEntityDao.dropTable(db, ifExists);
        VideonobEntityDao.dropTable(db, ifExists);
        VideoTestEntityDao.dropTable(db, ifExists);
        CourseListEntityDao.dropTable(db, ifExists);
        CourseListItemPreImgPlaceHolderEntityDao.dropTable(db, ifExists);
        HistoryCourseEntityDao.dropTable(db, ifExists);
        TeacherConfigEntityDao.dropTable(db, ifExists);
        TeacherEntityDao.dropTable(db, ifExists);
        AppLogEntityDao.dropTable(db, ifExists);
        CourseDownLoadedEntityDao.dropTable(db, ifExists);
        CourseRegistrationEntityDao.dropTable(db, ifExists);
        UserChannelEntityDao.dropTable(db, ifExists);
        VideoTestLogEntityDao.dropTable(db, ifExists);
        VideoTestScoreEntityDao.dropTable(db, ifExists);
        MenuItemEntityDao.dropTable(db, ifExists);
        UserEntityDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(CourseDetailEntityDao.class);
        registerDaoClass(ArticelEntityDao.class);
        registerDaoClass(ArticleClassEntityDao.class);
        registerDaoClass(CourseDetailFilesEntityDao.class);
        registerDaoClass(FileInformationEntityDao.class);
        registerDaoClass(PreCourseEntityDao.class);
        registerDaoClass(PreCourseGroupEntityDao.class);
        registerDaoClass(VideonobEntityDao.class);
        registerDaoClass(VideoTestEntityDao.class);
        registerDaoClass(CourseListEntityDao.class);
        registerDaoClass(CourseListItemPreImgPlaceHolderEntityDao.class);
        registerDaoClass(HistoryCourseEntityDao.class);
        registerDaoClass(TeacherConfigEntityDao.class);
        registerDaoClass(TeacherEntityDao.class);
        registerDaoClass(AppLogEntityDao.class);
        registerDaoClass(CourseDownLoadedEntityDao.class);
        registerDaoClass(CourseRegistrationEntityDao.class);
        registerDaoClass(UserChannelEntityDao.class);
        registerDaoClass(VideoTestLogEntityDao.class);
        registerDaoClass(VideoTestScoreEntityDao.class);
        registerDaoClass(MenuItemEntityDao.class);
        registerDaoClass(UserEntityDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
