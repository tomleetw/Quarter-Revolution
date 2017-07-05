package com.app.quarter_revolution_tool;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.widget.*;
import android.view.*;

import java.util.Calendar;

import com.app.quarter_revolution_tool.R;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.ContentValues;
import android.database.Cursor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
	
	SampleAlarmReceiver alarm = new SampleAlarmReceiver();
	
	private EditText leftticket;
	private EditText tocketcanvote;
	private EditText c1ticket;
	private EditText c2ticket;
	private EditText c3ticket;
	private EditText c4ticket;
	private EditText lastdate;
	private EditText nextdate;
	private EditText todaydate;
	private EditText c1name;
	private EditText c2name;
	private EditText c3name;
	private EditText c4name;
	private TextView output;
	private EditText votereason;
	private EditText outputdebug;
	private TextView rv1;//reason of vote1 
	private TextView rv2;
	private TextView rv3;
	private TextView rv4;
	private TextView rv5;
	private TextView rv6;
	private TextView rv7;
	private TextView rv8;
	private TextView rv9;
	private TextView rv10;
	private TextView rv11;
	private TextView rv12;
	private TextView rv13;
	private TextView rv14;
	private TextView rv15;
	private TextView rv16;
		
	private SQLiteDatabase db;
	private MyDBHelper dbHelper;
	private static String DATABASE_TABLE = "titles3";
	private static String firstname = "xyz";

    private int vote1=0;
    private int vote2=0;
    private int vote3=0;
    private int vote4=0;
    private int totalvotedtickets;
	private int d1y;
    private int d1m;
    private int d1d;
    private int d2y;
    private int d2m;
    private int d2d;
    private int totalticket;
    private int totalticket_max;
    
    private int d1y_temp;
    private int d1m_temp;
    private int d1d_temp;
    private int d2y_temp;
    private int d2m_temp;
    private int d2d_temp;
    private int totalticket_temp;
    private int totalticket_max_temp;
    
	private int d1y_now;
    private int d1m_now;
    private int d1d_now;
    private int d2y_now;
    private int d2m_now;
    private int d2d_now;
    private int totalticketcanvote_now;
    
    private int date_diff;
    
    private int reset_count;
    String todaymsg = "";
    String outputmsg = "";
    String outputmsgDia = "";
    String reasonofvote = "";
    //String[] todaymessage = {"今天","2013","12","31"};
    
    private PendingIntent pendingIntent;
    private PendingIntent pendingIntent2;
    
    private int first_boot=0;
    
    private Cursor mCursor = null;
    private static final String[] COLS = new String[] {
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events._ID};
    Button AddEvent;
    Button ReadEvent;
    Button DelEvent;
    TextView TextViewReadEvent;
    Context _context;
    String Ctitle[];
    String Cdate[];
    String CDescription[];
    String EventID[];
    String EventID_del[];
    int[] CalIds;
    String ParseDatainCalendarDescription;
    
    String CalenderSaveDate;
    
 	//2,4 立春農民節,also vote for environment.
    //5,1  國際勞工節, also for Monther's day.
    //8,12 國際青年節.
    //10,31 萬聖節.Vote 幫小孩子趕鬼.
    private int Cal_Event_id;//0,1,2,3 
    private int[] VoteM={2,5,8,10};
    private int[] VoteD={4,1,12,31};   
 	
 	private int Cal_Event_year;
    private int Cal_Event_mon;
    private int Cal_Event_day;
    
    private int NewRecordFromCalendar=0;
    private int InitRecordDataInCalendar=0;
    
    final int Vote_Interval = 90;//3 months = 90 days

    Cursor c_dB;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_context=this;
		
		leftticket = (EditText) findViewById(R.id.editText6);
		tocketcanvote = (EditText) findViewById(R.id.editText36);
		c1ticket = (EditText) findViewById(R.id.editText7);
		c2ticket = (EditText) findViewById(R.id.editText8);
		c3ticket = (EditText) findViewById(R.id.editText9);
		c4ticket = (EditText) findViewById(R.id.editText10);
		c1name = (EditText) findViewById(R.id.editText1);
		c2name = (EditText) findViewById(R.id.editText2);
		c3name = (EditText) findViewById(R.id.editText3);
		c4name = (EditText) findViewById(R.id.editText4);

		
		lastdate = (EditText) findViewById(R.id.editText11);
		nextdate = (EditText) findViewById(R.id.editText12);
		//todaydate = (EditText) findViewById(R.id.editText46);
		output=(TextView)findViewById(R.id.textView14);
		votereason=(EditText)findViewById(R.id.editText15);
		//outputdebug = (EditText) findViewById(R.id.editText5);
		rv1=(TextView)findViewById(R.id.textView51);
		/*
		rv2=(TextView)findViewById(R.id.textView52);
		rv3=(TextView)findViewById(R.id.textView53);
		rv4=(TextView)findViewById(R.id.textView54);
		rv5=(TextView)findViewById(R.id.textView55);
		rv6=(TextView)findViewById(R.id.textView56);
		rv7=(TextView)findViewById(R.id.textView57);
		rv8=(TextView)findViewById(R.id.textView58);
		rv9=(TextView)findViewById(R.id.textView59);
		rv10=(TextView)findViewById(R.id.textView60);
		rv11=(TextView)findViewById(R.id.textView61);
		rv12=(TextView)findViewById(R.id.textView62);
		rv13=(TextView)findViewById(R.id.textView63);
		rv14=(TextView)findViewById(R.id.textView64);
		rv15=(TextView)findViewById(R.id.textView65);
		rv16=(TextView)findViewById(R.id.textView66);
		*/
		//If press any button or action, reset the reset 3 times counter. 
   	    reset_count=0;
		 		
		// 建立SQLiteOpenHelper物件
        //dbHelper = new MyDBHelper(this);
   	    dbHelper = MyDBHelperFactory.GetDBHelper(this);
        db = dbHelper.getWritableDatabase(); // 開啟資料庫
        
        Data_init();
        
        //Reset, because read from calendar will set as 1.
        NewRecordFromCalendar=0;
        
        //Put here is better, sometimes alarm will not work, it can recover after re-launch this AP/
        alarm.setAlarm(this);//Let the QR tool in the notification bar always?

	}
	@Override
	public void onResume(){
	    super.onResume();
	    c1name.setEnabled(VarManager.isEnableEdit);
		c2name.setEnabled(VarManager.isEnableEdit);
		c3name.setEnabled(VarManager.isEnableEdit);
		c4name.setEnabled(VarManager.isEnableEdit);
		
		if(VarManager.isNeedResetDB)
		{
			VarManager.isNeedResetDB=false;
			ResetDB();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getOrder()) {
	        case 1:
	            // do whatever
	        	Intent intent = new Intent();
	            intent.setClass(MainActivity.this, SettingActivity.class);
	            startActivity(intent);
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
//========================================================================================================================
// Database management
//========================================================================================================================	
	
	protected void onStop() {
		super.onStop();
		db.close(); // 關閉資料庫
	}
	
//========================================================================================================================
// Init management
//========================================================================================================================	
		
		 // init所有記錄
	    public void Data_init() { 
	    	
	    	//If press any button, reset the reset 3 times counter. 
	   	    reset_count=0;
	    	
	    	int dblength;
	    	
	    	 InitGlobalVariabls();
			 
		     d1y_temp=d1y;
		     d1m_temp=d1m;
		     d1d_temp=d1d;
		     d2y_temp=d2y;
		     d2m_temp=d2m;
		     d2d_temp=d2d;
		     totalticket_max_temp=totalticket_max;
		     totalticket_temp=totalticket;

	    	//db.execSQL("Select * from 1a where name = 'jack'");
		     c_dB = db.rawQuery("Select * from " + DATABASE_TABLE , null);//cursor of dB  
	    	
	    	if(c_dB.getCount()==1)//Correct dB and dB data.
	    	{
	    		LoadFromDB();
	    		return;
	    	}
	    	    	
	        //If no data, c.getCount() is 0
	    	if(c_dB.getCount()==0)
	    	{   //first time use that AP, init dB
	    		//output.setText("Debug: no data in database");//Remove, in first time, it appeared. 
	    		//init the data base with init values in variable.
	         	//db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d)Values('"+ firstname + "'," + leftticket.getText().toString() + ",'" + c1name.getText().toString()+ "'," + c1ticket.getText().toString() + ",'" + c2name.getText().toString() + "'," + c2ticket.getText().toString()+ ",'" + c3name.getText().toString() + "'," + c3ticket.getText().toString()+ ",'" + c4name.getText().toString() + "'," + c4ticket.getText().toString()+ ",'" + lastdate.getText().toString() + "',"+d1y+","+d1m+","+d1d+",'" + nextdate.getText().toString() +"',"+d2y+","+d2m+","+d2d+")");
	    		
	    		//Set the alarm only in the first execute after install. 
	    		//alarm.setAlarm(this);//Let the QR tool in the notification bar always?
	    		
	    		GetTicketCanVote();
	    		
	    		//First time install the QR tool. Check if date in Calendar, if yes, read and NewRecordFromCalendar=1.
	    		InitloadorNewdB();
	    		
	    		//InitDateFromCalendar();
	    		
	    		//No dB data, return!.
	    		if(NewRecordFromCalendar==1)
	    		{
	    			
	    			 //Tom: Only update, not save, one on data in dB!!!
		   		      // (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1,rv2,rv3,rv4,rv5,rv6,rv7,rv8,rv9,rv10,rv11,rv12,rv13,rv14,rv15,rv16)
		   	       	  //db.execSQL("Update " + DATABASE_TABLE + " set tvote=" + leftticket.getText().toString() + ", c1name = '" + c1name.getText().toString() + "', c1vote=" + c1ticket.getText().toString() + ", c2name ='" + c2name.getText().toString() + "', c2vote=" + c2ticket.getText().toString() + ", c3name ='" + c3name.getText().toString() + "', c3vote=" + c3ticket.getText().toString() + ", c4name ='" + c4name.getText().toString() + "', c4vote=" + c4ticket.getText().toString() + ", d1all='" + lastdate.getText().toString() + "', d1y=" + d1y + ", d1m=" + d1m + ", d1d=" + d1d + ", d2all='" + nextdate.getText().toString() + "', d2y=" + d2y + ", d2m=" + d2m + ", d2d=" + d2d + ", rv1 = '" + rv1.getText().toString() + "', rv2 = '" + rv2.getText().toString() + "', rv3 = '" + rv3.getText().toString() + "', rv4 = '" + rv4.getText().toString() + "', rv5 = '" + rv5.getText().toString() + "', rv6 = '" + rv6.getText().toString() + "', rv7 = '" + rv7.getText().toString() + "', rv8 = '" + rv8.getText().toString() + "', rv9 = '" + rv9.getText().toString() + "', rv10 = '" + rv10.getText().toString() + "', rv11 = '" + rv11.getText().toString() + "', rv12 = '" + rv12.getText().toString() + "' , rv13 = '" + rv13.getText().toString() + "', rv14 = '" + rv14.getText().toString() + "', rv15 = '" + rv15.getText().toString() + "', rv16 = '" + rv16.getText().toString() + "'where dbname='" + firstname + "'");
		   	          //InitRecordDataInCalendar=1;
		   	     	  AlertDialog.Builder builder = new AlertDialog.Builder(_context); 
		              builder.setTitle("安裝後，第一次使用的設定");
		              builder.setMessage("你要使用新紀錄或日曆上「"+CalenderSaveDate+"」的紀錄？");
		              builder.setPositiveButton("之前的紀錄",
		          		new DialogInterface.OnClickListener() { 
		                  public void onClick(DialogInterface  
		                                 dialoginterface, int i) { 
		                  	
		             	        
		                	    //Read dB content from Calendar
		                	    //Write date in calendar to dB
		             	        InitDateFromCalendar();
		             	        LoadFromDB(); 
		             	        
		                     	outputmsg="你使用了日曆上之前「"+CalenderSaveDate+"」的紀錄。";
		             	        outputmsgDia="訊息："+outputmsg;
		                	    AlertD();

		                  } 
		            }); 
		          
		          //builder.setNegativeButton("新紀錄", null); 
		          builder.setNegativeButton("新紀錄",
		            		new DialogInterface.OnClickListener() { 
		                      public void onClick(DialogInterface  
		                             dialoginterface, int i) {               	
		         	        
		         	        //dB is empty, in LoadFromDB(), it will write initial date to dB.
		                    LoadFromDB();	  	  
		                    NewRecordFromCalendar=0;
		                    return;
		              } 
		          });
		          
		           builder.show();    	
	    			
	    			
	    		}else{//NewRecordFromCalendar==0, no date in Calendar.
	         	return;
	    		}
	         	
	    	} else if(c_dB.getCount()!=1){
	    		outputmsgDia="Debug: Wrong! only one row is allowed";
	    	    AlertD();
	    		//output.setText("Debug: Wrong! only one row is allowed");
	    		return;
	        }    	   

	    }


		private void LoadFromDB() {
			//Read again here, since dB content may be changed.
			c_dB = db.rawQuery("Select * from " + DATABASE_TABLE , null);//cursor of dB
			
			if(c_dB.getCount()==0)
			{
				//Initial dB data
	         	//db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d)Values('"+ firstname + "'," + leftticket.getText().toString() + ",'" + c1name.getText().toString()+ "'," + c1ticket.getText().toString() + ",'" + c2name.getText().toString() + "'," + c2ticket.getText().toString()+ ",'" + c3name.getText().toString() + "'," + c3ticket.getText().toString()+ ",'" + c4name.getText().toString() + "'," + c4ticket.getText().toString()+ ",'" + lastdate.getText().toString() + "',"+d1y+","+d1m+","+d1d+",'" + nextdate.getText().toString() +"',"+d2y+","+d2m+","+d2d+")");
   	          	//db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1,rv2,rv3,rv4,rv5,rv6,rv7,rv8,rv9,rv10,rv11,rv12,rv13,rv14,rv15,rv16)Values('"+ firstname + "'," + leftticket.getText().toString() + ",'" + c1name.getText().toString()+ "'," + c1ticket.getText().toString() + ",'" + c2name.getText().toString() + "'," + c2ticket.getText().toString()+ ",'" + c3name.getText().toString() + "'," + c3ticket.getText().toString()+ ",'" + c4name.getText().toString() + "'," + c4ticket.getText().toString()+ ",'" + lastdate.getText().toString() + "',"+d1y+","+d1m+","+d1d+",'" + nextdate.getText().toString() +"',"+d2y+","+d2m+","+d2d+",'" + rv1.getText().toString() +"','" + rv2.getText().toString() +"','" + rv3.getText().toString() +"','" + rv4.getText().toString() +"','" + rv5.getText().toString() +"','" + rv6.getText().toString() +"','" + rv7.getText().toString() +"','" + rv8.getText().toString() +"','" + rv9.getText().toString() +"','" + rv10.getText().toString() +"','" + rv11.getText().toString() +"','" + rv12.getText().toString() +"','" + rv13.getText().toString() +"','" + rv14.getText().toString() +"','" + rv15.getText().toString() +"','" + rv16.getText().toString() +"')");
   	            db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1)Values('"+ firstname + "'," + leftticket.getText().toString() + ",'" + c1name.getText().toString()+ "'," + c1ticket.getText().toString() + ",'" + c2name.getText().toString() + "'," + c2ticket.getText().toString()+ ",'" + c3name.getText().toString() + "'," + c3ticket.getText().toString()+ ",'" + c4name.getText().toString() + "'," + c4ticket.getText().toString()+ ",'" + lastdate.getText().toString() + "',"+d1y+","+d1m+","+d1d+",'" + nextdate.getText().toString() +"',"+d2y+","+d2m+","+d2d+",'" + rv1.getText().toString() +"')");

	         	return;
			}
			//Read from dB to show the view again.
	    	// 顯示欄位名稱
	    	String[] colNames=c_dB.getColumnNames();    	
	    	String str = "";   
	    	String str_name = ""; 
	      	for (int i = 0; i < colNames.length; i++)
	    		str += colNames[i] + "\t\t";
	    	str += "\n";
	    	c_dB.moveToFirst();  // 第1筆   
	    	
	    	str_name = c_dB.getString(1);
	    	
	    	// read from data base only 1 row
	    	//for (int i = 0; i < c.getCount(); i++) 
	    	{
	    		//str += c.getString(c.getColumnIndex(colNames[0])) + "\t\t";
	    		//str += c.getString(1) + "\t\t"; //第2欄位   		
	    		//str += c.getString(2) + "\t\t";//第3欄位
	    		//str += c.getString(3) + "\n";//第4欄位
	    		//str_name = c.getString(1);
	    		//c.moveToNext();  // 下一筆
	    	}
	    	//Init text by dB
	    	leftticket.setText(c_dB.getString(2));
	    	c1name.setText(c_dB.getString(3));
	    	c1ticket.setText(c_dB.getString(4));
	    	c2name.setText(c_dB.getString(5));
	    	c2ticket.setText(c_dB.getString(6));
	    	c3name.setText(c_dB.getString(7));  	
	    	c3ticket.setText(c_dB.getString(8));
	    	c4name.setText(c_dB.getString(9));  	
	    	c4ticket.setText(c_dB.getString(10));
	    	lastdate.setText(c_dB.getString(11));  	
	    	nextdate.setText(c_dB.getString(15));
	    	rv1.setText(c_dB.getString(19));//reason of vote 1
	    	/*
	    	rv2.setText(c_dB.getString(20));
	    	rv3.setText(c_dB.getString(21));
	    	rv4.setText(c_dB.getString(22));
	    	rv5.setText(c_dB.getString(23));
	    	rv6.setText(c_dB.getString(24));
	    	rv7.setText(c_dB.getString(25));
	    	rv8.setText(c_dB.getString(26));
	    	rv9.setText(c_dB.getString(27));
	    	rv10.setText(c_dB.getString(28));
	    	rv11.setText(c_dB.getString(29));
	    	rv12.setText(c_dB.getString(30));
	    	rv13.setText(c_dB.getString(31));
	    	rv14.setText(c_dB.getString(32));
	    	rv15.setText(c_dB.getString(33));
	    	rv16.setText(c_dB.getString(34));
	    	*/
	    	d1y=Integer.parseInt(c_dB.getString(12));
		    d1m=Integer.parseInt(c_dB.getString(13));
		    d1d=Integer.parseInt(c_dB.getString(14));
		    
	    	d2y=Integer.parseInt(c_dB.getString(16));
		    d2m=Integer.parseInt(c_dB.getString(17));
		    d2d=Integer.parseInt(c_dB.getString(18));	        
		    
		    //int timediff=TimeDiff(d1y,d1m,d1d,d2y,d2m,d2d);
			//totalticket_max = timediff/Vote_Interval;
			
			totalticket_max=TicketDiff(d1y,d1m,d1d,d2y,d2m,d2d);
	    	
	    	vote1 = Integer.parseInt(c1ticket.getText().toString());
	    	vote2 = Integer.parseInt(c2ticket.getText().toString());
	    	vote3 = Integer.parseInt(c3ticket.getText().toString());
	    	vote4 = Integer.parseInt(c4ticket.getText().toString());
	    	totalticket = Integer.parseInt(leftticket.getText().toString());
	    	
	    	d1y_temp=d1y;
	    	d1m_temp=d1m;
	    	d1d_temp=d1d;
	    	d2y_temp=d2y;
	    	d2m_temp=d2m;
	    	d2d_temp=d2d;
	    	totalticket_max_temp=totalticket_max;
	    	totalticket_temp=totalticket;
	    	
	    	GetTicketCanVote();
	    	
	    	output.setTextColor(0xff0000ff);
	    	output.setText("訊息："+todaymsg+"歡迎使用四分之一革命投票器！");
	    	votereason.setTextColor(0xff0000ff);
	    	votereason.setText("投票原因：");
		    //outputmsgDia="訊息："+todaymsg+"歡迎使用四分之一革命投票器！";
	     	//AlertD();
	    	
	    	
	    	TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
		        @Override
		        public void afterTextChanged(Editable s) {
		        	
		        }

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					//Here, be careful, cause repeatedly Alert Dia
					SaveData();
				}
		    };
		    c1name.addTextChangedListener(fieldValidatorTextWatcher);
		    c2name.addTextChangedListener(fieldValidatorTextWatcher);
		    c3name.addTextChangedListener(fieldValidatorTextWatcher);
		    c4name.addTextChangedListener(fieldValidatorTextWatcher);
		}
	 	
	
