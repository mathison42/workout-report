import com.google.api.services.sheets.v4.model.ValueRange
import com.google.api.services.sheets.v4.Sheets

import com.seven.discs.client.RunnerHelper

public class Runner {

  //////////////////////////////////////////
  // Intialize REST SheetsService and Variables.
  // Feel free to edit these values
  //////////////////////////////////////////
  final static String Application_Name = "Wrapper Test"  // Name of your Test Application
  final static String Data_Store_Dir = ".credentials/sheets.googleapis.com-groovy-wrapper-test.json" //Location to save cookie credentials
  final static String spreadsheetId = "1hiWxPsbGu3svXpu0hj3bvwhil69-1BP19st-veN-6cU" // Google Sheets Unique ID
  final static def data = [["Falcore", "21", "23", "41"]] // Values to send to Sheet
  final static String postRange = "Sheet1!A23" // Post values to here
  final static String getRange  = "Sheet1!A23:D23"   // Pull value range from here
  // Note: It's assumed that a client_secret.json file exists in src/main/resources.
  // Look for a README.md at that location for directions


  public static void main(String[] args) {
    println ""
    println "Starting..."
    println ""

    //////////////////////////////////////////
    // Intialize REST SheetsService
    //////////////////////////////////////////
    // Build a new authorized API client service.
    RunnerHelper rh = new RunnerHelper(Application_Name, Data_Store_Dir)
    final Sheets service = rh.getSheetsService()   // Create the SheetsService for Auth

    //////////////////////////////////////////
    // Create Payload (ValueRange)
    //////////////////////////////////////////
    ValueRange payload = new ValueRange()
    payload.setMajorDimension("ROWS")
    payload.setRange(postRange)
    payload.setValues(data)

    //////////////////////////////////////////
    // Run Post REST API call
    //////////////////////////////////////////
    println ""
    println "[Start] Post Call..."
    println "Sending data... ${data}"
    ValueRange postRes = service.spreadsheets().values()
          .update(spreadsheetId, postRange, payload)
          .setValueInputOption("RAW")
          .execute()

    //////////////////////////////////////////
    // Print to screen
    //////////////////////////////////////////
    if (postRes == null || postRes.size() == 0) {
      println "No data found."
    } else {
      println "----------------------"
      println "Key             Value"
      println "---             -----"
      println "spreadsheetId : " + postRes.spreadsheetId
      println "updatedRange  : " + postRes.updatedRange
      println "updatedRows   : " + postRes.updatedRows
      println "updatedColumns: " + postRes.updatedColumns
      println "updatedCells  : " + postRes.updatedCells
      println "----------------------"
    }
    println "[End] Post Call"

    //////////////////////////////////////////
    // Intialize Get Call
    //////////////////////////////////////////

    // RunnerHelper and Sheets are reused from above

    //////////////////////////////////////////
    // Run Get REST API call
    //////////////////////////////////////////
    println ""
    println "[Start] Get Call"
    ValueRange getRes = service.spreadsheets().values()
          .get(spreadsheetId, getRange)
          .execute()

    //////////////////////////////////////////
    // Print to screen
    //////////////////////////////////////////
    List<List<Object>> getVal = getRes.getValues()
    if (getVal == null || getVal.size() == 0) {
      System.out.println("No data found.")
    } else {
      println "----------------------"
      println "Cell            Value"
      println "---             -----"
      for (List row : getVal) {
        for (int i; i < row.size(); i++) {
          // Print columns A and E, which correspond to indices 0 and 4.
          println "Cell#${i}\t\t${row.get(i)}"
        }
      println "----------------------"
      }
    }
    println "[End] Get Call"
    println ""
    println "...Ending"
  }

}
