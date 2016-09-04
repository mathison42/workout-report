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

public class BatchGetRanges {

  private List<String> ranges;

  public BatchGetRanges() {
    this.ranges = new ArrayList<String>();
  }

  public List<String> getRanges() {
    return ranges;
  }

  public void addRange(String range) {
    ranges.add(range);
  }

  public void addRange(List<String> ranges) {
    ranges.addAll(ranges);
  }

  public void setRanges(List<String> ranges) {
    this.ranges = ranges;
  }

  public void getXAxisHeader(String sheetName) {
    String range = sheetName + "!1:1";
    addRange(range);
  }

  public void getYAxisHeader(String sheetName) {
    String range = sheetName + "!A:A";
    addRange(range);
  }
}
