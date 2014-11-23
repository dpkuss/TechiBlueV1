package com.techiblue.client;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class SpinActivity extends Activity {

	private ImageView image;
	private String[] states;
	private Spinner spinner;
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinactivity);

		
		states = new String[1];
		 
		states[0] = "ppp";
		 
	 
		spinner = (Spinner) findViewById(R.id.country_spinner);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, states);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			/*	image.setImageResource(imgs.getResourceId(
						spinner.getSelectedItemPosition(), -1));*/
				
				System.out.println("spinner.getSelectedItemPosition()   "+spinner.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}
}