//========================================================================================================================
// Calendar management
//========================================================================================================================	
	
	  public void DatetoCalender() {
		  
		   GetTicketCanVote();
		   
		   //1/1~2/3 ---> Save in 10/31 in last year
	   	   {//Default
	   		//alarmmsg="10月31日萬聖夜，一起幫小孩子趕走壞蛋。";
	   		if((d1y_now-1)<0)
	   		{
	   			outputmsgDia="Debug:(d1y_now-1)<0!";
    	   		AlertD();
	   			return;
	   		}
	   		Cal_Event_year=d1y_now-1;
	   	    Cal_Event_mon=VoteM[3];
	   	    Cal_Event_day=VoteD[3];
	   	    Cal_Event_id=3;
	   	   }
		  
	   	   if((d1m_now>VoteM[0])||((d1m_now==VoteM[0])&&(d1d_now>=VoteD[0])))//2/4~12/31 ---> Save in 2/4 first
	   	   {
	   		//alarmmsg="2月4日春天農民節到了，您辛苦了。";	   		
	   		Cal_Event_year=d1y_now;
	   	    Cal_Event_mon=VoteM[0];
	   	    Cal_Event_day=VoteD[0];
	   	    Cal_Event_id=0;
	   	   } 
	   	   if ((d1m_now>VoteM[1])||((d1m_now==VoteM[1])&&(d1d_now>=VoteD[1])))//5/1~12/31 ---> Save in 5/1
	   	   {
	   		//alarmmsg="5月1日 國際勞動節到了，您辛苦了。";
	   		Cal_Event_year=d1y_now;
	   	    Cal_Event_mon=VoteM[1];
	   	    Cal_Event_day=VoteD[1];
	   	    Cal_Event_id=1;
	   	   } 
	   	   if ((d1m_now>VoteM[2])||((d1m_now==VoteM[2])&&(d1d_now>=VoteD[2])))//8/12~12/31 ---> Save in 8/12
	   	   {
	   		//alarmmsg="8月12日國際青年節到了，想想未來。";
	   		Cal_Event_year=d1y_now;
	   	    Cal_Event_mon=VoteM[2];
	   	    Cal_Event_day=VoteD[2];
	   	    Cal_Event_id=2;
	   	   } 
	   	   if ((d1m_now>VoteM[3])||((d1m_now==VoteM[3])&&(d1d_now>=VoteD[3])))//10/31~12/31
	   	   {
	   		//alarmmsg="10月31日萬聖夜到了，一起幫小孩子趕走壞蛋。";
	   		Cal_Event_year=d1y_now;
	   	    Cal_Event_mon=VoteM[3];
	   	    Cal_Event_day=VoteD[3];
	   	    Cal_Event_id=3;
	   	   }
	   	  	   	   
	   	CalenderSaveDate=Integer.toString(Cal_Event_year)+"/"+Integer.toString(Cal_Event_mon)+"/"+Integer.toString(Cal_Event_day);
	  
	  }
	
	  public void WritetoCalender() {
		  
		  //Decide the write date.   

          Intent intent = new Intent(Intent.ACTION_INSERT);
          intent.setType("vnd.android.cursor.item/event");
          intent.putExtra(CalendarContract.Events.TITLE, "四分之一投票記錄"+CalenderSaveDate);
          intent.putExtra(CalendarContract.Events.DESCRIPTION, "###我的四分之一革命進度###\n" +
        		  "[距離下次選舉剩下]:["+leftticket.getText().toString()+"]\n" +
                  "[尚可投]:["+tocketcanvote.getText().toString()+"]\n" +
                  "["+c1name.getText().toString()+"]:["+c1ticket.getText().toString()+"]\n" +
                  "["+c2name.getText().toString()+"]:["+c2ticket.getText().toString()+"]\n" +                  
                  "["+c3name.getText().toString()+"]:["+c3ticket.getText().toString()+"]\n" +
                  "["+c4name.getText().toString()+"]:["+c4ticket.getText().toString()+"]\n" +
                  "[上次選舉日期]:["+lastdate.getText().toString()+"]\n" +
                  "[下次選舉日期]:["+nextdate.getText().toString()+"]\n" +
                  "\n" +
                  "###投票原因記錄###\n" +
                  "==="+rv1.getText().toString()+"===\n");
                  //Only use rv1 to save all reasons
                  //+
                  //"==="+rv2.getText().toString()+"===\n" +
                  //"==="+rv3.getText().toString()+"===\n" +
                  //"==="+rv4.getText().toString()+"===\n" +
                  //"==="+rv5.getText().toString()+"===\n" +
                  //"==="+rv6.getText().toString()+"===\n" +
                  //"==="+rv7.getText().toString()+"===\n" +
                  //"==="+rv8.getText().toString()+"===\n" +
                  //"==="+rv9.getText().toString()+"===\n" +
                  //"==="+rv10.getText().toString()+"===\n" +
                  //"==="+rv11.getText().toString()+"===\n" +
                  //"==="+rv12.getText().toString()+"===\n" +
                  //"==="+rv13.getText().toString()+"===\n" +
                  //"==="+rv14.getText().toString()+"===\n" +
                  //"==="+rv15.getText().toString()+"===\n" +
                  //"==="+rv16.getText().toString()+"===\n");

          // Setting dates
          //GregorianCalendar calDate = new GregorianCalendar(2014, 04, 01);//2014/5/1
          GregorianCalendar calDate = new GregorianCalendar(Cal_Event_year, Cal_Event_mon-1, Cal_Event_day);
          intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                  calDate.getTimeInMillis());
          intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                  calDate.getTimeInMillis());

          // make it a full day event
          intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

          // Making it private and shown as busy
          intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
          intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE);
          intent.putExtra(CalendarContract.Events.HAS_ALARM, 0);
          intent.putExtra(CalendarContract.Events.ALLOWED_REMINDERS, "");

          startActivity(intent);
		  
	  }
	  
      public void ReadfromCalender() {
    	  
    	  //Read data from right date.
    	  
          mCursor = null;
          mCursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, COLS,"title = ?", new String[]{"四分之一投票記錄"+CalenderSaveDate}, null);
          mCursor.moveToFirst();
          CalIds = new int[mCursor.getCount()];
          Ctitle = new String[mCursor.getCount()];
          CDescription = new String[mCursor.getCount()];
          Cdate = new String[mCursor.getCount()];
          EventID = new String[mCursor.getCount()];
          Format formator = DateFormat.getDateFormat(_context);
          for (int i = 0; i < Ctitle.length; i++) {
              CalIds[i] = mCursor.getInt(0);
              Ctitle[i] = mCursor.getString(1);
              Cdate[i] = formator.format(mCursor.getLong(2)) ;
              CDescription[i] = mCursor.getString(3);
              EventID[i]=mCursor.getString(4);
              mCursor.moveToNext();
          }
          mCursor.moveToFirst();
          String title = "N/A";
          Long start = 0L;
          String _Description="";
          if(!mCursor.isLast())
          {
              mCursor.moveToNext();
          }

          //In case, 2 same data, get which one?
          Format df = DateFormat.getDateFormat(_context);
          Format tf = DateFormat.getTimeFormat(_context);
          try {
              title = mCursor.getString(1);
              start = mCursor.getLong(2);
              _Description = mCursor.getString(3);
              ParseDatainCalendarDescription = mCursor.getString(3);
          } catch (Exception e) {
          }
          //TextViewReadEvent.setText(title + " on " + df.format(start) + " at " + tf.format(start) +"\n"+ _Description);		  
	  }
      
      public void DelEventInCalender() {
    	  
    	  //Delete data in right date.
    	  
    	  if(Ctitle.length<1)
    	  {
    		  return; // No matching event.
    	  }
          
    	  for (int i = 0; i < Ctitle.length; i++) 
    	  {
    	      String[] selArgs = new String[] {EventID[Ctitle.length-1]};
    	      int deleted = getContentResolver().delete(CalendarContract.Events.CONTENT_URI,CalendarContract.Events._ID + " =? ", selArgs);
          }
    	  
      }
      
      public void InitloadorNewdB() 
      {
      	
  	   	//Date to Calendar
  	    DatetoCalender();
  	    
  	    //Read previous data in Calendar  	    
     	for(int j = 0; j < 1; j++)//Just read at least 1 year data from Google calendar.
     	{
  	       for (int i = 0; i < 4; i++) //i=0,1,2,3, read 4 times, whatever, just read from 11/1. 
  	       {
  	          CalenderSaveDate=Integer.toString(Cal_Event_year-j)+"/"+Integer.toString(VoteM[3-i])+"/"+Integer.toString(VoteD[3-i]);  	          
  	          ReadfromCalender();
  	          
  	          if(Ctitle.length>0)
  	          {
  	        	//InitDateFromCalendar();
  	        	NewRecordFromCalendar=1;
  	        	break;
  	          }
  	        }
     	}  	
      }
      
      public void InitDateFromCalendar() 
      {
    	  
          Pattern pattern4 = Pattern.compile("\\[(.*)\\]:\\[(.*)\\]");
          Matcher m4 = pattern4.matcher(ParseDatainCalendarDescription);
          List<String> Candidate4 = new ArrayList<String>();
          List<String> TheVotes4 = new ArrayList<String>();
          while (m4.find()) {
              Candidate4.add(m4.group(1));
              TheVotes4.add(m4.group(2));
          }

          Pattern pattern5 = Pattern.compile("===(.*)===",Pattern.DOTALL);
          Matcher m5 = pattern5.matcher(ParseDatainCalendarDescription);
          List<String> CommentReason5 = new ArrayList<String>();
          //List<String> Comments5 = new ArrayList<String>();
          while (m5.find()) {
          	CommentReason5.add(m5.group(1));
              //Comments5.add(m5.group(2));
          }
          
          //String[] y = x.toArray(new String[0]);
          String[] x = Candidate4.toArray(new String[0]);
          String[] y = TheVotes4.toArray(new String[0]);
          String[] z = CommentReason5.toArray(new String[0]);
          String[] LastDateSplit = y[6].split("/");
          String[] NextDateSplit = y[7].split("/");
    	     	  
    	//because while monitor off, dB will be removed by OS. 
	    db = dbHelper.getWritableDatabase(); // 開啟資料庫
      	//db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1,rv2,rv3,rv4,rv5,rv6,rv7,rv8,rv9,rv10,rv11,rv12,rv13,rv14,rv15,rv16)" +
      	String sqlstring= "Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1)" +
      			"Values('"+ firstname + "'," +y[0] + "," +
					"'" + x[2]+ "'," + y[2] + 
					",'" + x[3] + "'," + y[3]+ 
					",'" + x[4] + "'," + y[4]+ 
					",'" + x[5] + "'," +y[5]+ 
					",'" + y[6] + "',"+Integer.parseInt(LastDateSplit[0])+","+Integer.parseInt(LastDateSplit[1])+","+Integer.parseInt(LastDateSplit[2])+
					",'" + y[7] +"',"+Integer.parseInt(NextDateSplit[0])+","+Integer.parseInt(NextDateSplit[1])+","+Integer.parseInt(NextDateSplit[2])+ 	
	                ",'" + z[0] +"')";
      	 
      	db.execSQL(sqlstring);
      	                //Only use rv1 
      					//",'" + z[0] +"','" + z[1] +"','" +z[2] +"','" + z[3] +"','" + z[4] +"','" + z[5] +"','" + z[6] +"','" + z[7] +"','" + z[8] +"','" + z[9] +"','" + z[10] +"','" + z[11] +"','" + z[12] +"','" + z[13] +"','" + z[14] +"','" + z[15] +"')");
      }
	
	
	Calendar dt = Calendar.getInstance();

   
