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

  /**
   * param service Google Sheets containing connection and credential information
   */
  public SpreadsheetInstance(Sheets service) {
    this.service = service;
    this.spreadsheet = createSpreadsheet();
  }

  /**
   * param service Google Sheets containing connection and credential information
   * param spreadsheet Connect this SpreadsheetInstance to a Google Spreadsheet
   */
  public SpreadsheetInstance(Sheets service, Spreadsheet spreadsheet) {
    this.service = service;
    this.spreadsheet = spreadsheet;
  }

 /**
  * return Generates a Google Spreadsheet. Only called in SpreadsheetInstance(Sheets)
  */
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

  /**
   * return SheetIntance list of all objects monitored by this spreadsheetInstance
   */
  public List<SheetInstance> getSheetInstances() {
   return sheetInstanceList;
  }

  /**
   * param sheetId The SheetInstance id used to identify the Spreadsheet's tabs
   * return The identified SheetInstance, else null if not found
   */
  public SheetInstance getSheetInstance(int sheetId) {
    for (SheetInstance sheetInstance : getSheetInstances()) {
      if(sheetInstance.getSheetId() == sheetId) {
        return sheetInstance;
      }
    }
    return null;
  }

  /**
   * param sheetInstance SheetInstance object to add to the SpreadsheetInstance's List
   * return List<SpreadsheetInstance> contains the new SheetInstance, unless already found
   */
  public void addSheetInstance(SheetInstance sheetInstance) {
    if (!sheetInstanceList.contains(sheetInstance)) {
      this.sheetInstanceList.add(sheetInstance);
    }
  }

  /**
   * return Remove a SheetInstance from the List<SpradsheetInstance>
   */
  public void removeSheetInstance(SheetInstance sheetInstance) {
    this.sheetInstanceList.remove(sheetInstance);
  }

  /**
   * param service The Google Sheets service for connection and credentials
   * return Sets the instances's service parameter
   */
  public void setService(Sheets service) {
    this.service = service;
  }

 /**
  * return Retreives the instance's Google Sheets service
  */
  public Sheets getService() {
   return service;
  }

  /**
   * param spreadsheet New instance's spreadsheet
   * return Setter for Spreadsheet variable
   */
  public void setSpreadsheet(Spreadsheet spreadsheet) {
    this.spreadsheet = spreadsheet;
  }

  /**
   * return Retreive instance's Spreadsheet Object
   */
  public Spreadsheet getSpreadsheet() {
   return spreadsheet;
  }

  /**
   * return Update Spreadsheet with current data
   */
  public void updateSpreadsheet() {
    try {
      Spreadsheet result = getService().spreadsheets()
      .get(getSpreadsheet().getSpreadsheetId())
      .execute();
      setSpreadsheet(result);
    }
    catch (IOException io) {
      io.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * return Update all Sheets with current data
   */
  public void updateSheets() {
    for (SheetInstance si : getSheetInstances()) {
      si.updateSheet();
    }
  }

  /**
   * param range Single A1 Notation range
   * return Retreives ValueRange which contain the values from the given range
   */
  // Move into different class
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

  /**
   * param range List of A1 Notation ranges
   * return Retreives List of ValueRanges which contain the values from the given ranges
   */
  // Move into different class
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

  /**
   * param range Single A1 Notation range
   * param valueRange Single ValueRange containing new values
   * return Updates sheet with new ValueRange. Retreives response containing update status
   */
  // Move into different class
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

  /**
   * param range List of A1 Notation ranges
   * param valueRange List of ValueRanges containing new values
   * return Updates sheets with new ValueRanges. Retreives response containing update statuses
   */
  // Move into different class
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
