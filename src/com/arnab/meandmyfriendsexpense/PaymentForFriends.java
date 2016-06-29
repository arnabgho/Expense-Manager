package com.arnab.meandmyfriendsexpense;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import com.arnab.meandmyfriendsexpense.R.id;

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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentForFriends extends Activity implements OnItemSelectedListener, OnClickListener{
	Button manyFriends,mPickDate;
	EditText description,expense,day,month,year;
	Spinner spinner;
	TextView mDateDisplay,exp,cat,des,payFor;
	String categories[]={"Food","Entertainment","Accessories","Apparel","Transportation","Rent"};
	int sday,smonth,syear,number,j;
	long result;
	public AddExpenseDataSource datasource;
	public CommentsDataSource datasource1,datasource2;
	String copy[];
	CheckBox current;
	String sCategory,sDescription,tExpense,sName;
	int sExpense;
	LinearLayout layout;
	 static final int DATE_DIALOG_ID = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paymentforfriends);
		
		
		description=(EditText)findViewById(R.id.etPaymentForFriend_Description);
		expense=(EditText)findViewById(R.id.etPaymentForFriend_TotalExpense);
		
		mPickDate = (Button) findViewById(R.id.pickDate1);
		layout=(LinearLayout)findViewById(R.id.grouplayout1);
		manyFriends=(Button)findViewById(R.id.bPaymentForFriend_ManyFriends);
		
		
		des=(TextView)findViewById(R.id.tvPaymentForFriend_Description);
		cat=(TextView)findViewById(R.id.tvPaymentForFriend_Category);
		exp=(TextView)findViewById(R.id.tvPaymentForFriend_total);
		payFor=(TextView)findViewById(R.id.tvPaymentForFriend_payfor);
	
		
		exp.setTextColor(Color.parseColor("#FFFFFF"));
		des.setTextColor(Color.parseColor("#FFFFFF"));
		cat.setTextColor(Color.parseColor("#FFFFFF"));
		payFor.setTextColor(Color.parseColor("#FFFFFF"));
		mPickDate.setTextColor(Color.parseColor("#FFFFFF"));
		manyFriends.setTextColor(Color.parseColor("#FFFFFF"));
		
		description.setBackgroundColor(Color.argb(200,100, 70, 180));
		expense.setBackgroundColor(Color.argb(200,100, 70, 180));
		
		
		manyFriends.getBackground().setColorFilter(new LightingColorFilter(0xFF0000FF, 0x00000000));
		mPickDate.getBackground().setColorFilter(new LightingColorFilter(0xFF0000FF, 0x00000000));

		manyFriends.setOnClickListener(this);
		
		datasource1 = new CommentsDataSource(this);
	    datasource1.open();
		
		 String checkboxtext[]=datasource1.getAllCommentsString();
		
		 
		 number=checkboxtext.length;
		
		 
		for(int i=1;i<=checkboxtext.length;i++)
        {
        final CheckBox checkbox=new CheckBox(this);
        checkbox.setId(i);
        checkbox.setText(checkboxtext[i-1]);
        checkbox.setTextColor(Color.parseColor("#FFFFFF"));
        layout.addView(checkbox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
              //  doCheck(checkbox);
            }
        });
        }
		
		datasource1.close();
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(PaymentForFriends.this,android.R.layout.simple_spinner_item,categories);
		spinner=(Spinner)findViewById(R.id.spinPaymentForFriend_Category);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		spinner.setBackgroundColor(Color.argb(120,40, 200, 200));

		
		
		mDateDisplay = (TextView) findViewById(R.id.dateDisplay1);
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
		
		
		switch(arg0.getId())
		{
			
			case R.id.bPaymentForFriend_ManyFriends:
				
				 datasource2 = new CommentsDataSource(this);
				datasource2.open();
				
				String checkboxtext1[]=datasource2.getAllCommentsString();
				tExpense=expense.getText().toString();
				
								
				sDescription=description.getText().toString();
				if(!tExpense.contentEquals("")&&!sDescription.contentEquals("")){
					
				sExpense=Integer.parseInt(tExpense);
				int nchecked=0;
				for(int i=0;i<number;i++){
					 current = (CheckBox)findViewById(i+1);
					
					if(current.isChecked()){
						nchecked++;
					}
				}
				sExpense=sExpense/nchecked;
				for(int j=0;j<number;j++){
					
					current=(CheckBox)findViewById(j+1);
					if(current.isChecked()){
						sName=checkboxtext1[j];
						try{
							 datasource=new AddExpenseDataSource(PaymentForFriends.this);
							datasource.open();
								
						result=datasource.createFriendEntry(sName,sday, smonth +1, syear, sDescription, sExpense, sCategory);		
							datasource.close();
							
						}
						catch(Exception e){
							e.printStackTrace();
							String error=e.toString();
							Dialog d=new Dialog(this);
							 d.setTitle("Dang It !!");
							 TextView tv=new TextView(this);
							 tv.setText(error);
							 d.setContentView(tv);
							 d.show();
							didItWork=false;
							
						}
						
					}
				}
				if(didItWork){
					 Dialog d=new Dialog(this);
					 d.setTitle("Heck Yeah !!");
					 TextView tv1=new TextView(this);
					 tv1.setText("success "+result);
					 d.setContentView(tv1);
					 d.show();
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
				
				datasource2.close();
		}
		
	}
}
