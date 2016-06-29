package com.arnab.meandmyfriendsexpense;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Budget extends Activity implements OnClickListener, OnItemSelectedListener{

	String categories[]={"Food","Entertainment","Accessories","Apparel","Transportation","Rent"};	
	Button monthly,yearly,categoryWise;
	EditText emonthly,eyearly,ecategoryWise;
	Spinner spinner;
	String smonthly,syearly,scategoryWise;
	TextView tvMonthly,tvYearly,tvCategoryWise;
	AddExpenseDataSource datasource;
	int fmonthly,fyearly,fcategoryWise;
	String sCategory;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.budget);		
		monthly=(Button)findViewById(R.id.bBudgetMonthly);
		yearly=(Button)findViewById(R.id.bBudgetYearly);
		categoryWise=(Button)findViewById(R.id.bBudgetCategoryWise);
		
		emonthly=(EditText)findViewById(R.id.etBudget_Monthly);
		eyearly=(EditText)findViewById(R.id.etBudget_Yearly);
		ecategoryWise=(EditText)findViewById(R.id.etBudget_CategoryWise);
		
		tvMonthly=(TextView)findViewById(R.id.tvBudget_Monthly);
		tvYearly=(TextView)findViewById(R.id.tvBudget_Yearly);
		tvCategoryWise=(TextView)findViewById(R.id.tvBudget_CategoryWise);
		
		monthly.setOnClickListener(this);
		yearly.setOnClickListener(this);
		categoryWise.setOnClickListener(this);
		
		tvMonthly.setTextColor(Color.parseColor("#FFFFFF"));
		tvYearly.setTextColor(Color.parseColor("#FFFFFF"));
		tvCategoryWise.setTextColor(Color.parseColor("#FFFFFF"));
		emonthly.setTextColor(Color.parseColor("#FFFFFF"));
		eyearly.setTextColor(Color.parseColor("#FFFFFF"));
		ecategoryWise.setTextColor(Color.parseColor("#FFFFFF"));
		
		
		monthly.setTextColor(Color.parseColor("#FFFFFF"));
		yearly.setTextColor(Color.parseColor("#FFFFFF"));
		categoryWise.setTextColor(Color.parseColor("#FFFFFF"));	
		
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(Budget.this,android.R.layout.simple_spinner_item,categories);
		spinner=(Spinner)findViewById(R.id.spinner_Budget);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		spinner.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 89, 227, 39), 0x00000000));
		spinner.setBackgroundColor(Color.argb(120,50, 70, 180));
		
		
		emonthly.setBackgroundColor(Color.argb(60,50, 70, 180));
		eyearly.setBackgroundColor(Color.argb(60,50, 70, 180));
		ecategoryWise.setBackgroundColor(Color.argb(60,50, 70, 180));

		CallMe();
	}

	private void CallMe() {
		// TODO Auto-generated method stub
		datasource=new AddExpenseDataSource(this);
		datasource.open();
		smonthly=datasource.getBudget("Monthly");
		syearly=datasource.getBudget("Yearly");
		scategoryWise=datasource.getBudget(sCategory);
		tvMonthly.setText(smonthly);
		tvYearly.setText(syearly);
		tvCategoryWise.setText(scategoryWise);
		datasource.close();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
		case R.id.bBudgetMonthly:
			
			smonthly=emonthly.getText().toString();
			
			if(!smonthly.contentEquals("")){
			fmonthly=Integer.parseInt(smonthly);
			datasource=new AddExpenseDataSource(this);
			datasource.open();
			
			datasource.createBudget("Monthly", fmonthly);
			datasource.close();
			CallMe();}
			else{
				Dialog d=new Dialog(this);
				TextView tv2=new TextView(this);
				d.setTitle("Dude ,Hey dude");
				tv2.setText("Any Parameter can't be neglected");
				d.setContentView(tv2);
				d.show();
			}
			break;
			
		case R.id.bBudgetYearly:
			syearly=eyearly.getText().toString();
			if(!syearly.contentEquals("")){
			fyearly=Integer.parseInt(syearly);
			datasource=new AddExpenseDataSource(this);
			datasource.open();
			
			datasource.createBudget("Yearly", fyearly);
			datasource.close();	
			CallMe();}
			else{
				Dialog d=new Dialog(this);
				TextView tv2=new TextView(this);
				d.setTitle("Dude ,Hey dude");
				tv2.setText("Any Parameter can't be neglected");
				d.setContentView(tv2);
				d.show();
				
			}
			break;
		case R.id.bBudgetCategoryWise:
			scategoryWise=ecategoryWise.getText().toString();
			if(!scategoryWise.contentEquals("")){
			fcategoryWise=Integer.parseInt(scategoryWise);
			datasource=new AddExpenseDataSource(this);
			datasource.open();
			
			datasource.createBudget(sCategory, fcategoryWise);
			datasource.close();		
			CallMe();}
			
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		int position=spinner.getSelectedItemPosition();
		sCategory=categories[position];
		
		
		datasource=new AddExpenseDataSource(this);
		datasource.open();
		scategoryWise=datasource.getBudget(sCategory);
		tvCategoryWise.setText(scategoryWise);
		datasource.close();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
