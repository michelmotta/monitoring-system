package com.monitoringsystem.notification;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Notification {
	
	public void sendNotification(String errorMessage) {
		
		try {
		   String jsonResponse;
		   
		   URL url = new URL("https://onesignal.com/api/v1/notifications");
		   HttpURLConnection con = (HttpURLConnection)url.openConnection();
		   con.setUseCaches(false);
		   con.setDoOutput(true);
		   con.setDoInput(true);

		   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		   con.setRequestProperty("Authorization", "Basic ZTJkZDUyNzgtNGEyOC00ZDY5LTg0NzMtZWI0M2I4ZGQ2MTdj");
		   con.setRequestMethod("POST");

		   String strJsonBody = "{"
		                      +   "\"app_id\": \"99675fcd-00bd-4ac7-9959-4c5f1dea5db2\","
		                      +   "\"included_segments\": [\"All\"],"
		                      +   "\"contents\": {\"en\": \"" + errorMessage + "\"}"
		                      + "}";
		         
		   
		   //System.out.println("strJsonBody:\n" + strJsonBody);

		   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
		   con.setFixedLengthStreamingMode(sendBytes.length);

		   OutputStream outputStream = con.getOutputStream();
		   outputStream.write(sendBytes);

		   int httpResponse = con.getResponseCode();
		   System.out.println("httpResponse: " + httpResponse);

		   if (  httpResponse >= HttpURLConnection.HTTP_OK
		      && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
		      Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
		      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
		      scanner.close();
		   }
		   else {
		      Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
		      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
		      scanner.close();
		   }
		   System.out.println("OneSignal Response:\n" + jsonResponse);
		   
		} catch(Throwable t) {
		   t.printStackTrace();
		}
		
	}
	
}