//========================================================================================================================
// Vote management
//========================================================================================================================	    
    
    
    //public void GetTicketCanVote() {
    public void GetTicketCanVote() {
    	  	    	
   	    d1y_now=dt.get(Calendar.YEAR);
		d1m_now=dt.get(Calendar.MONTH)+1;
		d1d_now=dt.get(Calendar.DAY_OF_MONTH);
		
		//int timediff=TimeDiff(d1y,d1m,d1d,d1y_now,d1m_now,d1d_now);		
		//totalticketcanvote_now=timediff/Vote_Interval-(vote1+vote2+vote3+vote4);
		totalticketcanvote_now=TicketDiff(d1y,d1m,d1d,d1y_now,d1m_now,d1d_now);
		totalticketcanvote_now=totalticketcanvote_now-(vote1+vote2+vote3+vote4);
		
		if(totalticketcanvote_now<0)
		{
			totalticketcanvote_now=0;
		}
		
		//Tom: if today > the next vote date, just return!
		int day_diff_after_lastvote=TimeDiff(d2y,d2m,d2d,d1y_now,d1m_now,d1d_now);
		if(day_diff_after_lastvote>0)
		{
		  totalticketcanvote_now=0;
		}
				
		tocketcanvote.setText(Integer.toString(totalticketcanvote_now));
		
		todaymsg="今天是"+Integer.toString(d1y_now)+"/"+Integer.toString(d1m_now)+"/"+Integer.toString(d1d_now)+"，";
		
		//todaydate.setText(Integer.toString(d1y_now)+"/"+Integer.toString(d1m_now)+"/" + Integer.toString(d1d_now));
    	
    }
    
    public int TimeDiff(int y1, int m1, int d1, int y2, int m2, int d2) {
    	   	
    	//time interval of 2 dates
    	Calendar c1 = Calendar.getInstance();
    	Calendar c2 = Calendar.getInstance();
    	c1.set(y1,m1,d1);
    	c2.set(y2,m2,d2);
    	int day1 = c1.get(Calendar.DAY_OF_YEAR);
    	int day2 = c2.get(Calendar.DAY_OF_YEAR);
    	int dayDiff = (day2 - day1)+(y2-y1)*356;
    	
    	return dayDiff;  	
    }
  
 //========================================================================================================================
 // Vote calculate by 4 tickets in 4 holidays in a year.
 //========================================================================================================================	       
   
    public int TicketDiff(int y1, int m1, int d1, int y2, int m2, int d2) {
	   	
    	int TickDiff=0;
    	//y1 is before, y2 is after.
    	int c1 = WhatPeriod_1(y1, m1, d1); 
    	int c2 = WhatPeriod_2(y2, m2, d2);
    	if((y2-y1)==0)
    	{
    		TickDiff=c2-(4-c1);//(4-c1) are tickets have used in that year.
    		
    	}else//(y2-y1>0)
    	{
    		TickDiff=c2+c1+(y2-y1-1)*4;
    	}
    	
    	if((TickDiff)<0)
   		{
   			outputmsgDia="Debug:(TickDiff)<0!";
	   		AlertD();
   		}
    	
    	return TickDiff;  	
    }
    
   //Last day 
   public int WhatPeriod_1(int y1, int m1, int d1) {
		  
	       int Ticketis=4;
	       //GetTicketCanVote();
		   
		   //1/1~2/4 ---> 4 tickets
	   	   {//Default
	   		//alarmmsg="10月31日萬聖夜，一起幫小孩子趕走壞蛋。";
	   		Ticketis=4;
	   	   }
		  
	   	   if((m1>VoteM[0])||((m1==VoteM[0])&&(d1>VoteD[0])))//2/5~5/1 ---> 3 tickets 
	   	   {
	   		//alarmmsg="2月4日春天農民節到了，您辛苦了。";	   		
	   		Ticketis=3;
	   	   } 
	   	   if ((m1>VoteM[1])||((m1==VoteM[1])&&(d1>VoteD[1])))//5/2~8/12 ---> 2 tickets
	   	   {
	   		//alarmmsg="5月1日 國際勞動節到了，您辛苦了。";
	   		Ticketis=2;
	   	   } 
	   	   if ((m1>VoteM[2])||((m1==VoteM[2])&&(d1>VoteD[2])))//8/13~10/31 ---> 1 tickets
	   	   {
	   		//alarmmsg="8月12日國際青年節到了，想想未來。";
	   		Ticketis=1;
	   	   } 
	   	   if ((m1>VoteM[3])||((m1==VoteM[3])&&(d1>VoteD[3])))//11/1~12/31 ---> 0 tickets
	   	   {
	   		//alarmmsg="10月31日萬聖夜到了，一起幫小孩子趕走壞蛋。";
	   		// If the last vote day is 11/2, no ticket in that year.
	   		Ticketis=0;
	   	   }
	   	  	   	   
	   	return Ticketis;
	  }
   
   //Next day
   public int WhatPeriod_2(int y2, int m2, int d2) {
		  
       int Ticketis=0;
       //GetTicketCanVote();
	   
	   //1/1~2/3 ---> 4 tickets
   	   {//Default
   		//alarmmsg="10月31日萬聖夜，一起幫小孩子趕走壞蛋。";
   		Ticketis=0;
   	   }
	  
   	   if((m2>VoteM[0])||((m2==VoteM[0])&&(d2>=VoteD[0])))//2/4~4/30 ---> 3 tickets 
   	   {
   		//alarmmsg="2月4日春天農民節到了，您辛苦了。";	   		
   		Ticketis=1;
   	   } 
   	   if ((m2>VoteM[1])||((m2==VoteM[1])&&(d2>=VoteD[1])))//5/1~8/11 ---> 2 tickets
   	   {
   		//alarmmsg="5月1日 國際勞動節到了，您辛苦了。";
   		Ticketis=2;
   	   } 
   	   if ((m2>VoteM[2])||((m2==VoteM[2])&&(d2>=VoteD[2])))//8/12~10/30 ---> 1 tickets
   	   {
   		//alarmmsg="8月12日國際青年節到了，想想未來。";
   		Ticketis=3;
   	   } 
   	   if ((m2>VoteM[3])||((m2==VoteM[3])&&(d2>=VoteD[3])))//10/31~12/31 ---> 0 tickets
   	   {
   		//alarmmsg="10月31日萬聖夜到了，一起幫小孩子趕走壞蛋。";
   		// If the next vote day is 11/1, 4 ticket in that year.
   		Ticketis=4;
   	   }
   	  	   	   
   	return Ticketis;
  }
    
    
	// button1事件處理程序
	@SuppressWarnings("deprecation")
	public void buttonlastdate_Click(View view) {
		//If press any button, reset the reset 3 times counter. 
   	    reset_count=0;
		Log.d("=====>", "button5 onClick");		
		if(VarManager.isEnableEdit)
		{
			showDialog(0);
		}
			   
	}
	// button2事件處理程序
	@SuppressWarnings("deprecation")
	public void buttonnextdate_Click(View view) {
		//If press any button, reset the reset 3 times counter. 
   	    reset_count=0;
		Log.d("=====>", "button6 onClick");
		if(VarManager.isEnableEdit)
		{
			showDialog(1);
		}
		
				
		//output.setText("訊息：更改下次選舉日期成功，請存檔！");
	}

	@Override
	protected Dialog onCreateDialog(int id){
		if (id == 0) {
			return new DatePickerDialog(this, dpickerListner1, d1y_temp, d1m_temp, d1d_temp);
		}else if (id == 1) {
			return new DatePickerDialog(this, dpickerListner2, d1y_temp, d1m_temp, d1d_temp);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener dpickerListner1
			= new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
			d1y_temp = year;
			d1m_temp = monthOfYear+1;
			d1d_temp = dayOfMonth;
			totalticket_max_temp = TicketDiff(d1y_temp,d1m_temp,d1d_temp,d2y,d2m,d2d);
			Toast.makeText(MainActivity.this, d1y_temp + "/" + d1m_temp+ "/" + d1d_temp, Toast.LENGTH_LONG).show();
			DateDiog1();
		}
	};

	private DatePickerDialog.OnDateSetListener dpickerListner2
			= new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
			d2y_temp = year;
			d2m_temp = monthOfYear+1;
			d2d_temp = dayOfMonth;
			totalticket_max_temp = TicketDiff(d1y,d1m,d1d,d2y_temp,d2m_temp,d2d_temp);
			Toast.makeText(MainActivity.this, d2y_temp + "/" + d2m_temp+ "/" + d2d_temp, Toast.LENGTH_LONG).show();
			DateDiog2();
		}
	};
