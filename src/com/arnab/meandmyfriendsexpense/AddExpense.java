package com.arnab.meandmyfriendsexpense;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddExpense extends Activity implements OnItemSelectedListener, OnClickListener{
	Button update,payForFriend,mPickDate;
	EditText description,expense,day,month,year;
	Spinner spinner;
	TextView mDateDisplay,cat,des,exp;
	String categories[]={"Food","Entertainment","Accessories","Apparel","Transportation","Rent"};
	int sday,smonth,syear;
	long result;
	public AddExpenseDataSource datasource;
	 static final int DATE_DIALOG_ID = 0;
	String sCategory,sDescription,tExpense;
	int sExpense;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addexpense);
		update=(Button)findViewById(R.id.bAddExpense_Update);
		mPickDate = (Button) findViewById(R.id.pickDate);
		description=(EditText)findViewById(R.id.etAddexpense_description);
		expense=(EditText)findViewById(R.id.etAddExpense_TotalExpense);
		
		
		exp=(TextView)findViewById(R.id.tvAddExpense_total);
		des=(TextView)findViewById(R.id.tvAddExpense_Description);
		cat=(TextView)findViewById(R.id.tvAddExpense_Category);
		
		
		exp.setTextColor(Color.parseColor("#FFFFFF"));
		des.setTextColor(Color.parseColor("#FFFFFF"));
		cat.setTextColor(Color.parseColor("#FFFFFF"));
		mPickDate.setTextColor(Color.parseColor("#FFFFFF"));
		update.setTextColor(Color.parseColor("#FFFFFF"));
		
		description.setBackgroundColor(Color.argb(200,100, 70, 180));
		expense.setBackgroundColor(Color.argb(200,100, 70, 180));

		
		//update.getBackground().setColorFilter(new LightingColorFilter(0xFFFF0000, 0x00000000));
		update.setOnClickListener(this);
	//	mPickDate.getBackground().setColorFilter(new LightingColorFilter(0xFFFF0000, 0x00000000));

		update.getBackground().setColorFilter(new LightingColorFilter(0xFF0000FF, 0x00000000));
		mPickDate.getBackground().setColorFilter(new LightingColorFilter(0xFF0000FF, 0x00000000));
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(AddExpense.this,android.R.layout.simple_spinner_item,categories);
		spinner=(Spinner)findViewById(R.id.spinAddExpense_Category);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		spinner.setBackgroundColor(Color.argb(120,40, 200, 200));
		
		mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
	    
		mDateDisplay.setTextColor(Color.parseColor("#FFFFFF"));
	    // add a click listener to the button
	    mPickDate.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            showDialog(DATE_DIALOG_ID);
	        }
	    });

	    // get the current date
	    final Calendar c = Calendar.getInstance();
	    syear = c.get(Calendar.YEAR);
	    smonth = c.get(Calendar.MONTH);
	    sday = c.get(Calendar.DAY_OF_MONTH);

	    // display the current date (this method is below)
	    updateDisplay();
		
	}
	
	
	
	
	private void updateDisplay() {
	    
	    mDateDisplay.setText( sday+"/"+(smonth+1)+"/" +syear );
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener =
	        new DatePickerDialog.OnDateSetListener() {

	            public void onDateSet(DatePicker view, int year,
	                                  int monthOfYear, int dayOfMonth) {
	                syear = year;
	                smonth = monthOfYear;
	                sday = dayOfMonth;
	                updateDisplay();
	            }
	        };

	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DATE_DIALOG_ID:
	        return new DatePickerDialog(this, mDateSetListener, syear, smonth,
	                sday);
	    }
	    return null;
	}
	
	
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		int position=spinner.getSelectedItemPosition();
		sCategory=categories[position];
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		boolean didItWork=true;
		Log.d(ACTIVITY_SERVICE, "Hey i m finally inside on Click");
		
		switch(arg0.getId())
		{
		
		
		case R.id.bAddExpense_Update:
			
			   Log.d(ACTIVITY_SERVICE, "Hey i m finally inside on update"); 
			tExpense=expense.getText().toString();
			
			
			sDescription=description.getText().toString();
			
			if(!tExpense.contentEquals("")&&!sDescription.contentEquals("")){
			try{
				sExpense=Integer.parseInt(tExpense);
				 datasource=new AddExpenseDataSource(AddExpense.this);
				datasource.open();
					
			result=datasource.createFriendEntry("SELF",sday, smonth+1, syear, sDescription, sExpense, sCategory);		
				datasource.close();
				
			}
			catch(Exception e){
				e.printStackTrace();
				String error=e.toString();
				Dialog d=new Dialog(this);
				 d.setTitle("Dang It !!");
				 TextView tv1=new TextView(this);
				 tv1.setText(error);
				 d.setContentView(tv1);
				 d.show();
				didItWork=false;
				
			}
			finally{
				if(didItWork){
					 Dialog d=new Dialog(this);
					 d.setTitle("Heck Yeah !!");
					 TextView tv1=new TextView(this);
					 tv1.setText("success "+result);
					 d.setContentView(tv1);
					 d.show();
				}
			}
		}
			else{
				
				Dialog d=new Dialog(this);
				TextView tv2=new TextView(this);
				d.setTitle("Dude ,Hey dude");
				tv2.setText("Any Parameter can't be neglected");
				d.setContentView(tv2);
				d.show();
			}
			
			break;
		
		
		
		}
		
	}
}
