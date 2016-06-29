package com.arnab.meandmyfriendsexpense;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class PieGraph {
	
	public Intent getIntent(Context context) {
		AddExpenseDataSource datasource ;
		datasource=new AddExpenseDataSource(context);
		datasource.open();
		
		 String categories[]={"Food","Entertainment","Accessories","Apparel","Transportation","Rent","Amount Got From Friends"};

		int[] values = { 1, 2, 3, 4, 5,6,7 };
		int []x=datasource.icategoryWisem();
		for(int i=0;i<7;i++)
			values[i]=x[i];
		CategorySeries series = new CategorySeries("Pie Graph");
		for (int k=0;k<7;k++) {
			series.add(categories[k], values[k]);
		}

		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN ,Color.GRAY,Color.RED};

		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		renderer.setLabelsTextSize(20);
		renderer.setLabelsColor(Color.BLACK);
	//	renderer.setTextTypeface(null, Color.BLACK);
	//	renderer.setApplyBackgroundColor(true);
		renderer.setChartTitle("Category Wise Expenses");
		renderer.setChartTitleTextSize(7);
		renderer.setZoomButtonsVisible(true);

		Intent intent = ChartFactory.getPieChartIntent(context, series, renderer, "Category Wise Expense (Monthly)");
		
		return intent;
	}
		
	

}
