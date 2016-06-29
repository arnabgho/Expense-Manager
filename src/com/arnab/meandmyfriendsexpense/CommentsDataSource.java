package com.arnab.meandmyfriendsexpense;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CommentsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper1 dbHelper;
	private String[] allColumns = { MySQLiteHelper1.COLUMN_ID,
			MySQLiteHelper1.COLUMN_COMMENT };

	private String[] allColumns_INCOME = { MySQLiteHelper1.INCOME_ID,
			MySQLiteHelper1.COLUMN_SOURCE, MySQLiteHelper1.COLUMN_AMOUNT };

	public CommentsDataSource(Context context) {
		dbHelper = new MySQLiteHelper1(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Comment createComment(String comment) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper1.COLUMN_COMMENT, comment);
		long insertId = database.insert(MySQLiteHelper1.TABLE_COMMENTS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper1.TABLE_COMMENTS,
				allColumns, MySQLiteHelper1.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Comment newComment = cursorToComment(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteComment(Comment comment) {
		long id = comment.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper1.TABLE_COMMENTS,
				MySQLiteHelper1.COLUMN_ID + " = " + id, null);
	}
	public void delete(String name){
		
		database.delete(MySQLiteHelper1.TABLE_COMMENTS,
				MySQLiteHelper1.COLUMN_COMMENT + " = '" + name +"'", null);
	}
	
	public Comment smartDelete(String name){
		Cursor cursor=null;
		Comment newComment =null;
		try{
		cursor=database.query(MySQLiteHelper1.TABLE_COMMENTS,
				allColumns, MySQLiteHelper1.COLUMN_COMMENT + " = '" + name+"'", null,
				null, null, null);
		cursor.moveToFirst();
		 newComment = cursorToComment(cursor);
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
				
	//	cursor=database.rawQuery("SELECT * FROM comments WHERE comment IS '"+name+"'", null);
		
		cursor.close();
		
		return newComment;
	}
	
	public void deleteIncome(String source){
		
		database.delete(MySQLiteHelper1.TABLE_INCOME, MySQLiteHelper1.COLUMN_SOURCE + " = '" +source +"'" , null);		
		
	}
	
	public long createIncome(String source, int amount) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper1.COLUMN_SOURCE, source);
		values.put(MySQLiteHelper1.COLUMN_AMOUNT, amount);

		long insertId = database.insert(MySQLiteHelper1.TABLE_INCOME, null,
				values);
		values.clear();
		return insertId;
	}

	public String getStringCompleteData() {
		String data = new String();
		Cursor cursor = null;
		try {
			// database.query(data, allColumns_COMPLETE, data,
			// allColumns_COMPLETE, data, data, data, data)
			// database.query(distinct, table, columns, selection,
			// selectionArgs, groupBy, having, orderBy, limit,
			// cancellationSignal)
			cursor = database.rawQuery("SELECT * FROM  income", null);

			cursor.moveToFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * while (!cursor.isAfterLast()) { Comment comment =
		 * cursorToComment(cursor); comments.add(comment); cursor.moveToNext();
		 * }
		 */

		int iRow = cursor.getColumnIndex(MySQLiteHelper1.INCOME_ID);
		int iSource = cursor.getColumnIndex(MySQLiteHelper1.COLUMN_SOURCE);
		int iAmount = cursor.getColumnIndex(MySQLiteHelper1.COLUMN_AMOUNT);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			data = data 
					+ cursor.getString(iSource) + "  Rs." + cursor.getInt(iAmount)
					+ '\n';
		}
		// Make sure to close the cursor
		cursor.close();
		return data;
	}

	public List<Comment> getAllComments() {
		List<Comment> comments = new ArrayList<Comment>();

		Cursor cursor = database.query(MySQLiteHelper1.TABLE_COMMENTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Comment comment = cursorToComment(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

	public String[] getAllCommentsString() {

		int c = 0;
		Cursor cursor = database.query(MySQLiteHelper1.TABLE_COMMENTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			c++;
			cursor.moveToNext();
		}
		String[] comments = new String[c+1];
		cursor.moveToFirst();
		comments[0]="SELF";
		c = 1;
		while (!cursor.isAfterLast()) {
			comments[c] = cursor.getString(1);
			c++;
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

	public String[] getAllFriendsString() {

		int c = 0;
		Cursor cursor = database.query(MySQLiteHelper1.TABLE_COMMENTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			c++;
			cursor.moveToNext();
		}
		String[] comments = new String[c+1];
		cursor.moveToFirst();
		
		c = 0;
		while (!cursor.isAfterLast()) {
			comments[c] = cursor.getString(1);
			c++;
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}
	
	public int getTotalIncome(){
		Cursor c = null;
		int amount;
		c = database.rawQuery("select sum(amount) from income ;", null);
		if(c.moveToFirst())
		    amount = c.getInt(0);
		else
		    amount = -1;
		c.close();
		
		
		return amount;
	}
	
	private Comment cursorToComment(Cursor cursor) {
		Comment comment = new Comment();
		comment.setId(cursor.getLong(0));
		comment.setComment(cursor.getString(1));
		return comment;
	}
}
