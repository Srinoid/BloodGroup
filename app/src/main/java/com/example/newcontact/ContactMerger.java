package com.example.newcontact;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactMerger {
	private static final String CONTACT_TABLE = "_contact_table";
	private static final String CONTACT_ID = "_contactId";
	private static final String CONTACT_NAME = "_contactName";
	private static final String CONTACT_MOBILE_NUMBER = "_contactNumber";
	private static final String CONTACT_DATE  = "_contactDate";


	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "DB_Contact";

	private final Context context;
	private SQLiteDatabase ourDatabase;
	private DbHelper ourHelper;

	private class DbHelper extends SQLiteOpenHelper {

	    public DbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);

	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        // TODO Auto-generated method stub
	        String contactQuery = "CREATE TABLE " + CONTACT_TABLE + " ("
	                + CONTACT_MOBILE_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	                + CONTACT_NAME + " TEXT NOT NULL) ";

	        db.execSQL(contactQuery);
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // TODO Auto-generated method stub
	        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
	        onCreate(db);
	    }

	}

	public ContactMerger(Context context) {
	    this.context = context;
	}

	public ContactMerger open() throws SQLException {
	    ourHelper = new DbHelper(context);
	    ourDatabase = ourHelper.getWritableDatabase();
	    return this;
	}

	public void close() {
	    ourHelper.close();
	}

	// Insert Data to Contact Table
	public long insertContacts(String name, String number) throws SQLException {
	    ContentValues cv = new ContentValues();
	    cv.put(CONTACT_NAME, name);
	    //cv.put(CONTACT_DATE, date);
	    cv.put(CONTACT_MOBILE_NUMBER, number);
	    Log.d("Insert Data", cv.toString());
	    return ourDatabase.insert(CONTACT_TABLE, null, cv);
	}

	//Get Contact details from Contact Table
	public ArrayList<SelectUser> getContactDetails() throws Exception{
	    ArrayList<SelectUser> contactDetails = new ArrayList<SelectUser>();
	    String[] culumns = new String[] { CONTACT_NAME, CONTACT_MOBILE_NUMBER };
	    Cursor c = ourDatabase.query(CONTACT_TABLE, culumns, null, null, null,null, null);

	    int iContactName = c.getColumnIndex(CONTACT_NAME);  
	   //int iContactDate = c.getColumnIndex(CONTACT_DATE);  
	    int iContactMobileNumber = c.getColumnIndex(CONTACT_MOBILE_NUMBER);

	    for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

	        SelectUser data = new SelectUser();
	        data.setName(c.getString(iContactName));
	        //data.setDate(c.getString(iContactDate));
	        data.setPhone(c.getString(iContactMobileNumber));

	        contactDetails.add(data);
	    }

	    return contactDetails;

	 }
	}