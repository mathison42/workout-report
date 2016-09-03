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

  public void addSheet(Sheet sheet) {
   List<Sheet> sheets = getSheets();
   sheets.add(sheet);
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
}
