package com.arnab.meandmyfriendsexpense;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Incomes extends Activity implements OnClickListener{

	TextView tv,tv1,tv2;
	CommentsDataSource datasource;
	String  result,tsource,tamount,sMonthlyIncome,trsource;
	Button addIncome,removeIncome;
	EditText source,amount,rsource;
	int samount=0,monthly_income=0;
	long  res;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.incomes_new);
		
		tv2=(TextView)findViewById(R.id.tvIncomes_data);
		tv1=(TextView)findViewById(R.id.tvIncomes_Monthly);
		tv=(TextView)findViewById(R.id.tvIncomes_Source);
		addIncome=(Button)findViewById(R.id.bIncomes_addSource);
		removeIncome=(Button)findViewById(R.id.bIncomes_remove);
		source=(EditText)findViewById(R.id.etIncomes_source);
		amount=(EditText)findViewById(R.id.etIncomes_amount);
		rsource=(EditText)findViewById(R.id.etIncomes_remove);
		
		tv2.setTextColor(Color.parseColor("#FFFFFF"));
		addIncome.setTextColor(Color.parseColor("#FFFFFF"));
		removeIncome.setTextColor(Color.parseColor("#FFFFFF"));
		
		datasource=new CommentsDataSource(this);
		datasource.open();
		result=datasource.getStringCompleteData();
		tv.setText(result);
		monthly_income=datasource.getTotalIncome();
		sMonthlyIncome=Integer.toString(monthly_income);
		tv1.setText("Rs."+sMonthlyIncome);
		datasource.close();
		
		addIncome.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 89, 227, 39), 0x00000000));
		removeIncome.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 237, 28, 28), 0x00000000));

		source.setBackgroundColor(Color.argb(90,50, 70, 180));
		amount.setBackgroundColor(Color.argb(90,50, 70, 180));
		rsource.setBackgroundColor(Color.argb(90,50, 70, 180));
		
		addIncome.setOnClickListener(this);
		removeIncome.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
		 
			case R.id.bIncomes_addSource:
				boolean didItWork=true;
				tsource=source.getText().toString();
				tamount=amount.getText().toString();
				if(!tamount.contentEquals("") && !tsource.contentEquals("") ){
				samount=Integer.parseInt(tamount);
				try{
					datasource= new CommentsDataSource(Incomes.this);
					datasource.open();
					res=datasource.createIncome(tsource, samount);
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
				
				}
				
				else{
					Dialog d=new Dialog(this);
					TextView tv2=new TextView(this);
					d.setTitle("Dude ,Hey dude");
					tv2.setText("Any Parameter can't be neglected");
					d.setContentView(tv2);
					d.show();
				}
				
				datasource= new CommentsDataSource(Incomes.this);
				datasource.open();
				result=datasource.getStringCompleteData();
				tv.setText(result);
				monthly_income=datasource.getTotalIncome();
				sMonthlyIncome=Integer.toString(monthly_income);
				tv1.setText(sMonthlyIncome);
				datasource.close();
				
				break;
				
			case R.id.bIncomes_remove:
				boolean didItWork1=true;
				trsource=rsource.getText().toString();
				try{
					datasource= new CommentsDataSource(Incomes.this);
					datasource.open();
					datasource.deleteIncome(trsource);
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
					didItWork1=false;
					
				}
				
				
				
				datasource= new CommentsDataSource(Incomes.this);
				datasource.open();
				result=datasource.getStringCompleteData();
				tv.setText(result);
				monthly_income=datasource.getTotalIncome();
				sMonthlyIncome=Integer.toString(monthly_income);
				tv1.setText("Rs."+sMonthlyIncome);
				datasource.close();
				break;
		}
		
		
			
		
		
	}

}
