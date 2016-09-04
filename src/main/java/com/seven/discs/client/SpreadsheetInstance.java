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
        Spreadsheet s = getService().spreadsheets().get(getSpreadsheet().getSpreadsheetId()).setFields("sheets").execute();
        sheetList = s.getSheets();
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
    for (Sheet i : sheetList) {
      System.out.println("Full: " + i.getProperties());
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

  // Update Values within a Spreadsheet
  public void updateValues(BatchUpdateValuesRequest update) {
    try {
      BatchUpdateValuesResponse response = getService().spreadsheets().values()
          .batchUpdate(getSpreadsheet().getSpreadsheetId(), update)
          .execute();
      for (UpdateValuesResponse temp : response.getResponses()) {
        System.out.println ("Response: " + temp);
      }
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
  }

}