/**
	protected Dialog onCreateDialog(int id) {
		
	    switch (id) {
         case 0: // 傳回DatePickerDialog對話方塊
        	 CustomDatePickerDialog dDialog1 = new CustomDatePickerDialog(this,
	 	     new DatePickerDialog.OnDateSetListener() {
				 public void onDateSet(DatePicker view, int year,
						  int monthOfYear,	int dayOfMonth) {	
					 		    			    					 
					    d1y_temp=year;
					    d1m_temp=monthOfYear+1;
					    d1d_temp=dayOfMonth;
						//int timediff=TimeDiff(d1y_temp,d1m_temp,d1d_temp,d2y,d2m,d2d);
					    //totalticket_max_temp = timediff/Vote_Interval;
					    
					    totalticket_max_temp = TicketDiff(d1y_temp,d1m_temp,d1d_temp,d2y,d2m,d2d);
					    
					    if(VarManager.getfirstbuttonstatus())
				    	{
				    		 //save
				    		 DateDiog1();
				    	}
					 	  

				 }						   	
					  }, dt.get(Calendar.YEAR),
						 dt.get(Calendar.MONTH),
						 dt.get(Calendar.DAY_OF_MONTH));
        	     	 
			 	  return dDialog1;
			 	  
			 	  
			    case 1: // 傳回TimePickerDialog對話方塊

			    	CustomDatePickerDialog dDialog2 = new CustomDatePickerDialog(this,
				 	     new DatePickerDialog.OnDateSetListener() {
							 public void onDateSet(DatePicker view, int year,
									  int monthOfYear,	int dayOfMonth) {	
								
								    d2y_temp=year;
								    d2m_temp=monthOfYear+1;
								    d2d_temp=dayOfMonth;
								    //int timediff=TimeDiff(d1y,d1m,d1d,d2y_temp,d2m_temp,d2d_temp);
								    //totalticket_max_temp = timediff/Vote_Interval;
								    //totalticket_max_temp = ((d2y_temp*12+d2m_temp)-(d1y*12+d1m))/Vote_Interval;
								    
								    totalticket_max_temp = TicketDiff(d1y,d1m,d1d,d2y_temp,d2m_temp,d2d_temp);
								    
								    if(VarManager.getfirstbuttonstatus())
							    	{
							    		 //save
							    		 DateDiog2();
							    	}
								 	
								   	 }							   	
								  }, dt.get(Calendar.YEAR),
									 dt.get(Calendar.MONTH),
									 dt.get(Calendar.DAY_OF_MONTH));
			    	
				 	  return dDialog2;
				 	  
			    }
			    return null;
			 }  // end of Dialog onCreateDialog() 
*/
//========================================================================================================================
// Vote button
//========================================================================================================================	
	
	public void buttonv1_Click(View view) {
      	 long id;
      	//If press any button, reset the reset 3 times counter. 
    	 reset_count=0;
    	 
       	GetTicketCanVote();
       	
      	if(((vote1+vote2+vote3+vote4)>=totalticket_max)||(totalticket<=0)||(totalticketcanvote_now<=0))
      	{
      		     		
     		//output.setTextColor(0xffff00ff);
     		//output.setText("訊息：一季一票，到今天為止，可用的票用完了，無法投票!");
     		outputmsgDia="訊息：一季一票，到今天為止，可用的票用完了，無法投票!";
         	AlertD();
          		
      		return;
      	}
       	
       	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
        builder.setTitle("投票確認！");
        builder.setMessage("請寫下你要投「"+c1name.getText()+"」的原因");
        
        // Set an EditText view to get user input 
        final EditText votereasoninput = new EditText(this);
        builder.setView(votereasoninput);
        
        builder.setPositiveButton("確定",
        		new DialogInterface.OnClickListener() { 
                public void onClick(DialogInterface  
                               dialoginterface, int i) { 
                	// 結束程式指令finish
                	//finish();
                  	vote1++;
                  	c1ticket.setText(Integer.toString(vote1));
                  	totalticket=totalticket-1;
                  	leftticket.setText(Integer.toString(totalticket));
               	 
                   	GetTicketCanVote();
                   	outputmsg="你投了「"+c1name.getText()+"」一票。";
           	        output.setText("訊息："+todaymsg+outputmsg);
           	        //String srt = votereasoninput.getEditableText().toString();
           	        votereason.setText("投票原因："+votereasoninput.getEditableText().toString());
           	        Reasonforvote();
                	
           	        //Save to dB.
           	        SaveData();
                	
           	        //Only save to calendar while user votes.
           	       	//Date to Calendar
           	        DatetoCalender();
           	       	//Read previous data in Calendar
           	        ReadfromCalender();
           	       	//Delete repeat data in Calendar 
           	        DelEventInCalender();
           	       	//Write data into Calendar        
           	        WritetoCalender();
           	        
           	        //outputmsgDia="訊息："+todaymsg+outputmsg;
              	    //AlertD();
                } 
          }); 
        
        builder.setNegativeButton("取消", null); 
        builder.show();    	
       	      	 
      	//utput.setTextColor(0xff0000ff);
        //output.setText("訊息：投了"+c1name.getText()+"一票，請存檔。"); 
              
      }
       
       public void buttonv2_Click(View view) {
         	long id;
         	//If press any button, reset the reset 3 times counter. 
	    	 reset_count=0;
	    	 
	        GetTicketCanVote();
	        	      	          	
         	if(((vote1+vote2+vote3+vote4)>=totalticket_max)||(totalticket<=0)||(totalticketcanvote_now<=0))
         	{
         		//output.setTextColor(0xffff00ff);
         		//output.setText("訊息：一季一票，到今天為止，可用的票用完了，無法投票!");
         		outputmsgDia="訊息：一季一票，到今天為止，可用的票用完了，無法投票!";
             	AlertD();
         		return;
         	}
         	
           	AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle("投票確認！");
            builder2.setMessage("請寫下你要投「"+c2name.getText()+"」的原因");
            
            // Set an EditText view to get user input 
            final EditText votereasoninput = new EditText(this);
            builder2.setView(votereasoninput);
            
            builder2.setPositiveButton("確定",
            		new DialogInterface.OnClickListener() { 
                    public void onClick(DialogInterface  
                                   dialoginterface, int i) { 
                    	// 結束程式指令finish
                    	//finish();
                      	vote2++;
                      	c2ticket.setText(Integer.toString(vote2));
                      	totalticket=totalticket-1;
                      	leftticket.setText(Integer.toString(totalticket));
                   	 
                       	GetTicketCanVote();
                       	outputmsg="你投了「"+c2name.getText()+"」一票。";
               	        output.setText("訊息："+todaymsg+outputmsg);
               	        
               	        votereason.setText("投票原因："+votereasoninput.getEditableText().toString());
               	        Reasonforvote();
                    	SaveData();
                    	
                    	//Only save to calendar while user votes.
               	       	//Date to Calendar
               	        DatetoCalender();
               	       	//Read previous data in Calendar
               	        ReadfromCalender();
               	       	//Delete repeat data in Calendar 
               	        DelEventInCalender();
               	       	//Write data into Calendar        
               	        WritetoCalender();
               	        //outputmsgDia="訊息："+todaymsg+outputmsg;
               	        //AlertD();
                    } 
              }); 
            
            builder2.setNegativeButton("取消", null); 
            builder2.show();  
          	
          	//output.setTextColor(0xff0000ff);
          	//output.setText("訊息：投了"+c2name.getText()+"一票，請存檔。");               
         }
       public void buttonv3_Click(View view) {
    	   	 long id;
    	     //If press any button, reset the reset 3 times counter. 
	    	 reset_count=0;
	    	 
          	 GetTicketCanVote();	         	      	 
   	   	 
    	   	if(((vote1+vote2+vote3+vote4)>=totalticket_max)||(totalticket<=0)||(totalticketcanvote_now<=0))
    	   	{
    	   		//output.setTextColor(0xffff00ff);
         		//output.setText("訊息：一季一票，到今天為止，可用的票用完了，無法投票!");
         		outputmsgDia="訊息：一季一票，到今天為止，可用的票用完了，無法投票!";
             	AlertD();
    	   		return;
    	   	}
    	   	
           	AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
            builder3.setTitle("投票確認！");
            builder3.setMessage("請寫下你要投「"+c3name.getText()+"」的原因");
            
            // Set an EditText view to get user input 
            final EditText votereasoninput = new EditText(this);
            builder3.setView(votereasoninput);
            
            builder3.setPositiveButton("確定",
            		new DialogInterface.OnClickListener() { 
                    public void onClick(DialogInterface  
                                   dialoginterface, int i) { 
                    	// 結束程式指令finish
                    	//finish();
                      	vote3++;
                      	c3ticket.setText(Integer.toString(vote3));
                      	totalticket=totalticket-1;
                      	leftticket.setText(Integer.toString(totalticket));
                   	 
                       	GetTicketCanVote();
                       	outputmsg="你投了「"+c3name.getText()+"」一票。";
               	        output.setText("訊息："+todaymsg+outputmsg);
               	        
               	        votereason.setText("投票原因："+votereasoninput.getEditableText().toString());
               	        Reasonforvote();
                    	SaveData();
                    	
                    	//Only save to calendar while user votes.
               	       	//Date to Calendar
               	        DatetoCalender();
               	       	//Read previous data in Calendar
               	        ReadfromCalender();
               	       	//Delete repeat data in Calendar 
               	        DelEventInCalender();
               	       	//Write data into Calendar        
               	        WritetoCalender();
               	        //outputmsgDia="訊息："+todaymsg+outputmsg;
                  	    //AlertD();
                    } 
              }); 
            
            builder3.setNegativeButton("取消", null); 
            builder3.show();
    	   	
    	   	//output.setTextColor(0xff0000ff);
    	   	//output.setText("訊息：投了"+c3name.getText()+"一票，請存檔。");             
    	   }
    	    
    	    public void buttonv4_Click(View view) {
    	      	long id; 
    	        //If press any button, reset the reset 3 times counter. 
   	    	    reset_count=0;
   	    	 
   	        	GetTicketCanVote();   	        	      	 
    	      	
    	      	if(((vote1+vote2+vote3+vote4)>=totalticket_max)||(totalticket<=0)||(totalticketcanvote_now<=0))
    	      	{
    	      		//output.setTextColor(0xffff00ff);
    	     		//output.setText("訊息：一季一票，到今天為止，可用的票用完了，無法投票!");
    	     		outputmsgDia="訊息：一季一票，到今天為止，可用的票用完了，無法投票!";
                 	AlertD();
    	      		return;
    	      	}     	 
    	       	
               	AlertDialog.Builder builder4 = new AlertDialog.Builder(this); 
                builder4.setTitle("投票確認！");
                builder4.setMessage("請寫下你要投「"+c4name.getText()+"」的原因");
                
                // Set an EditText view to get user input 
                final EditText votereasoninput = new EditText(this);
                builder4.setView(votereasoninput);
                builder4.setPositiveButton("確定",
                		new DialogInterface.OnClickListener() { 
                        public void onClick(DialogInterface  
                                       dialoginterface, int i) { 
                        	// 結束程式指令finish
                        	//finish();
                          	vote4++;
                          	c4ticket.setText(Integer.toString(vote4));
                          	totalticket=totalticket-1;
                          	leftticket.setText(Integer.toString(totalticket));
                       	 
                           	GetTicketCanVote();
                           	outputmsg="你投了「"+c4name.getText()+"」一票。";
                   	        output.setText("訊息："+todaymsg+outputmsg);
                   	        
                   	        votereason.setText("投票原因："+votereasoninput.getEditableText().toString());
                   	        Reasonforvote();
                        	SaveData();
                        	
                        	//Only save to calendar while user votes.
                   	       	//Date to Calendar
                   	        DatetoCalender();
                   	       	//Read previous data in Calendar
                   	        ReadfromCalender();
                   	       	//Delete repeat data in Calendar 
                   	        DelEventInCalender();
                   	       	//Write data into Calendar        
                   	        WritetoCalender();
                   	        //outputmsgDia="訊息："+todaymsg+outputmsg;
                      	    //AlertD();
                        } 
                  }); 
                
                builder4.setNegativeButton("取消", null); 
                builder4.show();  	       	
    	       	//output.setTextColor(0xff0000ff);
    	       	//output.setText("訊息：投了"+c4name.getText()+"一票，請存檔。");   
    	      }

