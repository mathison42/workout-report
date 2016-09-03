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
import java.util.ArrayList;

public enum SheetTemplate {
  // Days "Checked In" Template
  DAY_COUNTER (new Request()
    .setUpdateSheetProperties(new UpdateSheetPropertiesRequest()
      .setProperties(new SheetProperties()
        .setSheetId(0)
        .setIndex(0)
        .setTitle("Day Counter")
        .setGridProperties(new GridProperties()
          .setRowCount(100)
          .setColumnCount(3)
        )
        .setTabColor(new Color()
          .setRed((float)0.0)
          .setGreen((float)0.0)
          .setBlue((float)0.0)
        )
      )
      .setFields("*")
    )
  ),
  // Full Record Template
  FULL_RECORD (new Request()
    .setAddSheet(new AddSheetRequest()
      .setProperties(new SheetProperties()
        .setSheetId(1)
        .setIndex(1)
        .setTitle("Full Record")
        .setTabColor(new Color()
          .setRed((float)1.0)
          .setGreen((float)1.0)
          .setBlue((float)1.0)
        )
      )
    )
  ),
  // Day by Day Record
  DAY_RECORD (new Request()
    .setAddSheet(new AddSheetRequest()
      .setProperties(new SheetProperties()
        .setTitle("Day Record")
        .setGridProperties(new GridProperties()
          .setRowCount(100)
        )
        .setTabColor(new Color()
          .setRed((float)0.5)
          .setGreen((float)0.5)
          .setBlue((float)0.5)
        )
      )
    )
  );

  private final Request request;

  SheetTemplate(Request request) {
    this.request = request;
  }

  public Request getRequest() {
    return request;
  }

}
