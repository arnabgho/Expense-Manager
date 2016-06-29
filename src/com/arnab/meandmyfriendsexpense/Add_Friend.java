package com.arnab.meandmyfriendsexpense;


import java.util.List;
import java.util.Random;

import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @author arnab
 *
 */
public class Add_Friend extends ListActivity implements OnClickListener{
  private CommentsDataSource datasource;

  Button add,remove;
  EditText et_friendName;
  String friendName;
  ListView listview;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_friend);

    add=(Button)findViewById(R.id.bAdd_Friend_add);
    remove=(Button)findViewById(R.id.bAdd_Friend_remove);
    et_friendName=(EditText)findViewById(R.id.etFriend_Name_Name);
    listview=(ListView)findViewById(android.R.id.list);
    
  datasource = new CommentsDataSource(this);
    datasource.open();

	add.setBackgroundColor(Color.argb(150,50, 70, 180));
	remove.setBackgroundColor(Color.argb(150,180, 70, 50));

	et_friendName.setBackgroundColor(Color.argb(100,50,180 , 70));

    add.setOnClickListener(this);
    remove.setOnClickListener(this);
    List<Comment> values = datasource.getAllComments();

    // Use the SimpleCursorAdapter to show the
    // elements in a ListView
  ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  }

  // Will be called via the onClick attribute
  // of the buttons in main.xml
  public void onClick(View view) {
	  
    @SuppressWarnings("unchecked")
    Comment comment = null;
    switch (view.getId()) {
    
    case R.id.bAdd_Friend_remove:
    	/*
    	 friendName=et_friendName.getText().toString();
    	 comment=datasource.smartDelete(friendName);
         adapter.remove(comment);
         */
    	friendName=et_friendName.getText().toString();
    	
      if (getListAdapter().getCount() > 0) {
       Comment  newComment = datasource.smartDelete(friendName);
        
       if(newComment!=null)
        datasource.deleteComment(newComment);
       else{
    	   
    	   Dialog d=new Dialog(this);
    	   d.setTitle("Friend Doesn't Exist Dude");
    	   d.show();
       }
    	  
      }
      List<Comment> values = datasource.getAllComments();

      // Use the SimpleCursorAdapter to show the
      // elements in a ListView
    ArrayAdapter<Comment> adapter1 = new ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1, values);
      setListAdapter(adapter1);
      /*
      if (getListAdapter().getCount() > 0) {
          comment = (Comment) getListAdapter().getItem(0);
          datasource.deleteComment(comment);
          adapter.remove(comment);
        }
      */   
      adapter1.notifyDataSetChanged(); 
      
      break;    
    
    case R.id.bAdd_Friend_add:
    //  String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
    // int nextInt = new Random().nextInt(3);
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();     
      friendName=et_friendName.getText().toString();
      
      // Save the new comment to the database
      comment = datasource.createComment(friendName);
      adapter.add(comment);
      adapter.notifyDataSetChanged();
      
      break;

    }
  
  }

  
  @Override
  protected void onResume() {
    datasource.open();
    super.onResume();
  }

  @Override
  protected void onPause() {
    datasource.close();
    super.onPause();
  }

} 