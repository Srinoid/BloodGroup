package com.example.newcontact;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	// ArrayList
	ArrayList<SelectUser> selectUsers;
	List<SelectUser> temp;
	// Contact List
	ListView listView;
	// Cursor to load contacts list
	Cursor phones, email;

	// Pop up
	ContentResolver resolver;
	SearchView search;
	SelectUserAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		selectUsers = new ArrayList<SelectUser>();
		resolver = this.getContentResolver();
		listView = (ListView) findViewById(R.id.contacts_list);
		
		
		

		phones = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
		LoadContact loadContact = new LoadContact();
		loadContact.execute();
	}

	

	class LoadContact extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... voids) {
			// Get Contact list from Phone

			if (phones != null) {
				Log.e("count", "" + phones.getCount());
				if (phones.getCount() == 0) {
					Toast.makeText(MainActivity.this,
							"No contacts in your contact list.",
							Toast.LENGTH_LONG).show();
				}

				while (phones.moveToNext()) {
					//Bitmap bit_thumb = null;
					
					String name = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					String phoneNumber = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					
					SelectUser selectUser = new SelectUser();
					selectUser.setName(name);
					selectUser.setPhone(phoneNumber);
					
					selectUsers.add(selectUser);
				}
			} else {
				Log.e("Cursor close 1", "----------------");
			}
			// phones.close();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			adapter = new SelectUserAdapter(selectUsers, MainActivity.this);
			listView.setAdapter(adapter);

		}
	}

	
}