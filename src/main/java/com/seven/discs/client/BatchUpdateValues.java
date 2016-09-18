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

public class BatchUpdateValues {

  private BatchUpdateValuesRequest request;
  private List<ValueRange> values = new ArrayList<ValueRange>();

  public BatchUpdateValues() {
    this.request = new BatchUpdateValuesRequest();
    this.request.setValueInputOption("RAW");
  }

  public BatchUpdateValuesRequest getRequest() {
    return request;
  }

  public void setValueRange2Request() {
    this.request.setData(values);
  }

  public List<ValueRange> getValueRange() {
    return values;
  }

  public void setValueRange(List<ValueRange> values) {
    this.values = values;
  }

  public void add2ValueRange(ValueRange valueRange) {
    values.add(valueRange);
  }

  public void createXAxisHeader(String sheetName, List<Object> header) {
    String range = sheetName + "!B1:1";
    List<List<Object>> data = new ArrayList<List<Object>>();
    data.add(header);

    ValueRange result = new ValueRange();
    result.setMajorDimension("ROWS");
    result.setRange(range);
    result.setValues(data);
    add2ValueRange(result);
  }

  public void createYAxisHeader(String sheetName, List<Object> header) {
    String range = sheetName + "!A2:A";
    List<List<Object>> data = new ArrayList<List<Object>>();
    data.add(header);

    ValueRange result = new ValueRange();
    result.setMajorDimension("COLUMNS");
    result.setRange(range);
    result.setValues(data);
    add2ValueRange(result);
  }

  public void addExercise(SheetInstance sheet, String exercise, String input) {
      createValueRangeRequest(sheet, exercise, DateHelper.getTodaysDate(), input);
      // If exercise is not being added to the "Full Record", add it!
      if (!sheet.getTitle().equals("Full Record")) {
          createValueRangeRequest(sheet.getSpreadsheetInstance().getSheetInstance(1), exercise, DateHelper.getTodaysDate(), input);
      }
  }

  // Is the Sheet necessary? Always index(0) or "Day Counter"
  public void addLocation(SheetInstance sheet, String location) {
      createValueRangeRequest(sheet, "Checked In", DateHelper.getTodaysDate(), "X");
      createValueRangeRequest(sheet, "Location", DateHelper.getTodaysDate(), location);
  }

  public void createValueRangeRequest(SheetInstance sheet, String xAxis, String yAxis, String input) {
    // Generate range
    // Add date if it doesn't exist, separate into different function
    int xIndex = sheet.getXAxis().indexOf(xAxis);
    // Add exercise if it doesn't exist, separate into different function
    int yIndex = sheet.getYAxis().indexOf(yAxis); //Assume date is in correct format.
    String range = sheet.getTitle() + "!";
    range = range + A1Conversion.getA1XConversion(xIndex);
    range = range + A1Conversion.getA1YConversion(yIndex);

    // System.out.println("XAxis: " + sheet.getXAxis());
    // System.out.println("YAxis: " + sheet.getYAxis());
    // System.out.println("XAxis: " + xAxis + " : " + xIndex);
    // System.out.println("yAxis: " + yAxis + " : " + yIndex);
    // System.out.println("Range: " + range);
    if (xIndex == -1 || yIndex == -1) {
      System.out.println("[Error] X or Y Axis was not found. Cannot add " + input
          + ", when it's coordinates do not exist in the sheet.");
      System.exit(1);
    }
    // Generate List<List<Object>>
    List<List<Object>> data = new ArrayList<List<Object>>();
    List<Object> singleData = new ArrayList<Object>();
    singleData.add(input);
    data.add(singleData);

    // Generate ValueRange
    ValueRange result = new ValueRange();
    result.setMajorDimension("ROWS");
    result.setRange(range);
    result.setValues(data);
    add2ValueRange(result);
  }

}
