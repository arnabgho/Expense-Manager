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

public class Specifically extends Activity implements OnClickListener{

	Button get;
	EditText month , year;
	TextView tv;
	String smonth,syear,result;
	int imonth,iyear;
	AddExpenseDataSource datasource;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.specifically);
		
		month=(EditText)findViewById(R.id.etSpecifically_month);
		year=(EditText)findViewById(R.id.etSpecifically_year);
		tv=(TextView)findViewById(R.id.tvSpecifically_result);
		get=(Button)findViewById(R.id.bSpecifically_get);
		
		get.setTextColor(Color.parseColor("#FFFFFF"));
		tv.setTextColor(Color.parseColor("#FFFFFF"));
		month.setTextColor(Color.parseColor("#FFFFFF"));
		year.setTextColor(Color.parseColor("#FFFFFF"));
		
		month.setBackgroundColor(Color.argb(60,50, 70, 180));
		year.setBackgroundColor(Color.argb(60,50, 70, 180));

		
		get.setOnClickListener(this);
		//get.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 27, 74, 10), 0x00000000));
		get.setBackgroundColor(Color.argb(120,50, 70, 180));
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.bSpecifically_get:
			
			smonth=month.getText().toString();
			syear=year.getText().toString();
			if(!smonth.contentEquals("")&&!syear.contentEquals("")){
			imonth=Integer.parseInt(smonth);
			iyear=Integer.parseInt(syear);
			datasource=new AddExpenseDataSource(this);
			datasource.open();
			result=datasource.monthlyExpenseBy(imonth, iyear);
			
			tv.setText(result);
			tv.setVisibility(tv.VISIBLE);
			}
			else{
				Dialog d=new Dialog(this);
				TextView tv2=new TextView(this);
				d.setTitle("Dude ,Hey dude");
				tv2.setText("Any Parameter can't be neglected");
				d.setContentView(tv2);
				d.show();
			}
		
		}
		
	}

}
