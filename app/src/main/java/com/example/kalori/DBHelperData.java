package com.example.kalori;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class DBHelperData extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Kaloriku.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_calorie";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TGL = "tanggal";
    private static final String COLUMN_KATEGORI = "kategori";
    private static final String COLUMN_MAKANAN1 = "makanan1";
    private static final String COLUMN_MAKANAN2 = "makanan2";
    private static final String COLUMN_MAKANAN3 = "makanan3";
    private static final String COLUMN_MAKANAN4 = "makanan4";
    private static final String COLUMN_BM1 = "bm1";
    private static final String COLUMN_BM2 = "bm2";
    private static final String COLUMN_BM3 = "bm3";
    private static final String COLUMN_BM4 = "bm4";
    private static final String COLUMN_TOTALCL = "totalcl";
    DBHelperData(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TGL + " TEXT, " +
                COLUMN_KATEGORI + " TEXT, " +
                COLUMN_MAKANAN1 + " TEXT, " +
                COLUMN_MAKANAN2 + " TEXT, " +
                COLUMN_MAKANAN3 + " TEXT, " +
                COLUMN_MAKANAN4 + " TEXT, " +
                COLUMN_BM1 + " TEXT, " +
                COLUMN_BM2 + " TEXT, " +
                COLUMN_BM3 + " TEXT, " +
                COLUMN_BM4 + " TEXT, " +
                COLUMN_TOTALCL + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addMakanan(String tanggal, String kategori,
                    String makanan1, String makanan2,
                    String makanan3, String makanan4,
                    String bm1, String bm2,
                    String bm3, String bm4,
                    String totalcl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TGL, tanggal);
        cv.put(COLUMN_KATEGORI, kategori);
        cv.put(COLUMN_MAKANAN1, makanan1);
        cv.put(COLUMN_MAKANAN2, makanan2);
        cv.put(COLUMN_MAKANAN3, makanan3);
        cv.put(COLUMN_MAKANAN4, makanan4);
        cv.put(COLUMN_BM1, bm1);
        cv.put(COLUMN_BM2, bm2);
        cv.put(COLUMN_BM3, bm3);
        cv.put(COLUMN_BM4, bm4);
        cv.put(COLUMN_TOTALCL, totalcl);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String tanggal, String kategori,
                    String makanan1, String makanan2,
                    String makanan3, String makanan4,
                    String bm1, String bm2,
                    String bm3, String bm4,
                    String totalcl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TGL, tanggal);
        cv.put(COLUMN_KATEGORI, kategori);
        cv.put(COLUMN_MAKANAN1, makanan1);
        cv.put(COLUMN_MAKANAN2, makanan2);
        cv.put(COLUMN_MAKANAN3, makanan3);
        cv.put(COLUMN_MAKANAN4, makanan4);
        cv.put(COLUMN_BM1, bm1);
        cv.put(COLUMN_BM2, bm2);
        cv.put(COLUMN_BM3, bm3);
        cv.put(COLUMN_BM4, bm4);
        cv.put(COLUMN_TOTALCL, totalcl);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Gagal di Update!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Berhasil di Update", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Gagal menghapus!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Berhasil menghapus", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
