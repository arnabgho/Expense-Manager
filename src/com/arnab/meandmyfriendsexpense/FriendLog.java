package com.arnab.meandmyfriendsexpense;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FriendLog extends Activity{
	String name,result;
	AddExpenseDataSource datasource;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_log);
		Bundle extras=getIntent().getExtras();
		name=extras.getString("name");
		tv=(TextView)findViewById(R.id.tvFriendLog_data);
		
		datasource=new AddExpenseDataSource(FriendLog.this);
		datasource.open();
		result=datasource.getFriendData(name);
		
		if(result.contentEquals("0"))
			tv.setText("No Such Friend Dude , Or The Friend Has no Transactions");
		else
			tv.setText(result);
		datasource.close();
	}

}
