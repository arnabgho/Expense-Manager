package com.arnab.meandmyfriendsexpense;


import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CumulativeExpense extends Activity implements OnClickListener{

	
	Button yearly,monthly,categoryWisem,categoryWisey,Specifically,budget;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cumulativeexpenses);
		yearly=(Button)findViewById(R.id.bCumulativeExpenses_Yearly);
		monthly=(Button)findViewById(R.id.bCumulativeExpenses_Monthly);
		categoryWisem=(Button)findViewById(R.id.bCumulativeExpenses_CategoryWiseMonthly);
		categoryWisey=(Button)findViewById(R.id.bCumulativeExpenses_CategoryWiseYearly);
		Specifically=(Button)findViewById(R.id.bCumulativeExpenses_Specifically);
		budget=(Button)findViewById(R.id.bCumulativeExpenses_Budget);
		
		budget.setTextColor(Color.parseColor("#FFFFFF"));
		yearly.setTextColor(Color.parseColor("#FFFFFF"));
		monthly.setTextColor(Color.parseColor("#FFFFFF"));
		categoryWisem.setTextColor(Color.parseColor("#FFFFFF"));
		categoryWisey.setTextColor(Color.parseColor("#FFFFFF"));
		Specifically.setTextColor(Color.parseColor("#FFFFFF"));
		
		budget.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 133, 78, 222), 0x00000000));
		Specifically.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 78, 222, 133), 0x00000000));
		monthly.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 78, 121, 222), 0x00000000));
		categoryWisem.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 78, 121, 222), 0x00000000));
		yearly.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 227, 102, 146), 0x00000000));
		categoryWisey.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 227, 102, 146), 0x00000000));
		
		
		budget.setOnClickListener(this);		
		Specifically.setOnClickListener(this);
		monthly.setOnClickListener(this);
		categoryWisem.setOnClickListener(this);
		categoryWisey.setOnClickListener(this);
		yearly.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.bCumulativeExpenses_Yearly:
			
			Intent i=new Intent(CumulativeExpense.this,yearlyExpense.class);
			i.putExtra("name", "yearly");
			startActivity(i);
		break;
		
		case R.id.bCumulativeExpenses_Monthly:
			
			Intent in=new Intent(CumulativeExpense.this,yearlyExpense.class);
			in.putExtra("name", "monthly");
			startActivity(in);
		break;
		
		
		case R.id.bCumulativeExpenses_CategoryWiseMonthly:
			
		//	Intent inte=new Intent(CumulativeExpense.this,yearlyExpense.class);
		//	inte.putExtra("name", "Category Wise Monthly");
		//	startActivity(inte);
			PieGraph pie=new PieGraph();
			Intent pieIntent=pie.getIntent(this);
			startActivity(pieIntent);
		break;
		
		case R.id.bCumulativeExpenses_CategoryWiseYearly:
			
		//	Intent inten=new Intent(CumulativeExpense.this,yearlyExpense.class);
		//	inten.putExtra("name", "Category Wise Yearly");
		//	startActivity(inten);
			PieGraph1 pie1=new PieGraph1();
			Intent pieIntent1=pie1.getIntent(this);
			startActivity(pieIntent1);
		break;
		
		case R.id.bCumulativeExpenses_Specifically:
			Intent intent = new Intent(CumulativeExpense.this,Specifically.class);
			startActivity(intent);
			break;
			
		case R.id.bCumulativeExpenses_Budget:
			Intent intent1=new Intent(CumulativeExpense.this,Budget.class);
			startActivity(intent1);
			break;
		}
		
	}

	
}