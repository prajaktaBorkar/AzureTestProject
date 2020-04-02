package com.perficient.util;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * This is utility to read the JSON object of Webservices.
 * @author pooja.manna
 *
 */

public class ReadJsonObject{

	/**
	 * This method is used to read the Json Object response
	 * @param apiLink
	 */
	public void apiTesting(String apiLink ){
		try {
		URL url = new URL(apiLink);
		HttpURLConnection conn =   (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException(" HTTP error code : "
			+ conn.getResponseCode());
			}

			Scanner scan = new Scanner(url.openStream());
			String entireResponse = new String();
			
			while (scan.hasNext())
		 	entireResponse =entireResponse+ scan.nextLine();

			System.out.println("Response : "+entireResponse);

			scan.close();

		    JSONObject obj =new JSONObject(entireResponse);
			
			
			String responseCode = obj.getString("status");
			System.out.println("status : " + responseCode);
			
			org.json.JSONArray arr = obj.getJSONArray("results");
			for (int i = 0; i < arr.length(); i++) {
			String placeid = arr.getJSONObject(i).getString("place_id");
			System.out.println("Place id : " + placeid);
			String formatAddress = arr.getJSONObject(i).getString(
			"formatted_address");
			System.out.println("Address : " + formatAddress);

			//validating Address as per the requirement
			if(formatAddress.equalsIgnoreCase("Chicago, IL, USA"))
			{
			System.out.println("Address is as Expected");
			}
			else
			{
			System.out.println("Address is not as Expected");
			}
			}

			conn.disconnect();
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	} 




		

