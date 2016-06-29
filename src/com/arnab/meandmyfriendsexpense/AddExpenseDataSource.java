package com.arnab.meandmyfriendsexpense;




import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.arnab.meandmyfriendsexpense.MySQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AddExpenseDataSource {

	CommentsDataSource datasource;
	
	
  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns_COMPLETE = { MySQLiteHelper.COMPLETE_ID,
      MySQLiteHelper.COMPLETE_FRIEND,MySQLiteHelper.COMPLETE_EXPENSE,MySQLiteHelper.COMPLETE_DESCRIPTION,MySQLiteHelper.COMPLETE_DAY,MySQLiteHelper.COMPLETE_MONTH,MySQLiteHelper.COMPLETE_YEAR,MySQLiteHelper.COMPLETE_CATEGORY };

  private String[] allColumns_FRIEND={MySQLiteHelper.FRIEND_ID,MySQLiteHelper.FRIEND_NAME,MySQLiteHelper.FRIEND_DUE};
  
  private String[] allColumns_INCOME={MySQLiteHelper.INCOME_ID,MySQLiteHelper.INCOME_SOURCE,MySQLiteHelper.INCOME_AMOUNT};
  
  
  public AddExpenseDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }
  
  public long createOwnEntry(int day,int month,int year,String description,int totalExpense,String Category){
  

	  ContentValues values = new ContentValues();
  
  values.put(MySQLiteHelper.COMPLETE_FRIEND,"SELF");
  values.put(MySQLiteHelper.COMPLETE_EXPENSE,totalExpense);
  values.put(MySQLiteHelper.COMPLETE_DAY,day);
  values.put(MySQLiteHelper.COMPLETE_MONTH,month);	  
  values.put(MySQLiteHelper.COMPLETE_YEAR,year);
  values.put(MySQLiteHelper.COMPLETE_DESCRIPTION,description);
  values.put(MySQLiteHelper.COMPLETE_CATEGORY,Category);
  
  long insertId = database.insert(MySQLiteHelper.TABLE_COMPLETE, null,
	        values);
	  /*  Cursor cursor = database.query(MySQLiteHelper.TABLE_COMPLETE,
	        allColumns_COMPLETE, MySQLiteHelper.COMPLETE_ID + " = " + insertId, null,
	        null, null, null);
		*/
  	return insertId;
  }
 
  public long createFriendEntry(String friendName,int day,int month,int year,String description,int totalExpense,String Category){
	  long insertId=1;
	  ContentValues values1 = new ContentValues();
	  
	  values1.put(MySQLiteHelper.COMPLETE_FRIEND,friendName);
	  values1.put(MySQLiteHelper.COMPLETE_EXPENSE,totalExpense);
	  values1.put(MySQLiteHelper.COMPLETE_DAY,day);
	  values1.put(MySQLiteHelper.COMPLETE_MONTH,month);	  
	  values1.put(MySQLiteHelper.COMPLETE_YEAR,year);
	  values1.put(MySQLiteHelper.COMPLETE_DESCRIPTION,description);
	  values1.put(MySQLiteHelper.COMPLETE_CATEGORY,Category);
	  values1.put(MySQLiteHelper.COMPLETE_BANK, "credit");
	  
	 insertId = database.insertOrThrow(MySQLiteHelper.TABLE_COMPLETE, null,values1);
	 
	 values1.clear();
	// String sql= "INSERT INTO complete (friend) " +   "VALUES ('" + friendName +  "')";
			
	 
	// database.execSQL(sql);
	  return insertId;
	  }
  
 public long friendPay(String name,int amount){
	
	 long insertId=1;
	 int year=Calendar.getInstance().get(Calendar.YEAR);
	 int month=Calendar.getInstance().get(Calendar.MONTH)+1;
	 int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	 
	 ContentValues values=new ContentValues();
	 
	 values.put(MySQLiteHelper.COMPLETE_FRIEND, name);
	 values.put(MySQLiteHelper.COMPLETE_EXPENSE,amount);
	 values.put(MySQLiteHelper.COMPLETE_DAY,day);
	 values.put(MySQLiteHelper.COMPLETE_MONTH,month);	  
	 values.put(MySQLiteHelper.COMPLETE_YEAR,year);	  
	 values.put(MySQLiteHelper.COMPLETE_BANK, "debit");
	 values.put(MySQLiteHelper.COMPLETE_DESCRIPTION,"what the hell, dude's paying");
	 values.put(MySQLiteHelper.COMPLETE_CATEGORY,"friend's payday");
	 
	 insertId = database.insertOrThrow(MySQLiteHelper.TABLE_COMPLETE, null,values);
	 
	 values.clear();
	  return insertId;	  	  
  }
  
  public String friendDue(String[] arr){
	 int len=arr.length;
	 int add,sub,net;
	 add=0;
	 sub=0;
	 String name,result,bank; 
	 Cursor cursor=null;
	 int iExpense,iBank,amount;
	 result="";
	 
	 for(int i=0;i<len-1;i++){
		 
		 name=arr[i];
		 try{
			  cursor=database.rawQuery("SELECT * FROM complete WHERE friend IS '" + name +"' ", null);
			  cursor.moveToFirst();
		  }
		  
		  catch (Exception e)
		  {
			
		  e.printStackTrace();
		  }
		 
		 iExpense=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_EXPENSE);
		 iBank=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_BANK);
		 
		 
		 for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			 amount=cursor.getInt(iExpense);
			 bank=cursor.getString(iBank); 	
		    	
			 if(bank.contentEquals("debit")){
				 
				 sub +=amount;
			 }
			 
			 else if(bank.contentEquals("credit")){
				 
				 add +=amount;
				 
			 }
			 
		    }
		
		 
		 net=add-sub;
		 
		 if(add==0 && sub==0)
			 result += name + "      0 " + '\n' + '\n'; 
	
		 else
			 result += name +"      "+ net + '\n' + '\n';
		 cursor.close();
		 	 add=0;
		 	 sub=0;
	 }
	  
	  
	return result;	  
  }
  
  public String yearlyExpense(){	 
	 int add,sub,net;
	 add=0;
	 sub=0;
	 String result; 
	 Cursor cursor=null;
	 result="";
	 int year=Calendar.getInstance().get(Calendar.YEAR);
	 	 try{
			  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'credit' AND year IS '"+ year +"'", null);
			  cursor.moveToFirst();
			  add=cursor.getInt(0);
		  }
		  
		  catch (Exception e)
		  {
			
		  e.printStackTrace();
		  }
		 
		  
		 try{
			  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'debit' AND year IS '"+ year +"'", null);
			  cursor.moveToFirst();
			  sub=cursor.getInt(0);
		  }
		  
		  catch (Exception e)
		  {
			
		  e.printStackTrace();
		  } 
		 
		
		 
		 net=add-sub;
		 
			 result =  "   IN THIS CURRENT YEAR YOU HAVE SPLURGED RS."+ net +"  ;) ";
		 cursor.close();
		 	 add=0;
		 	 sub=0;
	 
	  
	  
	return result;	  
  }
  
  public String monthlyExpense() {
		// TODO Auto-generated method stub
		 int add,sub,net;
		 add=0;
		 sub=0;
		 String result; 
		 Cursor cursor=null;
		 result="";
		 int year=Calendar.getInstance().get(Calendar.YEAR);
		 int month=Calendar.getInstance().get(Calendar.MONTH)+1;
		 	 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'credit' AND year IS '"+ year +"'  AND month IS '" + month + "'", null);
				  cursor.moveToFirst();
				  add=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  }
			 
			  
			 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'debit' AND year IS '"+ year +"'  AND month IS '" + month + "'", null);
				  cursor.moveToFirst();
				  sub=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  } 
			 
			
			 
			 net=add-sub;
			 
				 result =  "   IN THIS CURRENT MONTH YOU HAVE SPLURGED RS."+ net +"  ;) ";
			 cursor.close();
			 	 add=0;
			 	 sub=0;
		 
		  
		  
		return result;	  
	}
  public String monthlyExpenseBy(int month,int year) {
		// TODO Auto-generated method stub
		 int add,sub,net;
		 add=0;
		 sub=0;
		 String result; 
		 Cursor cursor=null;
		 result="";
		 
		 	 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'credit' AND year IS '"+ year +"'  AND month IS '" + month + "'", null);
				  cursor.moveToFirst();
				  add=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  }
			 
			  
			 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'debit' AND year IS '"+ year +"'  AND month IS '" + month + "'", null);
				  cursor.moveToFirst();
				  sub=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  } 
			 
			
			 
			 net=add-sub;
			 
				 result =  "   IN THIS MONTH YOU HAVE SPLURGED RS."+ net +"  ;) ";
			 cursor.close();
			 	 add=0;
			 	 sub=0;
		 
		  
		  
		return result;	  
	}
  
	public String categoryWisem() {
		// TODO Auto-generated method stub
		 int add,sub,net;
		 add=0;
		 sub=0;
		 String result; 
		 Cursor cursor=null;
		 result="";
		 String categories[]={"Food","Entertainment","Accessories","Apparel","Transportation","Rent"};
		 int year=Calendar.getInstance().get(Calendar.YEAR);
		 int month=Calendar.getInstance().get(Calendar.MONTH)+1;
		 
		 for(int i=0;i<6;i++){
		 	 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'credit' AND year IS '"+ year +"' AND category IS '" + categories[i] +"' AND month IS '"+month+"'" , null);
				  cursor.moveToFirst();
				  add=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  }
			 
			  
			
			 
			
			 
			 net=add;
			 
				 result  = result + "   IN THIS CURRENT YEAR YOU HAVE SPLURGED RS."+ net +" ON " + categories[i] +" ;) \n \n";
				 cursor.close();
			 	 add=0;
			 	 sub=0;
		 
		 }  
		 
		 try{
			  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'debit' AND year IS '"+ year +"'  AND month IS '" + month + "'", null);
			  cursor.moveToFirst();
			  sub=cursor.getInt(0);
		  }
		  
		  catch (Exception e)
		  {
			
		  e.printStackTrace();
		  } 
		 
		 result= result + " IN THIS CURRENT YEAR YOU HAVE GOT RS."+ sub + "  FROM YOUR FRIENDS  ";
		 
		return result;	  
	}
  
	public int[] icategoryWisem() {
		// TODO Auto-generated method stub
		int arr[]={0,0,0,0,0,0,0};
		 int add,sub,net;
		 add=0;
		 sub=0;
		 String result; 
		 Cursor cursor=null;
		 result="";
		 String categories[]={"Food","Entertainment","Accessories","Apparel","Transportation","Rent"};
		 int year=Calendar.getInstance().get(Calendar.YEAR);
		 int month=Calendar.getInstance().get(Calendar.MONTH)+1;
		 
		 for(int i=0;i<6;i++){
		 	 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'credit' AND year IS '"+ year +"' AND category IS '" + categories[i] +"' AND month IS '"+month+"'" , null);
				  cursor.moveToFirst();
				  add=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  }
			 
			  
			
			 
			
			 
			 net=add;
			 	arr[i]=net;
				 cursor.close();
			 	 add=0;
			 	 sub=0;
		 
		 }  
		 
		 try{
			  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'debit' AND year IS '"+ year +"'  AND month IS '" + month + "'", null);
			  cursor.moveToFirst();
			  sub=cursor.getInt(0);
		  }
		  
		  catch (Exception e)
		  {
			
		  e.printStackTrace();
		  } 
		 
		 arr[6]=sub;
		return arr;	  
	}
  
	public int[] icategoryWisey() {
		// TODO Auto-generated method stub
		int arr[]={0,0,0,0,0,0,0};
		 int add,sub,net;
		 add=0;
		 sub=0;
		 String result; 
		 Cursor cursor=null;
		 result="";
		 String categories[]={"Food","Entertainment","Accessories","Apparel","Transportation","Rent"};
		 int year=Calendar.getInstance().get(Calendar.YEAR);
		 
		 for(int i=0;i<6;i++){
		 	 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'credit' AND year IS '"+ year +"' AND category IS '" + categories[i] +"'" , null);
				  cursor.moveToFirst();
				  add=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  }
			 
			  
			 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'debit' AND year IS '"+ year +"' AND category IS '" + categories[i] +"'" , null);
				  cursor.moveToFirst();
				  sub=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  } 
			 
			
			 
			 net=add-sub;
			 	arr[i]=net;
				 cursor.close();
			 	 add=0;
			 	 sub=0;
		 
		 }  
		 
		 try{
			  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'debit' AND year IS '"+ year +"'", null);
			  cursor.moveToFirst();
			  sub=cursor.getInt(0);
		  }
		  
		  catch (Exception e)
		  {
			
		  e.printStackTrace();
		  } 
		 arr[6]=sub;
		 
		return arr;	  
	}
  
	public String categoryWisey() {
		// TODO Auto-generated method stub
		 int add,sub,net;
		 add=0;
		 sub=0;
		 String result; 
		 Cursor cursor=null;
		 result="";
		 String categories[]={"Food","Entertainment","Accessories","Apparel","Transportation","Rent"};
		 int year=Calendar.getInstance().get(Calendar.YEAR);
		 
		 for(int i=0;i<6;i++){
		 	 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'credit' AND year IS '"+ year +"' AND category IS '" + categories[i] +"'" , null);
				  cursor.moveToFirst();
				  add=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  }
			 
			  
			 try{
				  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'debit' AND year IS '"+ year +"' AND category IS '" + categories[i] +"'" , null);
				  cursor.moveToFirst();
				  sub=cursor.getInt(0);
			  }
			  
			  catch (Exception e)
			  {
				
			  e.printStackTrace();
			  } 
			 
			
			 
			 net=add-sub;
			 
				 result  = result + "   IN THIS CURRENT YEAR YOU HAVE SPLURGED RS."+ net +" ON " + categories[i] +" ;) \n \n";
				 cursor.close();
			 	 add=0;
			 	 sub=0;
		 
		 }  
		 
		 try{
			  cursor=database.rawQuery("SELECT SUM(expense) FROM complete WHERE bank IS 'debit' AND year IS '"+ year +"'", null);
			  cursor.moveToFirst();
			  sub=cursor.getInt(0);
		  }
		  
		  catch (Exception e)
		  {
			
		  e.printStackTrace();
		  } 
		 
		 result= result + " IN THIS CURRENT YEAR YOU HAVE GOT RS."+ sub + "  FROM YOUR FRIENDS  ";
		 
		return result;	  
	}
  
  
  
  public long createFriendName(String friendName){
	  String[] allColumns = { MySQLiteHelper.FRIENDNAME_ID,
		      MySQLiteHelper.FRIENDNAME_NAME };
	  long insertId=1;
	  ContentValues values2 = new ContentValues();
	  
	  values2.put(MySQLiteHelper.FRIENDNAME_NAME,friendName);
	 
	 insertId = database.insertOrThrow(MySQLiteHelper.TABLE_FRIENDNAME, null,values2);
	 
	 
		    return insertId;
	 
	 
	// String sql= "INSERT INTO complete (friend) " +   "VALUES ('" + friendName +  "')";
			
	 
	// database.execSQL(sql)
	  }
  
