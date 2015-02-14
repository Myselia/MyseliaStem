package cms.monitoring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cms.display.communication.LogDisplay;

public class LogSystem {
	
	public static void log(boolean display, boolean export, String str){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy@HH:mm:ss.SSS");
		Date today = Calendar.getInstance().getTime(); 
		String reportDate = df.format(today);
		String make = reportDate+ " :: " +  str;
		
		if(display){
			LogDisplay.display(make);
		}
		
		if(export){
			LogFileWriter.write(make);
		} 

	}
	
}
