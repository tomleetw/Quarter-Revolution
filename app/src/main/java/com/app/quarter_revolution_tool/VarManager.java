package com.app.quarter_revolution_tool;

public class VarManager {
	public static boolean isfirstbutton=false;
	public static boolean isEnableEdit=false;
	public static boolean getfirstbuttonstatus()
	{
		if(isfirstbutton)
		{
			isfirstbutton=false;
			return true;
		}
	    return false;
	}
	
	public static boolean isNeedResetDB=false;
}
