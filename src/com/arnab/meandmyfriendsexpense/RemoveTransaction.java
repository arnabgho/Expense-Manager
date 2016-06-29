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

public class RemoveTransaction extends Activity implements OnClickListener{

	Button remove;
	EditText id;
	String id1;
	AddExpenseDataSource datasource;
	int final_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remove_transaction);
		
		remove=(Button)findViewById(R.id.bRemoveTransaction_remove);
		id=(EditText)findViewById(R.id.etRemoveTransaction_data);
		
		id.setBackgroundColor(Color.argb(60,50, 70, 180));
		
		remove.getBackground().setColorFilter(new LightingColorFilter(Color.rgb( 237, 28, 28), 0x00000000));
		remove.setTextColor(Color.parseColor("#FFFFFF"));
		id.setTextColor(Color.parseColor("#FFFFFF"));
		remove.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.bRemoveTransaction_remove:
			
			id1=id.getText().toString();
			
			if(!id1.contentEquals("")){
			final_id=Integer.parseInt(id1);
			
			datasource=new AddExpenseDataSource(this);
			datasource.open();
			
			datasource.removeTransaction(final_id);
			
			datasource.close();
			}
			
			else
			{
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

	
	
	
	
}
