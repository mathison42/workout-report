import com.google.api.services.sheets.v4.model.ValueRange
import com.google.api.services.sheets.v4.Sheets

import com.seven.discs.client.SheetsAuthenticator
import com.seven.discs.client.SpreadsheetInstance
import com.seven.discs.client.SheetInstance


public class SheetsRunner {
  final static String Application_Name = "Test Runner"  // Name of your Test Application
  final static String Data_Store_Dir = ".credentials/sheets.googleapis.com-groovy-wrapper-test.json" //Location to save cookie credentials
  static Sheets service

    public static void main(String[] args) {
      println "[Start]..."
      SheetsAuthenticator sa = new SheetsAuthenticator(Application_Name, Data_Store_Dir)
      service = sa.getSheetsService()
      SpreadsheetInstance ssi = new SpreadsheetInstance(service)
      SheetInstance si = new SheetInstance(service, ssi.getSpreadsheet())
      //SheetInstance newSi = new SheetInstance(service)
      println "...[End]"
    }
}
