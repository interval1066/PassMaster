/**
 * Created by bwana on 10/26/15.
 */
package com.hellotechie.passmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "passmaster";
    private static final String TABLE_SITES = "sites";

    // Sites Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SITE = "name";
    private static final String KEY_USER = "site_username";
    private static final String KEY_PW = "site_pw";
    private static final String KEY_DESC = "site_desc";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITES);
        // Creating tables again
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SITES_TABLE = "CREATE TABLE IF NOT EXISTS" + TABLE_SITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE + " INTEGER,"
                + KEY_SITE + " TEXT NOT NULL," + KEY_USER + " TEXT, " + KEY_PW + " TEXT,"
                + KEY_DESC + " TEXT" + ")";
        db.execSQL(CREATE_SITES_TABLE);
    }

    public void onInstall(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("CREAT TABLE IF NOT EXISTS " + TABLE_SITES);
        // Creating tables again
        onCreate(db);
    }

    // Adding new shop
    public void addSite(Site site) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, site.getType()); // Site Type
        values.put(KEY_SITE, site.getName()); // Site URL
        values.put(KEY_USER, site.getUser()); // Site User
        values.put(KEY_PW, site.getPW()); // site PW
        values.put(KEY_SITE, site.getDesc()); //site Desc

        // Inserting Row
        db.insert(TABLE_SITES, null, values);
        db.close(); // Closing database connection
    }

    // Getting one site
    public Site getSite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SITES, new String[]{KEY_ID,
                        KEY_ID, KEY_SITE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Site site = new Site(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4));
        // return site
        return site;
    }

    // Getting All Sites
    public List<Site> getAllSites() {
        List<Site> siteList = new ArrayList<Site>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SITES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Site site = new Site();
                site.setName(cursor.getString(2));
                site.setURL(cursor.getString(3));
                site.setUser(cursor.getString(4));
                site.setPW(cursor.getString(5));
                site.setDesc(cursor.getString(6));
                // Adding contact to list
                siteList.add(site);
            } while (cursor.moveToNext());
        }

        // return contact list
        return siteList;
    }

    // Get Site Count
    public int getSitesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SITES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating a site
    public int updateSite(Site site) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, site.getType());
        values.put(KEY_USER, site.getUser());
        values.put(KEY_PW, site.getPW());
        values.put(KEY_DESC, site.getDesc());
        values.put(KEY_TYPE, site.getType());

        // updating row
        return db.update(TABLE_SITES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(site.getName())});
    }

    // Deleting a site
    public void deleteSite(Site site) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SITES, KEY_ID + " = ?",
                new String[] { String.valueOf(site.getName()) });
        db.close();
    }
}