//========================================================================================================================
// Vote Reason
//========================================================================================================================	
    	    
	
        	  public void Reasonforvote() {
        		  
        		  String reasonofvoteSum = "";
        		  
        		  totalvotedtickets=(vote1+vote2+vote3+vote4);
        		  if((totalvotedtickets<=0)||(totalvotedtickets>totalticket_max))
        		  {
        			  //Wrong number!!!
        			  return;
        		  }
        		  
        		  reasonofvoteSum=rv1.getText().toString();
        		  reasonofvote="第"+Integer.toString(totalvotedtickets)+"票："+todaymsg+outputmsg+votereason.getText()+"\n"+reasonofvoteSum;
        		  rv1.setText(reasonofvote);
        		 
        		  /* Just use one text box rv1
        		  switch(totalvotedtickets)
        		  {
        		  case 1:
        			  rv1.setText(reasonofvote);
        			  break;
        		  case 2:
        			  rv2.setText(reasonofvote);
        			  break;
        		  case 3:
        			  rv3.setText(reasonofvote);
        			  break;
        		  case 4:
        			  rv4.setText(reasonofvote);
        			  break;
        		  case 5:
        			  rv5.setText(reasonofvote);
        			  break;
        		  case 6:
        			  rv6.setText(reasonofvote);
        			  break;
        		  case 7:
        			  rv7.setText(reasonofvote);
        			  break;
        		  case 8:
        			  rv8.setText(reasonofvote);
        			  break;
        		  case 9:
        			  rv9.setText(reasonofvote);
        			  break;
        		  case 10:
        			  rv10.setText(reasonofvote);
        			  break;
        		  case 11:
        			  rv11.setText(reasonofvote);
        			  break;
        		  case 12:
        			  rv12.setText(reasonofvote);
        			  break;
        		  case 13:
        			  rv13.setText(reasonofvote);
        			  break;
        		  case 14:
        			  rv14.setText(reasonofvote);
        			  break;
        		  case 15:
        			  rv15.setText(reasonofvote);
        			  break;
        		  case 16:
        			  rv16.setText(reasonofvote);
        			  break;
        			  
        		  default:
        			  rv1.setText("Wrong...");
            		  break;
        		  }
        		  */
        		  
        	  }	
	 
    		// Button元件的事件處理 - 新增記錄
    		//Tom: Only update, no save, one on data in dB!!!
    	    public void buttonsave_Click(View view) {
    	    	 long id; 
    	    	 //If press any button, reset the reset 3 times counter. 
    	    	 reset_count=0;
    	    	 SaveData();
    	    	 
    	    } 

