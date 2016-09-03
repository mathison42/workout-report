import com.google.api.services.sheets.v4.model.ValueRange
import com.google.api.services.sheets.v4.Sheets

import com.seven.discs.client.SheetsAuthenticator
import com.seven.discs.client.SpreadsheetInstance
import com.seven.discs.client.SheetInstance
import com.seven.discs.client.SheetTemplate


public class SheetsRunner {
  final static String Application_Name = "Test Runner"  // Name of your Test Application
  final static String Data_Store_Dir = ".credentials/sheets.googleapis.com-groovy-wrapper-test.json" //Location to save cookie credentials
  static Sheets service

    public static void main(String[] args) {
      println "[Start]..."
      SheetsAuthenticator sa = new SheetsAuthenticator(Application_Name, Data_Store_Dir)
      service = sa.getSheetsService()
      // SpreadsheetInstance ssi = new SpreadsheetInstance(service)
      // SheetInstance si = new SheetInstance(service, ssi.getSpreadsheet())
      // println ssi.getSheets()
      //SheetInstance newSi = new SheetInstance(service)
      println "Values: " + SheetTemplate.values();
      SheetInstance si1 = new SheetInstance(service)
      si1.updateSheet(SheetTemplate.DAY_COUNTER.getRequest())

      SheetInstance si2 = new SheetInstance(service, si1.getSpreadsheet())
      si2.createSheet(SheetTemplate.FULL_RECORD.getRequest())

      SheetInstance si3 = new SheetInstance(service, si1.getSpreadsheet())
      si3.createSheet(SheetTemplate.DAY_RECORD.getRequest())

      println "...[End]"
    }
}
