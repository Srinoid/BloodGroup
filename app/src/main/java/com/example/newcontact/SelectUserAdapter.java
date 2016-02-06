package com.example.newcontact;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SelectUserAdapter extends BaseAdapter {

	public List<SelectUser> _data;
	private ArrayList<SelectUser> arraylist;
	Context _c;
	ViewHolder v;

	String data;
	EditText phone_number;


	public SelectUserAdapter(List<SelectUser> selectUsers, Context context) {
		_data = selectUsers;
		_c = context;
		this.arraylist = new ArrayList<SelectUser>();
		this.arraylist.addAll(_data);
	}

	@Override
	public int getCount() {
		return _data.size();
	}

	@Override
	public Object getItem(int i) {
		return _data.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View convertView, ViewGroup viewGroup) {
		View view = convertView;
		if (view == null) {
			LayoutInflater li = (LayoutInflater) _c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.row, null);

			Log.e("Inside", "here--------------------------- In view1");
		} else {
			view = convertView;
			Log.e("Inside", "here--------------------------- In view2");
		}
		
		  

		v = new ViewHolder();

		v.title = (TextView) view.findViewById(R.id.name);
		v.bg = (TextView) view.findViewById(R.id.textViewBg);
		// v.check = (CheckBox) view.findViewById(R.id.check);
		v.phone = (TextView) view.findViewById(R.id.no);

		v.b = (Button) view.findViewById(R.id.Add);
		// v.imageView = (ImageView) view.findViewById(R.id.pic);

		final SelectUser data = (SelectUser) _data.get(i);
		v.title.setText(data.getName());
		// v.check.setChecked(data.getCheckedBox());
		v.phone.setText(data.getPhone());

		Intent intent = ((MainActivity) _c).getIntent();
		Bundle b = intent.getExtras();

		if (b != null) {
			String j = (String) b.get("blood_group");
			v.bg.setText(j);
			

		}

		v.b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View i) {

				Intent contact = new Intent(_c, Contact_Activity.class);
				contact.putExtra("number", data.getPhone());

				_c.startActivity(contact);

			}

		});

		view.setTag(data);
		return view;
	}

	/*// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		_data.clear();
		if (charText.length() == 0) {
			_data.addAll(arraylist);
		} else {
			for (SelectUser wp : arraylist) {
				if (wp.getName().toLowerCase(Locale.getDefault())
						.contains(charText)) {
					_data.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}*/

	static class ViewHolder {
		// ImageView imageView;
		TextView title, phone, bg;
		Button b;
	}
}