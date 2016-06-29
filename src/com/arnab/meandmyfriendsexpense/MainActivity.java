package com.arnab.meandmyfriendsexpense;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ListActivity implements OnClickListener{
  private CommentsDataSource datasource;
  EditText et_friendName;
  String friendName;
  Button add,remove;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_friend);

    et_friendName=(EditText)findViewById(R.id.etFriend_Name_Name);
    add=(Button)findViewById(R.id.bAdd_Friend_add);
    remove=(Button)findViewById(R.id.bAdd_Friend_remove);
    
    datasource = new CommentsDataSource(this);
    datasource.open();
    add.setOnClickListener(this);
    remove.setOnClickListener(this);
    List<Comment> values = datasource.getAllComments();

    // Use the SimpleCursorAdapter to show the
    // elements in a ListView
    ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
        android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  }

  // Will be called via the onClick attribute
  // of the buttons in main.xml
  public void onClick(View view) {
    @SuppressWarnings("unchecked")
    ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
    Comment comment = null;
    switch (view.getId()) {
    case R.id.bAdd_Friend_add:
      String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
      int nextInt = new Random().nextInt(3);
      // Save the new comment to the database
      friendName=et_friendName.getText().toString();
      
      comment = datasource.createComment(friendName);
      adapter.add(comment);
      break;
    case R.id.bAdd_Friend_remove:
      if (getListAdapter().getCount() > 0) {
        comment = (Comment) getListAdapter().getItem(0);
        datasource.deleteComment(comment);
        adapter.remove(comment);
      }
      break;
    }
    adapter.notifyDataSetChanged();
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