public long createBudget(String particular,int amount){
	 
	Cursor cursor=null;
	
	try{
		cursor=database.rawQuery("SELECT * FROM budget WHERE particular IS '"+particular+"'", null);		
	}
	
	 catch (Exception e)
	  {
		
	  e.printStackTrace();
	  }
	if((cursor)!=null)
		database.delete(MySQLiteHelper.TABLE_BUDGET, MySQLiteHelper.BUDGET_PARTICULAR + " = '" + particular+"'", null);

	long insertId=0;
	ContentValues values=new ContentValues();
	
	values.put(MySQLiteHelper.BUDGET_PARTICULAR, particular);
	values.put(MySQLiteHelper.BUDGET_AMOUNT, amount);	
	
	insertId = database.insertOrThrow(MySQLiteHelper.TABLE_BUDGET, null,values);
	return insertId;	
}
  
  
  public List<String> getAllFriends() {
	  String[] allColumns = { MySQLiteHelper.FRIENDNAME_ID,
		      MySQLiteHelper.FRIENDNAME_NAME };
	    List<String> friends = new ArrayList<String>();
	    Cursor cursor=null;
	    
	    cursor=database.rawQuery("SELECT * FROM  nfriend_name",null);

	    String friend=null;
	  /*  
	    while (!cursor.isAfterLast()) {
	      Comment comment = cursorToComment(cursor);
	      comments.add(comment);
	      cursor.moveToNext();
	    }
	   */
	    
	    int iRow=cursor.getColumnIndex(MySQLiteHelper.FRIENDNAME_ID);
	    int iFriend=cursor.getColumnIndex(MySQLiteHelper.FRIENDNAME_NAME);
	    for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
	    	
	    	friend=cursor.getString(iFriend);
	    	friends.add(friend);	    	
	    }
	    
	    // Make sure to close the cursor
	    cursor.close();
	    return friends;
	  }