//========================================================================================================================
// Save data to dB
//========================================================================================================================	
    	    
    	    
    	    public void SaveData() {
    	    	
   	    	 //because while monitor off, dB will be removed by OS. 
   	    	 db = dbHelper.getWritableDatabase(); // 開啟資料庫
   	    	 
   	     	//db.execSQL("Select * from 1a where name = 'jack'");
   	     	Cursor c = db.rawQuery("Select * from " + DATABASE_TABLE , null);    	
   	     	    	
   	     	//The first time init dB. If out in data_init() will crash, seems onCreate did not finish some textview.
   	        //If no data, c.getCount() is 0
   	     	if(c.getCount()==0)
   	     	{   //first time use that AP, init dB
   	     		//output.setText("Debug: no data in database");
   	     		//init the data base with init values in variable.
   	          	//db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1,rv2,rv3,rv4,rv5,rv6,rv7,rv8,rv9,rv10,rv11,rv12,rv13,rv14,rv15,rv16)Values('"+ firstname + "'," + leftticket.getText().toString() + ",'" + c1name.getText().toString()+ "'," + c1ticket.getText().toString() + ",'" + c2name.getText().toString() + "'," + c2ticket.getText().toString()+ ",'" + c3name.getText().toString() + "'," + c3ticket.getText().toString()+ ",'" + c4name.getText().toString() + "'," + c4ticket.getText().toString()+ ",'" + lastdate.getText().toString() + "',"+d1y+","+d1m+","+d1d+",'" + nextdate.getText().toString() +"',"+d2y+","+d2m+","+d2d+",'" + rv1.getText().toString() +"','" + rv2.getText().toString() +"','" + rv3.getText().toString() +"','" + rv4.getText().toString() +"','" + rv5.getText().toString() +"','" + rv6.getText().toString() +"','" + rv7.getText().toString() +"','" + rv8.getText().toString() +"','" + rv9.getText().toString() +"','" + rv10.getText().toString() +"','" + rv11.getText().toString() +"','" + rv12.getText().toString() +"','" + rv13.getText().toString() +"','" + rv14.getText().toString() +"','" + rv15.getText().toString() +"','" + rv16.getText().toString() +"')");
   	            db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1)Values('"+ firstname + "'," + leftticket.getText().toString() + ",'" + c1name.getText().toString()+ "'," + c1ticket.getText().toString() + ",'" + c2name.getText().toString() + "'," + c2ticket.getText().toString()+ ",'" + c3name.getText().toString() + "'," + c3ticket.getText().toString()+ ",'" + c4name.getText().toString() + "'," + c4ticket.getText().toString()+ ",'" + lastdate.getText().toString() + "',"+d1y+","+d1m+","+d1d+",'" + nextdate.getText().toString() +"',"+d2y+","+d2m+","+d2d+",'" + rv1.getText().toString() +"')");

   	     	} else if(c.getCount()!=1){
   	     		//output.setText("Debug: Wrong! only one row is allowed");
   	     		return;
   	         }

 	     	
   	    	 //init the variables by the data of text box, user might change data and operate again.
   	    	 //The date variables are updated while change the date!!!
   	 		 leftticket = (EditText) findViewById(R.id.editText6);
   	 		 tocketcanvote = (EditText) findViewById(R.id.editText36);
   			 c1ticket = (EditText) findViewById(R.id.editText7);
   			 c2ticket = (EditText) findViewById(R.id.editText8);
   			 c3ticket = (EditText) findViewById(R.id.editText9);
   			 c4ticket = (EditText) findViewById(R.id.editText10);
   			 c1name = (EditText) findViewById(R.id.editText1);
   			 c2name = (EditText) findViewById(R.id.editText2);
   			 c3name = (EditText) findViewById(R.id.editText3);
   			 c4name = (EditText) findViewById(R.id.editText4);
   			 lastdate = (EditText) findViewById(R.id.editText11);
   			 nextdate = (EditText) findViewById(R.id.editText12);
   			 rv1=(TextView)findViewById(R.id.textView51);
   			 /*
   			 rv2=(TextView)findViewById(R.id.textView52);
   			 rv3=(TextView)findViewById(R.id.textView53);
   			 rv4=(TextView)findViewById(R.id.textView54);
   			 rv5=(TextView)findViewById(R.id.textView55);
   			 rv6=(TextView)findViewById(R.id.textView56);
   			 rv7=(TextView)findViewById(R.id.textView57);
   			 rv8=(TextView)findViewById(R.id.textView58);
   			 rv9=(TextView)findViewById(R.id.textView59);
   			 rv10=(TextView)findViewById(R.id.textView60);
   			 rv11=(TextView)findViewById(R.id.textView61);
   			 rv12=(TextView)findViewById(R.id.textView62);
   			 rv13=(TextView)findViewById(R.id.textView63);
   			 rv14=(TextView)findViewById(R.id.textView64);
   			 rv15=(TextView)findViewById(R.id.textView65);
   			 rv16=(TextView)findViewById(R.id.textView66);
   			*/
			 //int timediff=TimeDiff(d1y,d1m,d1d,d2y,d2m,d2d);
			 //totalticket_max_temp = timediff/Vote_Interval;
			 
			 totalticket_max_temp = TicketDiff(d1y,d1m,d1d,d2y,d2m,d2d);

   			 //totalticket_max = ((d2y*12+d2m)-(d1y*12+d1m))/Vote_Interval;
   		    	
   		     vote1 = Integer.parseInt(c1ticket.getText().toString());
   		     vote2 = Integer.parseInt(c2ticket.getText().toString());
   		     vote3 = Integer.parseInt(c3ticket.getText().toString());
   		     vote4 = Integer.parseInt(c4ticket.getText().toString());
   		     totalticket = Integer.parseInt(leftticket.getText().toString());
   		     
   		     d1y_temp=d1y;
   		     d1m_temp=d1m;
   		     d1d_temp=d1d;
   		     d2y_temp=d2y;
   		     d2m_temp=d2m;
   		     d2d_temp=d2d;
   		     totalticket_max_temp=totalticket_max;
   		     totalticket_temp=totalticket;
   	    	  	 
   	     	//Tom: Only update, not save, one on data in dB!!!
   		    // (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1,rv2,rv3,rv4,rv5,rv6,rv7,rv8,rv9,rv10,rv11,rv12,rv13,rv14,rv15,rv16)
   	       	//db.execSQL("Update " + DATABASE_TABLE + " set tvote=" + leftticket.getText().toString() + ", c1name = '" + c1name.getText().toString() + "', c1vote=" + c1ticket.getText().toString() + ", c2name ='" + c2name.getText().toString() + "', c2vote=" + c2ticket.getText().toString() + ", c3name ='" + c3name.getText().toString() + "', c3vote=" + c3ticket.getText().toString() + ", c4name ='" + c4name.getText().toString() + "', c4vote=" + c4ticket.getText().toString() + ", d1all='" + lastdate.getText().toString() + "', d1y=" + d1y + ", d1m=" + d1m + ", d1d=" + d1d + ", d2all='" + nextdate.getText().toString() + "', d2y=" + d2y + ", d2m=" + d2m + ", d2d=" + d2d + ", rv1 = '" + rv1.getText().toString() + "', rv2 = '" + rv2.getText().toString() + "', rv3 = '" + rv3.getText().toString() + "', rv4 = '" + rv4.getText().toString() + "', rv5 = '" + rv5.getText().toString() + "', rv6 = '" + rv6.getText().toString() + "', rv7 = '" + rv7.getText().toString() + "', rv8 = '" + rv8.getText().toString() + "', rv9 = '" + rv9.getText().toString() + "', rv10 = '" + rv10.getText().toString() + "', rv11 = '" + rv11.getText().toString() + "', rv12 = '" + rv12.getText().toString() + "' , rv13 = '" + rv13.getText().toString() + "', rv14 = '" + rv14.getText().toString() + "', rv15 = '" + rv15.getText().toString() + "', rv16 = '" + rv16.getText().toString() + "'where dbname='" + firstname + "'");
   	        db.execSQL("Update " + DATABASE_TABLE + " set tvote=" + leftticket.getText().toString() + ", c1name = '" + c1name.getText().toString() + "', c1vote=" + c1ticket.getText().toString() + ", c2name ='" + c2name.getText().toString() + "', c2vote=" + c2ticket.getText().toString() + ", c3name ='" + c3name.getText().toString() + "', c3vote=" + c3ticket.getText().toString() + ", c4name ='" + c4name.getText().toString() + "', c4vote=" + c4ticket.getText().toString() + ", d1all='" + lastdate.getText().toString() + "', d1y=" + d1y + ", d1m=" + d1m + ", d1d=" + d1d + ", d2all='" + nextdate.getText().toString() + "', d2y=" + d2y + ", d2m=" + d2m + ", d2d=" + d2d + ", rv1 = '" + rv1.getText().toString() + "'where dbname='" + firstname + "'");
	     	
   	       	GetTicketCanVote();
   	       	
   	     	//顯示成功新增記錄的訊息
   	       	//output.setTextColor(0xff0000ff);
   	     	//output.setText("訊息："+todaymsg+outputmsg);
   	        //output.setText("訊息："+todaymsg);
   	        //outputmsgDia="訊息："+todaymsg+outputmsg;
      	    //AlertD();
   	       	    	    	
    	    }

