package com.lijianping.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.lijianping.greendao.VideoCache;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table VIDEO_CACHE.
*/
public class VideoCacheDao extends AbstractDao<VideoCache, Long> {

    public static final String TABLENAME = "VIDEO_CACHE";

    /**
     * Properties of entity VideoCache.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Result = new Property(1, String.class, "result", false, "RESULT");
        public final static Property Page = new Property(2, Integer.class, "page", false, "PAGE");
        public final static Property Time = new Property(3, Long.class, "time", false, "TIME");
    };


    public VideoCacheDao(DaoConfig config) {
        super(config);
    }
    
    public VideoCacheDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'VIDEO_CACHE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'RESULT' TEXT," + // 1: result
                "'PAGE' INTEGER," + // 2: page
                "'TIME' INTEGER);"); // 3: time
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'VIDEO_CACHE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, VideoCache entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String result = entity.getResult();
        if (result != null) {
            stmt.bindString(2, result);
        }
 
        Integer page = entity.getPage();
        if (page != null) {
            stmt.bindLong(3, page);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(4, time);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public VideoCache readEntity(Cursor cursor, int offset) {
        VideoCache entity = new VideoCache( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // result
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // page
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // time
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, VideoCache entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setResult(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPage(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(VideoCache entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(VideoCache entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
