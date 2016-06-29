package com.arnab.meandmyfriendsexpense;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper1 extends SQLiteOpenHelper {

  public static final String TABLE_COMMENTS = "comments";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_COMMENT = "comment";

  public static final String TABLE_INCOME = "income";
  public static final String INCOME_ID = "_id";
  public static final String COLUMN_SOURCE = "source";
  public static final String COLUMN_AMOUNT = "amount";
  
  
  private static final String DATABASE_NAME = "commments.db";
  private static final int DATABASE_VERSION = 2;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_COMMENTS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_COMMENT
      + " text not null);";

  private static final String INCOME_CREATE = "create table "
	      + TABLE_INCOME + "(" + INCOME_ID
	      + " integer primary key autoincrement, " + COLUMN_SOURCE
	      + " text not null , " + COLUMN_AMOUNT +" integer );";
  
  public MySQLiteHelper1(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
    database.execSQL(INCOME_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper1.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
    onCreate(db);
  }

}