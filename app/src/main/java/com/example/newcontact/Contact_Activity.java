package com.example.newcontact;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;

public class Contact_Activity extends Activity implements OnClickListener {

	Spinner sp;
	Button bAdd, bShow;
	
	SQLiteDatabase db;
	EditText phno;
	//TextView tv;
	String bg;
	String data ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		sp = (Spinner) findViewById(R.id.spinner1);
		bAdd = (Button) findViewById(R.id.buttonAdd);
		phno=(EditText)findViewById(R.id.editTextPno);
		bShow = (Button) findViewById(R.id.buttonShow);
		Intent intent=getIntent();
		Bundle extras = intent.getExtras();
	    
		if(extras != null)
	     data = extras.getString("number");
		phno.setText(data);
		
		bAdd.setOnClickListener(this);
		bShow.setOnClickListener(this);
		db = openOrCreateDatabase("Blood Group", MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS bg(phone_number text primary key not null,blood_group VARCHAR);");

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		if (view == bAdd) {
			
			/*db.isOpen();
			ContentValues cv = new ContentValues();
			cv.put("name", sp.getSelectedItem().toString());
			db.insert("bg", null, cv);
			 db.close();*/
			
			
			
			Cursor c=db.rawQuery("SELECT * FROM bg WHERE phone_number='"+phno.getText()+"'", null);
			if(c.moveToFirst())
			{
				 Toast.makeText(Contact_Activity.this, "Already Blood Group Added", 4).show();
				 Intent back=new Intent(Contact_Activity.this,MainActivity.class);
				 startActivity(back);
				 finish();
			}
			else
			{
				db.execSQL("INSERT INTO bg VALUES('"+phno.getText()+"','"+sp.getSelectedItem()+
		 				   "');");
					
				Intent main=new Intent(Contact_Activity.this,MainActivity.class);
				main.putExtra("blood_group",sp.getSelectedItem().toString());
			    startActivity(main);
					 Toast.makeText(Contact_Activity.this, "Successfully Entered Blood Group", 4).show();
			}

			
			

			// showMessage("Success", "Record added");
			// clearText();
		}

	}
}

