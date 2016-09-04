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

}
