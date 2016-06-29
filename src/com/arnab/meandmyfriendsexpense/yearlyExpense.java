package com.arnab.meandmyfriendsexpense;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class yearlyExpense extends Activity{

	AddExpenseDataSource datasource;
	String data,name;
	TextView tv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yearly_expense);
		tv=(TextView)findViewById(R.id.tvYearlyExpense_data);
		tv.setTextColor(Color.parseColor("#FFFFFF"));
		
		
		Bundle extras= getIntent().getExtras();
		name=extras.getString("name");
		
		
		datasource=new AddExpenseDataSource(this);
		datasource.open();
		
		if(name.contentEquals("yearly")){
			data=datasource.yearlyExpense();
		}
		
		
		
		
		else if(name.contentEquals("Category Wise Monthly"))	
			data=datasource.categoryWisem();
				
		else if(name.contentEquals("Category Wise Yearly"))	
			data=datasource.categoryWisey();
		
		else if(name.contentEquals("monthly"))
			data=datasource.monthlyExpense();
		
		
		
		tv.setText(data);
		datasource.close();
		
	}	
	
}
