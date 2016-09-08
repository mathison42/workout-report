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

public class SpreadsheetInstance {

  private Sheets service;
  private Spreadsheet spreadsheet;
  private List<SheetInstance> sheetInstanceList = new ArrayList<SheetInstance>();

  public SpreadsheetInstance(Sheets service) {
    this.service = service;
    this.spreadsheet = createSpreadsheet();
  }

  public SpreadsheetInstance(Sheets service, Spreadsheet spreadsheet) {
    this.service = service;
    this.spreadsheet = spreadsheet;
  }

  private Spreadsheet createSpreadsheet() {
    Spreadsheet result = new Spreadsheet();
    Spreadsheet genericTemplate = new Spreadsheet();
    genericTemplate.setProperties(new SpreadsheetProperties()
      .setTitle("My Workout Spreadsheet")
      .setLocale("en_US")
    );

    try {
      result = service.spreadsheets().create(genericTemplate).execute();
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
    return result;
  }

  public List<SheetInstance> getSheetInstances() {
   return sheetInstanceList;
  }

  public SheetInstance getSheetInstance(int sheetId) {
    for (SheetInstance sheetInstance : getSheetInstances()) {
      if(sheetInstance.getSheetId() == sheetId) {
        return sheetInstance;
      }
    }
    return null;
  }

  public void addSheetInstance(SheetInstance sheetInstance) {
    if (!sheetInstanceList.contains(sheetInstance)) {
      this.sheetInstanceList.add(sheetInstance);
    }
  }

  public void removeSheetInstance(SheetInstance sheetInstance) {
    this.sheetInstanceList.remove(sheetInstance);
  }

  public void setService(Sheets service) {
    this.service = service;
  }

  public Sheets getService() {
   return service;
  }

  public void setSheets(List<Sheet> paramList) {
    spreadsheet.setSheets(paramList);
  }

  public List<Sheet> getSheets() {
    return spreadsheet.getSheets();
  }

  public void updateSheets() {
    List<Sheet> sheetList = new ArrayList<Sheet>();
    try {
        Spreadsheet s = getService().spreadsheets()
          .get(getSpreadsheet().getSpreadsheetId())
          .setFields("sheets")
          .execute();
        sheetList = s.getSheets();
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
    setSheets(sheetList);
  }

  public void addSheet(Sheet sheet) {
    int sheetId = sheet.getProperties().getSheetId();
    int i = 0;
    boolean found = false;
    List<Sheet> sheets = getSheets();
    // Replace if it exists in our local list
    for (Sheet temp : sheets) {
      if (temp.getProperties().getSheetId() == sheetId) {
        sheets.set(i,sheet);
        found = true;
        break;
      }
      i++;
    }

    // If it doesn't exist in our local list, add it.
    if (!found) {
      sheets.add(sheet);
    }
    setSheets(sheets);
  }

  public void removeSheet(Sheet sheet) {
    List<Sheet> sheets = getSheets();
    sheets.remove(sheet);
    setSheets(sheets);
  }

  public void setSpreadsheet(Spreadsheet spreadsheet) {
    this.spreadsheet = spreadsheet;
  }

  public Spreadsheet getSpreadsheet() {
   return spreadsheet;
  }


  public ValueRange getValues(String range) {
    ValueRange result = new ValueRange();
    try {
      result = service.spreadsheets().values()
            .get(getSpreadsheet().getSpreadsheetId(), range)
            .execute();
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
    return result;
  }

  // Get Values within a Spreadsheet
  public BatchGetValuesResponse batchGetValues(List<String> ranges) {
    BatchGetValuesResponse result = new BatchGetValuesResponse();
    try {
      result = service.spreadsheets().values()
            .batchGet(getSpreadsheet().getSpreadsheetId())
            .setRanges(ranges)
            .execute();
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
    return result;
  }

  public UpdateValuesResponse updateValues(String range, ValueRange valueRange) {
    UpdateValuesResponse result = new UpdateValuesResponse();
    try {
      result = service.spreadsheets().values()
            .update(getSpreadsheet().getSpreadsheetId(), range, valueRange)
            .setValueInputOption("RAW")
            .execute();
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
    return result;
  }

  // Update Values within a Spreadsheet
  public BatchUpdateValuesResponse batchUpdateValues(BatchUpdateValuesRequest update) {
    BatchUpdateValuesResponse result = new BatchUpdateValuesResponse();
    try {
      result = service.spreadsheets().values()
          .batchUpdate(getSpreadsheet().getSpreadsheetId(), update)
          .execute();
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
    return result;
  }
}
