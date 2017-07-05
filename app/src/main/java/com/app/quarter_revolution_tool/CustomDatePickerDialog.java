package com.app.quarter_revolution_tool;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CustomDatePickerDialog extends DatePickerDialog {
	
	public CustomDatePickerDialog(Context context, int theme,
	        OnDateSetListener callBack, int year, int monthOfYear,
	        int dayOfMonth) {
	    super(context, theme, callBack, year, monthOfYear, dayOfMonth);
	    VarManager.isfirstbutton=false;
	    
	    // TODO Auto-generated constructor stub
	}
	public CustomDatePickerDialog(Context context,
	        OnDateSetListener callBack, int year, int monthOfYear,
	        int dayOfMonth) {
	    super(context, callBack, year, monthOfYear, dayOfMonth);
	    VarManager.isfirstbutton=false;
	    // TODO Auto-generated constructor stub
	    //this.setButton("確定", this);
	    
	    this.setButton(DialogInterface.BUTTON_POSITIVE, "確定", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	           if (which == DialogInterface.BUTTON_POSITIVE) {
	        	   VarManager.isfirstbutton=true;
	           }
	        }
	      });
	    this.setButton2("取消",(OnClickListener)null );
	}
}
