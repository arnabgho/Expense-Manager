package com.arnab.meandmyfriendsexpense;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_COMPLETE = "complete";
  public static final String COMPLETE_ID = "_id";
  public static final String COMPLETE_FRIEND = "friend";
  public static final String COMPLETE_EXPENSE = "expense";
  public static final String COMPLETE_DESCRIPTION = "description";
  public static final String COMPLETE_DAY = "day";
  public static final String COMPLETE_MONTH = "month";
  public static final String COMPLETE_YEAR = "year";
  public static final String COMPLETE_CATEGORY = "category";
  public static final String COMPLETE_BANK="bank";
  

  public static final String TABLE_FRIEND = "friend";
  public static final String FRIEND_ID = "_id";
  public static final String FRIEND_NAME = "friend_name";
  public static final String FRIEND_DUE = "friend_due";
      
  public static final String TABLE_INCOME = "income";
  public static final String INCOME_ID = "_id";
  public static final String INCOME_SOURCE = "income_source"; 
  public static final String INCOME_AMOUNT = "income_amount";
  
  public static final String TABLE_FRIENDNAME = "nfriend";
  public static final String FRIENDNAME_ID = "n_id";
  public static final String FRIENDNAME_NAME = "nfriend_name";
  
  public static final String TABLE_BUDGET="budget";
  public static final String BUDGET_ID="_id";
  public static final String BUDGET_PARTICULAR="particular";
  public static final String BUDGET_AMOUNT="amount";
  
  
  private static final String DATABASE_NAME1 = "expense.db";
  
  private static final int DATABASE_VERSION = 2;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_COMPLETE + "(" + COMPLETE_ID
      + " integer primary key autoincrement, " 
      + COMPLETE_FRIEND +" text not null, "
      +	COMPLETE_EXPENSE +"  integer, "
      +	COMPLETE_DESCRIPTION+"  text , "
      +	COMPLETE_DAY+"  integer, "
      +	COMPLETE_MONTH +"  integer, "
      + COMPLETE_YEAR +" integer, "
      + COMPLETE_CATEGORY +  " text ," + COMPLETE_BANK 
      + " text not null );";

  private static final String FRIEND_CREATE = "create table "
	      + TABLE_FRIEND + "(" + FRIEND_ID
	      + " integer primary key autoincrement, " 
	      + FRIEND_NAME +" text not null,"
	      +	FRIEND_DUE +" integer"
	   
	      + " );";

  private static final String INCOME_CREATE = "create table "
	      + TABLE_INCOME + "(" + INCOME_ID
	      + " integer primary key autoincrement, " 
	      + INCOME_SOURCE +" text not null ,"
	      +	INCOME_AMOUNT +"  integer"
	   
	      + " );";
 
  private static final String BUDGET_CREATE="create table budget ( _id integer primary key autoincrement, particular text not null , amount integer );";
  
  private static final String FRIENDNAME_CREATE = "create table "
	      + TABLE_FRIENDNAME + "(" + FRIENDNAME_ID
	      + " integer primary key autoincrement, " 
	      + FRIENDNAME_NAME +" text not null"	      	   
	      + " );";
  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME1, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
	  
    database.execSQL(DATABASE_CREATE);
    database.execSQL(FRIEND_CREATE);
    database.execSQL(INCOME_CREATE);
    database.execSQL(FRIENDNAME_CREATE);
    database.execSQL(BUDGET_CREATE);
    Log.d(DATABASE_CREATE, "SQL CREATED");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLETE);
    onCreate(db);
  }

}