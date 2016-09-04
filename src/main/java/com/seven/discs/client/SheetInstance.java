package com.seven.discs.client;

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
import java.util.ArrayList;
import java.util.List;

public class SheetInstance extends SpreadsheetInstance {

  private int sheetId;

  public SheetInstance(Sheets service) {
    super(service);
  }

  public SheetInstance(Sheets service, Spreadsheet spreadsheet){
    super(service, spreadsheet);
  }

  public SheetInstance(Sheets service, Spreadsheet spreadsheet, int sheetId) {
    super(service, spreadsheet);
    this.sheetId = sheetId;
  }

  public Sheet getSheet() {
    Sheet result = new Sheet();
    for (Sheet sheet : getSheets()) {
      SheetProperties props = sheet.getProperties();
      if (props.getSheetId() == sheetId) {
        result = sheet;
        break;
      }
    }
    return result;
  }

  public int getSheetId() {
    return sheetId;
  }

  public void setSheetId(int sheetId) {
    this.sheetId = sheetId;
  }

  public String getTitle() {
    return getSheet().getProperties().getTitle();
  }

  public Sheet createSheet() {
    Request tempReq = new Request()
      .setAddSheet(new AddSheetRequest()
        .setProperties(new SheetProperties()
          .setTitle("Blank Tab")
          .setGridProperties(new GridProperties()
            .setRowCount(20)
            .setColumnCount(12)
          )
          .setTabColor(new Color()
            .setRed((float)1.0)
            .setGreen((float)0.3)
            .setBlue((float)0.4)
          )
        )
      );
    return createSheet(tempReq);
  }

  public Sheet createSheet(Request request) {
    Sheet result = new Sheet();
    List<Request> requests = new ArrayList<>();
    requests.add(request);
    BatchUpdateSpreadsheetRequest update =
            new BatchUpdateSpreadsheetRequest().setRequests(requests);
    try {
        BatchUpdateSpreadsheetResponse response =
         getService().spreadsheets().batchUpdate(getSpreadsheet().getSpreadsheetId(), update).execute();
         List<Response> replies = response.getReplies();
         AddSheetResponse sheetResponse = replies.get(0).getAddSheet();
         SheetProperties props = sheetResponse.getProperties();
         result.setProperties(props);
         System.out.println("Result: " + result);
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }

    setSheetId(result.getProperties().getSheetId());
    addSheet(result);
    return result;
  }

  // Update Sheet Data is not given to List<Sheet> of Spreadsheet
  // Should confirm that "Sheet" is !null
  public void updateSheet(Request request) {
    List<Request> requests = new ArrayList<>();
    requests.add(request);
    BatchUpdateSpreadsheetRequest update =
            new BatchUpdateSpreadsheetRequest().setRequests(requests);
    try {
        getService().spreadsheets().batchUpdate(getSpreadsheet().getSpreadsheetId(), update).execute();
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
  }
}
