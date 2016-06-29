package com.arnab.meandmyfriendsexpense;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu_new1 extends Activity implements OnClickListener{

	
	Button AddExpense,Friend,CumulativeExpense,DatabaseView,Add_Friend,PaymentForFriends,Incomes,RemoveTransaction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		
		AddExpense=(Button)findViewById(R.id.bMenu_AddExpense);
		Friend=(Button)findViewById(R.id.bMenu_FriendAccount);
		CumulativeExpense=(Button)findViewById(R.id.bMenu_CumulativeExpense);
		DatabaseView=(Button)findViewById(R.id.bMenu_DatabaseView);
		Add_Friend=(Button)findViewById(R.id.bMenu_AddFriend);
		PaymentForFriends=(Button)findViewById(R.id.bMenu_PaymentForFriends);
		Incomes=(Button)findViewById(R.id.bMenu_Incomes);
		RemoveTransaction=(Button)findViewById(R.id.bMenu_Remove);
		
		AddExpense.setTextColor(Color.parseColor("#FFFFFF"));
		Friend.setTextColor(Color.parseColor("#FFFFFF"));
		CumulativeExpense.setTextColor(Color.parseColor("#FFFFFF"));
		DatabaseView.setTextColor(Color.parseColor("#FFFFFF"));
		Add_Friend.setTextColor(Color.parseColor("#FFFFFF"));
		PaymentForFriends.setTextColor(Color.parseColor("#FFFFFF"));
		Incomes.setTextColor(Color.parseColor("#FFFFFF"));
		RemoveTransaction.setTextColor(Color.parseColor("#FFFFFF"));
		
		
		AddExpense.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 78, 121, 222), 0x00000000));
		DatabaseView.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 78, 121, 222), 0x00000000));
		CumulativeExpense.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 227, 102, 146), 0x00000000));
		Friend.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 227, 102, 146), 0x00000000));
		Add_Friend.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 78, 121, 222), 0x00000000));
		Incomes.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 78, 121, 222), 0x00000000));
		PaymentForFriends.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 227, 102, 146), 0x00000000));
		RemoveTransaction.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 227, 102, 146), 0x00000000));

		
		
		AddExpense.setOnClickListener(this);
		Friend.setOnClickListener(this);
		CumulativeExpense.setOnClickListener(this);
		DatabaseView.setOnClickListener(this);
		Add_Friend.setOnClickListener(this);
		PaymentForFriends.setOnClickListener(this);
		Incomes.setOnClickListener(this);
		RemoveTransaction.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		
		case R.id.bMenu_AddExpense:
			
			Intent a=new Intent(Menu_new1.this,AddExpense.class);
			startActivity(a);
			break;
			
		case R.id.bMenu_FriendAccount:	
			Intent b=new Intent(Menu_new1.this,Friend.class);
			startActivity(b);
			break;
		
		case R.id.bMenu_AddFriend:	
			Intent c=new Intent(Menu_new1.this,Add_Friend.class);
			startActivity(c);
			break;	
		case R.id.bMenu_CumulativeExpense:	
			Intent d=new Intent(Menu_new1.this,CumulativeExpense.class);
			startActivity(d);
			break;	
			
		case R.id.bMenu_DatabaseView:	
			Intent e=new Intent(Menu_new1.this,DatabaseView.class);
			startActivity(e);
			break;	
			
		case R.id.bMenu_Incomes:	
			Intent f=new Intent(Menu_new1.this,Incomes.class);
			startActivity(f);
			break;	
			
		case R.id.bMenu_PaymentForFriends:	
			Intent g=new Intent(Menu_new1.this,PaymentForFriends.class);
			startActivity(g);
			break;	
		case R.id.bMenu_Remove:	
			Intent h=new Intent(Menu_new1.this,RemoveTransaction.class);
			startActivity(h);
			break;	
		}		
		
		
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		 super.onCreateOptionsMenu(menu);
		 MenuInflater blowUp=getMenuInflater();
		 blowUp.inflate(R.menu.cool_menu, menu);
		 return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.aboutUs:
			Class a=null;
			try {
				 a=Class.forName("com.example.expensemanager.AboutUs");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent i= new Intent(Menu_new1.this,a);
			startActivity(i);
			break;
		
		case R.id.preferences:
			Class b=null;
			try {
				 b=Class.forName("com.example.expensemanager.Prefs");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent j= new Intent(Menu_new1.this,b);
			startActivity(j);
			break;
		case R.id.exit:
			finish();
			break;
			
			
		}
		return false;
	}
	
	
}
