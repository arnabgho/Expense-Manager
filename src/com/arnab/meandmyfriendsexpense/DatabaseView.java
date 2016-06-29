package com.arnab.meandmyfriendsexpense;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DatabaseView extends Activity {

	TextView tv;
	AddExpenseDataSource view;
	String result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.databaseview);
		TextView tv = (TextView)findViewById(R.id.tvDatabaseView);
	view = new AddExpenseDataSource(DatabaseView.this);
		
	view.open();
	
		result=view.getStringCompleteData();
		
		tv.setText(result);
		view.close();
		
	}
	
	
	

}
