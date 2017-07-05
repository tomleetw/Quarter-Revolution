package com.app.quarter_revolution_tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import android.net.Uri;

public class SettingActivity extends Activity
{
	private ToggleButton EnableSetting;
	private Button Button_reset,Button_app_concept,Button_verions_info,letter_info;
	private int reset_count;
	Context _context;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_setting);
		UI_Event_Setting();
	}
	public void UI_Event_Setting()
	{
		_context=this;
		reset_count=0;
		EnableSetting=(ToggleButton) findViewById(R.id.togglebutton_enable_setting);
		EnableSetting.setChecked(VarManager.isEnableEdit);
		Button_reset=(Button) findViewById(R.id.button_reset);
		Button_app_concept=(Button) findViewById(R.id.button_app_concept);
		Button_verions_info=(Button) findViewById(R.id.button_verions_info);
		letter_info=(Button) findViewById(R.id.letter_info);

		EnableSetting.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				reset_count=0;
				VarManager.isEnableEdit=isChecked;
			}
		});

		Button_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				reset_count++;
				if(reset_count<3)
    	    	{
    	    		//output.setTextColor(0xffff00ff);
        	   		//output.setText("�T���G�ݳs���3���s�~�୫�s�}�l�@�ӷs�����|�A�o�O��"+Integer.toString(reset_count)+"�����s!");
        	   		AlertDialog.Builder buildermsg = new AlertDialog.Builder(_context);
                    buildermsg.setTitle("訊息！");
					buildermsg.setMessage("訊息：需連續按3次鈕才能重新開始一個新的選舉，這是第"+Integer.toString(reset_count)+"次按鈕!");
					buildermsg.setPositiveButton("確定",
                    		new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface
                                           dialoginterface, int i) {
                            	// ����
                            	//return;


                            }
                      });
                    buildermsg.show();
        	   		return;
    	    	}
				VarManager.isNeedResetDB=true;
				AlertDialog.Builder buildermsg = new AlertDialog.Builder(_context);
				buildermsg.setTitle("訊息！");
				buildermsg.setMessage("訊息：重新開始一個新的選舉！");
				buildermsg.setPositiveButton("確定",
                		new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface
                                       dialoginterface, int i) {

                        }
                  });
                buildermsg.show();
			}

		});

		Button_app_concept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				reset_count=0;
				//Intent intent = new Intent();
	            //intent.setClass(SettingActivity.this, AppConceptActivity.class);
	            //startActivity(intent);
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lettertodibert.blogspot.com/2014/07/app.html"));
				startActivity(browserIntent);
			}

		});

		Button_verions_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				reset_count=0;
				//Intent intent = new Intent();
	            //intent.setClass(SettingActivity.this, VersionInfoActivity.class);
	            //startActivity(intent);
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lettertodibert.blogspot.com/2014/07/app_29.html"));
				startActivity(browserIntent);

			}

		});

		letter_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				reset_count=0;
				//Intent intent = new Intent();
				//intent.setClass(SettingActivity.this, VersionInfoActivity.class);
				//startActivity(intent);
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://lettertodibert.blogspot.com/2014/05/blog-post.html"));
				startActivity(browserIntent);

			}

		});

	}
}
