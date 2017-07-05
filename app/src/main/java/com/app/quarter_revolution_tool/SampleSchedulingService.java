package com.app.quarter_revolution_tool;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;


public class SampleSchedulingService extends IntentService {
	public SampleSchedulingService() {
		super("SchedulingService");
	}

	//For dB
	private static String DATABASE_TABLE = "titles3";
	MyDBHelper dbHelper;// = MyDBHelperFactory.GetDBHelper(this);//Can not do initial here, it will cause crash.
	SQLiteDatabase db;// = dbHelper.getReadableDatabase(); // 開啟資料庫
	Cursor c_dB;

	private int vote1=0 ;
	private int vote2=0;
	private int vote3=0;
	private int vote4=0;
	private int d1y=2014; //!!! 2013/5/1, The date must be same as MainActivity.
	private int d1m=5;
	private int d1d=1;
	private int d2y=2016; //!!! 2016/1/14, The date must be same as MainActivity.
	private int d2m=1;
	private int d2d=14;
	private int d1y_now=0;
	private int d1m_now=0;
	private int d1d_now=0;
	private int totalticketcanvote_now=0;
	final int Vote_Interval = 90;//3 months = 90 days

	public static final String TAG = "Scheduling Demo";
	// An ID used to post the notification.
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	private NotificationManager mManager;

	String alarmmsg = "";

	//2,4 立春農民節,also vote for environment.
	//5,1  國際勞工節, also for Monther's day.
	//8,12 國際青年節.
	//10,31 萬聖夜.Vote 幫小孩子趕鬼.
	private int Cal_Event_id;//0,1,2,3
	private int[] VoteM={2,5,8,10};
	private int[] VoteD={4,1,12,31};
	String[] AlarmMessage={"2月4日春天農民節到了，農民辛苦了。",
			"5月1日 國際勞動節到了，勞工辛苦了。",
			"8月12日國際青年節到了，想想未來。",
			"10月31日萬聖夜到了，一起幫小孩子趕走壞蛋。"};

	private int TicketcanVote;
	private int Test_D;

	Calendar dt = Calendar.getInstance();

	//TicketcanVote = MainClass.GetTicketCanVote(this);

	@Override
	protected void onHandleIntent(Intent intent) {
		// BEGIN_INCLUDE(service_onhandle)

		String result ="";

		d1y_now=dt.get(Calendar.YEAR);
		d1m_now=dt.get(Calendar.MONTH)+1;
		d1d_now=dt.get(Calendar.DAY_OF_MONTH);

		//Decide today is in which period?
		DatetoCalender();
		//To protect the array alarmmsg=AlarmMessage[Cal_Event_id];
		if((Cal_Event_id>3)||(Cal_Event_id<0))
		{
			alarmmsg="Error:Cal_Event_id is wrong。";
		}

		//Tom: Just for test
		//Test_D=d1d_now/4;
		//Cal_Event_id=d1d_now-Test_D*4;//0~3
		//End of test

		//Tom: Notification message.
		alarmmsg=AlarmMessage[Cal_Event_id];
		//alarmmsg=AlarmMessage[3];

		//Calculate how many tickets can be voted.
		TickeCanVoteDateFromdB();
		//Only send notification in case user still has tickets can be voted.
		if(totalticketcanvote_now>0)
		{
			alarmmsg="可投"+Integer.toString(totalticketcanvote_now)+"票。"+alarmmsg;
			sendNotification(alarmmsg);
		}
		SampleAlarmReceiver.completeWakefulIntent(intent);
		// END_INCLUDE(service_onhandle)
	}