//========================================================================================================================
// Reset for a new election management
//========================================================================================================================	

    	    // 更新記錄
    	    public void buttonreset_Click(View view) {
    	    	int count;
    	    	
    	    	reset_count++;    	    	
    	    	if(reset_count<3)
    	    	{
    	    		//output.setTextColor(0xffff00ff);
        	   		//output.setText("訊息：需連續按3次鈕才能重新開始一個新的選舉，這是第"+Integer.toString(reset_count)+"次按鈕!");
        	   		outputmsgDia="訊息：需連續按3次鈕才能重新開始一個新的選舉，這是第"+Integer.toString(reset_count)+"次按鈕!";
        	   		AlertD();
        	   		return;
    	    	}
    	    	
    	    	ResetDB();
    	    	
     	        reset_count=0;
     	        
     	       	//output.setTextColor(0xff0000ff);
    	     	//output.setText("訊息：資料重置成功！");
    	     	outputmsg="重新開始一個新的選舉！";
    	     	output.setText("訊息："+todaymsg+outputmsg);
    	     	outputmsgDia="訊息："+todaymsg+outputmsg; 
    	     	votereason.setTextColor(0xff0000ff);
    	    	votereason.setText("投票原因：");
    	     	AlertD();
    	    }
    	    
    	    public void ResetDB()
    	    {
	    		InitGlobalVariabls();
    	    	
    	    	//because while monitor off, dB will be removed by OS.
    	    	db = dbHelper.getWritableDatabase(); // 開啟資料庫
    	    	
    	    	//db.execSQL("Select * from 1a where name = 'jack'");
    	    	Cursor c = db.rawQuery("Select * from " + DATABASE_TABLE , null);    	
    	    	
    	    	//The first time init dB. If out in data_init() will crash, seems onCreate did not finish some textview.
    	        //If no data, c.getCount() is 0
    	    	if(c.getCount()==0)
    	    	{   
    	    		//first time use that AP, init dB
    	    		//output.setText("Debug: no data in database");//Remove that due to shared in first time. 
    	    		//init the data base with init values in variable.
    	         	//db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d)Values('"+ firstname + "'," + leftticket.getText().toString() + ",'" + c1name.getText().toString()+ "'," + c1ticket.getText().toString() + ",'" + c2name.getText().toString() + "'," + c2ticket.getText().toString()+ ",'" + c3name.getText().toString() + "'," + c3ticket.getText().toString()+ ",'" + c4name.getText().toString() + "'," + c4ticket.getText().toString()+ ",'" + lastdate.getText().toString() + "',"+d1y+","+d1m+","+d1d+",'" + nextdate.getText().toString() +"',"+d2y+","+d2m+","+d2d+")");
       	          	//db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1,rv2,rv3,rv4,rv5,rv6,rv7,rv8,rv9,rv10,rv11,rv12,rv13,rv14,rv15,rv16)Values('"+ firstname + "'," + leftticket.getText().toString() + ",'" + c1name.getText().toString()+ "'," + c1ticket.getText().toString() + ",'" + c2name.getText().toString() + "'," + c2ticket.getText().toString()+ ",'" + c3name.getText().toString() + "'," + c3ticket.getText().toString()+ ",'" + c4name.getText().toString() + "'," + c4ticket.getText().toString()+ ",'" + lastdate.getText().toString() + "',"+d1y+","+d1m+","+d1d+",'" + nextdate.getText().toString() +"',"+d2y+","+d2m+","+d2d+",'" + rv1.getText().toString() +"','" + rv2.getText().toString() +"','" + rv3.getText().toString() +"','" + rv4.getText().toString() +"','" + rv5.getText().toString() +"','" + rv6.getText().toString() +"','" + rv7.getText().toString() +"','" + rv8.getText().toString() +"','" + rv9.getText().toString() +"','" + rv10.getText().toString() +"','" + rv11.getText().toString() +"','" + rv12.getText().toString() +"','" + rv13.getText().toString() +"','" + rv14.getText().toString() +"','" + rv15.getText().toString() +"','" + rv16.getText().toString() +"')");
       	            db.execSQL("Insert Into " + DATABASE_TABLE + " (dbname,tvote,c1name,c1vote,c2name,c2vote,c3name,c3vote,c4name,c4vote,d1all,d1y,d1m,d1d,d2all,d2y,d2m,d2d,rv1)Values('"+ firstname + "'," + leftticket.getText().toString() + ",'" + c1name.getText().toString()+ "'," + c1ticket.getText().toString() + ",'" + c2name.getText().toString() + "'," + c2ticket.getText().toString()+ ",'" + c3name.getText().toString() + "'," + c3ticket.getText().toString()+ ",'" + c4name.getText().toString() + "'," + c4ticket.getText().toString()+ ",'" + lastdate.getText().toString() + "',"+d1y+","+d1m+","+d1d+",'" + nextdate.getText().toString() +"',"+d2y+","+d2m+","+d2d+",'" + rv1.getText().toString() +"')");

    	    	} else if(c.getCount()!=1){
    	    		output.setText("Debug: Wrong! only one row in dB is allowed");
    	    		return;
    	        }    			
    		     
    		    GetTicketCanVote();
    			 
    		     //Tom: Reset the dB as default value
     	       	//db.execSQL("Update " + DATABASE_TABLE + " set tvote=" + leftticket.getText().toString() + ", c1name = '" + c1name.getText().toString() + "', c1vote=" + c1ticket.getText().toString() + ", c2name ='" + c2name.getText().toString() + "', c2vote=" + c2ticket.getText().toString() + ", c3name ='" + c3name.getText().toString() + "', c3vote=" + c3ticket.getText().toString() + ", c4name ='" + c4name.getText().toString() + "', c4vote=" + c4ticket.getText().toString() + ", d1all='" + lastdate.getText().toString() + "', d1y=" + d1y + ", d1m=" + d1m + ", d1d=" + d1d + ", d2all='" + nextdate.getText().toString() + "', d2y=" + d2y + ", d2m=" + d2m + ", d2d=" + d2d + " where dbname='" + firstname + "'");
     	        db.execSQL("Update " + DATABASE_TABLE + " set tvote=" + leftticket.getText().toString() + ", c1name = '" + c1name.getText().toString() + "', c1vote=" + c1ticket.getText().toString() + ", c2name ='" + c2name.getText().toString() + "', c2vote=" + c2ticket.getText().toString() + ", c3name ='" + c3name.getText().toString() + "', c3vote=" + c3ticket.getText().toString() + ", c4name ='" + c4name.getText().toString() + "', c4vote=" + c4ticket.getText().toString() + ", d1all='" + lastdate.getText().toString() + "', d1y=" + d1y + ", d1m=" + d1m + ", d1d=" + d1d + ", d2all='" + nextdate.getText().toString() + "', d2y=" + d2y + ", d2m=" + d2m + ", d2d=" + d2d + ", rv1 = '" + rv1.getText().toString() + "'where dbname='" + firstname + "'");
   	     	
    	    }

 //========================================================================================================================
 // Some components
 //========================================================================================================================	
    	    
       	    public void InitGlobalVariabls() {
       	    	
       	     d1y=2016;
      		 d1m=11;
      		 d1d=8;
      		 d2y=2020;
      		 d2m=11;
      		 d2d=8;
     		 //int timediff=TimeDiff(d1y,d1m,d1d,d2y,d2m,d2d);
			 //totalticket_max = timediff/Vote_Interval;
			 
			 totalticket_max = TicketDiff(d1y,d1m,d1d,d2y,d2m,d2d);
			 
			 totalticket=totalticket_max;
			 
			 GetTicketCanVote();
			 
       	     leftticket.setText(Integer.toString(totalticket_max));
   			 //tocketcanvote.setText("2");
   			 c1name.setText("張三");
   			 c1ticket.setText("0");
   			 c2name.setText("李四");
   			 c2ticket.setText("0");
   			 c3name.setText("志明");
   			 c3ticket.setText("0");
   			 c4name.setText("春嬌");
   			 c4ticket.setText("0");
   			 //lastdate.setText("2013/5/1");
   			 //nextdate.setText("2016/1/14");
   			 rv1.setText("");
   			 /*
   			 rv2.setText("第2票");
   			 rv3.setText("第3票");
   			 rv4.setText("第4票");
   			 rv5.setText("第5票");
   			 rv6.setText("第6票");
   			 rv7.setText("第7票");
   			 rv8.setText("第8票");
   			 rv9.setText("第9票");
   			 rv10.setText("第10票");
   			 rv11.setText("第11票");
   			 rv12.setText("第12票");
   			 rv13.setText("第13票");
   			 rv14.setText("第14票");
   			 rv15.setText("第15票");
   			 rv16.setText("第16票");
   			 */
   			 vote1 = Integer.parseInt(c1ticket.getText().toString());
  		     vote2 = Integer.parseInt(c2ticket.getText().toString());
  		     vote3 = Integer.parseInt(c3ticket.getText().toString());
  		     vote4 = Integer.parseInt(c4ticket.getText().toString());
  		     totalticket = Integer.parseInt(leftticket.getText().toString());
		 	 lastdate.setText(Integer.toString(d1y)+"/"+Integer.toString(d1m)+"/" + Integer.toString(d1d));
		 	 nextdate.setText(Integer.toString(d2y)+"/"+Integer.toString(d2m)+"/" + Integer.toString(d2d));
			 	    			     
		     d1y_temp=d1y;
		     d1m_temp=d1m;
		     d1d_temp=d1d;
		     d2y_temp=d2y;
		     d2m_temp=d2m;
		     d2d_temp=d2d;
		     totalticket_max_temp=totalticket_max;
		     totalticket_temp=totalticket;
		    			    	
		     output.setTextColor(0xff0000ff);
		     output.setText("訊息："+todaymsg+"歡迎使用四分之一革命投票器！");
		     votereason.setTextColor(0xff0000ff);
		     votereason.setText("投票原因：");
       	    	
       	    }
       	    public void DateDiog1() {
       	    	
			 	if(totalticket_max_temp<=0)
			 	{
			 	 //output.setTextColor(0xffff00ff);
			 	 output.setText("訊息：錯誤，要更改的上次選舉日期"+Integer.toString(d1y_temp)+"/"+Integer.toString(d1m_temp)+"/" + Integer.toString(d1d_temp)+"需比下次選舉日期還早三個月以上。");
			 	 return;
			 	}		 
			    
			    d1y_now=dt.get(Calendar.YEAR);
				d1m_now=dt.get(Calendar.MONTH)+1;
				d1d_now=dt.get(Calendar.DAY_OF_MONTH);
				date_diff = ((d1y_now*12+d1m_now)-(d1y_temp*12+d1m_temp));
				
				if(date_diff<0)
			 	{
			 	 //output.setTextColor(0xffff00ff);
			 	 //output.setText("訊息：錯誤，要更改的上次選舉日期"+Integer.toString(d1y_temp)+"/"+Integer.toString(d1m_temp)+"/" + Integer.toString(d1d_temp)+"需在今天以前。");
			 	 outputmsgDia="訊息：錯誤，要更改的上次選舉日期"+Integer.toString(d1y_temp)+"/"+Integer.toString(d1m_temp)+"/" + Integer.toString(d1d_temp)+"需在今月以前。";
    	     	 AlertD();
			 	 return;
			 	}
				
				
			    //here, just a warning.
			    if(totalticket_max_temp<(vote1+vote2+vote3+vote4))
			    {
			    	//output.setTextColor(0xffff00ff);
			    	//output.setText("訊息：錯誤，要更改上次選舉日期"+Integer.toString(d1y_temp)+"/"+Integer.toString(d1m_temp)+"/" + Integer.toString(d1d_temp)+"，但是已使用的票多過可以用的票，請先更改下次選舉日期！");
			    	outputmsgDia="訊息：錯誤，要更改上次選舉日期"+Integer.toString(d1y_temp)+"/"+Integer.toString(d1m_temp)+"/" + Integer.toString(d1d_temp)+"，但是已使用的票多過可以用的票，請先更改下次選舉日期！";
	    	     	AlertD();
			     return;
			    }else{
			     
				 d1y=d1y_temp;
				 d1m=d1m_temp;
				 d1d=d1d_temp;
				 totalticket_max =totalticket_max_temp;
			 	 lastdate.setText(Integer.toString(d1y_temp)+"/"+Integer.toString(d1m_temp)+"/" + Integer.toString(d1d_temp));
				 //Tom: Set the total tickets
			 	 totalticket=totalticket_max-(vote1+vote2+vote3+vote4);
				 leftticket.setText(Integer.toString(totalticket));
				 
				 GetTicketCanVote();
				 
				 //output.setTextColor(0xff0000ff);
				 //output.setText("訊息：更改上次選舉日期成功，請存檔！");
				 outputmsg="更改上次選舉日期成功！";
				 SaveData();
		   	     outputmsgDia="訊息："+todaymsg+outputmsg;
		      	 AlertD();
			    }   

       	    	
       	    }
       	    
      	    public void DateDiog2() {
      	    	
			 	if(totalticket_max_temp<=0)
			 	{
			 	 //output.setTextColor(0xffff00ff);
			 	 //output.setText("訊息：錯誤，要更改的下次選舉日期"+Integer.toString(d2y_temp)+"/"+Integer.toString(d2m_temp)+"/" + Integer.toString(d2d_temp)+"需比上次選舉日期還晚三個月以上。");
			 	 outputmsgDia="訊息：錯誤，要更改的下次選舉日期"+Integer.toString(d2y_temp)+"/"+Integer.toString(d2m_temp)+"/" + Integer.toString(d2d_temp)+"需比上次選舉日期還晚三個月以上。";
    	     	 AlertD();
			 	 return;
			 	}
			 	
			 	d2y_now=dt.get(Calendar.YEAR);
				d2m_now=dt.get(Calendar.MONTH)+1;
				d2d_now=dt.get(Calendar.DAY_OF_MONTH);
				date_diff = ((d2y_temp*12+d2m_temp)-(d2y_now*12+d2m_now));
				
				if(date_diff<0)
			 	{
			 	 //output.setTextColor(0xffff00ff);
			 	 //output.setText("訊息：錯誤，要更改的下次選舉日期"+Integer.toString(d2y_temp)+"/"+Integer.toString(d2m_temp)+"/" + Integer.toString(d2d_temp)+"需在今天以後。");
			 	 outputmsgDia="訊息：錯誤，要更改的下次選舉日期"+Integer.toString(d2y_temp)+"/"+Integer.toString(d2m_temp)+"/" + Integer.toString(d2d_temp)+"需在今月以後。";
   	     	     AlertD();
			 	 return;
			 	}
			    
			    //here, just a warning.
			    if(totalticket_max_temp<(vote1+vote2+vote3+vote4))
			    {
			    	//output.setTextColor(0xffff00ff);
			    	//output.setText("訊息：錯誤，要更改下次選舉日期"+Integer.toString(d2y_temp)+"/"+Integer.toString(d2m_temp)+"/" + Integer.toString(d2d_temp)+"，但是已使用的票多過可以用的票，請先更改上次選舉日期！");
			    	outputmsgDia="訊息：錯誤，要更改下次選舉日期"+Integer.toString(d2y_temp)+"/"+Integer.toString(d2m_temp)+"/" + Integer.toString(d2d_temp)+"，但是已使用的票多過可以用的票，請先更改上次選舉日期！";
	   	     	    AlertD();
			    	return;
			    }else{
			     
				 d2y=d2y_temp;
				 d2m=d2m_temp;
				 d2d=d2d_temp;
				 totalticket_max =totalticket_max_temp;
			 	 nextdate.setText(Integer.toString(d2y_temp)+"/"+Integer.toString(d2m_temp)+"/" + Integer.toString(d2d_temp));
				 //Tom: Set the total tickets
			 	 totalticket=totalticket_max-(vote1+vote2+vote3+vote4);
				 leftticket.setText(Integer.toString(totalticket));
				 
				 GetTicketCanVote();
				 
				 //output.setTextColor(0xff0000ff);
				 //output.setText("訊息：更改下次選舉日期成功，請存檔！");
				 outputmsg="更改下次選舉日期成功！";
				 SaveData();
		   	     outputmsgDia="訊息："+todaymsg+outputmsg;
		      	 AlertD();
				 
			    }
       	    	
       	    }
      	    
      	  public void AlertD() {
      		  
              AlertDialog.Builder buildermsg = new AlertDialog.Builder(this); 
              buildermsg.setTitle("訊息！");
              buildermsg.setMessage(outputmsgDia);
              buildermsg.setPositiveButton("確定",
              		new DialogInterface.OnClickListener() { 
                      public void onClick(DialogInterface  
                                     dialoginterface, int i) { 
                      	// 結束
                      	//return;
                    	  
                        
                      } 
                });
              //buildermsg.setNegativeButton("取消", null); 
              buildermsg.show();    		  
      	  }
    	    	
  	    // 查詢所有記錄
  	    public void buttonshow_Click(View view) {  
  	    	//if(db==null)
  	    	//{
  	    	//because while monitor off, dB will be removed by OS.
  	    	db = dbHelper.getWritableDatabase(); // 開啟資料庫
  	    	//}
  	    	//db.execSQL("Select * from 1a where name = 'jack'");
  	    	Cursor c = db.rawQuery("Select * from " + DATABASE_TABLE , null);   	

  	    	// 顯示欄位名稱
  	    	String[] colNames=c.getColumnNames();    	
  	    	String str = "";    	
  	      	for (int i = 0; i < colNames.length; i++)
  	    		str += colNames[i] + "\t\t";
  	    	str += "\n";
  	    	c.moveToFirst();  // 第1筆   	
  	    	
  	    	// 顯示欄位值
  	    	for (int i = 0; i < c.getCount(); i++) {
  	    		str += c.getString(c.getColumnIndex(colNames[0])) + "\t\t";
  	    		str += c.getString(1) + "\t\t"; //第2欄位
  	    		str += c.getString(2) + "\t\t";//第3欄位   		
  	    		str += c.getString(3) + "\t\t"; //第4欄位
  	    		str += c.getString(4) + "\t\t"; //第5欄位
  	    		str += c.getString(5) + "\t\t"; //第6欄位
  	    		str += c.getString(6) + "\t\t";//第7欄位
  	    		str += c.getString(7) + "\t\t";//第8欄位
  	    		str += c.getString(8) + "\t\t"; //第9欄位
  	    		str += c.getString(9) + "\t\t"; //第10欄位
  	    		str += c.getString(10) + "\t\t"; //第11欄位
  	    		str += c.getString(11) + "\t\t";//第12欄位
  	    		str += c.getString(12) + "\t\t";//第13欄位
  	    		str += c.getString(13) + "\t\t";//第14欄位
  	    		str += c.getString(14) + "\t\t"; //第15欄位
  	    		str += c.getString(15) + "\t\t";//第16欄位
  	    		str += c.getString(16) + "\t\t";//第17欄位
  	    		str += c.getString(17) + "\t\t";//第18欄位
  	    		str += c.getString(18) + "\n";//第19欄位
  	    		c.moveToNext();  // 下一筆
  	    	}
  	    	//output.setTextColor(0xff0000ff);
  	    	output.setText(str.toString());    	
  	    }
      	  
}
