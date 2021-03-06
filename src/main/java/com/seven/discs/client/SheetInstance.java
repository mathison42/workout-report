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

import com.seven.discs.helper.A1Conversion;

public class SheetInstance{

  private SpreadsheetInstance spreadsheetInstance;
  private Sheet sheet;
  private int sheetId = -1;
  private List<Object> xAxis = new ArrayList<Object>();
  private List<Object> yAxis = new ArrayList<Object>();

  public SheetInstance(SpreadsheetInstance spreadsheetInstance) {
    this.spreadsheetInstance = spreadsheetInstance;
    this.spreadsheetInstance.addSheetInstance(this);
  }

  public SheetInstance(SpreadsheetInstance spreadsheetInstance, int sheetId) {
    this.spreadsheetInstance = spreadsheetInstance;
    this.sheetId = sheetId;
    this.spreadsheetInstance.addSheetInstance(this);
  }

  public Sheet getSheet() {
    return sheet;
  }

  public void setSheet(Sheet sheet) {
    this.sheet = sheet;
  }

  public void updateSheet() {
    Sheet result = new Sheet();
    for (Sheet sheet : spreadsheetInstance.getSpreadsheet().getSheets()) {
      SheetProperties props = sheet.getProperties();
      if (props.getSheetId() == sheetId) {
        result = sheet;
        break;
      }
    }
    setSheet(result);
    setAxes();
  }

  public SpreadsheetInstance getSpreadsheetInstance() {
    return spreadsheetInstance;
  }

  public Spreadsheet getSpreadsheet() {
    return spreadsheetInstance.getSpreadsheet();
  }

  public Sheets getService() {
    return spreadsheetInstance.getService();
  }

  // Pull from sheet object
  public int getSheetId() {
    return sheetId;
  }

  // Eventually remove for setSheet(Sheet sheet)
  public void setSheetId(int sheetId) {
    this.sheetId = sheetId;
  }

  public String getTitle() {
    return getSheet().getProperties().getTitle();
  }

  public List<Object> getXAxis() {
    return this.xAxis;
  }

  public List<Object> getYAxis() {
    return this.yAxis;
  }

  /**
   * return Generates generic Sheet/tab
   */
  public void createSheet() {
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
    createSheet(tempReq);
  }

  /**
   * param request Request containing the setAddSheet property set
   * return Generates generic Spreadsheet Sheet/tab
   */
  public void createSheet(Request request) {
    Sheet result = new Sheet();
    List<Request> requests = new ArrayList<Request>();
    requests.add(request);
    BatchUpdateSpreadsheetRequest update =
            new BatchUpdateSpreadsheetRequest().setRequests(requests);
    try {
        BatchUpdateSpreadsheetResponse response =
         getService().spreadsheets()
         .batchUpdate(getSpreadsheet().getSpreadsheetId(), update)
         .execute();
         List<Response> replies = response.getReplies();
         AddSheetResponse sheetResponse = replies.get(0).getAddSheet();
         SheetProperties props = sheetResponse.getProperties();
         result.setProperties(props);
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }

    setSheetId(result.getProperties().getSheetId());
    //spreadsheetInstance.addSheet(result);
    System.out.println ("Output: " + result);
    setSheet(result);
    spreadsheetInstance.addSheetInstance(this);
  }

  // Update Sheet Data is not given to List<Sheet> of Spreadsheet
  // Should confirm that "Sheet" is !null
  public BatchUpdateSpreadsheetResponse updateSheetProps(Request request) {
    BatchUpdateSpreadsheetResponse result = new BatchUpdateSpreadsheetResponse();
    List<Request> requests = new ArrayList<Request>();
    requests.add(request);
    BatchUpdateSpreadsheetRequest update =
            new BatchUpdateSpreadsheetRequest().setRequests(requests);
    try {
        result = getService().spreadsheets()
          .batchUpdate(getSpreadsheet().getSpreadsheetId(), update)
          .execute();
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
    return result;
  }

  /**
   * return Retrieves the Axes from Sheet, and places them into their associated instance variables
   * Reset to empty if no values are found.
   */
  public void setAxes() {
    BatchGetRanges payload = new BatchGetRanges();
    payload.getXAxisHeader(getTitle());
    payload.getYAxisHeader(getTitle());
    BatchGetValuesResponse response = getSpreadsheetInstance()
          .batchGetValues(payload.getRanges());

    // Get X Axis
    List<List<Object>> tempX = response.getValueRanges().get(0).getValues();
    List<Object> xResult = new ArrayList<Object>();
    if(tempX != null && tempX.size() > 0) {
      xResult = tempX.get(0);
    }

    // Get Y Axis
    List<List<Object>> tempY = response.getValueRanges().get(1).getValues();
    List<Object> yResult = new ArrayList<Object>();
    if(tempY != null && tempY.size() > 0) {
      Object header;
      for (List<Object> temp : tempY) {
        header = "";
        if(temp.size() > 0 ) {
          header = temp.get(0);
        }
        yResult.add(header);
      }
    }

    // Set Axes
    this.xAxis = xResult;
    this.yAxis = yResult;
  }

  /**
   * return Retrieves the last X Index in A1 Notation
   */
  public String getLastXIndex() {
    int xSize = getXAxis().size();
    if (xSize == 0) return "";
    return A1Conversion.getA1XConversion(xSize-1);
  }

  /**
   * return Retrieves the last Y Index in A1 Notation
   */
  public int getLastYIndex() {
    return getYAxis().size();
  }
}
