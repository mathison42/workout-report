package com.seven.discs.client

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.json.JsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.*
import com.google.api.services.sheets.v4.Sheets

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Arrays
import java.util.List

import com.seven.discs.client.SheetsQuickstart

public class RunnerHelper {

  /** Application name. */
  private final String APPLICATION_NAME

  /** Directory to store user credentials for this application. */
  private final java.io.File DATA_STORE_DIR

  /** Global instance of the {@link FileDataStoreFactory}. */
  private static FileDataStoreFactory DATA_STORE_FACTORY

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance()

  /** Global instance of the HTTP transport. */
  private static HttpTransport HTTP_TRANSPORT

  /** Global instance of the scopes required by this quickstart.
  *
  * If modifying these scopes, delete your previously saved credentials
  * at ${DATA_STORE_DIR}
  */
  private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS)

    public RunnerHelper(APPLICATION_NAME, DATA_STORE_DIR) {
      this.APPLICATION_NAME = APPLICATION_NAME
      this.DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), DATA_STORE_DIR)

      try {
        this.HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        this.DATA_STORE_FACTORY = new FileDataStoreFactory(this.DATA_STORE_DIR)
      } catch (Throwable t) {
        t.printStackTrace()
        System.exit(1)
      }
    }
  //////////////////////////////////////////
  // Create Google Auth Client
  //////////////////////////////////////////
  /**
  * Creates an authorized Credential object.
  * @return an authorized Credential object.
  * @throws IOException
  */
  public Credential authorize() throws IOException {
    // Load client secrets.
    InputStream input = SheetsQuickstart.class.getResourceAsStream("/client_secret.json")
    GoogleClientSecrets clientSecrets =
      GoogleClientSecrets.load(this.JSON_FACTORY, new InputStreamReader(input))

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow =
        new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(DATA_STORE_FACTORY)
        .setAccessType("offline")
        .build()
    Credential credential = new AuthorizationCodeInstalledApp(
      flow, new LocalServerReceiver()).authorize("user")
    System.out.println(
        "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath())
    return credential
  }

  /**
  * Build and return an authorized Sheets API client service.
  * @return an authorized Sheets API client service
  * @throws IOException
  */
  public Sheets getSheetsService() throws IOException {
    Credential credential = authorize()
    return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build()
  }
}