/*
  private Comment cursorToComment(Cursor cursor) {
	    Comment comment = new Comment();
	    comment.setId(cursor.getLong(0));
	    comment.setComment(cursor.getString(1));
	    return comment;
	  }
*/
  
  

 
  public String getFriendData (String name){
	  
	  String data = new String();
	  Cursor cursor=null;
	  boolean didItWork=true;
	  try{
		  cursor=database.rawQuery("SELECT * FROM complete WHERE friend IS '" + name +"' ", null);
		  cursor.moveToFirst();
	  }
	  
	  catch (Exception e)
	  {
		
	  e.printStackTrace();
	  }
	 
	    int iRow=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_ID);
	    int iFriend=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_FRIEND);
	    int iExpense=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_EXPENSE);
	    int iDescription=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_DESCRIPTION);
	    int iCategory=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_CATEGORY);
	    int iDay=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_DAY);
	    int iMonth=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_MONTH);
	    int iYear=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_YEAR);

	    
	    
	    for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			data =data +"Rs."+
					cursor.getInt(iExpense) + " " +cursor.getString(iDescription) +" "+
					cursor.getString(iCategory)+ " "+
					cursor.getInt(iDay) + "/"+cursor.getInt(iMonth) + "/"+cursor.getInt(iYear)+
					'\n'+'\n';
		}	    
	    // Make sure to close the cursor
	    if(data.length()==0)
	    	didItWork=false;
	    	
	    cursor.close();
	    if (didItWork)
	    	return data;
	    else
	    	return "0";
	  
	  
  }
 /*
  public String getBudget(String particular){
	  String p;
	  int a;
	  String data=new String();
	  Cursor cursor=null;
	  try{
		  cursor=database.rawQuery("SELECT * FROM budget WHERE particular IS '" + particular +"' ", null);
		  cursor.moveToFirst();
	  }
	  
	  catch (Exception e)
	  {
		
	  e.printStackTrace();
	  }	  
	  
	  if(cursor!=null){
	  int iPar=cursor.getColumnIndex(MySQLiteHelper.BUDGET_PARTICULAR);
	  int iAmt=cursor.getColumnIndex(MySQLiteHelper.BUDGET_AMOUNT);
	  
	  cursor.moveToFirst();
	  
	  p=cursor.getString(iPar);
	  a=cursor.getInt(iAmt);
	  
	  data="Your "+ particular+" Budget is : Rs."+a;
	  }
	  else {
		  data="Your "+ particular+" Budget is Not Set Yet ...";
	  }
	  return data;
	  
	  
  }
 */
  public String getBudget(String particular){
	 
	  String data = new String();
	  Cursor cursor=null;
	  boolean didItWork=true;
	  try{
		  cursor=database.rawQuery("SELECT * FROM budget WHERE particular IS '" + particular +"' ", null);
		  cursor.moveToFirst();
	  }
	  
	  catch (Exception e)
	  {
		
	  e.printStackTrace();
	  }
	 
	    int iRow=cursor.getColumnIndex(MySQLiteHelper.BUDGET_ID);
	    int iAmt=cursor.getColumnIndex(MySQLiteHelper.BUDGET_AMOUNT);
	    int iPar=cursor.getColumnIndex(MySQLiteHelper.BUDGET_PARTICULAR);
	  

	    
	    
	    for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			data = "Your "+ particular+" Budget is : Rs."+cursor.getInt(iAmt);
		}	    
	    // Make sure to close the cursor
	    if(data.length()==0)
	    	didItWork=false;
	    	
	    cursor.close();
	    if (didItWork)
	    	return data;
	    else
	    	return "Your "+ particular+" Budget is Not Set Yet ...";
	  
	  
	  
  }
  
  
  public String getStringCompleteData() {
	    String data = new String();
	    Cursor cursor  = null;
try
{
	//database.query(data, allColumns_COMPLETE, data, allColumns_COMPLETE, data, data, data, data)
	//database.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal)
	cursor = database.rawQuery(			"SELECT * FROM  complete",null);
	        
	cursor.moveToFirst();
}
catch (Exception e)
{
e.printStackTrace();
}

	    
	/*    while (!cursor.isAfterLast()) {
	      Comment comment = cursorToComment(cursor);
	      comments.add(comment);
	      cursor.moveToNext();
	    }
	    */
  		
	    int iRow=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_ID);
	    int iFriend=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_FRIEND);
	    int iExpense=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_EXPENSE);
	    int iDescription=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_DESCRIPTION);
	    int iCategory=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_CATEGORY);
	    int iDay=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_DAY);
	    int iMonth=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_MONTH);
	    int iYear=cursor.getColumnIndex(MySQLiteHelper.COMPLETE_YEAR);

	    
	    for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			data =data + cursor.getString(iRow) + ". " +cursor.getString(iFriend) +"  "+"Rs."+
					cursor.getInt(iExpense) + " " +cursor.getString(iDescription) +" "+
					cursor.getString(iCategory)+ " "+
					cursor.getInt(iDay) + "/" + cursor.getInt(iMonth) + "/"+cursor.getInt(iYear)+
					'\n'+'\n';
		}	    
	    // Make sure to close the cursor
	    cursor.close();
	    return data;
	  }

public void removeTransaction(int final_id) {
	// TODO Auto-generated method stub
	
	database.delete(MySQLiteHelper.TABLE_COMPLETE, MySQLiteHelper.COMPLETE_ID + " = " + final_id, null);
	
}



 
}