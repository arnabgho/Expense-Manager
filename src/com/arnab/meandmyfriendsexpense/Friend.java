package com.arnab.meandmyfriendsexpense;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Friend extends Activity implements OnClickListener{

	TextView tvAmount,tvName;
	EditText name,amount;
	Button go,update,owed;
	AddExpenseDataSource datasource;
	String sName,tAmount;
	int sAmount;
	long result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendaccount);
		
		owed=(Button)findViewById(R.id.bFriendAccount_AmountOwedByFriend);
		go=(Button)findViewById(R.id.bFriendAccount_Go);
		update=(Button)findViewById(R.id.bFriendAccount_Update);
		name=(EditText)findViewById(R.id.etFriendAccount_Name);
		amount=(EditText)findViewById(R.id.etFriendAccount_Payment);
		tvName=(TextView)findViewById(R.id.tvFriendAccount_Name);
		tvAmount=(TextView)findViewById(R.id.tvFriendAccount_Amount);
		
		tvName.setTextColor(Color.parseColor("#FFFFFF"));
		tvAmount.setTextColor(Color.parseColor("#FFFFFF"));
		name.setTextColor(Color.parseColor("#FFFFFF"));
		amount.setTextColor(Color.parseColor("#FFFFFF"));
		owed.setTextColor(Color.parseColor("#FFFFFF"));
		go.setTextColor(Color.parseColor("#FFFFFF"));
		update.setTextColor(Color.parseColor("#FFFFFF"));
		
		
		name.setBackgroundColor(Color.argb(200,100, 70, 180));
		amount.setBackgroundColor(Color.argb(200,100, 70, 180));
		
		owed.getBackground().setColorFilter(new LightingColorFilter(Color.CYAN, 0x000000FF));
		go.getBackground().setColorFilter(new LightingColorFilter(Color.GREEN, 0x00000000));
		update.getBackground().setColorFilter(new LightingColorFilter(Color.MAGENTA, 0x00000000));

		
		go.setOnClickListener(this);
		update.setOnClickListener(this);
		owed.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.bFriendAccount_Go:
			sName=name.getText().toString();
			
			
			if(!sName.contentEquals("")){
			Intent i= new Intent(Friend.this,FriendLog.class);
			i.putExtra("name",sName);
			startActivity(i);
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
		
			
		case R.id.bFriendAccount_Update:
			boolean didItWork=true;
			sName=name.getText().toString();
			tAmount=amount.getText().toString();
			
			if(!sName.contentEquals("")&&!tAmount.contentEquals("")){
			sAmount=Integer.parseInt(tAmount);
			try{
				 datasource=new AddExpenseDataSource(Friend.this);
				datasource.open();
					
			result=datasource.friendPay(sName, sAmount);		
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
					 tv1.setText("success ");
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
			
		case R.id.bFriendAccount_AmountOwedByFriend:
			
			Intent intent= new Intent(Friend.this,friendDue.class);
			startActivity(intent);
			break;
			
		}
	}

}
