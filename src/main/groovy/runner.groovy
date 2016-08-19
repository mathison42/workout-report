import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.seven.discs.client.RunnerHelper

class Runner {

	public static void main(String[] args) {
		println ""
		println "Starting..."
		println ""
		//////////////////////////////////////////
		// Intialize REST Calls
		//////////////////////////////////////////
		
		// Build a new authorized API client service.
		RunnerHelper rh = new RunnerHelper()
		Sheets service = rh.getSheetsService();
		String spreadsheetId = "1hiWxPsbGu3svXpu0hj3bvwhil69-1BP19st-veN-6cU"
		String range = "Sheet1!A23"
		
		//////////////////////////////////////////
		// Create Payload (ValueRange)
		//////////////////////////////////////////
		def data = [["Falcore", "21", "23", "41"]]
		ValueRange payload = new ValueRange()
		payload.setMajorDimension("ROWS")
		payload.setRange("Sheet1!A23")
		payload.setValues(data)
		
		//////////////////////////////////////////
		// Run Post REST API call
		//////////////////////////////////////////
		println ""
		println "[Start] Post Call..."
		ValueRange postRes = service.spreadsheets().values()
					.update(spreadsheetId, range, payload)
					.setValueInputOption("RAW")
					.execute();
		//////////////////////////////////////////
		// Print to screen
		//////////////////////////////////////////
		if (postRes == null || postRes.size() == 0) {
			println "No data found."
		} else {
			println "spreadsheetId, updatedRange, updatedRows, updatedColumns, updatedCells"
			printf ("%s, %s,  %s,  %s,  %s, \n", postRes.spreadsheetId, postRes.updatedRange, postRes.updatedRows, postRes.updatedColumns, postRes.updatedCells)
		}
		println "[End] Post Call\n"
		//////////////////////////////////////////
		// Intialize Get Call
		//////////////////////////////////////////
		// Sheets service = getSheetsService(); //Already Retrieved Above
		//String spreadsheetId = "1hiWxPsbGu3svXpu0hj3bvwhil69-1BP19st-veN-6cU"
		range = "Sheet1!A23:D23"
		//////////////////////////////////////////
		// Run Get REST API call
		//////////////////////////////////////////
		println "[Start] Get Call"
		ValueRange getRes = service.spreadsheets().values()
					.get(spreadsheetId, range)
					.execute();
		//////////////////////////////////////////
		// Print to screen
		//////////////////////////////////////////
		List<List<Object>> getVal = getRes.getValues();
		if (getVal == null || getVal.size() == 0) {
			System.out.println("No data found.");
		} else {
			println "Name, Number#1, Number#2, Number#3"
			for (List row : getVal) {
				// Print columns A and E, which correspond to indices 0 and 4.
				println row
				//System.out.printf("%s, %s, %s, %s\n", row.get(0), row.get(1), row.get(2), row.get(3));
			}
		}
		println "[End] Get Call"
	}
}
