package jp.hack4.safety_transmission;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
    private final static String DB_NAME ="bujidesu.db";//DB名
    private final static String DB_TABLE="bujidesu";   //テーブル名
    private final static int    DB_VERSION=1;      //バージョン
    
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "drop talbe if exists "+DB_TABLE);
        db.execSQL(
                "create table if not exists "+
                DB_TABLE+"(id text,value text,sort int)");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
            "drop talbe if exists "+DB_TABLE);
        onCreate(db);
	}    

	
}
