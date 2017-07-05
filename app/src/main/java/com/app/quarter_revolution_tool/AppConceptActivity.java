package com.app.quarter_revolution_tool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AppConceptActivity extends Activity {
	Button Button_url;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_concept);
		UI_Event_Setting();
	}	
	public void UI_Event_Setting()
	{
		Button_url=(Button) findViewById(R.id.button_url);

		
		Button_url.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://quarterrevolution.blogspot.com/2017/01/app_11.html"));
				startActivity(browserIntent);
			}
	 
		});
	}
}