	// Post a notification indicating whether a doodle was found.
	private void sendNotification(String msg) {
		mNotificationManager = (NotificationManager)
				this.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class), 0);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.drawable.ic_launcher)
						.setContentTitle(getString(R.string.QR_alert))
						.setStyle(new NotificationCompat.BigTextStyle()
								.bigText(msg))
						.setContentText(msg);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

	public void DatetoCalender() {


		//1/1~2/3 ---> Save in 10/31 in last year
		{//Default
			alarmmsg="10月31日萬聖夜，一起幫小孩子趕走壞蛋。";
			Cal_Event_id=3;
		}

		if((d1m_now>VoteM[0])||((d1m_now==VoteM[0])&&(d1d_now>=VoteD[0])))//2/4~12/31 ---> Save in 2/4 first
		{
			//alarmmsg="2月4日春天農民節到了，您辛苦了。";
			Cal_Event_id=0;
		}
		if ((d1m_now>VoteM[1])||((d1m_now==VoteM[1])&&(d1d_now>=VoteD[1])))//5/1~12/31 ---> Save in 5/1
		{
			//alarmmsg="5月1日 國際勞動節到了，您辛苦了。";
			Cal_Event_id=1;
		}
		if ((d1m_now>VoteM[2])||((d1m_now==VoteM[2])&&(d1d_now>=VoteD[2])))//8/12~12/31 ---> Save in 8/12
		{
			//alarmmsg="8月12日國際青年節到了，想想未來。";
			Cal_Event_id=2;
		}
		if ((d1m_now>VoteM[3])||((d1m_now==VoteM[3])&&(d1d_now>=VoteD[3])))//10/31~12/31
		{
			//alarmmsg="10月31日萬聖夜到了，一起幫小孩子趕走壞蛋。";
			Cal_Event_id=3;
		}

		//CalenderSaveDate=Integer.toString(Cal_Event_year)+"/"+Integer.toString(Cal_Event_mon)+"/"+Integer.toString(Cal_Event_day);

	}

	public void TickeCanVoteDateFromdB() {

		LoadFromDB();
		GetTicketCanVote();

	}

	private void LoadFromDB() {

		dbHelper = MyDBHelperFactory.GetDBHelper(this);
		db = dbHelper.getReadableDatabase(); // 開啟資料庫
		//Read again here, since dB content may be changed.
		c_dB = db.rawQuery("Select * from " + DATABASE_TABLE , null);//cursor of dB

		if(c_dB.getCount()==0)
		{
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

		//totalticket = Integer.parseInt(c_dB.getString(2));
		vote1 = Integer.parseInt(c_dB.getString(4));
		vote2 = Integer.parseInt(c_dB.getString(6));
		vote3 = Integer.parseInt(c_dB.getString(8));
		vote4 = Integer.parseInt(c_dB.getString(10));

		d1y=Integer.parseInt(c_dB.getString(12));
		d1m=Integer.parseInt(c_dB.getString(13));
		d1d=Integer.parseInt(c_dB.getString(14));

		d2y=Integer.parseInt(c_dB.getString(16));
		d2m=Integer.parseInt(c_dB.getString(17));
		d2d=Integer.parseInt(c_dB.getString(18));

		//d2y=Integer.parseInt(c_dB.getString(16));
		//d2m=Integer.parseInt(c_dB.getString(17));
		//d2d=Integer.parseInt(c_dB.getString(18));

		//int timediff=TimeDiff(d1y,d1m,d1d,d2y,d2m,d2d);
		//totalticket_max = timediff/Vote_Interval;
		//totalticket_max = ((d2y*12+d2m)-(d1y*12+d1m))/Vote_Interval;

	}

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

	public void DatetoCalender_test() {


		//1/1~2/3 ---> Save in 10/31 in last year
		{//Default
			alarmmsg="10月31日萬聖夜，一起幫小孩子趕走壞蛋。";
			Cal_Event_id=3;
		}

		if((d1m_now>VoteM[0])||((d1m_now==VoteM[0])&&(d1d_now>VoteD[0])))//2/5~12/31 ---> Save in 2/4 first
		{
			alarmmsg="2月4日春天農民節到了，您辛苦了。";
			Cal_Event_id=0;
		}
		if ((d1m_now>VoteM[1])||((d1m_now==VoteM[1])&&(d1d_now>VoteD[1])))//5/2~12/31 ---> Save in 5/1
		{
			alarmmsg="5月1日 國際勞動節到了，您辛苦了。";
			Cal_Event_id=1;
		}
		if ((d1m_now>VoteM[2])||((d1m_now==VoteM[2])&&(d1d_now>VoteD[2])))//8/13~12/31 ---> Save in 8/12
		{
			alarmmsg="8月12日國際青年節到了，想想未來。";
			Cal_Event_id=2;
		}
		if ((d1m_now>VoteM[3])||((d1m_now==VoteM[3])&&(d1d_now>VoteD[3])))//11/1~12/31
		{
			alarmmsg="10月31日萬聖夜到了，一起幫小孩子趕走壞蛋。";
			Cal_Event_id=3;
		}

		//CalenderSaveDate=Integer.toString(Cal_Event_year)+"/"+Integer.toString(Cal_Event_mon)+"/"+Integer.toString(Cal_Event_day);

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

		//if((TickDiff)<0)
		//	{
		//		outputmsgDia="Debug:(TickDiff)<0!";
		//	AlertD();
		//	}

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

}