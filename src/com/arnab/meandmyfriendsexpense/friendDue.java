package com.arnab.meandmyfriendsexpense;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class friendDue extends Activity{

	CommentsDataSource datasource;
	AddExpenseDataSource datasource1;
	TextView tv;
	 String result;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_due);
		tv=(TextView)findViewById(R.id.tvFriendDue_list);
		
		datasource=new CommentsDataSource(this);
		datasource.open();
		
		datasource1=new AddExpenseDataSource(this);
		datasource1.open();
		String friends[]= datasource.getAllFriendsString();
		
		result=datasource1.friendDue(friends);
		
		tv.setTextColor(Color.parseColor("#FFFFFF"));
		tv.setText(result);
		datasource.close();
		datasource1.close();
		
		
	}

	
	
	
}
