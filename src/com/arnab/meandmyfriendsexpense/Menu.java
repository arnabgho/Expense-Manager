package com.arnab.meandmyfriendsexpense;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

	String classes[]={"AddExpense","Friend","CumulativeExpense","DatabaseView","Add_Friend","PaymentForFriends","Incomes","RemoveTransaction"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		//full screen
		/*requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		
		setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, classes));
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String cheese=classes[position];
		Class ourClass=null;
		try {
			
			 ourClass = Class.forName("com.example.expensemanager."+cheese);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent ourIntent = new Intent(Menu.this, ourClass);
		startActivity(ourIntent);
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
			Intent i= new Intent(Menu.this,a);
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
			Intent j= new Intent(Menu.this,b);
			startActivity(j);
			break;
		case R.id.exit:
			finish();
			break;
			
			
		}
		return false;
	}


}